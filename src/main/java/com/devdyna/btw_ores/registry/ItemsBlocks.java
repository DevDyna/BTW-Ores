package com.devdyna.btw_ores.registry;

import com.devdyna.btw_ores.Main;
import com.devdyna.btw_ores.registry.builders.ClusterBlock;
import com.devdyna.btw_ores.registry.builders.Scanner;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ItemsBlocks {

    public static void register(IEventBus bus) {
        ITEMS.register(bus);
        BLOCKS.register(bus);
    }

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Main.MODID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Main.MODID);

    public static final RegistryObject<ClusterBlock> STONE_CLUSTER_BLOCK = regClusterBlock("stone");
    public static final RegistryObject<ClusterBlock> DEEP_CLUSTER_BLOCK = regClusterBlock("deep");
    public static final RegistryObject<ClusterBlock> NETHER_CLUSTER_BLOCK = regClusterBlock("nether");
    public static final RegistryObject<ClusterBlock> END_CLUSTER_BLOCK = regClusterBlock("end");
    public static final RegistryObject<ClusterBlock> NULL_CLUSTER_BLOCK = regClusterBlock("null");

    public static final RegistryObject<Item> SCANNER = ITEMS.register("scanner",
            () -> new Scanner(new Item.Properties()));

    public static RegistryObject<ClusterBlock> regClusterBlock(String name) {

        var block = BLOCKS.register(name + "_cluster", () -> new ClusterBlock(
                BlockBehaviour.Properties.of().randomTicks().destroyTime(100).explosionResistance(100)
                        .sound(SoundType.ANCIENT_DEBRIS),
                name));

        ITEMS.register(name + "_cluster", () -> new BlockItem(block.get(), new Properties()));
        
        return block;
    }

}
