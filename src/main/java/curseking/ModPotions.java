package curseking;

import curseking.eventhandlers.mobeventhandlers.PotionCursed;
import net.minecraft.potion.Potion;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static curseking.CurseKing.MODID;

@Mod.EventBusSubscriber(modid = MODID)
public class ModPotions {

    public static Potion cursedDecay;
    public static Potion cursedSloth;
    public static Potion cursedStarving;

    @SubscribeEvent
    public static void onRegisterPotions(RegistryEvent.Register<Potion> event) {
        cursedDecay = new PotionCursed(PotionCursed.CurseType.DECAY);
        cursedSloth = new PotionCursed(PotionCursed.CurseType.SLOTH);
        cursedStarving = new PotionCursed(PotionCursed.CurseType.STARVING);

        event.getRegistry().registerAll(cursedDecay, cursedSloth, cursedStarving);
    }
}
