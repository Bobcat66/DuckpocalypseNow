package io.github.duckpocalypse;

public final class Constants {
    public static class PhysicsConstants {
        public static final float TIME_STEP = 1/60f; //Standard time step
        public static final int VELOCITY_ITERATIONS = 6;
        public static final int POSITION_ITERATIONS = 2;
    }
    public static class WorldConstants {
        public static final int WORLD_WIDTH = 100;
        public static final int WORLD_HEIGHT = 100;
    }
}
