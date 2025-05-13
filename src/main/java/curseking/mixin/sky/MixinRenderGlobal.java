package curseking.mixin.sky;

import curseking.biome.BiomeGraveForgottenDeity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@SideOnly(Side.CLIENT)
@Mixin(RenderGlobal.class)
public abstract class MixinRenderGlobal {

    @Redirect(method = "renderSky(FI)V",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/texture/TextureManager;bindTexture(Lnet/minecraft/util/ResourceLocation;)V",
                    ordinal = 0))
    private void redirectBindSunTexture(TextureManager manager, ResourceLocation location) {
        Minecraft mc = Minecraft.getMinecraft();
        EntityPlayer player = mc.player;
        if (player != null && player.world != null) {
            Biome biome = player.world.getBiome(player.getPosition());
            if (biome instanceof BiomeGraveForgottenDeity) {
                manager.bindTexture(new ResourceLocation("curseking:textures/environment/newsun2.png"));
                return;
            }
        }

        manager.bindTexture(location);
    }

    @Shadow
    public TextureManager renderEngine;

    @Inject(method = "renderSky(FI)V", at = @At("TAIL"))
    private void afterSkyRender(float partialTicks, int pass, CallbackInfo ci) {

        Minecraft mc = Minecraft.getMinecraft();
        EntityPlayer player = mc.player;

        if (player == null || player.world == null) return;

        Biome biome = player.world.getBiome(player.getPosition());

        if (!(biome instanceof BiomeGraveForgottenDeity)) return;

        GlStateManager.pushMatrix();
        GlStateManager.disableLighting();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
        GlStateManager.depthMask(false);

        renderEngine.bindTexture(new ResourceLocation("curseking:textures/environment/eye.png"));
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F); // full brightness/opacity


        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);

        Random rand = new Random(54321L);

        for (int i = 0; i < 60; ++i) {
            // Generate a point on a unit sphere
            double dx = rand.nextFloat() * 2.0F - 1.0F;
            double dy = rand.nextFloat() * 2.0F - 1.0F;
            double dz = rand.nextFloat() * 2.0F - 1.0F;

            double distSq = dx * dx + dy * dy + dz * dz;
            if (distSq >= 0.01D && distSq <= 1.0D) {
                double dist = Math.sqrt(distSq);
                dx /= dist;
                dy /= dist;
                dz /= dist;

                double px = dx * 100.0D;
                double py = dy * 100.0D;
                double pz = dz * 100.0D;

                double baseSize = 4.0D;
                double sizeVariation = 12.0D;
                double size = baseSize + ((rand.nextDouble() - 0.5D) * sizeVariation);


                // Rotation for quad
                double angle = rand.nextDouble() * Math.PI * 2.0D;
                double sinA = Math.sin(angle);
                double cosA = Math.cos(angle);

                // Build quad same way as renderStars does
                for (int j = 0; j < 4; ++j) {
                    double offX = ((j & 2) - 1) * size;
                    double offY = ((j + 1 & 2) - 1) * size;

                    double x1 = offX * cosA - offY * sinA;
                    double y1 = offY * cosA + offX * sinA;

                    // Align to sphere surface
                    double rx = x1 * dz - y1 * dx * dy;
                    double ry = y1 * (1.0D - dy * dy);
                    double rz = -x1 * dx - y1 * dy * dz;

                    double finalX = px + rx;
                    double finalY = py + ry;
                    double finalZ = pz + rz;

                    buffer.pos(finalX, finalY, finalZ).tex((j & 2) >> 1, ((j + 1 & 2) >> 1)).endVertex();
                }
            }
        }

        tessellator.draw();

        GlStateManager.depthMask(true);
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }
}
