package curseking.mobs.AIHelper;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;

public class EntityAINearestAttackableTargetFlying extends EntityAITargetFlying {
    private final EntityLiving entity;
    private EntityLivingBase target;
    private final Class<? extends EntityLivingBase> targetClass;

    public EntityAINearestAttackableTargetFlying(EntityLiving entity, Class<? extends EntityLivingBase> targetClass) {
        super(entity, false, false);
        this.entity = entity;
        this.targetClass = targetClass;
    }

    @Override
    public boolean shouldExecute() {
        double range = 16.0D;
        this.target = this.entity.world.getEntitiesWithinAABB(this.targetClass, this.entity.getEntityBoundingBox().grow(range, range, range))
                .stream()
                .filter(e -> e != this.entity && this.isSuitableTarget(e, false))
                .min((e1, e2) -> Double.compare(this.entity.getDistanceSq(e1), this.entity.getDistanceSq(e2)))
                .orElse(null);
        return this.target != null;
    }

    @Override
    public void startExecuting() {
        this.taskOwner.setAttackTarget(this.target);
        super.startExecuting();
    }
}