package dev.ash.resynced;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;

import java.util.HashMap;
import java.util.Map;

public final class PinglessHurtFeature {

    private final Map<Integer, Long> hurtEntityMap = new HashMap<>();

    public PinglessHurtFeature() {
        ClientTickEvents.START_CLIENT_TICK.register(client -> this.tick());
    }

    public void onAttack(Entity target) {
        this.hurtEntityMap.put(target.getId(), System.currentTimeMillis() + 1000L);
        MinecraftClient client = MinecraftClient.getInstance();
        DamageSource damageSource = target.getDamageSources().playerAttack(client.player);
        target.onDamaged(damageSource);
    }

    public void tick() {
        long now = System.currentTimeMillis();
        this.hurtEntityMap.values().removeIf(t -> t >= now);
    }

    public boolean isCancelHurt(int id) {
        return this.hurtEntityMap.containsKey(id);
    }
}
