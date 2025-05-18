package curseking.creativetabs;

import curseking.ModItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class CurseKingCreativeTab extends CreativeTabs {
    public CurseKingCreativeTab() {
        super("curseking_tab");
    }

    @Override
    public ItemStack createIcon() {
        return new ItemStack(ModItems.ASHES_OF_DIVINITY);
    }
}