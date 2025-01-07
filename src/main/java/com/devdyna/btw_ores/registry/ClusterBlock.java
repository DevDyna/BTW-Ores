package com.devdyna.btw_ores.registry;

import java.util.List;

import com.devdyna.btw_ores.Main;
import com.devdyna.btw_ores.Utils;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.Tags;

public class ClusterBlock extends Block {

    @SuppressWarnings("unused")
    private String type; 
    public ClusterBlock(Properties properties,String type) {
        super(properties);
        this.type = type;
    }

    @SuppressWarnings("null")
    protected void randomTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        TagKey<Block> blockTag = null;

        if (Utils.isDimension(world, Level.OVERWORLD) && pos.getY() >= 0 && state.is(ItemsBlocks.STONE_CLUSTER_BLOCK))
            blockTag = Tags.Blocks.ORES_IN_GROUND_STONE;

        if (Utils.isDimension(world, Level.OVERWORLD) && pos.getY() < 0 && state.is(ItemsBlocks.DEEP_CLUSTER_BLOCK))
            blockTag = Tags.Blocks.ORES_IN_GROUND_DEEPSLATE;

        if (Utils.isDimension(world, Level.NETHER) && state.is(ItemsBlocks.NETHER_CLUSTER_BLOCK))
            blockTag = Tags.Blocks.ORES_IN_GROUND_NETHERRACK;

        if (Utils.isDimension(world, Level.END)&& state.is(ItemsBlocks.END_CLUSTER_BLOCK))
            blockTag = AnyTags.ORES_IN_GROUND_END;

        if (blockTag != null) {

            List<Holder<Block>> blocksInTag = BuiltInRegistries.BLOCK.getOrCreateTag(blockTag).stream().toList();
            world.setBlockAndUpdate(pos,
                    blocksInTag.get(Utils.getRandomValue(blocksInTag.size() - 1)).value().defaultBlockState());
        }

    }


    @SuppressWarnings("null")
    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents,
            TooltipFlag tooltipFlag) {
        if (Screen.hasShiftDown()) {
            tooltipComponents.add(Component.translatable(Main.MODID+"."+type+".shift.on"));
        } else {
            tooltipComponents.add(Component.translatable(Main.MODID+".shift.off"));
        }
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }

}
