package com.devdyna.btw_ores.events;

import java.util.List;

import com.devdyna.btw_ores.Utils;
import com.devdyna.btw_ores.registry.ItemsBlocks;

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
import net.neoforged.neoforge.event.level.BlockEvent.BreakEvent;
import net.neoforged.neoforge.registries.DeferredBlock;

public class BlockBreak {

    @SubscribeEvent
    public void BlockBreakEvent(BlockEvent.BreakEvent event) {

        LevelAccessor levelAccessor = event.getLevel();
        BlockPos pos = event.getPos();
        Player player = event.getPlayer();
        BlockState state = event.getState();
        String raw_ore_name = state.getBlock().getDescriptionId();

        if (state.is(Tags.Blocks.ORES) && !state.is(com.devdyna.btw_ores.registry.AnyTags.BLACKLISTED_ORES)
                && player.getMainHandItem()
                        .getEnchantmentLevel(Utils.getEnchantHolder(levelAccessor, Enchantments.SILK_TOUCH)) == 0) {

            // ore broken on overworld with stone tag over 0
            if (Utils.isDimension(player, Level.OVERWORLD) && state.is(Tags.Blocks.ORES_IN_GROUND_STONE)
                    && pos.getY() >= 0) {
                Utils.SimplePlaceBlock(levelAccessor, pos, ItemsBlocks.STONE_CLUSTER_BLOCK);
                DropItems(event, raw_ore_name, ItemsBlocks.STONE_CLUSTER_BLOCK);

            }
            // ore broken on overworld with deepslate tag below 0
            if (Utils.isDimension(player, Level.OVERWORLD) && state.is(Tags.Blocks.ORES_IN_GROUND_DEEPSLATE)
                    && pos.getY() < 0) {
                DropItems(event, raw_ore_name, ItemsBlocks.DEEP_CLUSTER_BLOCK);

            }
            // ore broken on nether with nether tag
            if (Utils.isDimension(player, Level.NETHER) && state.is(Tags.Blocks.ORES_IN_GROUND_NETHERRACK)) {
                DropItems(event, raw_ore_name, ItemsBlocks.NETHER_CLUSTER_BLOCK);
            }
            // ore broken on end with custom end tag with "end" without "nether" & "stone" &
            // "deepslate"
            if (Utils.isDimension(player, Level.END)
                    && state.is(com.devdyna.btw_ores.registry.AnyTags.ORES_IN_GROUND_END)
                    && raw_ore_name.contains("end")
                    && !raw_ore_name.contains("nether") && !raw_ore_name.contains("deepslate")
                    && !raw_ore_name.contains("stone")) {
                DropItems(event, raw_ore_name, ItemsBlocks.END_CLUSTER_BLOCK);
            }
            // ore broken on other dimension
            if (!Utils.isDimension(player, Level.OVERWORLD) && !Utils.isDimension(player, Level.NETHER)
                    && !Utils.isDimension(player, Level.END)) {
                DropItems(event, raw_ore_name, ItemsBlocks.NULL_CLUSTER_BLOCK);
            }
        }

    }

    private void DropItems(BreakEvent event, String raw_ore_name, DeferredBlock<Block> block) {
        for (int i = 0; i < Utils.getRandomValue(event.getPlayer().getMainHandItem()
                .getEnchantmentLevel(Utils.getEnchantHolder(event.getLevel(), Enchantments.FORTUNE))); i++) {

            List<ItemStack> list = Utils.getItemStackFromLootTable(event.getLevel(), event.getPlayer(), raw_ore_name);

            for (ItemStack itemStack : list) {
                ItemEntity itementity = new ItemEntity((net.minecraft.world.level.Level) event.getLevel(),
                        event.getPos().getX(),
                        event.getPos().getY(),
                        event.getPos().getZ(),
                        itemStack);
                event.getLevel().addFreshEntity(itementity);
            }
            event.setCanceled(true);
        }
        Utils.SimplePlaceBlock(event.getLevel(), event.getPos(), block);
    }

}
