package com.devdyna.btw_ores.registry.builders;

import java.util.List;

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
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
// import net.minecraft.world.level.block.state.StateDefinition;
// import net.minecraft.world.level.block.state.properties.BlockStateProperties;
// import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.neoforged.neoforge.common.Tags;

public class ClusterBlock extends Block {

    // public static final BooleanProperty GROW = BlockStateProperties.ENABLED;
    private String type;

    public ClusterBlock(BlockBehaviour.Properties properties, String type) {
        super(properties);
        this.type = type;
        // this.registerDefaultState(stateDefinition.any()
        //         .setValue(GROW, false));
    }

    @SuppressWarnings("null")
    protected void randomTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {

        Level level = world.getLevel();

        if (com.devdyna.btw_ores.utils.Math.chance(75))
            return;

        TagKey<Block> blockTag = null;
        TagKey<Block> validReGrow = null;

        if (LevelUtil.isDimension(world, Level.OVERWORLD) && pos.getY() >= 0
                && state.is(ItemsBlocks.STONE_CLUSTER_BLOCK)) {
            blockTag = Tags.Blocks.ORES_IN_GROUND_STONE;
            validReGrow = BlockTags.VALID_REGROW_STONE;
        }

        if (LevelUtil.isDimension(world, Level.OVERWORLD) && pos.getY() < 0
                && state.is(ItemsBlocks.DEEP_CLUSTER_BLOCK)) {
            blockTag = Tags.Blocks.ORES_IN_GROUND_DEEPSLATE;
            validReGrow = BlockTags.VALID_REGROW_DEEPSLATE;
        }

        if (LevelUtil.isDimension(world, Level.NETHER) && state.is(ItemsBlocks.NETHER_CLUSTER_BLOCK)) {
            blockTag = Tags.Blocks.ORES_IN_GROUND_NETHERRACK;
            validReGrow = BlockTags.VALID_REGROW_NETHER;
        }

        if (LevelUtil.isDimension(world, Level.END) && state.is(ItemsBlocks.END_CLUSTER_BLOCK)) {
            blockTag = BlockTags.ORES_IN_GROUND_END;
            validReGrow = BlockTags.VALID_REGROW_END;
        }

        if (blockTag != null) {

            if (LevelUtil.ValidFaces(pos, level, validReGrow) >= 3) {

                // state.setValue(GROW, true);

                BlockState ore = LevelUtil.ResourceByTag(blockTag, Math.getRandomValue(LevelUtil.getSizeTag(blockTag))).defaultBlockState();
                
                if (!ore.is(BlockTags.NO_CLUSTER_RESULT))
                    world.setBlockAndUpdate(pos, ore);

            }

        }

    }

    @SuppressWarnings("null")
    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents,
            TooltipFlag tooltipFlag) {
        if (Screen.hasControlDown()) {
            tooltipComponents.add(Component.translatable(Main.MODID + "." + type + ".on"));
        } else {
            tooltipComponents.add(Component.translatable(Main.MODID + ".off"));
        }
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }

    // @SuppressWarnings("null")
    // @Override
    // protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
    //     pBuilder.add(GROW);
    // }

}
