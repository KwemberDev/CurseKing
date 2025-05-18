package curseking.items;

import curseking.CurseDataProvider;
import curseking.CurseKing;
import curseking.ICurseData;
import curseking.ModItems;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static curseking.eventhandlers.PlayerEventHandler.sendCurseDataToClient;

public class ItemNeutralDivinityStone extends Item {

    public ItemNeutralDivinityStone() {
        setMaxStackSize(1);
        setTranslationKey(CurseKing.MODID + "." + "neutral_divinity_stone");
        setRegistryName("neutral_divinity_stone");
        setCreativeTab(CurseKing.CURSEKING_TAB);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        ItemStack stack = player.getHeldItem(hand);

        if (!player.isSneaking()) return new ActionResult<>(EnumActionResult.PASS, stack);
        if (world.isRemote) return new ActionResult<>(EnumActionResult.SUCCESS, stack);

        ICurseData data = player.getCapability(CurseDataProvider.CURSE_DATA_CAP, null);
        if (data == null || data.getAllCurses().isEmpty()) {
            player.sendStatusMessage(new TextComponentString(TextFormatting.GRAY + "You feel no darkness to draw upon."), true);
            return new ActionResult<>(EnumActionResult.FAIL, stack);
        }

        List<String> curses = new ArrayList<>(data.getAllCurses());
        Collections.shuffle(curses);
        String removedCurse = curses.get(0);

        ItemStack replacement;
        switch (removedCurse) {
            case "curse_starving":
                replacement = new ItemStack(ModItems.STONE_OF_HUNGER);
                data.removeCurse(removedCurse);
                sendCurseDataToClient((EntityPlayerMP) player);
                break;
            case "curse_decay":
                replacement = new ItemStack(ModItems.STONE_OF_DECAY);
                data.removeCurse(removedCurse);
                sendCurseDataToClient((EntityPlayerMP) player);
                break;
            case "curse_sloth":
                replacement = new ItemStack(ModItems.STONE_OF_SLOWNESS);
                data.removeCurse(removedCurse);
                sendCurseDataToClient((EntityPlayerMP) player);
                break;
            default:
                player.sendStatusMessage(new TextComponentString(TextFormatting.GRAY + "The stone rejects you..."), true);
                return new ActionResult<>(EnumActionResult.FAIL, stack);
        }

        player.setHeldItem(hand, replacement);
        player.sendStatusMessage(new TextComponentString(TextFormatting.GRAY + "The stone pulses with a cursed energy."), true);

        return new ActionResult<>(EnumActionResult.SUCCESS, replacement);
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        tooltip.add(TextFormatting.DARK_GRAY + "A mysterious stone pulsing with dormant energy.");
        tooltip.add(TextFormatting.DARK_GRAY + "" + TextFormatting.ITALIC + "Sneak-right-click to absorb a curse.");
    }

}
