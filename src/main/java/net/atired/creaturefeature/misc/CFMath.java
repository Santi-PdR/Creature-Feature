package net.atired.creaturefeature.misc;

/** Compatibility helpers for Java 17, used by the Forge 1.20.1 port. */
public final class CFMath {
    private CFMath() {}

    public static float clamp(float value, float min, float max) {
        return Math.max(min, Math.min(max, value));
    }

    public static double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }

    public static int clamp(int value, int min, int max) {
        return Math.max(min, Math.min(max, value));
    }
}
