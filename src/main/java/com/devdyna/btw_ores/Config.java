package com.devdyna.btw_ores;

import net.neoforged.fml.ModContainer;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.common.ModConfigSpec.*;

public class Config {
    private static final ModConfigSpec.Builder qCOMMON = new ModConfigSpec.Builder();

    public static BooleanValue RANDOM_CLUSTERS;
    public static BooleanValue CORRUPTED_CLUSTERS;
    public static IntValue YLEVEL_GROUND_STONE;
    public static IntValue YLEVEL_GROUND_DEEPSLATE;

    public static void register(ModContainer c) {
        RANDOM_CLUSTERS = qCOMMON.comment("Change static regeneration of cluster to be random")
                .define("cluster_random_regen", false);

        CORRUPTED_CLUSTERS = qCOMMON.comment("Corrupted Cluster will replaced with Air to prevent Crash")
                .define("cluster_corrupted_crash_safety", true);

        YLEVEL_GROUND_STONE = qCOMMON.comment("Y level to define when end Overworld level (stone)")
                .defineInRange("cluster_dimension_y_stone", 0, Integer.MIN_VALUE, Integer.MAX_VALUE);

        YLEVEL_GROUND_DEEPSLATE = qCOMMON.comment("Y level to define when start Underground level (deepslate)")
                .defineInRange("cluster_dimension_y_deepslate", 0, Integer.MIN_VALUE, Integer.MAX_VALUE);

        c.registerConfig(ModConfig.Type.COMMON, qCOMMON.build());
    }

}
