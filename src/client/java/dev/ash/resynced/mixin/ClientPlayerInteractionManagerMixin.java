package dev.ash.resynced.mixin;

import dev.ash.resynced.PinglessHurtFeature;
import dev.ash.resynced.Resynced;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameMode;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientPlayerInteractionManager.class)
public abstract class ClientPlayerInteractionManagerMixin {

    @Shadow
    private BlockPos currentBreakingPos;
    @Shadow
    private ItemStack selectedStack;

    @Shadow
    private GameMode gameMode;

    @Shadow public abstract void pickFromInventory(int slot);

    @Inject(method = "isCurrentlyBreaking", at = @At("HEAD"), cancellable = true)
    private void isCurrentlyBreaking(BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null) {
            return;
        }
        ItemStack itemStack = client.player.getMainHandStack(); // TODO Will cause issues with differing components, need to remove JUST the durability condition
        cir.setReturnValue(pos.equals(this.currentBreakingPos) && this.selectedStack.isOf(itemStack.getItem()));
    }

    @Inject(method = "attackEntity", at = @At("HEAD"))
    private void attackEntity(PlayerEntity player, Entity target, CallbackInfo info) {
        if (player instanceof ClientPlayerEntity) {
            PinglessHurtFeature pinglessHurtFeature = Resynced.get().getPinglessHurtFeature();
            pinglessHurtFeature.onAttack(target);
        }
    }
}
