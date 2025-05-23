package curseking.mixin;

import curseking.CurseDataProvider;
import curseking.CurseKing;
import curseking.ICurseData;
import curseking.config.CurseKingConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.client.GuiIngameForge;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.Loader;
import net.silentchaos512.scalinghealth.config.Config;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(net.silentchaos512.scalinghealth.client.HeartDisplayHandler.class)
public class HeartCompat {

    // COMPAT CODE FOR WHEN SCALING HEALTH IN INSTALLED.
    @Inject(method = "renderHearts", at = @At("TAIL"), remap = false)
    public void renderCursedHearts(RenderGameOverlayEvent event, Minecraft mc, EntityPlayer player, CallbackInfo ci) {

        if (!(Loader.isModLoaded("scalinghealth") && Config.Client.Hearts.customHeartRendering)) return;
        if (player == null || !player.isEntityAlive()) return;

        int width = event.getResolution().getScaledWidth();
        int height = event.getResolution().getScaledHeight();
        int left = width / 2 - 91;
        int top = height - 39;

        int playerMaxHearts = MathHelper.ceil(player.getMaxHealth() / 2.0F);
        int customHearts = CurseKingConfig.defaultCurses.DecayHealthDecrease / 2;

        // === Blessing/Curse Detection ===
        ICurseData data = player.getCapability(CurseDataProvider.CURSE_DATA_CAP, null);
        int blessedHearts = 0;
        if (data != null && data.hasBlessing("blessing_ironskin")) {
            blessedHearts = MathHelper.ceil((float) CurseKingConfig.defaultBlessings.IronSkinHealthIncrease / 2);
        }

        int currentHealthHearts = MathHelper.ceil(player.getHealth() / 2.0F);
        int currentHealthRow = (currentHealthHearts - 1) / 10;

        GlStateManager.enableBlend();
        GlStateManager.pushMatrix();
        GlStateManager.translate(0, 0, 500);

        // === Render Cursed Hearts ===
        if (data.hasCurse("curse_decay")) {

            mc.getTextureManager().bindTexture(new ResourceLocation(CurseKing.MODID, "textures/gui/cursed2.png"));
            for (int i = 0; i < customHearts; i++) {
                int heartIndex = playerMaxHearts + i;
                int row = heartIndex / 10;
                int col = heartIndex % 10;
                if (row == currentHealthRow) {
                    int x = left + col * 8;
                    int y = top;
                    mc.ingameGUI.drawTexturedModalRect(x, y, 0, 0, 9, 9);
                }
            }
        }

        // === Render Blessed Hearts ===
        if (blessedHearts > 0) {
            mc.getTextureManager().bindTexture(new ResourceLocation(CurseKing.MODID, "textures/gui/blessedheart.png"));
            for (int i = 0; i < blessedHearts; i++) {
                int heartIndex = playerMaxHearts - i - 2;
                int row = heartIndex / 10;
                int col = heartIndex % 10;
                if (row == currentHealthRow && heartIndex >= 0) {
                    int x = left + col * 8;
                    int y = top - 3; // match y offset for blessed hearts
                    mc.ingameGUI.drawTexturedModalRect(x, y, 0, 0, 9, 12);
                }
            }
        }
        mc.getTextureManager().bindTexture(GuiIngameForge.ICONS);
        GlStateManager.popMatrix();
        GlStateManager.disableBlend();
    }
}