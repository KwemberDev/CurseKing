package curseking.curses;

import curseking.CurseDataProvider;
import curseking.ICurseData;
import curseking.config.CurseKingConfig;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.FoodStats;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

@Mod.EventBusSubscriber
public class CurseOfStarving {

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END || event.player.world.isRemote) return;

        EntityPlayer player = event.player;
        ICurseData data = player.getCapability(CurseDataProvider.CURSE_DATA_CAP, null);

        if (data != null && data.hasCurse("curse_starving")) {
            if (player.ticksExisted % CurseKingConfig.defaultCurses.StarvingTriggerTiming == 0) {
                FoodStats foodStats = player.getFoodStats();

                if (foodStats.getSaturationLevel() > 0) {
                    foodStats.setFoodSaturationLevel(Math.max(0, foodStats.getSaturationLevel() - (float) CurseKingConfig.defaultCurses.StarvingSaturationDecrease));
                } else if (foodStats.getFoodLevel() > 0) {
                    foodStats.setFoodLevel(foodStats.getFoodLevel() - CurseKingConfig.defaultCurses.StarvingHungerDecrease);
                }

            }
        }
    }
}
