package com.devdyna.btw_ores.registry.builders;

import com.devdyna.btw_ores.registry.ItemsBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

@SuppressWarnings("null")
public class ClusterBE extends BlockEntity {

    private String oreMined;

    public ClusterBE(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);
        this.oreMined = null;
    }

    public ClusterBE(BlockPos pos, BlockState blockState) {
        this(ItemsBlocks.CLUSTER.get(), pos, blockState);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putString("oreBroken", oreMined != null ? oreMined : "");
    }

    @Override
    protected void loadAdditional(CompoundTag tag, Provider registries) {
        super.loadAdditional(tag, registries);
        if (tag.contains("oreBroken"))
            oreMined = tag.getString("oreBroken");
    }

}
