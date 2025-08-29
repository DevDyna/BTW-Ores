package com.devdyna.btw_ores.registry;

import com.devdyna.btw_ores.Main;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class Tab {

    public static void register(IEventBus bus) {
        CREATIVE_MODE_TABS.register(bus);
    }

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister
            .create(Registries.CREATIVE_MODE_TAB, Main.MODID);
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> CLUSTER_TAB = CREATIVE_MODE_TABS
            .register(Main.MODID + "_tab", () -> CreativeModeTab.builder()
                    .title(Component.translatable(Main.MODID + ".tab"))
                    .withTabsBefore(CreativeModeTabs.COMBAT)
                    .icon(() -> ItemsBlocks.SCANNER.get().getDefaultInstance())
                    .displayItems((parameters, output) -> {

                        ItemsBlocks.ITEMS.getEntries().forEach(e -> {
                                                        output.accept((Item) e.get());
                                                });

                    }).build());
}
