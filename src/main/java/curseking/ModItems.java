package curseking;

import curseking.items.*;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModItems {

    public static final ItemNeutralDivinityStone NEUTRAL_DIVINITY_STONE = new ItemNeutralDivinityStone();
    public static final BasicModItem STONE_OF_HUNGER = new BasicModItem("stone_of_hunger", "A stone imbued with a stolen divinity," , "containing the Curse of Hunger.");
    public static final BasicModItem STONE_OF_DECAY = new BasicModItem("stone_of_decay", "A stone imbued with a stolen divinity," , "containing the Curse of Decay.");
    public static final BasicModItem STONE_OF_SLOWNESS = new BasicModItem("stone_of_slowness", "A stone imbued with a stolen divinity," , "containing the Curse of Slowness");
    public static final ItemStoneOfSatiety STONE_OF_SATIETY = new ItemStoneOfSatiety();
    public static final ItemStoneOfNimble STONE_OF_NIMBLE = new ItemStoneOfNimble();
    public static final ItemStoneOfIronSkin STONE_OF_IRON_SKIN = new ItemStoneOfIronSkin();
    public static final ItemAshesOfDivinity ASHES_OF_DIVINITY = new ItemAshesOfDivinity();
    public static final ItemShatteredPurity SHATTERED_PURITY = new ItemShatteredPurity();
    public static final ItemFlute FLUTE = new ItemFlute();

    @SideOnly(Side.CLIENT)
    public static void initModels() {
        NEUTRAL_DIVINITY_STONE.initModel();
        STONE_OF_DECAY.initModel();
        STONE_OF_SLOWNESS.initModel();
        STONE_OF_HUNGER.initModel();
        STONE_OF_SATIETY.initModel();
        STONE_OF_NIMBLE.initModel();
        STONE_OF_IRON_SKIN.initModel();
        ASHES_OF_DIVINITY.initModel();
        SHATTERED_PURITY.initModel();
        FLUTE.initModel();
    }

}
