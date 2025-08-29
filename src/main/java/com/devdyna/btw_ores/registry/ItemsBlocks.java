package com.devdyna.btw_ores.registry;

import java.util.Arrays;
import java.util.function.Supplier;

import com.devdyna.btw_ores.Main;

import com.devdyna.btw_ores.registry.builders.*;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.BlockEntityType.BlockEntitySupplier;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

@SuppressWarnings({ "unchecked", "null" })
public class ItemsBlocks {

        public static void register(IEventBus bus) {
                ITEMS.register(bus);
                BLOCKS.register(bus);
                BE.register(bus);
        }

        public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(Main.MODID);
        public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Main.MODID);
        public static final DeferredRegister<BlockEntityType<?>> BE = DeferredRegister
                        .create(BuiltInRegistries.BLOCK_ENTITY_TYPE, Main.MODID);

        public static final DeferredHolder<Block, ClusterBlock> STONE_CLUSTER_BLOCK = regClusterBlock("stone");
        public static final DeferredHolder<Block, ClusterBlock> DEEP_CLUSTER_BLOCK = regClusterBlock("deep");
        public static final DeferredHolder<Block, ClusterBlock> NETHER_CLUSTER_BLOCK = regClusterBlock("nether");
        public static final DeferredHolder<Block, ClusterBlock> END_CLUSTER_BLOCK = regClusterBlock("end");
        public static final DeferredHolder<Block, ClusterBlock> NULL_CLUSTER_BLOCK = regClusterBlock("null");

        public static final DeferredItem<BlockItem> STONE_CLUSTER_ITEM = ClusterItem(STONE_CLUSTER_BLOCK);
        public static final DeferredItem<BlockItem> DEEP_CLUSTER_ITEM = ClusterItem(DEEP_CLUSTER_BLOCK);
        public static final DeferredItem<BlockItem> NETHER_CLUSTER_ITEM = ClusterItem(NETHER_CLUSTER_BLOCK);
        public static final DeferredItem<BlockItem> END_CLUSTER_ITEM = ClusterItem(END_CLUSTER_BLOCK);
        public static final DeferredItem<BlockItem> NULL_CLUSTER_ITEM = ClusterItem(NULL_CLUSTER_BLOCK);

        public static final DeferredItem<Item> SCANNER = ITEMS.register("scanner",
                        () -> new Scanner(new Item.Properties().stacksTo(1)));

        public static final DeferredItem<Item> BLAZE_REAGENT = ITEMS.register("blaze_reagent",
                        () -> new BlazeReagent(new Item.Properties().stacksTo(16)));

        public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<ClusterBE>> CLUSTER = createBE("cluster",
                        ClusterBE::new, STONE_CLUSTER_BLOCK, DEEP_CLUSTER_BLOCK, NETHER_CLUSTER_BLOCK,
                        END_CLUSTER_BLOCK,
                        NULL_CLUSTER_BLOCK);

        public static DeferredHolder<Block, ClusterBlock> regClusterBlock(String name) {
                return BLOCKS.register(name + "_cluster", () -> new ClusterBlock(
                                BlockBehaviour.Properties.of().randomTicks().destroyTime(100).explosionResistance(100)
                                                .sound(SoundType.ANCIENT_DEBRIS),
                                name));
        }

        public static DeferredItem<BlockItem> ClusterItem(DeferredHolder<Block, ClusterBlock> block) {
                return ITEMS.registerSimpleBlockItem(block);
        }

        public static <T extends BlockEntity> DeferredHolder<BlockEntityType<?>, BlockEntityType<T>> createBE(
                        String name,
                        BlockEntitySupplier<T> factory, Supplier<? extends Block>... validBlocks) {
                return BE.register(name, () -> BlockEntityType.Builder.of(factory, Arrays.stream(validBlocks)
                                .map(Supplier::get)
                                .toArray(Block[]::new)).build(null));
        }

}
