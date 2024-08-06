package dev.ash.resynced;

import net.fabricmc.api.ClientModInitializer;

public final class ResyncedModClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        Resynced.set();
    }
}