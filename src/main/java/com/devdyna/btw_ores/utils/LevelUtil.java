package com.devdyna.btw_ores.utils;

import java.util.List;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

public class LevelUtil {
    public static boolean isDimension(Level level, ResourceKey<Level> dim) {
        return level.dimension().equals(dim);
    }

    public static void SimplePlaceBlock(Level level, BlockPos pos, Block block) {
        level.setBlock(pos, block.defaultBlockState(), 32);
    }

    public static int ValidFaces(BlockPos pos, Level level, TagKey<Block> tag) {
        BlockPos[] dir = { pos.above(), pos.below(), pos.north(), pos.south(), pos.east(), pos.west() };
        int value = 0;
        for (BlockPos face : dir) {
            value += level.getBlockState(face).is(tag) ? 1 : 0;
        }
        return value;
    }

    public static List<Holder<Block>> ResourceByTag(TagKey<Block> tag) {
        return BuiltInRegistries.BLOCK.getOrCreateTag(tag).stream().toList();
    }

    public static Block ResourceByTag(TagKey<Block> tag, int index) {
        return ResourceByTag(tag).get(index).value();
    }

    public static int getSizeTag(TagKey<Block> tag) {
        return ResourceByTag(tag).size()-1;
    }

}
