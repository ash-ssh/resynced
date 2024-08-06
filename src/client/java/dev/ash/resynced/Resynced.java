package dev.ash.resynced;

public final class Resynced {

    private final PinglessHurtFeature pinglessHurtFeature;

    private Resynced() {
        // TODO Feature manager
        this.pinglessHurtFeature = new PinglessHurtFeature();
    }

    public PinglessHurtFeature getPinglessHurtFeature() {
        return pinglessHurtFeature;
    }

    private static Resynced instance;

    static void set() {
        if (instance != null) {
            throw new RuntimeException("Singleton already initialized!");
        }
        instance = new Resynced();
    }

    public static Resynced get() {
        return instance;
    }
}
