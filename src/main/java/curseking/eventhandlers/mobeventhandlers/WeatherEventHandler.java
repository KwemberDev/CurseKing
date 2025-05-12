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

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.player.world.isRemote) return;
        if (!(event.player instanceof EntityPlayerMP)) return;

        EntityPlayerMP player = (EntityPlayerMP) event.player;
        boolean foundBoss = false;
        for (Entity entity : player.world.loadedEntityList) {
            if (entity instanceof EntityAquaRegia && entity.getDistance(player) <= WEATHER_RADIUS) {
                foundBoss = true;
                break;
            }
        }

        if (foundBoss && !isForcingRain) {
            CurseKing.logger.debug("STARTING RAIN");
            player.connection.sendPacket(new SPacketChangeGameState(7, 1.0F)); // Start rain
            player.connection.sendPacket(new SPacketChangeGameState(8, 0.0F)); // No thunder
            isForcingRain = true;
        } else if (!foundBoss && isForcingRain) {
            CurseKing.logger.debug("STOPPING RAIN");
            player.connection.sendPacket(new SPacketChangeGameState(7, 0.0F)); // Stop rain
            player.connection.sendPacket(new SPacketChangeGameState(8, 0.0F)); // Stop thunder
            isForcingRain = false;
        }
    }
}