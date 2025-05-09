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
public class BlessingOfTheNimble {

    private static final UUID NIMBLE_MOVEMENT_UUID = UUID.fromString("f49dd0cb-623e-47e4-90c3-fb12b9626cf3");
    private static final UUID NIMBLE_ATTACK_UUID = UUID.fromString("d984c345-4f6a-4f6b-8576-6ccae3c1c5f3");

    private static final double nimbleMovement = CurseKingConfig.defaultBlessings.NimbleMovementSpeedIncrease;
    private static final double nimbleAttack = CurseKingConfig.defaultBlessings.NimbleAttackSpeedIncrease;

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END || event.player.world.isRemote) return;

        EntityPlayer player = event.player;
        ICurseData data = player.getCapability(CurseDataProvider.CURSE_DATA_CAP, null);

        if (data == null) return;

        IAttributeInstance moveSpeed = player.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED);
        IAttributeInstance attackSpeed = player.getEntityAttribute(SharedMonsterAttributes.ATTACK_SPEED);

        if (data.hasBlessing("blessing_nimble")) {
            if (moveSpeed.getModifier(NIMBLE_MOVEMENT_UUID) == null) {
                AttributeModifier fastMove = new AttributeModifier(
                        NIMBLE_MOVEMENT_UUID,
                        "Blessing of the Nimble - Movement",
                        nimbleMovement,
                        2 // operation 2 = multiply base
                );
                moveSpeed.applyModifier(fastMove);
            }

            if (attackSpeed.getModifier(NIMBLE_ATTACK_UUID) == null) {
                AttributeModifier fastAttack = new AttributeModifier(
                        NIMBLE_ATTACK_UUID,
                        "Blessing of the Nimble - Attack",
                        nimbleAttack,
                        2
                );
                attackSpeed.applyModifier(fastAttack);
            }
        } else {
            if (moveSpeed.getModifier(NIMBLE_MOVEMENT_UUID) != null) {
                moveSpeed.removeModifier(NIMBLE_MOVEMENT_UUID);
            }

            if (attackSpeed.getModifier(NIMBLE_ATTACK_UUID) != null) {
                attackSpeed.removeModifier(NIMBLE_ATTACK_UUID);
            }
        }
    }
}