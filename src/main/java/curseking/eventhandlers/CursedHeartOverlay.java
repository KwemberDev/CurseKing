package curseking.eventhandlers;

import curseking.CurseDataProvider;
import curseking.CurseKing;
import curseking.ICurseData;
import curseking.curses.CurseOfDecayingFlesh;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.util.FoodStats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.client.GuiIngameForge;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Random;

@Mod.EventBusSubscriber(modid = CurseKing.MODID)
public class CursedHeartOverlay {

    @SubscribeEvent
    public static void onRenderPre(RenderGameOverlayEvent.Pre event) {
        if (event.getType() != RenderGameOverlayEvent.ElementType.ARMOR) return;

        Minecraft mc = Minecraft.getMinecraft();
        EntityPlayer player = mc.player;
        if (player == null || !player.isEntityAlive()) return;

        ICurseData data = player.getCapability(CurseDataProvider.CURSE_DATA_CAP, null);
        final int DECAY_HEALTH = CurseOfDecayingFlesh.DECAY_HEALTH;

        float playerMaxHealth = player.getMaxHealth();
        int playerMaxHearts = MathHelper.ceil(playerMaxHealth / 2);
        int playerHealthRows = MathHelper.ceil((float) playerMaxHearts / 10);

        int rowHeight = Math.max(10 - (playerHealthRows - 2), 3);
        if (rowHeight == 11) rowHeight = 10;

        if (data != null && data.hasCurse("curse_decay")) {
            int playerMaxHeartsCursed = MathHelper.ceil((playerMaxHealth + DECAY_HEALTH) / 2);
            int playerCursedHealthRows = MathHelper.ceil((float) playerMaxHeartsCursed / 10);

            if (playerCursedHealthRows > playerHealthRows) {
                GuiIngameForge.left_height += rowHeight;
            }
        }

        if (data != null && data.hasBlessing("blessing_ironskin")) {
            int playerMaxHeartsCursed = MathHelper.ceil((playerMaxHealth + DECAY_HEALTH) / 2);
            int playerCursedHealthRows = MathHelper.ceil((float) playerMaxHeartsCursed / 10);
            if (playerCursedHealthRows == playerHealthRows) {
                GuiIngameForge.left_height += 3 ;
            }
        }
    }

    @SubscribeEvent
    public static void onRenderFood(RenderGameOverlayEvent.Pre event) {
        if (event.getType() != RenderGameOverlayEvent.ElementType.FOOD) return;
        Minecraft mc = Minecraft.getMinecraft();
        EntityPlayer player = mc.player;
        if (player == null) return;

        event.setCanceled(true);

        FoodStats stats = player.getFoodStats();
        int level = stats.getFoodLevel();
        float saturation = stats.getSaturationLevel();

        GlStateManager.enableBlend();
        int width = event.getResolution().getScaledWidth();
        int height = event.getResolution().getScaledHeight();
        int left = width / 2 + 91;
        int top = height - GuiIngameForge.right_height;
        GuiIngameForge.right_height += 10;
        boolean isSatiated = false;
        boolean isCursed = false;
        if (player.isEntityAlive()) {
            ICurseData data = player.getCapability(CurseDataProvider.CURSE_DATA_CAP, null);
            if (data != null && data.hasBlessing("blessing_satiated")) {
                isSatiated = true;
            }
            if (data != null && data.hasCurse("curse_starving")) {
                isCursed = true;
            }
        }

        Random rand = new Random();
        rand.setSeed(player.ticksExisted * 312871L);

        for (int i = 0; i < 10; ++i) {
            int idx = i * 2 + 1;
            int x = left - i * 8 - 9;
            int y = top;
            int icon = 16;
            byte background = 0;

            if (mc.player.isPotionActive(MobEffects.HUNGER)) {
                icon += 36;
                background = 13;
            }

            if (saturation <= 0.0F && mc.ingameGUI.getUpdateCounter() % (level * 3 + 1) == 0) {
                y = top + (rand.nextInt(3) - 1);
            }

            if (isSatiated && !mc.player.isPotionActive(MobEffects.HUNGER) && !isCursed) {
                mc.getTextureManager().bindTexture(new ResourceLocation(CurseKing.MODID, "textures/gui/foodblessing3.png"));
                mc.ingameGUI.drawTexturedModalRect(x, y, 0, 0, 9, 9);

            } else if (isCursed && !mc.player.isPotionActive(MobEffects.HUNGER)) {
                mc.getTextureManager().bindTexture(new ResourceLocation(CurseKing.MODID, "textures/gui/foodcurse.png"));
                mc.ingameGUI.drawTexturedModalRect(x, y, 0, 0, 9, 9);
            } else {
                mc.getTextureManager().bindTexture(Gui.ICONS);
                mc.ingameGUI.drawTexturedModalRect(x, y, 16 + background * 9, 27, 9, 9);
            }

            mc.getTextureManager().bindTexture(Gui.ICONS);
            if (idx < level)
                mc.ingameGUI.drawTexturedModalRect(x, y, icon + 36, 27, 9, 9);
            else if (idx == level)
                mc.ingameGUI.drawTexturedModalRect(x, y, icon + 45, 27, 9, 9);
        }

        GlStateManager.disableBlend();
    }
}