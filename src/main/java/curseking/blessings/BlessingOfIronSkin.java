package curseking.blessings;

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
public class BlessingOfIronSkin {

    private static final UUID IRON_SKIN_UUID = UUID.fromString("e4b3a56a-420c-11ee-be56-0242ac120002");
    public static final int IRON_SKIN_HEALTH = CurseKingConfig.defaultBlessings.IronSkinHealthIncrease;

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END || event.player.world.isRemote) return;

        EntityPlayer player = event.player;
        ICurseData data = player.getCapability(CurseDataProvider.CURSE_DATA_CAP, null);

        if (data == null) return;

        IAttributeInstance attr = player.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH);

        if (data.hasBlessing("blessing_ironskin")) {

            if (attr.getModifier(IRON_SKIN_UUID) == null) {

                AttributeModifier modifier = new AttributeModifier(
                        IRON_SKIN_UUID,
                        "Blessing of Iron Skin",
                        IRON_SKIN_HEALTH,
                        0
                );
                attr.applyModifier(modifier);

            }
        } else {
            AttributeModifier attributeModifier = attr.getModifier(IRON_SKIN_UUID);
            if (attributeModifier != null) {
                attr.removeModifier(attributeModifier);
                if (player.getHealth() > player.getMaxHealth()) {
                    player.setHealth(player.getMaxHealth());
                }
            }
        }
    }
}
