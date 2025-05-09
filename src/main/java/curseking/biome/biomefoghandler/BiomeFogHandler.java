package curseking.biome.biomefoghandler;

import curseking.biome.BiomeGraveForgottenDeity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
@Mod.EventBusSubscriber
public class BiomeFogHandler {

    @SubscribeEvent
    public static void onRenderFog(EntityViewRenderEvent.RenderFogEvent event) {
        Minecraft mc = Minecraft.getMinecraft();
        if (mc.player == null || mc.world == null) return;

        World world = mc.world;
        BlockPos pos = mc.player.getPosition();
        Biome current = world.getBiome(pos);

        if (!(current instanceof BiomeGraveForgottenDeity)) return;

        BlockPos playerBlockPos = mc.player.getPosition();
        Vec3d playerExactPos = mc.player.getPositionVector();
        float factor = getBiomeFogFactor(world, playerBlockPos, playerExactPos, 25, current);

        float curve = (float) Math.pow(factor, 3); // Fog grows slowly at edge, faster inside

        GlStateManager.enableFog();
        GlStateManager.setFog(GlStateManager.FogMode.EXP2);

        // Less dense at edges (factor ≈ 0), denser in center (factor ≈ 1)
        float density = 0.0025F + curve * 0.02F; // Tweak range: softer edge, dense inside
        GlStateManager.setFogDensity(density);
    }

    @SubscribeEvent
    public static void onFogColors(EntityViewRenderEvent.FogColors event) {
        Minecraft mc = Minecraft.getMinecraft();
        if (mc.player == null || mc.world == null) return;

        World world = mc.world;
        BlockPos pos = mc.player.getPosition();
        Biome current = world.getBiome(pos);

        if (!(current instanceof BiomeGraveForgottenDeity)) return;

        BlockPos playerBlockPos = mc.player.getPosition();
        Vec3d playerExactPos = mc.player.getPositionVector();
        float factor = getBiomeFogFactor(world, playerBlockPos, playerExactPos, 25, current);

        float curve = (float) Math.pow(factor, 3);

        float baseR = event.getRed();
        float baseG = event.getGreen();
        float baseB = event.getBlue();

        float fogR = 0.15F;
        float fogG = 0.15F;
        float fogB = 0.15F;

        event.setRed(baseR - curve * (baseR - fogR));
        event.setGreen(baseG - curve * (baseG - fogG));
        event.setBlue(baseB - curve * (baseB - fogB));
    }

    public static float getBiomeFogFactor(World world, BlockPos centerPos, Vec3d playerPos, int maxRadius, Biome targetBiome) {
        double closestDistSq = Double.MAX_VALUE;
        BlockPos.MutableBlockPos checkPos = new BlockPos.MutableBlockPos();

        for (int dx = -maxRadius; dx <= maxRadius; dx++) {
            for (int dz = -maxRadius; dz <= maxRadius; dz++) {
                checkPos.setPos(centerPos.getX() + dx, centerPos.getY(), centerPos.getZ() + dz);
                if (world.getBiome(checkPos) != targetBiome) {
                    double blockCenterX = checkPos.getX() + 0.5;
                    double blockCenterZ = checkPos.getZ() + 0.5;

                    double distSq = playerPos.squareDistanceTo(blockCenterX, playerPos.y, blockCenterZ);
                    if (distSq < closestDistSq) {
                        closestDistSq = distSq;
                    }
                }
            }
        }

        if (closestDistSq == Double.MAX_VALUE) {
            return 1.0F;
        }

        double distance = Math.sqrt(closestDistSq);
        float factor = (float) Math.min(distance * 1.4F / maxRadius, 1.0);
        return factor;
    }
}
