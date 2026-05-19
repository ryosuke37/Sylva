package io.github.ryosuke37.sylva.common;

public class DebugModeChecker {
    public static boolean isDebug() {
        return Boolean.getBoolean("debug");
    }
}