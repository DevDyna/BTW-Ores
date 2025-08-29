package com.devdyna.btw_ores.events;

import com.devdyna.btw_ores.Config;
import com.devdyna.btw_ores.api.ClusterApi;
import com.devdyna.btw_ores.registry.zTags;
import com.devdyna.btw_ores.registry.ItemsBlocks;

import net.minecraft.world.level.Level;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class BlockBreak {

    @SubscribeEvent
    public void BlockBreakEvent(BlockEvent.BreakEvent event) {

        var level = (Level) event.getLevel();
        var pos = event.getPos();
        var player = event.getPlayer();
        var state = event.getState();
        if (level.isClientSide)
            return;

        var cluster = ClusterApi.getClusterFromOre(level, state, pos,
                player.getMainHandItem());

        if (cluster == null)
            return;

        if (cluster.defaultBlockState().is(ItemsBlocks.NULL_CLUSTER_BLOCK.get()) && !Config.GENERATE_NULL.get())
            return;

        if (!state.is(zTags.NO_CLUSTER_GEN)) {
            ClusterApi.dropItems(state, player.getMainHandItem(), level, pos, player);
            event.setCanceled(true);
            ClusterApi.consumeDurability(player);
            ClusterApi.createBEandApplyTag(level, pos, state, cluster);
        }

    }

}
