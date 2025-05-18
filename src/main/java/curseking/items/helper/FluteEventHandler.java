package curseking.items.helper;

import curseking.CurseKing;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.HashMap;
import java.util.Map;

@Mod.EventBusSubscriber
public class FluteEventHandler {

    private static final Map<Integer, Integer> playerNoteTicks = new HashMap<>();

    public static void playFluteMelody(EntityPlayer player, World world) {
        int entityId = player.getEntityId();
        playerNoteTicks.put(entityId, 0);
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        CurseKing.logger.debug("PLAYING FLUTE MELODY");
        EntityPlayer player = event.player;
        World world = player.world;
        int entityId = player.getEntityId();
        if (playerNoteTicks.containsKey(entityId)) {
            int tick = playerNoteTicks.get(entityId);
            if (tick == 0) {
                world.playSound(null, player.posX, player.posY, player.posZ,
                        SoundEvents.BLOCK_NOTE_FLUTE, SoundCategory.PLAYERS, 1.2F, 1.0F);
            } else if (tick == 10) {
                world.playSound(null, player.posX, player.posY, player.posZ,
                        SoundEvents.BLOCK_NOTE_FLUTE, SoundCategory.PLAYERS, 0.8F, 1.0F);
            } else if (tick == 20) {
                world.playSound(null, player.posX, player.posY, player.posZ,
                        SoundEvents.BLOCK_NOTE_FLUTE, SoundCategory.PLAYERS, 1.0F, 1.0F);
                playerNoteTicks.remove(entityId); // End of melody
                return;
            }
            playerNoteTicks.put(entityId, tick + 1);
        }
    }
}
