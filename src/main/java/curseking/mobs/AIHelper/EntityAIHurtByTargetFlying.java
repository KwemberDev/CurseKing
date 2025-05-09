package curseking.mobs.AIHelper;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.AxisAlignedBB;

import java.util.List;

public class EntityAIHurtByTargetFlying extends EntityAITargetFlying {
    private final boolean callsForHelp;
    private int revengeTimerOld;

    public EntityAIHurtByTargetFlying(EntityLiving entity, boolean callsForHelp) {
        super(entity, true, false);
        this.callsForHelp = callsForHelp;
    }

    @Override
    public boolean shouldExecute() {
        int revengeTimer = this.taskOwner.getRevengeTimer();
        EntityLivingBase attacker = this.taskOwner.getRevengeTarget();
        return revengeTimer != this.revengeTimerOld && attacker != null && this.isSuitableTarget(attacker, false);
    }

    @Override
    public void startExecuting() {
        this.taskOwner.setAttackTarget(this.taskOwner.getRevengeTarget());
        this.revengeTimerOld = this.taskOwner.getRevengeTimer();

        if (this.callsForHelp) {
            double range = this.getTargetDistance();
            List<EntityLiving> list = this.taskOwner.world.getEntitiesWithinAABB(
                    this.taskOwner.getClass(),
                    (new AxisAlignedBB(
                            this.taskOwner.posX - range, this.taskOwner.posY - range, this.taskOwner.posZ - range,
                            this.taskOwner.posX + range, this.taskOwner.posY + range, this.taskOwner.posZ + range
                    ))
            );
            for (EntityLiving ally : list) {
                if (ally != this.taskOwner && ally.getAttackTarget() == null && !ally.isOnSameTeam(this.taskOwner.getRevengeTarget())) {
                    ally.setAttackTarget(this.taskOwner.getRevengeTarget());
                }
            }
        }
        super.startExecuting();
    }
}