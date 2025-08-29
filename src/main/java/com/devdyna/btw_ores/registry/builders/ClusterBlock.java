package com.devdyna.btw_ores.registry.builders;

import java.util.List;

import javax.annotation.Nullable;

import com.devdyna.btw_ores.Config;
import com.devdyna.btw_ores.Main;
import com.devdyna.btw_ores.api.ClusterApi;
import com.devdyna.btw_ores.registry.zTags;
import com.devdyna.btw_ores.registry.ItemsBlocks;
import com.devdyna.btw_ores.utils.LevelUtil;
import com.mojang.logging.LogUtils;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

@SuppressWarnings("null")
public class ClusterBlock extends Block implements EntityBlock {

    private String type;

    public ClusterBlock(BlockBehaviour.Properties properties, String type) {
        super(properties);
        this.type = type;
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {

        if (LevelUtil.chance(75, level) || state.is(ItemsBlocks.NULL_CLUSTER_BLOCK))
            return;

        var index = ClusterApi.getIndex(state, pos, level);
        var blockTag = ClusterApi.getOreTag(index);
        var validReGrow = ClusterApi.getTagRegrow(index);

        if (blockTag != null)

            if (LevelUtil.ValidFaces(pos, level, validReGrow) >= 3) {
                BlockState ore;
                try {
                    var oreID = level.getBlockEntity(pos).saveCustomAndMetadata(level.registryAccess())
                            .getString("oreBroken").split(":");

                    if (Config.RANDOM_CLUSTERS.get() && blockTag != null)
                        ore = LevelUtil
                                .ResourceByTag(blockTag,
                                        LevelUtil.getRandomValue(LevelUtil.getSizeTag(blockTag), level))
                                .defaultBlockState();
                    else
                        ore = BuiltInRegistries.BLOCK.get(ResourceLocation.fromNamespaceAndPath(oreID[0], oreID[1]))
                                .defaultBlockState();

                    if (ore == null || ore.is(zTags.NO_CLUSTER_RESULT))
                        return;

                    level.setBlockAndUpdate(pos, ore);
                } catch (Exception e1) {
                    // when nbt blockid wasn't correct
                    LogUtils.getLogger().error("##ERROR## Found a block cluster that contain broken value at " + pos.toShortString());
                    try {
                        LogUtils.getLogger()
                                .info("##-----## Value stored inside BE: "
                                        + level.getBlockEntity(pos).saveCustomAndMetadata(level.registryAccess())
                                                .getString("oreBroken"));

                    } catch (Exception e2) {
                        // To prevent another Crash when readed
                    }

                    if (Config.CORRUPTED_CLUSTERS.get()) {
                        LogUtils.getLogger().warn("##-----## Reverted as AIR to prevent CRASH");
                        level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
                    } else {
                        LogUtils.getLogger().warn(
                                "##-----## Consider to break/modify it or enable via configs 'cluster_corrupted_crash_safety' ");
                    }
                    return;
                }
            }
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents,
            TooltipFlag tooltipFlag) {
        if (Screen.hasControlDown()) {
            if (Config.RANDOM_CLUSTERS.getAsBoolean())
                tooltipComponents.add(Component.translatable(Main.MODID + "." + type + ".on"));
            else
                tooltipComponents.add(Component.translatable(Main.MODID + ".randomless"));
        } else {
            tooltipComponents.add(Component.translatable(Main.MODID + ".off"));
        }
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }

    @Override
    @Nullable
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new ClusterBE(pos, state);
    }

}
