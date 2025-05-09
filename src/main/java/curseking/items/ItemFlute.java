package curseking.items;

import curseking.CurseKing;
import curseking.mobs.EntityAquaRegia;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class ItemFlute extends Item {

    public ItemFlute() {
        this.setMaxStackSize(1);
        this.setCreativeTab(CreativeTabs.MISC);
        this.setTranslationKey(CurseKing.MODID + "flute");
        this.setRegistryName("summonflute");
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack) {
        return 72000;
    }

    @Override
    public EnumAction getItemUseAction(ItemStack stack) {
        return EnumAction.BOW;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        player.setActiveHand(hand);
        return new ActionResult<>(EnumActionResult.SUCCESS, player.getHeldItem(hand));
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack stack, World world, EntityLivingBase player, int timeLeft) {
        int useDuration = this.getMaxItemUseDuration(stack) - timeLeft;
        if (useDuration >= 60) {
            if (!world.isRemote) {
                boolean found = false;
                double px = player.posX;
                double py = player.posY;
                double pz = player.posZ;
                for (int i = 0; i < 16 && !found; i++) {
                    double angle = world.rand.nextDouble() * Math.PI * 2;
                    double dist = 7 + world.rand.nextDouble() * 8;
                    double dx = Math.cos(angle) * dist;
                    double dz = Math.sin(angle) * dist;
                    double dy = 3 + world.rand.nextDouble() * 7;
                    int x = (int)Math.floor(px + dx);
                    int y = (int)Math.floor(py + dy);
                    int z = (int)Math.floor(pz + dz);
                    if (world.isAirBlock(new BlockPos(x, y, z)) && world.isAirBlock(new BlockPos(x, y + 1, z))) {
                        EntityAquaRegia boss = new EntityAquaRegia(world);
                        boss.setPosition(x + 0.5, y, z + 0.5);
                        world.spawnEntity(boss);
                        EntityLightningBolt lightning = new EntityLightningBolt(world, x, y, z, true);
                        world.addWeatherEffect(lightning);
                        found = true;
                    }
                }
            }
        }
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        tooltip.add(TextFormatting.DARK_GRAY + "A certain elemental being likes the sound. Playing the flute might summon it.");
    }
}