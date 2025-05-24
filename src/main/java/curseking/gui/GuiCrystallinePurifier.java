package curseking.gui;

import curseking.CurseKing;
import curseking.tileentities.ContainerCrystallinePurifier;
import curseking.tileentities.TileEntityCrystallinePurifier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

public class GuiCrystallinePurifier extends GuiContainer {
    private final IInventory tile;

    private final TileEntityCrystallinePurifier tileEntity;

    public GuiCrystallinePurifier(InventoryPlayer playerInv, IInventory tile) {
        super(new ContainerCrystallinePurifier(playerInv, tile));
        this.tile = tile;
        this.tileEntity = (TileEntityCrystallinePurifier) tile;
        this.xSize = 176;
        this.ySize = 166;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation(CurseKing.MODID, "textures/gui/purifier2.png"));
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

        // === DRAW FLAME INDICATOR ===
        int burnTime = tileEntity.getField(0);
        int currentItemBurnTime = tileEntity.getField(1);

        if (burnTime > 0) {
            int burnHeight = (int)(20 * ((float) burnTime / (float) currentItemBurnTime));
            drawTexturedModalRect(guiLeft + 89, guiTop + 15 + 8 - burnHeight, 176, 40 - burnHeight, 20, burnHeight);
        }

        // === DRAW PURIFYING ARROW ===
        int cookTime = tileEntity.getField(2);
        int totalCookTime = tileEntity.getField(3);

        if (totalCookTime > 0 && cookTime > 0) {
            int progressHeight = (int)(20 * ((float)cookTime / totalCookTime));
            drawTexturedModalRect(guiLeft + 114, guiTop + 34, 176, 0, 14, progressHeight);
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }
}
