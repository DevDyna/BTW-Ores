package com.devdyna.btw_ores.registry;

import java.util.List;
import com.devdyna.btw_ores.Utils;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.Tags;

public class ClusterBlock extends Block {

    public ClusterBlock(Properties properties) {
        super(properties);
    }

    @SuppressWarnings("null")
    protected void randomTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        TagKey<Block> blockTag = null;

        if (Utils.isDimension(world, Level.OVERWORLD) && pos.getY() >= 0)
            blockTag = Tags.Blocks.ORES_IN_GROUND_STONE;

        if (Utils.isDimension(world, Level.OVERWORLD) && pos.getY() < 0)
            blockTag = Tags.Blocks.ORES_IN_GROUND_DEEPSLATE;

        if (Utils.isDimension(world, Level.NETHER))
            blockTag = Tags.Blocks.ORES_IN_GROUND_NETHERRACK;

        if (Utils.isDimension(world, Level.END))
            blockTag = AnyTags.ORES_IN_GROUND_END;

        if (blockTag != null) {

            List<Holder<Block>> blocksInTag = BuiltInRegistries.BLOCK.getOrCreateTag(blockTag).stream().toList();
            world.setBlockAndUpdate(pos,
                    blocksInTag.get(Utils.getRandomValue(blocksInTag.size() - 1)).value().defaultBlockState());
        }

    }

}
