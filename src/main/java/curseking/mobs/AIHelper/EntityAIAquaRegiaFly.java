package curseking.mobs.AIHelper;

import curseking.mobs.EntityAquaRegia;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.math.Vec3d;

public class EntityAIAquaRegiaFly extends EntityAIBase {

    private final EntityAquaRegia entity;
    private final int cooldown;
    private final double speed;
    private double xPosWander;
    private double yPosWander;
    private double zPosWander;

    public EntityAIAquaRegiaFly(EntityAquaRegia entity, double speed, int cooldown) {
        this.entity = entity;
        this.speed = speed;
        this.setMutexBits(1);
        this.cooldown = cooldown;
    }

    public boolean shouldExecute() {
        if (this.cooldown != 0 && this.entity.getRNG().nextInt(this.cooldown) != 0) {
            return false;
        } else {
            Vec3d target = RandomFlyPosition.getRandomFlyablePosition(this.entity, 15, 10);
            if (target == null) {
                return false;
            } else {
                this.xPosWander = target.x;
                this.yPosWander = target.y;
                this.zPosWander = target.z;
                return true;
            }
        }
    }

    public boolean shouldContinueExecuting() {
        if (this.entity.getDistance(this.xPosWander, this.yPosWander, this.zPosWander) < 2) {
            this.entity.getNavigator().clearPath();
            return false;
        } else {
            this.entity.getMoveHelper().setMoveTo(xPosWander, yPosWander, zPosWander, speed);
            return true;
        }
    }

    public void startExecuting() {
        this.entity.getMoveHelper().setMoveTo(xPosWander, yPosWander, zPosWander, speed);
    }
}