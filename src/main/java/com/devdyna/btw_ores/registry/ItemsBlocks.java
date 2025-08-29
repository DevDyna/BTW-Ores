package com.devdyna.btw_ores.registry;

import java.util.Arrays;
import java.util.function.Supplier;

import com.devdyna.btw_ores.Main;

import com.devdyna.btw_ores.registry.builders.*;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.BlockEntityType.BlockEntitySupplier;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@SuppressWarnings({ "unchecked", "null" })
public class ItemsBlocks {

        public static void register(IEventBus bus) {
                ITEMS.register(bus);
                BLOCKS.register(bus);
                BE.register(bus);
        }

        public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS,
                        Main.MODID);
        public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Main.MODID);
        public static final DeferredRegister<BlockEntityType<?>> BE = DeferredRegister
                        .create(ForgeRegistries.BLOCK_ENTITY_TYPES, Main.MODID);

        public static final RegistryObject<ClusterBlock> STONE_CLUSTER_BLOCK = regClusterBlock("stone");
        public static final RegistryObject<ClusterBlock> DEEP_CLUSTER_BLOCK = regClusterBlock("deep");
        public static final RegistryObject<ClusterBlock> NETHER_CLUSTER_BLOCK = regClusterBlock("nether");
        public static final RegistryObject<ClusterBlock> END_CLUSTER_BLOCK = regClusterBlock("end");
        public static final RegistryObject<ClusterBlock> NULL_CLUSTER_BLOCK = regClusterBlock("null");

        public static final RegistryObject<Item> SCANNER = ITEMS.register("scanner",
                        () -> new Scanner(new Item.Properties().stacksTo(1)));

        public static final RegistryObject<Item> BLAZE_REAGENT = ITEMS.register("blaze_reagent",
                        () -> new BlazeReagent(new Item.Properties().stacksTo(16)));

        public static final RegistryObject<BlockEntityType<?>> CLUSTER = createBE("cluster",
                        ClusterBE::new, STONE_CLUSTER_BLOCK, DEEP_CLUSTER_BLOCK, NETHER_CLUSTER_BLOCK,
                        END_CLUSTER_BLOCK,
                        NULL_CLUSTER_BLOCK);

        public static RegistryObject<ClusterBlock> regClusterBlock(String name) {

                var block = BLOCKS.register(name + "_cluster", () -> new ClusterBlock(
                                BlockBehaviour.Properties.of().randomTicks().destroyTime(100).explosionResistance(100)
                                                .sound(SoundType.ANCIENT_DEBRIS),
                                name));

                ITEMS.register(name + "_cluster", () -> new BlockItem(block.get(), new Properties()));

                return block;
        }

        public static <T extends BlockEntity> RegistryObject<BlockEntityType<?>> createBE(
                        String name,
                        BlockEntitySupplier<T> factory, Supplier<? extends Block>... validBlocks) {
                return BE.register(name, () -> BlockEntityType.Builder.of(factory, Arrays.stream(validBlocks)
                                .map(Supplier::get)
                                .toArray(Block[]::new)).build(null));
        }

}
