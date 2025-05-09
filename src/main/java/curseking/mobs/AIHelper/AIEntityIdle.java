package curseking.mobs.AIHelper;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;

public class AIEntityIdle extends EntityAIBase {

    private EntityLiving entity;

    private int idleTime;
    private int idleTimeMin = 20;
    private int idleTimeRange = 20;

    private double lookVecX;
    private double lookVecZ;

    public AIEntityIdle(EntityLiving entity) {
        this.setMutexBits(3);
        this.entity = entity;
    }

    public boolean shouldExecute() {
        return this.entity.getRNG().nextFloat() < 0.02F;
    }

    public boolean shouldContinueExecuting() {
        return this.idleTime >= 0;
    }

    public void startExecuting() {
        double d0 = (Math.PI * 2D) * this.entity.getRNG().nextDouble();
        this.lookVecX = Math.cos(d0);
        this.lookVecZ = Math.sin(d0);
        this.idleTime = idleTimeMin + this.entity.getRNG().nextInt(idleTimeRange);
    }

    public void updateTask() {
        this.idleTime--;
        this.entity.getLookHelper().setLookPosition(
                this.entity.posX + this.lookVecX,
                this.entity.posY + (double)this.entity.getEyeHeight(),
                this.entity.posZ + this.lookVecZ, 10.0F,
                (float)this.entity.getVerticalFaceSpeed()
        );
    }
}
