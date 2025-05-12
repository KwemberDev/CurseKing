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
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        this.fontRenderer.drawString("Crystalline Purifier", 8, 6, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation(CurseKing.MODID, "textures/gui/purifier.png"));
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

        // === DRAW FLAME INDICATOR ===
        int burnTime = tileEntity.getField(0);
        int currentItemBurnTime = tileEntity.getField(1);

        if (burnTime > 0) {
            int burnHeight = (int)(16 * ((float) burnTime / (float) currentItemBurnTime));
            drawTexturedModalRect(guiLeft + 74, guiTop + 36 + 14 - burnHeight, 176, 46 - burnHeight, 17, burnHeight + 1);
        }

        // === DRAW PURIFYING ARROW ===
        int cookTime = tileEntity.getField(2);
        int totalCookTime = tileEntity.getField(3);

        if (totalCookTime > 0 && cookTime > 0) {
            int progressWidth = (int)(24 * ((float)cookTime / totalCookTime));
            drawTexturedModalRect(guiLeft + 97, guiTop + 34, 176, 14, progressWidth + 1, 16);
            drawTexturedModalRect(guiLeft + 41, guiTop + 16, 176, 14, progressWidth + 1, 16);
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }
}
