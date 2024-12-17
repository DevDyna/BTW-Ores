package com.devdyna.btw_ores.registry;

import com.devdyna.btw_ores.Main;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class AnyTags {

    AnyTags() {
    }


    public static final TagKey<Block> BLACKLISTED_ORES = TagKey.create(BuiltInRegistries.BLOCK.key(),
            ResourceLocation.fromNamespaceAndPath(Main.MODID, "cannot_spawn_ore_cluster"));

            public static final TagKey<Block> ORES_IN_GROUND_END = TagKey.create(BuiltInRegistries.BLOCK.key(),
            ResourceLocation.fromNamespaceAndPath("c", "ores_in_ground/end"));
            
}
