package curseking.creativetabs;

import curseking.ModItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.init.Items; // Replace with your mod's item

public class CurseKingCreativeTab extends CreativeTabs {
    public CurseKingCreativeTab() {
        super("curseking_tab"); // Tab name
    }

    @Override
    public ItemStack createIcon() {
        return new ItemStack(ModItems.ASHES_OF_DIVINITY);
    }
}