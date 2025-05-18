package curseking.items;

import curseking.CurseKing;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemShatteredPurity extends Item {

    public ItemShatteredPurity() {
        this.setRegistryName("purity");
        this.setTranslationKey(CurseKing.MODID + ".purity");
        setCreativeTab(CurseKing.CURSEKING_TAB);
        this.setMaxStackSize(16);
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }
}
