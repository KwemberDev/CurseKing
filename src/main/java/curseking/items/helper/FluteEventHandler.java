package curseking.items.helper;

import curseking.items.ItemFlute;
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

    public static void stopFluteMelody(EntityPlayer player, World world) {
        int entityId = player.getEntityId();
        playerNoteTicks.remove(entityId);
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        EntityPlayer player = event.player;
        World world = player.world;
        int entityId = player.getEntityId();

        // Check if player is still holding the flute
        boolean holdingFlute = player.getHeldItemMainhand().getItem() instanceof ItemFlute
                || player.getHeldItemOffhand().getItem() instanceof ItemFlute;

        if (!holdingFlute && playerNoteTicks.containsKey(entityId)) {
            stopFluteMelody(player, world);
            return;
        }

        if (playerNoteTicks.containsKey(entityId)) {
            int tick = playerNoteTicks.get(entityId);
            if (tick % 10 == 0) {
                float volume = 0.6F + world.rand.nextFloat() * 0.8F;
                float pitch = 0.5F + world.rand.nextFloat() * 1.5F;
                world.playSound(null, player.posX, player.posY, player.posZ,
                        SoundEvents.BLOCK_NOTE_FLUTE, SoundCategory.PLAYERS, volume, pitch);
            }
            playerNoteTicks.put(entityId, tick + 1);
        }
    }
}
