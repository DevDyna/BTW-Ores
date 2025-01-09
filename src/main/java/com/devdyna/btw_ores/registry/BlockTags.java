package com.devdyna.btw_ores.registry;

import com.devdyna.btw_ores.Main;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class BlockTags {

    public static final TagKey<Block> NO_CLUSTER_GEN = createBlockTag(Main.MODID, "cannot_spawn_cluster");
    public static final TagKey<Block> NO_CLUSTER_RESULT = createBlockTag(Main.MODID, "cannot_generated_by_cluster");
    
    public static final TagKey<Block> ORES_IN_GROUND_END = createBlockTag("c", "ores_in_ground/end");
    public static final TagKey<Block> CLUSTERS = createBlockTag(Main.MODID, "clusters");
    public static final TagKey<Block> VALID_REGROW_STONE = createBlockTag(Main.MODID, "rocks/stone");
    public static final TagKey<Block> VALID_REGROW_DEEPSLATE = createBlockTag(Main.MODID, "rocks/deepslate");
    public static final TagKey<Block> VALID_REGROW_NETHER = createBlockTag(Main.MODID, "rocks/nether");
    public static final TagKey<Block> VALID_REGROW_END = createBlockTag(Main.MODID, "rocks/end");

    private static TagKey<Block> createBlockTag(String modId, String path) {
        return TagKey.create(BuiltInRegistries.BLOCK.key(),
                ResourceLocation.fromNamespaceAndPath(modId, path));
    }
}
