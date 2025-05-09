package curseking.eventhandlers;

import curseking.CurseDataProvider;
import curseking.CurseKing;
import curseking.ICurseData;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = CurseKing.MODID)
public class CurseEventHandler {

    @SubscribeEvent
    public static void attachCapabilities(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof EntityPlayer) {
            event.addCapability(CurseDataProvider.CURSE_CAP, new CurseDataProvider());
        }
    }

    @SubscribeEvent
    public static void clonePlayer(PlayerEvent.Clone event) {
        EntityPlayer original = event.getOriginal();
        EntityPlayer clone = event.getEntityPlayer();

        if (!clone.world.isRemote) {
            ICurseData originalData = original.getCapability(CurseDataProvider.CURSE_DATA_CAP, null);
            ICurseData cloneData = clone.getCapability(CurseDataProvider.CURSE_DATA_CAP, null);

            if (originalData != null && cloneData != null) {
                for (String curse : originalData.getAllCurses()) {
                    cloneData.addCurse(curse);
                }
                for (String blessing : originalData.getAllBlessings()) {
                    cloneData.addBlessing(blessing);
                }
            }
        }
    }
}
