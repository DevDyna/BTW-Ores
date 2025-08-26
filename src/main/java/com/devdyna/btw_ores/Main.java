package com.devdyna.btw_ores;

import com.devdyna.btw_ores.events.BlockBreak;
import com.devdyna.btw_ores.registry.ItemsBlocks;
import com.devdyna.btw_ores.registry.Tab;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Main.MODID)
public class Main {

    public static final String MODID = "btw_ores";

    public Main() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        // item reg
        ItemsBlocks.register(modEventBus);
        // events
        MinecraftForge.EVENT_BUS.register(new BlockBreak());
        // tab reg
        Tab.register(modEventBus);
    }

}
