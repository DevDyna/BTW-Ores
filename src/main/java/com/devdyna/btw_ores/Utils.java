package com.devdyna.btw_ores;

import java.util.List;
import java.util.Random;

// import org.slf4j.Logger;

// import com.mojang.logging.LogUtils;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.neoforge.registries.DeferredBlock;

public class Utils {

    /**
     * @param level   net.minecraft.world.level.Level
     * @param enchant Enchantments.name
     * @return Holder[Enchantment]
     */
    public static Holder<Enchantment> getEnchantHolder(LevelAccessor level, ResourceKey<Enchantment> enchant) {
        return level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT)
                .getOrThrow(enchant);
    }

    // private static final Logger LOGGER = LogUtils.getLogger();

    @SuppressWarnings("null")
    public static List<ItemStack> getItemStackFromLootTable(LevelAccessor level, Player player, String raw_ore_name) {

        LootParams.Builder builder = new LootParams.Builder((ServerLevel) level);
        LootParams params = builder.create(LootContextParamSets.EMPTY);
        builder.withLuck(player.getLuck());

        LootTable lootTable = level.getServer().reloadableRegistries().getLootTable(ResourceKey
                .create(Registries.LOOT_TABLE, ResourceLocation.fromNamespaceAndPath(
                        getModName(raw_ore_name), "blocks/"
                                + raw_ore_name.substring(raw_ore_name.lastIndexOf('.') + 1))));
        return lootTable.getRandomItems(params);

    }

    public void messageActionBar(String name, Player player) {
        player.displayClientMessage(Component.literal(name),
                true);
    }

    public static int getRandomValue(int value) {
        if (value == 0) {
            return 1;
        }

        Random random = new Random();
        return random.nextInt(value) + 1;
    }

    public static String getModName(String traslationName) {
        String[] parts = traslationName.split("\\.");
        if (parts.length >= 2) {
            return parts[1];
        } else {
            return null;
        }
    }

    public static boolean isDimension(Player player, ResourceKey<Level> dim) {
        return player.level().dimension().equals(dim);
    }

    public static void SimplePlaceBlock(LevelAccessor levelAccessor, BlockPos pos, DeferredBlock<Block> block) {
        levelAccessor.setBlock(pos, block.get().defaultBlockState(), 32);
    }

    /**
     * @param delay ticks -> 20t = 1s
     * @param name
     * @param level
     * @param pos
     */
    public static void ThenPlace(int delay, String name, LevelAccessor level, BlockPos pos) {
        if (level.dayTime() % delay == 0) {
            level.setBlock(pos,
                    BuiltInRegistries.BLOCK.get(ResourceLocation.tryParse(name)).defaultBlockState(),
                    32);
        }
    }

}
