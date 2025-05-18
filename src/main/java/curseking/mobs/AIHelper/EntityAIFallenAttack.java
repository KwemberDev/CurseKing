package curseking.mobs.AIHelper;

import curseking.mobs.EntityTheFallen;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.DamageSource;

import java.util.Random;

public class EntityAIFallenAttack extends EntityAIBase {
    private final EntityTheFallen entity;
    private EntityLivingBase target;
    private int attackCooldown = 0;
    private final double speedTowardsTarget = 0.8D;
    private final int attackInterval = 39; // ticks (2 seconds)
    private final int attackIntervalLight = 31; // blocks
    private boolean heavy;
    private final Random random = new Random();

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
        entity.setAttacking(false);
    }

    @Override
    public void updateTask() {
        if (target == null || !target.isEntityAlive()) {
            entity.setAttacking(false);
            return;
        }

        entity.getLookHelper().setLookPositionWithEntity(target, 30.0F, 30.0F);

        double distance = entity.getDistance(target.posX, target.posY, target.posZ);
        double attackReach = 3D; // 3 blocks

        if (distance > attackReach && (!heavy)) {
            // Move towards target if out of range
            entity.getNavigator().tryMoveToEntityLiving(target, speedTowardsTarget);
            if (!heavy) {
                attackCooldown++;
                if (attackCooldown > attackIntervalLight) {
                    attackCooldown = 0;
                    entity.setAttacking(false);
                }
            } else {
                attackCooldown = 0;
            }
        } else {
            // In range: stop moving and attack
            entity.getNavigator().clearPath();
            if (!entity.isAttacking()) {
                this.heavy = random.nextFloat() < 0.3F; // 30% chance for heavy or fast
                entity.startAttackAnimation(heavy);
                attackCooldown = 0;
            }
            attackCooldown++;
            if (attackCooldown == 27 && heavy) {
                if (target.isEntityAlive() && entity.getDistance(target) < 5D && isFacingTarget(entity, target, 135)) {
                    entity.doMeleeAttack(target);
                }
            } else if (attackCooldown == 12 && !heavy) {
                if (target.isEntityAlive() && entity.getDistance(target) < 4D) {
                    float fastDamage = (float) entity.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue() * 0.5F;
                    target.attackEntityFrom(DamageSource.causeMobDamage(this.entity), fastDamage);
                }
            }
            if (attackCooldown >= attackInterval && heavy) {
                entity.setAttacking(false);
                attackCooldown = 0;
                heavy = false;
            } else if (attackCooldown >= attackIntervalLight && !heavy) {
                entity.setAttacking(false);
                attackCooldown = 0;
                heavy = false;
            }
        }
    }

    private boolean isFacingTarget(EntityLivingBase attacker, EntityLivingBase target, double maxAngleDegrees) {
        double dx = target.posX - attacker.posX;
        double dz = target.posZ - attacker.posZ;
        double targetAngle = Math.toDegrees(Math.atan2(dz, dx)) - 90.0; // Minecraft's 0 is south
        double facingAngle = attacker.rotationYaw;
        double angleDiff = Math.abs(net.minecraft.util.math.MathHelper.wrapDegrees(targetAngle - facingAngle));
        return angleDiff < (maxAngleDegrees / 2);
    }

    @Override
    public boolean shouldContinueExecuting() {
        return shouldExecute();
    }
}