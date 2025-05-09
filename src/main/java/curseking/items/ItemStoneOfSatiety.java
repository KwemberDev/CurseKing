package curseking.items;

import curseking.CurseDataProvider;
import curseking.CurseKing;
import curseking.ICurseData;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class ItemStoneOfSatiety extends Item {

    public ItemStoneOfSatiety() {
        setRegistryName("stone_of_satiety");
        setTranslationKey(CurseKing.MODID + ".stone_of_satiety");
        setCreativeTab(CreativeTabs.MISC);
        setMaxStackSize(1);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        ItemStack stack = player.getHeldItem(hand);

        if (!world.isRemote && player.isSneaking()) {
            ICurseData data = player.getCapability(CurseDataProvider.CURSE_DATA_CAP, null);

            if (data != null && !data.hasBlessing("blessing_satiated")) {
                data.addBlessing("blessing_satiated");

                player.sendStatusMessage(new TextComponentString(TextFormatting.GRAY + "Your hunger lessens as you feel revitalized."), true);

                stack.shrink(1);

                world.playSound(null, player.getPosition(), SoundEvents.ENTITY_PLAYER_BURP, SoundCategory.PLAYERS, 1.0f, 1.0f);
                return new ActionResult<>(EnumActionResult.SUCCESS, stack);
            } else if (data != null && data.hasBlessing("blessing_satiated")) {
                player.sendStatusMessage(new TextComponentString(TextFormatting.GRAY + "You already have this blessing!"), true);
                return new ActionResult<>(EnumActionResult.FAIL, stack);
            }
        }

        return new ActionResult<>(EnumActionResult.PASS, stack);
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        tooltip.add(TextFormatting.DARK_GRAY + "A stolen Divinity purified, revealing the blessing it once held.");
        tooltip.add(TextFormatting.DARK_GRAY + "" + TextFormatting.ITALIC + "Sneak-right-click to consume the blessing.");
    }
}
