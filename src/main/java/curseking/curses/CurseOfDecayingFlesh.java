package curseking.curses;

import curseking.CurseDataProvider;
import curseking.ICurseData;
import curseking.config.CurseKingConfig;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.UUID;

@Mod.EventBusSubscriber
public class CurseOfDecayingFlesh {

    private static final UUID DECAYING_FLESH_UUID = UUID.fromString("6d8e28d3-bd7c-4b5f-b8a3-0be4262b4c44");
    public static final int DECAY_HEALTH = CurseKingConfig.defaultCurses.DecayHealthDecrease;

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END || event.player.world.isRemote) return;

        EntityPlayer player = event.player;
        ICurseData data = player.getCapability(CurseDataProvider.CURSE_DATA_CAP, null);

        if (data == null) return;

        IAttributeInstance maxHealth = player.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH);

        if (data.hasCurse("curse_decay")) {
            if (maxHealth.getModifier(DECAYING_FLESH_UUID) == null) {
                AttributeModifier modifier = new AttributeModifier(
                        DECAYING_FLESH_UUID,
                        "Curse of Decaying Flesh",
                        -DECAY_HEALTH,
                        0 // operation 0 = flat addition
                );
                maxHealth.applyModifier(modifier);
                if (player.getHealth() > player.getMaxHealth()) {
                    player.setHealth(player.getMaxHealth());
                }
            }
        } else {
            AttributeModifier modifier = maxHealth.getModifier(DECAYING_FLESH_UUID);
            if (modifier != null) {
                maxHealth.removeModifier(modifier);
            }
        }
    }
}
