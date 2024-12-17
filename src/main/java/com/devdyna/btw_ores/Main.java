package com.devdyna.btw_ores;

import com.devdyna.btw_ores.events.BlockBreak;
import com.devdyna.btw_ores.registry.ItemsBlocks;
import com.devdyna.btw_ores.registry.Tab;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;

@Mod(Main.MODID)
public class Main {

    public static final String MODID = "btw_ores";

    public Main(IEventBus modEventBus, ModContainer modContainer) {
        Tab.register(modEventBus);
        ItemsBlocks.register(modEventBus);
        NeoForge.EVENT_BUS.register(new BlockBreak());
    }

}
