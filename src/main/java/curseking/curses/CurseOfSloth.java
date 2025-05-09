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
public class CurseOfSloth {

    private static final UUID SLUGGISH_MOVEMENT_UUID = UUID.fromString("f49dd0cb-623e-47e4-90c3-fb12b9626c02");
    private static final UUID SLUGGISH_ATTACK_UUID = UUID.fromString("d984c345-4f6a-4f6b-8576-6ccae3c1c55f");

    private static final double slothMovement = CurseKingConfig.defaultCurses.SlothMovementSpeedDecrease;
    public static final double slothAttack = CurseKingConfig.defaultCurses.SlothAttackSpeedDecrease;

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END || event.player.world.isRemote) return;

        EntityPlayer player = event.player;
        ICurseData data = player.getCapability(CurseDataProvider.CURSE_DATA_CAP, null);

        if (data == null) return;

        IAttributeInstance moveSpeed = player.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED);
        IAttributeInstance attackSpeed = player.getEntityAttribute(SharedMonsterAttributes.ATTACK_SPEED);

        if (data.hasCurse("curse_sloth")) {
            // Movement speed (reduce by 15%)
            if (moveSpeed.getModifier(SLUGGISH_MOVEMENT_UUID) == null) {
                AttributeModifier slowMove = new AttributeModifier(
                        SLUGGISH_MOVEMENT_UUID,
                        "Curse of Sloth - Movement",
                        -slothMovement,
                        2 // operation 2 = multiply base
                );
                moveSpeed.applyModifier(slowMove);
            }

            // Attack speed (reduce by 10%)
            if (attackSpeed.getModifier(SLUGGISH_ATTACK_UUID) == null) {
                AttributeModifier slowAttack = new AttributeModifier(
                        SLUGGISH_ATTACK_UUID,
                        "Curse of Sloth - Attack",
                        -slothAttack,
                        2
                );
                attackSpeed.applyModifier(slowAttack);
            }
        } else {
            if (moveSpeed.getModifier(SLUGGISH_MOVEMENT_UUID) != null) {
                moveSpeed.removeModifier(SLUGGISH_MOVEMENT_UUID);
            }

            if (attackSpeed.getModifier(SLUGGISH_ATTACK_UUID) != null) {
                attackSpeed.removeModifier(SLUGGISH_ATTACK_UUID);
            }
        }
    }
}
