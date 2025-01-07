package com.devdyna.btw_ores.registry;

import com.devdyna.btw_ores.Main;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ItemsBlocks {

    public static void register(IEventBus bus) {
        ITEMS.register(bus);
        BLOCKS.register(bus);
    }

    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(Main.MODID);
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Main.MODID);

    public static final DeferredBlock<Block> STONE_CLUSTER_BLOCK = regClusterBlock("stone");
    public static final DeferredBlock<Block> DEEP_CLUSTER_BLOCK = regClusterBlock("deep");
    public static final DeferredBlock<Block> NETHER_CLUSTER_BLOCK = regClusterBlock("nether");
    public static final DeferredBlock<Block> END_CLUSTER_BLOCK = regClusterBlock("end");
    public static final DeferredBlock<Block> NULL_CLUSTER_BLOCK = regClusterBlock("null");

    public static final DeferredItem<BlockItem> STONE_CLUSTER_ITEM = ClusterItem(STONE_CLUSTER_BLOCK);
    public static final DeferredItem<BlockItem> DEEP_CLUSTER_ITEM = ClusterItem(DEEP_CLUSTER_BLOCK);
    public static final DeferredItem<BlockItem> NETHER_CLUSTER_ITEM = ClusterItem(NETHER_CLUSTER_BLOCK);
    public static final DeferredItem<BlockItem> END_CLUSTER_ITEM = ClusterItem(END_CLUSTER_BLOCK);
    public static final DeferredItem<BlockItem> NULL_CLUSTER_ITEM = ClusterItem(NULL_CLUSTER_BLOCK);

    public static DeferredBlock<Block> regClusterBlock(String name) {
        return BLOCKS.register(name + "_cluster", () -> new ClusterBlock(
                BlockBehaviour.Properties.of().randomTicks().destroyTime(100).explosionResistance(100)
                        .sound(SoundType.ANCIENT_DEBRIS).noLootTable(),name));
    }

    public static DeferredItem<BlockItem> ClusterItem(DeferredBlock<Block> block) {
        return ITEMS.registerSimpleBlockItem(block);
    }
}
