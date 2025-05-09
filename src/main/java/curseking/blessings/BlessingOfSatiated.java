package curseking.blessings;

import curseking.CurseDataProvider;
import curseking.ICurseData;
import curseking.config.CurseKingConfig;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

@Mod.EventBusSubscriber
public class BlessingOfSatiated {

    @SubscribeEvent
    public static void onFinishEating(LivingEntityUseItemEvent.Finish event) {
        if (!(event.getEntityLiving() instanceof EntityPlayer)) return;

        EntityPlayer player = (EntityPlayer) event.getEntityLiving();
        ItemStack item = event.getItem();

        if (!(item.getItem() instanceof ItemFood)) return;

        ICurseData data = player.getCapability(CurseDataProvider.CURSE_DATA_CAP, null);
        if (data != null && data.hasBlessing("blessing_satiated")) {
            player.getEntityData().setBoolean("curseking_blessing_satiated_pending", true);
        }
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;

        EntityPlayer player = event.player;

        NBTTagCompound data = player.getEntityData();
        if (data.getBoolean("curseking_blessing_satiated_pending")) {
            ICurseData curseData = player.getCapability(CurseDataProvider.CURSE_DATA_CAP, null);
            if (curseData != null && curseData.hasBlessing("blessing_satiated")) {
                player.getFoodStats().addStats(CurseKingConfig.defaultBlessings.ExtraHungerFillIncrease, (float) CurseKingConfig.defaultBlessings.ExtraSaturationFillIncrease);
            }
            data.removeTag("curseking_blessing_satiated_pending");
        }
    }


}

