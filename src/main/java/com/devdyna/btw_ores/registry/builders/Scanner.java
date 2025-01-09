package com.devdyna.btw_ores.registry.builders;

import java.util.List;

import com.devdyna.btw_ores.Main;
import com.devdyna.btw_ores.registry.BlockTags;
import com.devdyna.btw_ores.registry.ItemsBlocks;
import com.devdyna.btw_ores.utils.LevelUtil;
import com.devdyna.btw_ores.utils.PlayerUtil;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class Scanner extends Item {

    public Scanner(Properties p) {
        super(p);
    }

    @SuppressWarnings("null")
    @Override
    public InteractionResult useOn(UseOnContext event) {
        BlockPos pos = event.getClickedPos();
        Player player = event.getPlayer();
        Level level = event.getLevel();
        TagKey<Block> validTag = null;
        BlockState state = level.getBlockState(pos);
        String type = null;

        level.playLocalSound(pos.getX(), pos.getY(),
                pos.getZ(), SoundEvents.LODESTONE_COMPASS_LOCK, SoundSource.BLOCKS, 100,
                (int) Math.floor(Math.random() * 2), true);

        if (!state.is(BlockTags.CLUSTERS))
            return ActionResult("scanner.invalid", player);

        if (state.is(ItemsBlocks.NULL_CLUSTER_BLOCK)) {
            return ActionResult("scanner.null", player);
        }

        if (state.is(ItemsBlocks.STONE_CLUSTER_BLOCK)) {
            validTag = BlockTags.VALID_REGROW_STONE;
            type = "stone";
        }

        if (state.is(ItemsBlocks.DEEP_CLUSTER_BLOCK)) {
            validTag = BlockTags.VALID_REGROW_DEEPSLATE;
            type = "deepslate";
        }

        if (state.is(ItemsBlocks.NETHER_CLUSTER_BLOCK)) {
            validTag = BlockTags.VALID_REGROW_NETHER;
            type = "nether";
        }

        if (state.is(ItemsBlocks.END_CLUSTER_BLOCK)) {
            validTag = BlockTags.VALID_REGROW_END;
            type = "end";
        }

        if (!validDimension(type, pos, level)) {
            return ActionResult("scanner.placement", player);
        }

        if (LevelUtil.ValidFaces(pos, level, validTag) < 3) {
            return ActionResult("scanner." + type + ".missing", player);
        }

        return ActionResult("scanner.success", player);
    }

    @SuppressWarnings("null")
    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents,
            TooltipFlag tooltipFlag) {
        if (Screen.hasControlDown()) {
            tooltipComponents.add(Component.translatable(Main.MODID + ".scanner.on"));
        } else {
            tooltipComponents.add(Component.translatable(Main.MODID + ".off"));
        }
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }

    private Boolean validDimension(String type, BlockPos pos, Level level) {
        Boolean condition = false;
        switch (type) {
            case "stone":
                condition = LevelUtil.isDimension(level, Level.OVERWORLD) && pos.getY() >= 0;
                break;
            case "deepslate":
                condition = LevelUtil.isDimension(level, Level.OVERWORLD) && pos.getY() < 0;
                break;
            case "nether":
                condition = LevelUtil.isDimension(level, Level.NETHER);
                break;
            case "end":
                condition = LevelUtil.isDimension(level, Level.END);
                break;
            default:
                break;
        }
        return condition;
    }

    private InteractionResult ActionResult(String traslatekey, Player player) {
        PlayerUtil.traslableActionMessage(traslatekey, player);
        return InteractionResult.SUCCESS;
    }

}
