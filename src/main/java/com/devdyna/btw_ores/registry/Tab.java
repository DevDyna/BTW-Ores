package com.devdyna.btw_ores.registry;

import com.devdyna.btw_ores.Main;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class Tab {

    Tab() {
    }

    public static void register(IEventBus bus) {
        CREATIVE_MODE_TABS.register(bus);
    }

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister
            .create(Registries.CREATIVE_MODE_TAB, Main.MODID);
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> CLUSTER_TAB = CREATIVE_MODE_TABS
            .register("cluster_tab", () -> CreativeModeTab.builder()
                    .title(Component.translatable(Main.MODID + ".cluster.tab"))
                    .withTabsBefore(CreativeModeTabs.COMBAT)
                    .icon(() -> ItemsBlocks.STONE_CLUSTER_ITEM.get().getDefaultInstance())
                    .displayItems((parameters, output) -> {
                        output.accept(ItemsBlocks.STONE_CLUSTER_BLOCK.get());
                        output.accept(ItemsBlocks.DEEP_CLUSTER_BLOCK.get());
                        output.accept(ItemsBlocks.NETHER_CLUSTER_BLOCK.get());
                        output.accept(ItemsBlocks.END_CLUSTER_BLOCK.get());
                        output.accept(ItemsBlocks.NULL_CLUSTER_BLOCK.get());
                    }).build());
}
