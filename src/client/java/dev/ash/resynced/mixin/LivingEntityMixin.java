package dev.ash.resynced.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(LivingEntity.class)
public final class LivingEntityMixin {
    @Redirect(method = "tickItemStackUsage", at = @At(value = "FIELD", target = "Lnet/minecraft/world/World;isClient:Z", opcode = Opcodes.GETFIELD))
    private boolean isClient(World world) {
        return false;
    }
}
