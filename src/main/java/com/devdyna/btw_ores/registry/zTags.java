package com.devdyna.btw_ores.registry;

import com.devdyna.btw_ores.Main;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;

public class zTags {

    public static final TagKey<Block> NO_CLUSTER_GEN = createBlockTag(Main.MODID, "cannot_spawn_cluster");
    public static final TagKey<Block> NO_CLUSTER_RESULT = createBlockTag(Main.MODID, "cannot_generated_by_cluster");

    public static final TagKey<Block> ORES_IN_GROUND_END = createBlockTag("c", "ores_in_ground/end");
    public static final TagKey<Block> CLUSTERS = createBlockTag(Main.MODID, "clusters");
    public static final TagKey<Block> VALID_REGROW_STONE = createBlockTag(Main.MODID, "rocks/stone");
    public static final TagKey<Block> VALID_REGROW_DEEPSLATE = createBlockTag(Main.MODID, "rocks/deepslate");
    public static final TagKey<Block> VALID_REGROW_NETHER = createBlockTag(Main.MODID, "rocks/nether");
    public static final TagKey<Block> VALID_REGROW_END = createBlockTag(Main.MODID, "rocks/end");

    public static final TagKey<Biome> BIOME_OVERWORLD = createBiomeTag(Main.MODID, "is_overworld");
    public static final TagKey<Biome> BIOME_UNDERGROUND = createBiomeTag(Main.MODID, "is_underground");
    public static final TagKey<Biome> BIOME_NETHER = createBiomeTag(Main.MODID, "is_nether");
    public static final TagKey<Biome> BIOME_END = createBiomeTag(Main.MODID, "is_end");

    @SuppressWarnings("deprecation")
    private static TagKey<Block> createBlockTag(String modId, String path) {
        return TagKey.create(BuiltInRegistries.BLOCK.key(),
                new ResourceLocation(modId, path));
    }

    private static TagKey<Biome> createBiomeTag(String modId, String path) {
        return TagKey.create(Registries.BIOME,
                new ResourceLocation(modId, path));
    }
}
