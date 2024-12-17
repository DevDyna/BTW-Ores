package com.devdyna.btw_ores.registry;

import com.devdyna.btw_ores.Main;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ItemsBlocks {

    ItemsBlocks() {
    }

    public static void register(IEventBus bus) {
        ITEMS.register(bus);
        BLOCKS.register(bus);
    }

    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(Main.MODID);
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Main.MODID);

    public static final DeferredBlock<Block> STONE_CLUSTER_BLOCK = ClusterBlock("stone");
    public static final DeferredBlock<Block> DEEP_CLUSTER_BLOCK = ClusterBlock("deep");
    public static final DeferredBlock<Block> NETHER_CLUSTER_BLOCK = ClusterBlock("nether");
    public static final DeferredBlock<Block> END_CLUSTER_BLOCK = ClusterBlock("end");
    public static final DeferredBlock<Block> NULL_CLUSTER_BLOCK = ClusterBlock("null");

    

    public static final DeferredItem<BlockItem> STONE_CLUSTER_ITEM = ClusterItem(STONE_CLUSTER_BLOCK);
    public static final DeferredItem<BlockItem> DEEP_CLUSTER_ITEM = ClusterItem( DEEP_CLUSTER_BLOCK);
    public static final DeferredItem<BlockItem> NETHER_CLUSTER_ITEM = ClusterItem( NETHER_CLUSTER_BLOCK);
    public static final DeferredItem<BlockItem> END_CLUSTER_ITEM = ClusterItem( END_CLUSTER_BLOCK);
    public static final DeferredItem<BlockItem> NULL_CLUSTER_ITEM = ClusterItem( NULL_CLUSTER_BLOCK);

    public static DeferredBlock<Block> ClusterBlock(String name) {
        return BLOCKS.registerSimpleBlock( name+"_cluster",
                BlockBehaviour.Properties.of().destroyTime(10).explosionResistance(100));
    }


    public static DeferredItem<BlockItem> ClusterItem(DeferredBlock<Block> block ) {
        return ITEMS.registerSimpleBlockItem(block);
    }
}
