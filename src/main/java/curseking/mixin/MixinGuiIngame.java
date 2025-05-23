package curseking.mixin;

import curseking.CurseDataProvider;
import curseking.CurseKing;
import curseking.ICurseData;
import curseking.config.CurseKingConfig;
import curseking.config.ScalingHealthConfigUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.client.GuiIngameForge;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Loader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.File;

import static net.minecraftforge.client.GuiIngameForge.left_height;

@Mixin(GuiIngameForge.class)
public abstract class MixinGuiIngame extends GuiIngame {

    public MixinGuiIngame(Minecraft mcIn) {
        super(mcIn);
    }

    /**
     *  Health bar edit
     * @author Kwember
     * @reason curseking health bar edit
     */
    @Inject(method = "renderHealth", at = @At(value = "HEAD"), cancellable = true, remap = false)
    private void onRenderHealth(int width, int height, CallbackInfo ci) {
        if (Loader.isModLoaded("scalinghealth")) return;

        File configDir = new File(Minecraft.getMinecraft().gameDir, "config");
        if (ScalingHealthConfigUtil.isCustomHeartRenderingEnabled(configDir)) return;

        mc.getTextureManager().bindTexture(ICONS);
        if (MinecraftForge.EVENT_BUS.post(new RenderGameOverlayEvent.Pre(new RenderGameOverlayEvent(mc.getRenderPartialTicks(), new ScaledResolution(mc)), RenderGameOverlayEvent.ElementType.HEALTH))) return;
        mc.profiler.startSection("health");
        GlStateManager.enableBlend();

        EntityPlayer player = (EntityPlayer) this.mc.getRenderViewEntity();
        int health = MathHelper.ceil(player.getHealth());
        boolean highlight = healthUpdateCounter > (long) updateCounter && (healthUpdateCounter - (long) updateCounter) / 3L % 2L == 1L;

        if (health < this.playerHealth && player.hurtResistantTime > 0) {
            this.lastSystemTime = Minecraft.getSystemTime();
            this.healthUpdateCounter = this.updateCounter + 20L;
        } else if (health > this.playerHealth && player.hurtResistantTime > 0) {
            this.lastSystemTime = Minecraft.getSystemTime();
            this.healthUpdateCounter = this.updateCounter + 10L;
        }

        if (Minecraft.getSystemTime() - this.lastSystemTime > 1000L) {
            this.playerHealth = health;
            this.lastPlayerHealth = health;
            this.lastSystemTime = Minecraft.getSystemTime();
        }

        this.playerHealth = health;
        int healthLast = this.lastPlayerHealth;

        IAttributeInstance attrMaxHealth = player.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH);
        float healthMax = (float) attrMaxHealth.getAttributeValue();
        float absorb = MathHelper.ceil(player.getAbsorptionAmount());

        int healthRows = MathHelper.ceil((healthMax + absorb) / 2.0F / 10.0F);
        int rowHeight = Math.max(10 - (healthRows - 2), 3);
        if (rowHeight == 11) {
            rowHeight = 10;
        }

        this.rand.setSeed(updateCounter * 312871L);
        int left = width / 2 - 91;
        int top = height - left_height;
        left_height += (healthRows * rowHeight);
        if (rowHeight != 10) left_height += 10 - rowHeight;

        int regen = -1;
        if (player.isPotionActive(MobEffects.REGENERATION)) {
            regen = updateCounter % 25;
        }

        final int TOP = 9 * (mc.world.getWorldInfo().isHardcoreModeEnabled() ? 5 : 0);
        final int BACKGROUND = (highlight ? 25 : 16);
        int MARGIN = 16;
        if (player.isPotionActive(MobEffects.POISON))      MARGIN += 36;
        else if (player.isPotionActive(MobEffects.WITHER)) MARGIN += 72;

        float absorbRemaining = absorb;

        // === Blessing/Curse Detection ===
        ICurseData data = player.getCapability(CurseDataProvider.CURSE_DATA_CAP, null);
        int blessedHearts = 0;
        boolean hasCurseDecay = false;

