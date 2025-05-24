package curseking.tileentities;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ContainerCrystallinePurifier extends Container {
    private final IInventory tile;
    private int cookTimeRemaining;
    private final int lastCookTimeRemaining = -1;


    public ContainerCrystallinePurifier(InventoryPlayer playerInv, IInventory tile) {
        this.tile = tile;
        // Slot 0: Input
        this.addSlotToContainer(new Slot(tile, 0, 113, 17));
        this.addSlotToContainer(new Slot(tile, 1, 91, 25));
        this.addSlotToContainer(new Slot(tile, 2, 135, 8));

        // Slot 1: Output
        this.addSlotToContainer(new SlotFurnaceOutput(playerInv.player, tile, 3, 113, 60));

        // Player inventory
        for (int i = 0; i < 3; ++i)
            for (int j = 0; j < 9; ++j)
                this.addSlotToContainer(new Slot(playerInv, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));

        for (int k = 0; k < 9; ++k)
            this.addSlotToContainer(new Slot(playerInv, k, 8 + k * 18, 142));
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return tile.isUsableByPlayer(player);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack()) {
            ItemStack stackInSlot = slot.getStack();
            itemstack = stackInSlot.copy();

            int containerSlots = 4;

            if (index < containerSlots) {
                if (!this.mergeItemStack(stackInSlot, containerSlots + 1, inventorySlots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else {
                if (!this.mergeItemStack(stackInSlot, 0, 3, false)) {
                    return ItemStack.EMPTY;
                }
            }

            if (stackInSlot.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }

            if (stackInSlot.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(playerIn, stackInSlot);
        }

        return itemstack;
    }

    public void addListener(IContainerListener listener)
    {
        super.addListener(listener);
        listener.sendAllWindowProperties(this, this.tile);
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();

        for (IContainerListener listener : this.listeners) {
            listener.sendWindowProperty(this, 0, tile.getField(0)); // furnaceBurnTime
            listener.sendWindowProperty(this, 1, tile.getField(1)); // currentItemBurnTime
            listener.sendWindowProperty(this, 2, tile.getField(2)); // cookTime
            listener.sendWindowProperty(this, 3, tile.getField(3)); // totalCookTime
            listener.sendWindowProperty(this, 4, tile.getField(4)); // cookTimeRemaining
        }
    }


    @Override
    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int id, int data) {
        this.tile.setField(id, data);
    }

}
