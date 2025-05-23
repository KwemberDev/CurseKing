package curseking.mixin.sky;

import curseking.CurseKing;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

import static curseking.eventhandlers.mobeventhandlers.WeatherEventHandler.*;

// In your client mod or mixin package
@Mixin(EntityRenderer.class)
public class MixinEntityRenderer {

    private final Random random = new Random();
    private static final ResourceLocation RAIN_TEXTURES = new ResourceLocation("textures/environment/rain.png");

    @Shadow
    private int rendererUpdateCount;

    @Shadow
    private float[] rainXCoords;

    @Shadow
    private float[] rainYCoords;

    @Shadow
    private ResourceLocation locationLightMap;


    @Inject(
            method = "renderWorldPass",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/profiler/Profiler;endStartSection(Ljava/lang/String;)V",
                    ordinal = 14
            )
    )
    private void onRenderWorld(int pass, float partialTicks, long finishTmeNano, CallbackInfo ci) {
        Minecraft mc = Minecraft.getMinecraft();
        EntityPlayer player = mc.player;
        if (player != null && isFoundBoss() && isForcingRain()) {
            if (mc.world.getRainStrength(partialTicks) == 0.0F) {
                renderForcedRain(partialTicks);
            }
        }
    }

    @SideOnly(Side.CLIENT)
    private void renderForcedRain(float partialTicks) {

        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GlStateManager.matrixMode(5890);
        GlStateManager.loadIdentity();
        GlStateManager.scale(0.00390625F, 0.00390625F, 0.00390625F);
        GlStateManager.translate(8.0F, 8.0F, 8.0F);
        GlStateManager.matrixMode(5888);
        Minecraft.getMinecraft().getTextureManager().bindTexture(this.locationLightMap);
        GlStateManager.glTexParameteri(3553, 10241, 9729);
        GlStateManager.glTexParameteri(3553, 10240, 9729);
        GlStateManager.glTexParameteri(3553, 10242, 10496);
        GlStateManager.glTexParameteri(3553, 10243, 10496);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.enableTexture2D();
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);

        Entity entity = Minecraft.getMinecraft().getRenderViewEntity();
        World world = Minecraft.getMinecraft().world;
        int i = MathHelper.floor(entity.posX);
        int j = MathHelper.floor(entity.posY);
        int k = MathHelper.floor(entity.posZ);

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        GlStateManager.disableCull();
        GlStateManager.glNormal3f(0.0F, 1.0F, 0.0F);
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.alphaFunc(516, 0.1F);
        double d0 = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * (double) partialTicks;
        double d1 = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * (double) partialTicks;
        double d2 = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * (double) partialTicks;
        int l = MathHelper.floor(d1);
        int i1 = 10;

        int j1 = -1;
        bufferbuilder.setTranslation(-d0, -d1, -d2);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

        for (int k1 = k - i1; k1 <= k + i1; ++k1) {
            for (int l1 = i - i1; l1 <= i + i1; ++l1) {
                int i2 = (k1 - k + 16) * 32 + l1 - i + 16;
                double d3 = (double) this.rainXCoords[i2] * 0.5D;
                double d4 = (double) this.rainYCoords[i2] * 0.5D;
                blockpos$mutableblockpos.setPos(l1, 0, k1);
                Biome biome = world.getBiome(blockpos$mutableblockpos);

                if (biome.canRain() || biome.getEnableSnow()) {
                    int j2 = world.getPrecipitationHeight(blockpos$mutableblockpos).getY();
                    int k2 = j - i1;
                    int l2 = j + i1;

                    if (k2 < j2) {
                        k2 = j2;
                    }

                    if (l2 < j2) {
                        l2 = j2;
                    }

                    int i3 = j2;

                    if (j2 < l) {
                        i3 = l;
                    }

                    if (k2 != l2) {
                        random.setSeed((long) (l1 * l1 * 3121 + l1 * 45238971 ^ k1 * k1 * 418711 + k1 * 13761));
                        blockpos$mutableblockpos.setPos(l1, k2, k1);
                        float f2 = biome.getTemperature(blockpos$mutableblockpos);

                        if (world.getBiomeProvider().getTemperatureAtHeight(f2, j2) >= 0.15F) {
                            if (j1 != 0) {
                                if (j1 >= 0) {
                                    tessellator.draw();
                                }

                                j1 = 0;
                                Minecraft.getMinecraft().getTextureManager().bindTexture(RAIN_TEXTURES);
                                bufferbuilder.begin(7, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);
                            }

                            double d5 = -((double) (this.rendererUpdateCount + l1 * l1 * 3121 + l1 * 45238971 + k1 * k1 * 418711 + k1 * 13761 & 31) + (double) partialTicks) / 32.0D * (3.0D + this.random.nextDouble());
                            double d6 = (double) ((float) l1 + 0.5F) - entity.posX;
                            double d7 = (double) ((float) k1 + 0.5F) - entity.posZ;
                            float f3 = MathHelper.sqrt(d6 * d6 + d7 * d7) / (float) i1;
                            float f4 = ((1.0F - f3 * f3) * 0.5F + 0.5F);
                            blockpos$mutableblockpos.setPos(l1, i3, k1);
                            int j3 = world.getCombinedLight(blockpos$mutableblockpos, 0);
                            int k3 = j3 >> 16 & 65535;
                            int l3 = j3 & 65535;
                            bufferbuilder.pos((double) l1 - d3 + 0.5D, (double) l2, (double) k1 - d4 + 0.5D).tex(0.0D, (double) k2 * 0.25D + d5).color(1.0F, 1.0F, 1.0F, f4).lightmap(k3, l3).endVertex();
                            bufferbuilder.pos((double) l1 + d3 + 0.5D, (double) l2, (double) k1 + d4 + 0.5D).tex(1.0D, (double) k2 * 0.25D + d5).color(1.0F, 1.0F, 1.0F, f4).lightmap(k3, l3).endVertex();
                            bufferbuilder.pos((double) l1 + d3 + 0.5D, (double) k2, (double) k1 + d4 + 0.5D).tex(1.0D, (double) l2 * 0.25D + d5).color(1.0F, 1.0F, 1.0F, f4).lightmap(k3, l3).endVertex();
                            bufferbuilder.pos((double) l1 - d3 + 0.5D, (double) k2, (double) k1 - d4 + 0.5D).tex(0.0D, (double) l2 * 0.25D + d5).color(1.0F, 1.0F, 1.0F, f4).lightmap(k3, l3).endVertex();
                        }
                    }
                }
            }
        }

        if (j1 >= 0) {
            tessellator.draw();
        }

        bufferbuilder.setTranslation(0.0D, 0.0D, 0.0D);
        GlStateManager.enableCull();
        GlStateManager.disableBlend();
        GlStateManager.alphaFunc(516, 0.1F);

        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GlStateManager.disableTexture2D();
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
    }
}