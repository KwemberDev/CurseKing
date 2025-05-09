package curseking;

import curseking.blocks.*;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModBlocks {

    public static final CrystallinePurifierBlock crystallinePurifierBlock = new CrystallinePurifierBlock();
    public static final BlockCrucifix crucifixBlock = new BlockCrucifix();
    public static final GraveSand graveSand = new GraveSand();
    public static final GraveSoil graveSoil = new GraveSoil();
    public static final SmallCrucifix smallCrucifix = new SmallCrucifix();

    @SideOnly(Side.CLIENT)
    public static void initModels() {
        crystallinePurifierBlock.initModel();
        crucifixBlock.initModel();
        graveSand.initModel();
        graveSoil.initModel();
        smallCrucifix.initModel();
    }

}
