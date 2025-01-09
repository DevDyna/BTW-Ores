package com.devdyna.btw_ores.events;

import java.util.List;

import com.devdyna.btw_ores.registry.BlockTags;
import com.devdyna.btw_ores.registry.ItemsBlocks;
import com.devdyna.btw_ores.utils.EnchantUtil;
import com.devdyna.btw_ores.utils.LevelUtil;
import com.devdyna.btw_ores.utils.LootTableUtil;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.event.level.BlockEvent;

public class BlockBreak {

    @SubscribeEvent
    public void BlockBreakEvent(BlockEvent.BreakEvent event) {

        LevelAccessor levelAccessor = event.getLevel();
        BlockPos pos = event.getPos();
        Player player = event.getPlayer();
        BlockState state = event.getState();
        String raw_ore_name = state.getBlock().getDescriptionId();
        Block cluster = ItemsBlocks.NULL_CLUSTER_BLOCK.get();

        if (state.is(Tags.Blocks.ORES) && !state.is(BlockTags.NO_CLUSTER_GEN)
                && player.getMainHandItem()
                        .getEnchantmentLevel(
                                EnchantUtil.getEnchantHolder(levelAccessor, Enchantments.SILK_TOUCH)) == 0) {

            if (LevelUtil.isDimension((Level) levelAccessor, Level.OVERWORLD) && pos.getY() >= 0
                    && state.is(Tags.Blocks.ORES_IN_GROUND_STONE))
                cluster = ItemsBlocks.STONE_CLUSTER_BLOCK.get();

            if (LevelUtil.isDimension((Level) levelAccessor, Level.OVERWORLD) && pos.getY() < 0
                    && state.is(Tags.Blocks.ORES_IN_GROUND_DEEPSLATE))
                cluster = ItemsBlocks.DEEP_CLUSTER_BLOCK.get();

            if (LevelUtil.isDimension((Level) levelAccessor, Level.NETHER)
                    && state.is(Tags.Blocks.ORES_IN_GROUND_NETHERRACK))
                cluster = ItemsBlocks.NETHER_CLUSTER_BLOCK.get();

            if (LevelUtil.isDimension((Level) levelAccessor, Level.END) && state.is(BlockTags.ORES_IN_GROUND_END))
                cluster = ItemsBlocks.END_CLUSTER_BLOCK.get();

            for (int i = 0; i < com.devdyna.btw_ores.utils.Math.getRandomValue(player.getMainHandItem()
                    .getEnchantmentLevel(EnchantUtil.getEnchantHolder(levelAccessor, Enchantments.FORTUNE))); i++) {

                List<ItemStack> list = LootTableUtil.getItemStackFromLootTable(levelAccessor, player,
                        raw_ore_name);

                for (ItemStack itemStack : list) {
                    ItemEntity itementity = new ItemEntity((Level) levelAccessor,
                            pos.getX(),
                            pos.getY(),
                            pos.getZ(),
                            itemStack);
                    levelAccessor.addFreshEntity(itementity);
                }
                event.setCanceled(true);
            }
            LevelUtil.SimplePlaceBlock((Level) levelAccessor, pos, cluster);
        }

    }

}
