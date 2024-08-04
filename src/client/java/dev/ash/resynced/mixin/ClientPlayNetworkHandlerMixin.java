package dev.ash.resynced.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.packet.s2c.play.EntityTrackerUpdateS2CPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayNetworkHandler.class)
public final class ClientPlayNetworkHandlerMixin {
    @Inject(method = "onEntityTrackerUpdate", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/data/DataTracker;writeUpdatedEntries(Ljava/util/List;)V", shift = At.Shift.BEFORE))
    private void onEntityTrackerUpdate(EntityTrackerUpdateS2CPacket packet, CallbackInfo info) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player != null && packet.id() == client.player.getId()) {
            packet.trackedValues().removeIf(entry -> isBadId(entry.id()));
        }
    }

    private static boolean isBadId(int id) {
        return id == EntityAccessor.getTrackedPose().id() || id == LivingEntityAccessor.getTrackedLivingFlags().id();
    }
}
