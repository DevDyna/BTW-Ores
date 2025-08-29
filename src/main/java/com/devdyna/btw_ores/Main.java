package com.devdyna.btw_ores;

import com.devdyna.btw_ores.events.BlockBreak;
import com.devdyna.btw_ores.registry.ItemsBlocks;
import com.devdyna.btw_ores.registry.Tab;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

@Mod(Main.MODID)
public class Main {

    public static final String MODID = "btw_ores";

    public Main(IEventBus modEventBus, ModContainer modContainer) {
        Tab.register(modEventBus);
        ItemsBlocks.register(modEventBus);
        MinecraftForge.EVENT_BUS.register(new BlockBreak());
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.CONFIG_SPEC);
    }

}
