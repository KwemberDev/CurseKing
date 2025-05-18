package curseking.mobs.AIHelper;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;

public class AILookClosest extends EntityAIBase {

    private final EntityLiving entity;

    protected Entity closestEntity;

    private final Class watchedClass = EntityLivingBase.class;
    private final float maxDistanceForPlayer = 4.0F;
    private int lookTime;
    private final int lookTimeMin = 40;
    private final int lookTimeRange = 40;
    private final float lookChance = 0.02F;

    public AILookClosest(EntityLiving entity) {
        this.entity = entity;
        this.setMutexBits(2);
    }

    public boolean shouldExecute() {
        if(this.entity.getRNG().nextFloat() >= this.lookChance)
            return false;
        else {
            if(this.entity.getAttackTarget() != null)
                this.closestEntity = this.entity.getAttackTarget();
            if(this.watchedClass == EntityPlayer.class)
                this.closestEntity = this.entity.getEntityWorld().getClosestPlayerToEntity(this.entity, this.maxDistanceForPlayer);
            else
                this.closestEntity = this.entity.getEntityWorld().findNearestEntityWithinAABB(this.watchedClass, this.entity.getEntityBoundingBox().grow(this.maxDistanceForPlayer, 3.0D, this.maxDistanceForPlayer), this.entity);

            return this.closestEntity != null;
        }
    }

    public boolean shouldContinueExecuting() {
        if(!this.closestEntity.isEntityAlive())
            return false;
        if(this.entity.getDistance(this.closestEntity) > (double)(this.maxDistanceForPlayer * this.maxDistanceForPlayer))
            return false;
        return this.lookTime > 0;
    }

    public void startExecuting() {
        this.lookTime = lookTimeMin + this.entity.getRNG().nextInt(lookTimeRange);
    }

    public void resetTask() {
        this.closestEntity = null;
    }


    public void updateTask() {
        this.entity.getLookHelper().setLookPosition(this.closestEntity.posX, this.closestEntity.posY + (double)this.closestEntity.getEyeHeight(), this.closestEntity.posZ, 10.0F, (float)this.entity.getVerticalFaceSpeed());
        this.lookTime--;
    }
}
