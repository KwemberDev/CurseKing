package curseking.eventhandlers.mobeventhandlers;

import curseking.CurseKing;
import curseking.mobs.EntityAquaRegia;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.server.SPacketChangeGameState;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

@Mod.EventBusSubscriber
public class WeatherEventHandler {
    private static final int WEATHER_RADIUS = 64;
    private static boolean isForcingRain = false;
    private static boolean foundBoss = false;

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.player.world.isRemote) return;
        if (!(event.player instanceof EntityPlayerMP)) return;

        EntityPlayerMP player = (EntityPlayerMP) event.player;
        for (Entity entity : player.world.loadedEntityList) {
            if (entity instanceof EntityAquaRegia && entity.getDistance(player) <= WEATHER_RADIUS) {
                foundBoss = true;
                break;
            } else {
                foundBoss = false;
            }
        }

        if (foundBoss && !isForcingRain) {
            isForcingRain = true;
        } else if (!foundBoss && isForcingRain) {
            isForcingRain = false;
        }
    }

    public static boolean isForcingRain() {
        return isForcingRain;
    }

    public static boolean isFoundBoss () {
        return foundBoss;
    }
}