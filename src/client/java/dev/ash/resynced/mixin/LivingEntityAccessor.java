package dev.ash.resynced.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.TrackedData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(LivingEntity.class)
public interface LivingEntityAccessor {
    @Accessor("LIVING_FLAGS")
    static TrackedData<Byte> getTrackedLivingFlags() {
        throw new AssertionError();
    }
}
