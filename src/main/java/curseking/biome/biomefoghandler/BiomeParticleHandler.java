package curseking.biome.biomefoghandler;

import curseking.biome.BiomeGraveForgottenDeity;
import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
@Mod.EventBusSubscriber(Side.CLIENT)
public class BiomeParticleHandler {

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        Minecraft mc = Minecraft.getMinecraft();
        if (mc.player == null || mc.world == null) return;

        int chunkRadius = 6;
        int chunkSize = 16;
        BlockPos playerPos = mc.player.getPosition();
        int playerChunkX = playerPos.getX() >> 4;
        int playerChunkZ = playerPos.getZ() >> 4;

        for (int dx = -chunkRadius; dx <= chunkRadius; dx++) {
            for (int dz = -chunkRadius; dz <= chunkRadius; dz++) {
                int chunkX = playerChunkX + dx;
                int chunkZ = playerChunkZ + dz;
                int x = (chunkX << 4) + mc.world.rand.nextInt(chunkSize);
                int z = (chunkZ << 4) + mc.world.rand.nextInt(chunkSize);
                int y = mc.world.getHeight(x, z) + 1 + mc.world.rand.nextInt(4);

                BlockPos pos = new BlockPos(x, y, z);
                Biome biome = mc.world.getBiome(pos);

                if (biome instanceof BiomeGraveForgottenDeity) {
                    if (mc.world.rand.nextFloat() < 0.5F) {
                        mc.world.spawnParticle(EnumParticleTypes.TOWN_AURA, x + 0.5, y + 0.5, z + 0.5, 0, 0.01, 0);
                    }
                }
            }
        }
    }
}