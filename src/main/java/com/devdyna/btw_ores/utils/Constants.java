package com.devdyna.btw_ores.utils;

import com.devdyna.btw_ores.registry.ItemsBlocks;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class Constants {
        public static final int STONE_OVERWORLD = 0;
        public static final int DEEPSLATE_OVERWORLD = 1;
        public static final int NETHER = 2;
        public static final int THE_END = 3;
        public static final int OTHER = 4;

        public static final Block[] AllBlocks = {
                        ItemsBlocks.STONE_CLUSTER_BLOCK.get(),
                        ItemsBlocks.DEEP_CLUSTER_BLOCK.get(),
                        ItemsBlocks.NETHER_CLUSTER_BLOCK.get(),
                        ItemsBlocks.END_CLUSTER_BLOCK.get(),
                        ItemsBlocks.NULL_CLUSTER_BLOCK.get(),
        };

        public static final Item[] AllItems = {
                        ItemsBlocks.SCANNER.get()
        };
}
