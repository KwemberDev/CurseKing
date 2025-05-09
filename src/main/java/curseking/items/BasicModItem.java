package curseking.items;

import curseking.CurseKing;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class BasicModItem extends Item {

    private final String[] loreLines;

    public BasicModItem(String name, String... loreLines) {
        setTranslationKey(CurseKing.MODID + "." + name);
        setRegistryName(name);
        setCreativeTab(CreativeTabs.MISC);
        setMaxStackSize(1);
        this.loreLines = loreLines;
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        for (String line : loreLines) {
            tooltip.add(TextFormatting.DARK_GRAY + line);
        }
    }
}
