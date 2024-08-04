package dev.ash.resynced.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientPlayerInteractionManager.class)
public final class ClientPlayerInteractionManagerMixin {

    @Shadow
    private BlockPos currentBreakingPos;
    @Shadow
    private ItemStack selectedStack;

    @Inject(method = "isCurrentlyBreaking", at = @At("HEAD"), cancellable = true)
    private void isCurrentlyBreaking(BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null) {
            return;
        }
        ItemStack itemStack = client.player.getMainHandStack(); // TODO Will cause issues with differing components, need to remove JUST the durability condition
        cir.setReturnValue(pos.equals(this.currentBreakingPos) && this.selectedStack.isOf(itemStack.getItem()));
    }
}
