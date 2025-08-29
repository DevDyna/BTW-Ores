package com.devdyna.btw_ores;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.*;
import net.minecraftforge.fml.ModContainer;

public class Config {
        private static final ForgeConfigSpec.Builder qCOMMON = new ForgeConfigSpec.Builder();

        static ForgeConfigSpec CONFIG_SPEC;

        public static BooleanValue GENERATE_NULL;
        public static BooleanValue RANDOM_CLUSTERS;
        public static BooleanValue CORRUPTED_CLUSTERS;
        public static IntValue YLEVEL_GROUND_STONE;
        public static IntValue YLEVEL_GROUND_DEEPSLATE;

        public static void register(ModContainer c) {
                GENERATE_NULL = qCOMMON.comment("Generate Null Clusters when condition fails")
                                .define("cluster_null", true);

                RANDOM_CLUSTERS = qCOMMON.comment("Change static regeneration of cluster to be random")
                                .define("cluster_random_regen", false);

                CORRUPTED_CLUSTERS = qCOMMON.comment("Corrupted Cluster will replaced with Air to prevent Crash")
                                .define("cluster_corrupted_crash_safety", true);

                YLEVEL_GROUND_STONE = qCOMMON.comment("Y level to define when end Overworld level (stone)")
                                .defineInRange("cluster_dimension_y_stone", 0, Integer.MIN_VALUE, Integer.MAX_VALUE);

                YLEVEL_GROUND_DEEPSLATE = qCOMMON.comment("Y level to define when start Underground level (deepslate)")
                                .defineInRange("cluster_dimension_y_deepslate", 0, Integer.MIN_VALUE,
                                                Integer.MAX_VALUE);

                CONFIG_SPEC = qCOMMON.build();
        }

}
