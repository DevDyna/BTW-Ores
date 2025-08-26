package com.devdyna.btw_ores.registry.builders;

import java.util.List;

import javax.annotation.Nullable;

import com.devdyna.btw_ores.Main;
import com.devdyna.btw_ores.registry.BlockTags;
import com.devdyna.btw_ores.registry.ItemsBlocks;
import com.devdyna.btw_ores.utils.LevelUtil;
import com.devdyna.btw_ores.utils.Math;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.Tags;

public class ClusterBlock extends Block {

    private String type;

    public ClusterBlock(BlockBehaviour.Properties properties, String type) {
        super(properties);
        this.type = type;
    }

    @SuppressWarnings("null")
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {

        Level level = world.getLevel();

        if (com.devdyna.btw_ores.utils.Math.chance(75))
            return;

        TagKey<Block> blockTag = null;
        TagKey<Block> validReGrow = null;

        if (LevelUtil.isDimension(world, Level.OVERWORLD) && pos.getY() >= 0
                && state.is(ItemsBlocks.STONE_CLUSTER_BLOCK.get())) {
            blockTag = Tags.Blocks.ORES_IN_GROUND_STONE;
            validReGrow = BlockTags.VALID_REGROW_STONE;
        }

        if (LevelUtil.isDimension(world, Level.OVERWORLD) && pos.getY() < 0
                && state.is(ItemsBlocks.DEEP_CLUSTER_BLOCK.get())) {
            blockTag = Tags.Blocks.ORES_IN_GROUND_DEEPSLATE;
            validReGrow = BlockTags.VALID_REGROW_DEEPSLATE;
        }

        if (LevelUtil.isDimension(world, Level.NETHER) && state.is(ItemsBlocks.NETHER_CLUSTER_BLOCK.get())) {
            blockTag = Tags.Blocks.ORES_IN_GROUND_NETHERRACK;
            validReGrow = BlockTags.VALID_REGROW_NETHER;
        }

        if (LevelUtil.isDimension(world, Level.END) && state.is(ItemsBlocks.END_CLUSTER_BLOCK.get())) {
            blockTag = BlockTags.ORES_IN_GROUND_END;
            validReGrow = BlockTags.VALID_REGROW_END;
        }

        if (blockTag != null) {

            if (LevelUtil.ValidFaces(pos, level, validReGrow) >= 3) {

                BlockState ore = LevelUtil.ResourceByTag(blockTag, Math.getRandomValue(LevelUtil.getSizeTag(blockTag)))
                        .defaultBlockState();

                if (!ore.is(BlockTags.NO_CLUSTER_RESULT))
                    world.setBlockAndUpdate(pos, ore);

            }

        }

    }

    @SuppressWarnings("null")
    @Override
    public void appendHoverText(ItemStack stack, @Nullable BlockGetter level, List<Component> context,
            TooltipFlag flag) {
        if (Screen.hasControlDown()) {
            context.add(Component.translatable(Main.MODID + "." + type + ".on"));
        } else {
            context.add(Component.translatable(Main.MODID + ".off"));
        }
    }

}
