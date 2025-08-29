package com.devdyna.btw_ores.api;

import java.util.List;

import com.devdyna.btw_ores.Config;
import com.devdyna.btw_ores.registry.zTags;
import com.devdyna.btw_ores.registry.ItemsBlocks;
import com.devdyna.btw_ores.registry.builders.ClusterBlock;
import com.devdyna.btw_ores.utils.EnchantUtil;
import com.devdyna.btw_ores.utils.LevelUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.Tags;

@SuppressWarnings("null")
public class ClusterApi {

    protected static int Ystone = Config.YLEVEL_GROUND_STONE.get();
    protected static int Ydeepslate = Config.YLEVEL_GROUND_DEEPSLATE.get();

    protected static List<TagKey<Block>> regrowRocks = List.of(
            zTags.VALID_REGROW_STONE,
            zTags.VALID_REGROW_DEEPSLATE,
            zTags.VALID_REGROW_NETHER,
            zTags.VALID_REGROW_END);

    protected static List<TagKey<Block>> randomOresList = List.of(
            Tags.Blocks.ORES_IN_GROUND_STONE,
            Tags.Blocks.ORES_IN_GROUND_DEEPSLATE,
            Tags.Blocks.ORES_IN_GROUND_NETHERRACK,
            zTags.ORES_IN_GROUND_END);

    public static ClusterBlock getClusterFromOre(Level level, BlockState state, BlockPos pos, ItemStack pickaxe) {

        if (!state.is(Tags.Blocks.ORES) || state.is(zTags.NO_CLUSTER_GEN)
                || pickaxe
                        .getEnchantmentLevel(
                                EnchantUtil.getEnchantHolder(level, Enchantments.SILK_TOUCH)) != 0)
            return null;

        if (LevelUtil.isBiome(level, pos, zTags.BIOME_OVERWORLD) && pos.getY() >= Ystone
                && state.is(Tags.Blocks.ORES_IN_GROUND_STONE))
            return ItemsBlocks.STONE_CLUSTER_BLOCK.get();

        if (LevelUtil.isBiome(level, pos, zTags.BIOME_UNDERGROUND) && pos.getY() < Ydeepslate
                && state.is(Tags.Blocks.ORES_IN_GROUND_DEEPSLATE))
            return ItemsBlocks.DEEP_CLUSTER_BLOCK.get();

        if (LevelUtil.isBiome(level, pos, zTags.BIOME_NETHER)
                && state.is(Tags.Blocks.ORES_IN_GROUND_NETHERRACK))
            return ItemsBlocks.NETHER_CLUSTER_BLOCK.get();

        if (LevelUtil.isBiome(level, pos, zTags.BIOME_END) && state.is(zTags.ORES_IN_GROUND_END))
            return ItemsBlocks.END_CLUSTER_BLOCK.get();

        return ItemsBlocks.NULL_CLUSTER_BLOCK.get();

    }

    public static int getIndex(BlockState state, BlockPos pos, Level level) {

        if (LevelUtil.isBiome(level, pos, zTags.BIOME_OVERWORLD) && pos.getY() >= Ystone
                && state.is(ItemsBlocks.STONE_CLUSTER_BLOCK)) {
            return 0;

        }

        if (LevelUtil.isBiome(level, pos, zTags.BIOME_UNDERGROUND) && pos.getY() < Ydeepslate
                && state.is(ItemsBlocks.DEEP_CLUSTER_BLOCK)) {
            return 1;

        }

        if (LevelUtil.isBiome(level, pos, zTags.BIOME_NETHER) && state.is(ItemsBlocks.NETHER_CLUSTER_BLOCK)) {
            return 2;

        }

        if (LevelUtil.isBiome(level, pos, zTags.BIOME_END) && state.is(ItemsBlocks.END_CLUSTER_BLOCK)) {
            return 3;

        }
        return -1;

    }

    public static TagKey<Block> getOreTag(int index) {
        if (index == -1)
            return null;
        else
            return randomOresList.get(index);
    }

    /**
     * return regrow tag based on ClusterBlock
     */
    public static TagKey<Block> getTagRegrow(int index) {
        if (index == -1)
            return null;
        else
            return regrowRocks.get(index);

    }

    /**
     * generate items to drop based on block and pickaxe
     */
    public static void dropItems(BlockState state, ItemStack pickaxe, Level level, BlockPos pos, Player player) {
        // if(!player.isCreative())
        for (int i = 0; i < LevelUtil.getRandomValue(pickaxe
                .getEnchantmentLevel(EnchantUtil.getEnchantHolder(level, Enchantments.FORTUNE)), level); i++) {

            var list = Block.getDrops(state, (ServerLevel) level, pos, null);

            for (ItemStack itemStack : list) {
                var itementity = new ItemEntity((Level) level,
                        pos.getX(),
                        pos.getY(),
                        pos.getZ(),
                        itemStack);
                level.addFreshEntity(itementity);
            }
        }
    }

    /**
     * consume durability or pickaxe
     */
    public static void consumeDurability(Player player) {
        var item = player.getMainHandItem();
        if (!player.isCreative()) {
            if (item.getMaxDamage() - item.getDamageValue() == 1) {
                item.shrink(1);
                player.playSound(SoundEvents.ITEM_BREAK);
            } else
                item.setDamageValue(item.getDamageValue() + 1);
        }
    }

    public static void createBEandApplyTag(Level level, BlockPos pos, BlockState state, ClusterBlock cluster) {
        level.setBlock(pos, cluster.defaultBlockState(), 3);
        var be = level.getBlockEntity(pos);
        var nbt = be.saveWithFullMetadata(level.registryAccess());
        nbt.putString("oreBroken", state.getBlock().toString().replace("Block{", "").replace("}", ""));
        be.loadWithComponents(nbt, level.registryAccess());
        be.setChanged();
    }

}
