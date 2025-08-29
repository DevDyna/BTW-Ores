package com.devdyna.btw_ores;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.*;

public class Config {
        private static final ForgeConfigSpec.Builder qCOMMON = new ForgeConfigSpec.Builder();

        public static BooleanValue GENERATE_NULL = qCOMMON.comment("Generate Null Clusters when condition fails")
                        .define("cluster_null", true);

        public static BooleanValue RANDOM_CLUSTERS = qCOMMON
                        .comment("Change static regeneration of cluster to be random")
                        .define("cluster_random_regen", false);

        public static BooleanValue CORRUPTED_CLUSTERS = qCOMMON
                        .comment("Corrupted Cluster will replaced with Air to prevent Crash")
                        .define("cluster_corrupted_crash_safety", true);

        public static IntValue YLEVEL_GROUND_STONE = qCOMMON
                        .comment("Y level to define when end Overworld level (stone)")
                        .defineInRange("cluster_dimension_y_stone", 0, Integer.MIN_VALUE, Integer.MAX_VALUE);

        public static IntValue YLEVEL_GROUND_DEEPSLATE = qCOMMON
                        .comment("Y level to define when start Underground level (deepslate)")
                        .defineInRange("cluster_dimension_y_deepslate", 0, Integer.MIN_VALUE,
                                        Integer.MAX_VALUE);

        static ForgeConfigSpec CONFIG_SPEC = qCOMMON.build();

}
