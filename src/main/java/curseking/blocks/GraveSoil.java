package curseking.blocks;

import curseking.CurseKing;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class GraveSoil extends Block {

    public GraveSoil() {
        super(Material.GROUND);
        setTranslationKey("gravesoil");
        setRegistryName("gravesoil");
        setHardness(0.5F);
        setCreativeTab(CurseKing.CURSEKING_TAB);
        setSoundType(SoundType.GROUND);
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }
}
