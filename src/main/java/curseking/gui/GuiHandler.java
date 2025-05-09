package curseking.gui;

import curseking.tileentities.ContainerCrystallinePurifier;
import curseking.tileentities.TileEntityCrystallinePurifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {
    public static final int CRYSTALLINE_PURIFIER_GUI_ID = 1;

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID == CRYSTALLINE_PURIFIER_GUI_ID)
            return new ContainerCrystallinePurifier(player.inventory, (TileEntityCrystallinePurifier) world.getTileEntity(new BlockPos(x, y, z)));
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID == CRYSTALLINE_PURIFIER_GUI_ID)
            return new GuiCrystallinePurifier(player.inventory, (TileEntityCrystallinePurifier) world.getTileEntity(new BlockPos(x, y, z)));
        return null;
    }
}
