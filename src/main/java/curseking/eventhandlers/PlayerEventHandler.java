package curseking.eventhandlers;

import curseking.CurseDataProvider;
import curseking.CurseKing;
import curseking.ICurseData;
import curseking.network.CurseSyncPacket;
import curseking.particles.ParticleNimbleFootstep;
import curseking.particles.ParticleSlothFootstep;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

@Mod.EventBusSubscriber
public class PlayerEventHandler {

    @SubscribeEvent
    public static void onPlayerLogin(net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent event) {
        if (!event.player.world.isRemote) {
            sendCurseDataToClient((EntityPlayerMP) event.player);
        }
    }

    @SubscribeEvent
    public static void onPlayerDeath(PlayerEvent.PlayerRespawnEvent event) {
        if (!event.player.world.isRemote) {
            sendCurseDataToClient((EntityPlayerMP) event.player);
        }
    }

    @SubscribeEvent
    public static void onPlayerChangeDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
        if (!event.player.world.isRemote) {
            sendCurseDataToClient((EntityPlayerMP) event.player);
        }
    }

    public static void sendCurseDataToClient(EntityPlayerMP player) {
        ICurseData data = player.getCapability(CurseDataProvider.CURSE_DATA_CAP, null);
        if (data != null) {
            CurseKing.NETWORK.sendTo(new CurseSyncPacket(data), player);
        }
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        EntityPlayer player = event.player;
        if (player == null || !player.world.isRemote) return;

        ICurseData data = player.getCapability(CurseDataProvider.CURSE_DATA_CAP, null);

        boolean hasNimble = data != null && data.hasBlessing("blessing_nimble");
        boolean hasSloth = data != null && data.hasCurse("curse_sloth");

        if (!hasNimble && !hasSloth) return;

        if (player.onGround && (player.moveForward != 0 || player.moveStrafing != 0)) {
            if (player.ticksExisted % 20 == 0) {
                double x = player.posX;
                double y = player.posY;
                double z = player.posZ;

                World world = player.world;
                Minecraft mc = Minecraft.getMinecraft();

                if (hasNimble) {
                    mc.effectRenderer.addEffect(new ParticleNimbleFootstep(mc.getTextureManager(), world, x, y, z, player.rotationYaw));
                }

                if (hasSloth) {
                    mc.effectRenderer.addEffect(new ParticleSlothFootstep(mc.getTextureManager(), world, x, y, z, player.rotationYaw));
                }
            }
        }
    }
}
