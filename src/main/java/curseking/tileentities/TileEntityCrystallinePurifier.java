package curseking.tileentities;

import curseking.ModItems;
import curseking.blocks.CrystallinePurifierBlock;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;

public class TileEntityCrystallinePurifier extends TileEntity implements IInventory, ITickable {

    private NonNullList<ItemStack> items = NonNullList.withSize(4, ItemStack.EMPTY);
    private int cookTime;
    private static int totalCookTime = 3000;
    private int cookTimeRemaining;
    private int furnaceBurnTime;
    private int currentItemBurnTime;

    @Override
    public void update() {
        boolean wasBurning = furnaceBurnTime > 0;
        boolean needsUpdate = false;

        if (furnaceBurnTime > 0) {
            furnaceBurnTime--;
        }

        if (!world.isRemote) {
            boolean canSmelt = canPurify();

            if (furnaceBurnTime == 0 && canSmelt) {
                ItemStack fuelStack = items.get(1); // Slot 1: fuel
                int burnTime = 200;
                if (burnTime > 0) {
                    furnaceBurnTime = burnTime;
                    currentItemBurnTime = burnTime;

                    fuelStack.shrink(1);
                    if (fuelStack.isEmpty()) {
                        items.set(1, ItemStack.EMPTY);
                    }
                    needsUpdate = true;
                }
            }

            if (furnaceBurnTime > 0 && canSmelt) {
                cookTime++;
                cookTimeRemaining = totalCookTime - cookTime;

                if (cookTime >= totalCookTime) {
                    cookTime = 0;
                    purifyItem();
                    needsUpdate = true;
                }
            } else {
                cookTime = 0;
                cookTimeRemaining = totalCookTime;
            }

            if (wasBurning != (furnaceBurnTime > 0)) {
                needsUpdate = true;
            }

            if (needsUpdate) {
                markDirty();
            }
        }
        boolean currentlyActive = furnaceBurnTime> 0;
        IBlockState currentState = world.getBlockState(pos);
        if (currentState.getBlock() instanceof CrystallinePurifierBlock) {
            if (currentState.getValue(CrystallinePurifierBlock.ACTIVE) != currentlyActive) {
                if (!world.isRemote) {
                    ((CrystallinePurifierBlock) currentState.getBlock()).setActive(world, pos, currentlyActive);
                }
            }
        }
    }

    private boolean canPurify() {
        ItemStack input = items.get(0);
        ItemStack fuel = items.get(1);
        ItemStack catalyst = items.get(2);
        ItemStack output = items.get(3);

        if (input.isEmpty() || fuel.isEmpty() || catalyst.isEmpty()) return false;
        if (!isCursedStone(input)) return false;
        if (!isCoal(fuel)) return false;
        if (!isBlazePowder(catalyst)) return false;

        ItemStack result = getBlessedStone(input);
        if (result.isEmpty()) return false;

        if (output.isEmpty()) return true;
        if (!output.isItemEqual(result)) return false;
        return output.getCount() + result.getCount() <= output.getMaxStackSize();
    }

    private void purifyItem() {
        ItemStack input = items.get(0);
        ItemStack fuel = items.get(1);
        ItemStack catalyst = items.get(2);
        ItemStack output = items.get(3);

        ItemStack result = getBlessedStone(input);

        if (output.isEmpty()) {
            items.set(3, result.copy());
        } else {
            output.grow(result.getCount());
        }

        input.shrink(1);
        fuel.shrink(1);
        catalyst.shrink(1);
    }

    private boolean isCursedStone(ItemStack stack) {
        return stack.getItem() == ModItems.STONE_OF_DECAY ||
                stack.getItem() == ModItems.STONE_OF_HUNGER ||
                stack.getItem() == ModItems.STONE_OF_SLOWNESS;
    }

    private boolean isCoal(ItemStack stack) {
        return stack.getItem() == ModItems.ASHES_OF_DIVINITY;
    }

    private boolean isBlazePowder(ItemStack stack) {
        return stack.getItem() == ModItems.SHATTERED_PURITY;
    }

    private ItemStack getBlessedStone(ItemStack cursedStone) {
        if (cursedStone.getItem().equals(ModItems.STONE_OF_DECAY)) {
            return new ItemStack(ModItems.STONE_OF_IRON_SKIN);
        } else if (cursedStone.getItem().equals(ModItems.STONE_OF_HUNGER)) {
            return new ItemStack(ModItems.STONE_OF_SATIETY);
        } else if (cursedStone.getItem().equals(ModItems.STONE_OF_SLOWNESS)) {
            return new ItemStack(ModItems.STONE_OF_NIMBLE);
        }
        return ItemStack.EMPTY;
        // fuck intellij switch casting dumb ahh type error
    }

    @Override
    public int getSizeInventory() {
        return items.size();
    }

    @Override
    public boolean isEmpty() {
        for (ItemStack stack : items) {
            if (!stack.isEmpty()) return false;
        }
        return true;
    }

    @Override
    public ItemStack getStackInSlot(int index) {
        return items.get(index);
    }

    @Override
    public ItemStack decrStackSize(int index, int count) {
        return ItemStackHelper.getAndSplit(items, index, count);
    }

    @Override
    public ItemStack removeStackFromSlot(int index) {
        return ItemStackHelper.getAndRemove(items, index);
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        items.set(index, stack);
        if (!stack.isEmpty() && stack.getCount() > getInventoryStackLimit()) {
            stack.setCount(getInventoryStackLimit());
        }
        markDirty();
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public boolean isUsableByPlayer(EntityPlayer player) {
        return !isInvalid() && player.getDistanceSq(pos.add(0.5, 0.5, 0.5)) <= 64.0;
    }

    @Override
    public void openInventory(EntityPlayer player) { }

    @Override
    public void closeInventory(EntityPlayer player) { }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        return index != 4;
    }

    public int getField(int id) {
        switch (id) {
            case 0: return this.furnaceBurnTime;
            case 1: return this.currentItemBurnTime;
            case 2: return this.cookTime;
            case 3: return totalCookTime;
            case 4: return this.cookTimeRemaining;
            default: return 0;
        }
    }


    public void setField(int id, int value) {
        switch (id) {
            case 0: this.furnaceBurnTime = value; break;
            case 1: this.currentItemBurnTime = value; break;
            case 2: this.cookTime = value; break;
            case 3: totalCookTime = value; break;
            case 4: this.cookTimeRemaining = value; break;
        }
    }


    @Override
    public int getFieldCount() {
        return 5;
    }

    @Override
    public void clear() {
        items.clear();
    }

    @Override
    public String getName() {
        return "container.crystalline_purifier";
    }

    @Override
    public boolean hasCustomName() {
        return false;
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        ItemStackHelper.saveAllItems(compound, this.items);
        compound.setInteger("CookTime", this.cookTime);
        compound.setInteger("CookTimeRemaining", this.cookTimeRemaining);
        compound.setInteger("BurnTime", this.furnaceBurnTime);
        compound.setInteger("CurrentBurnTime", this.currentItemBurnTime);
        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.items = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(compound, this.items);
        this.cookTime = compound.getInteger("CookTime");
        this.cookTimeRemaining = compound.getInteger("CookTimeRemaining");
        this.furnaceBurnTime = compound.getInteger("BurnTime");
        this.currentItemBurnTime = compound.getInteger("CurrentBurnTime");
    }


}

