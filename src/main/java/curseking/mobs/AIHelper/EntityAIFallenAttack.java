package curseking.mobs.AIHelper;

import curseking.CurseKing;
import curseking.mobs.EntityTheFallen;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;

public class EntityAIFallenAttack extends EntityAIBase {
    private final EntityTheFallen entity;
    private EntityLivingBase target;
    private int attackCooldown = 0;
    private final double speedTowardsTarget = 0.8D;
    private final int attackInterval = 39; // ticks (2 seconds)

    public EntityAIFallenAttack(EntityTheFallen entity) {
        this.entity = entity;
        this.setMutexBits(3); // movement + look
    }

    @Override
    public boolean shouldExecute() {
        target = entity.getAttackTarget();
        return target != null && target.isEntityAlive();
    }

    @Override
    public void startExecuting() {
        attackCooldown = 0;
    }

    @Override
    public void resetTask() {
        entity.getNavigator().clearPath();
    }

    @Override
    public void updateTask() {
        if (target == null) {
            entity.setAttacking(false);
            return;
        }

        entity.getLookHelper().setLookPositionWithEntity(target, 30.0F, 30.0F);

        double distance = entity.getDistance(target.posX, target.posY, target.posZ);
        double attackReach = 3.0D; // 3 blocks

        if (distance > attackReach && !entity.isAttacking()) {
            // Move towards target if out of range
            entity.getNavigator().tryMoveToEntityLiving(target, speedTowardsTarget);
            attackCooldown = 0;
        } else {
            // In range: stop moving and attack
            entity.getNavigator().clearPath();
            if (!entity.isAttacking()) {
                entity.startAttackAnimation();
                attackCooldown = 0;
            }
            attackCooldown++;
            if (attackCooldown == 27) { // Damage at the right tick
                if (target.isEntityAlive() && entity.getDistance(target) < 4D) {
                    entity.doMeleeAttack(target);
                }
            }
            if (attackCooldown >= attackInterval) {
                entity.setAttacking(false);
                CurseKing.logger.debug("SET ATTACKING TO FALSE.");
                attackCooldown = 0;
            }
        }
    }

    @Override
    public boolean shouldContinueExecuting() {
        return shouldExecute();
    }
}