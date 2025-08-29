package com.devdyna.btw_ores.registry.builders;

import java.util.List;

import javax.annotation.Nullable;

import com.devdyna.btw_ores.Main;
import com.devdyna.btw_ores.registry.ItemsBlocks;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

@SuppressWarnings("null")
public class BlazeReagent extends Item {

    public BlazeReagent(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext c) {

        var level = c.getLevel();
        var pos = c.getClickedPos();
        var block = level.getBlockState(pos);

        if (block.is(ItemsBlocks.NULL_CLUSTER_BLOCK.get())) {
            try {

                var oreID = level.getBlockEntity(pos).saveWithFullMetadata()
                        .getString("oreBroken").split(":");

                @SuppressWarnings("deprecation")
                var ore = BuiltInRegistries.BLOCK.get(new ResourceLocation(oreID[0], oreID[1]))
                        .defaultBlockState();

                level.setBlockAndUpdate(pos, ore);

            } catch (Exception e) {
            }
            c.getItemInHand().shrink(1);
            return InteractionResult.SUCCESS;
        } else
            return super.useOn(c);

    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> context,
            TooltipFlag flag) {
        if (Screen.hasControlDown()) {
            context.add(Component.translatable(Main.MODID + ".blaze_reagent.on"));
        } else {
            context.add(Component.translatable(Main.MODID + ".off"));
        }
        super.appendHoverText(stack, level, context, flag);
    }

}