        if (data != null) {
            if (data.hasBlessing("blessing_ironskin")) blessedHearts = MathHelper.ceil((float) CurseKingConfig.defaultBlessings.IronSkinHealthIncrease / 2);
            hasCurseDecay = data.hasCurse("curse_decay");
        }

        int fullHeartCount = MathHelper.ceil((healthMax + absorb) / 2.0F);
        int baselineMax = hasCurseDecay ? MathHelper.ceil((healthMax + CurseKingConfig.defaultCurses.DecayHealthDecrease) / 2.0F) : fullHeartCount;

        if (hasCurseDecay) {
            mc.getTextureManager().bindTexture(new ResourceLocation(CurseKing.MODID, "textures/gui/cursed2.png"));
            for (int i = fullHeartCount; i < baselineMax; i++) {
                int row = i / 10;
                int x = left + (i % 10) * 8;
                int y = top - (row * rowHeight);
                drawTexturedModalRect(x, y, 0, 0, 9, 9);
            }
        }

        // === Draw Hearts Pass ===
        for (int i = baselineMax - 1; i >= 0; --i) {
            if (hasCurseDecay && i >= fullHeartCount) continue;

            int row = i / 10;
            int x = left + i % 10 * 8;
            int y = top - row * rowHeight;

            if (health <= 4) y += rand.nextInt(2);
            if (i == regen) y -= 2;

            boolean isBlessed = (i >= (healthMax / 2) - blessedHearts);

            if (isBlessed) {
                mc.getTextureManager().bindTexture(new ResourceLocation(CurseKing.MODID, "textures/gui/blessedheart.png"));

                if (highlight || i == regen) {
                    drawTexturedModalRect(x, y - 3, 0, 0, 9, 12); // Use alternate texture region
                } else {
                    drawTexturedModalRect(x, y - 3, 0, 0, 9, 12);
                }
            } else {
                mc.getTextureManager().bindTexture(ICONS);
                drawTexturedModalRect(x, y, BACKGROUND, TOP, 9, 9);
            }


            mc.getTextureManager().bindTexture(ICONS);

            if (highlight && isBlessed) {
                mc.getTextureManager().bindTexture(new ResourceLocation(CurseKing.MODID, "textures/gui/blessedheart.png"));
                if (i * 2 + 1 < healthLast)
                    drawTexturedModalRect(x, y - 3, 10, 0, 9, 12);
                else if (i * 2 + 1 == healthLast)
                    drawTexturedModalRect(x, y - 3, 0, 0, 9, 12);
            }

            mc.getTextureManager().bindTexture(ICONS);

            if (highlight) {
                if (i * 2 + 1 < healthLast)
                    drawTexturedModalRect(x, y, MARGIN + 54, TOP, 9, 9);
                else if (i * 2 + 1 == healthLast)
                    drawTexturedModalRect(x, y, MARGIN + 63, TOP, 9, 9);
            }

            if (absorbRemaining > 0.0F) {
                if (absorbRemaining == absorb && absorb % 2.0F == 1.0F) {
                    drawTexturedModalRect(x, y, MARGIN + 153, TOP, 9, 9);
                    absorbRemaining -= 1.0F;
                } else {
                    drawTexturedModalRect(x, y, MARGIN + 144, TOP, 9, 9);
                    absorbRemaining -= 2.0F;
                }
            } else {
                if (i * 2 + 1 < health)
                    drawTexturedModalRect(x, y, MARGIN + 36, TOP, 9, 9);
                else if (i * 2 + 1 == health)
                    drawTexturedModalRect(x, y, MARGIN + 45, TOP, 9, 9);
            }
        }

        GlStateManager.disableBlend();
        mc.profiler.endSection();
        MinecraftForge.EVENT_BUS.post(new RenderGameOverlayEvent.Post(new RenderGameOverlayEvent(mc.getRenderPartialTicks(), new ScaledResolution(mc)), RenderGameOverlayEvent.ElementType.HEALTH));
        ci.cancel();
    }
}
