package curseking.eventhandlers;

import curseking.CurseDataProvider;
import curseking.CurseKing;
import curseking.ICurseData;
import curseking.config.CurseKingConfig;
import curseking.config.ScalingHealthConfigUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.client.GuiIngameForge;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.io.File;

@Mod.EventBusSubscriber(modid = CurseKing.MODID)
public class HeartCompat {

    // COMPAT CODE FOR WHEN SCALING HEALTH IN INSTALLED.
    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void renderCursedHearts(RenderGameOverlayEvent.Pre event) {
        if (event.getType() != RenderGameOverlayEvent.ElementType.FOOD) return;
        if (!(Loader.isModLoaded("scalinghealth"))) return;
        File configDir = new File(Minecraft.getMinecraft().gameDir, "config");
        if (!ScalingHealthConfigUtil.isCustomHeartRenderingEnabled(configDir)) return;
        Minecraft mc = Minecraft.getMinecraft();
        EntityPlayer player = mc.player;

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

        GlStateManager.pushMatrix();
        GlStateManager.translate(0, 0, 1000);

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
                    GlStateManager.enableBlend();
                    mc.ingameGUI.drawTexturedModalRect(x, y, 0, 0, 9, 9);
                    GlStateManager.disableBlend();
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
                    GlStateManager.enableBlend();
                    mc.ingameGUI.drawTexturedModalRect(x, y, 0, 0, 9, 12);
                    GlStateManager.disableBlend();
                }
            }
        }
        GlStateManager.disableBlend();
        mc.getTextureManager().bindTexture(GuiIngameForge.ICONS);
        GlStateManager.popMatrix();
    }
}