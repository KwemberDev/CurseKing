package curseking.items;

import curseking.CurseKing;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemCoreFragment extends Item {

    public ItemCoreFragment() {
        this.setMaxStackSize(64);
        setCreativeTab(CurseKing.CURSEKING_TAB);
        this.setTranslationKey(CurseKing.MODID + ".corefragment");
        this.setRegistryName("corefragment");
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }
}