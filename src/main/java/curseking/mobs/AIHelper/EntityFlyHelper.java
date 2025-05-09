package curseking.mobs.AIHelper;

import net.minecraft.entity.EntityFlying;
import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.util.math.MathHelper;

public class EntityFlyHelper extends EntityMoveHelper {
    private final EntityFlying entity;

    public EntityFlyHelper(EntityFlying entity) {
        super(entity);
        this.entity = entity;
    }

    @Override
    public void onUpdateMoveHelper() {
        if (this.action == Action.MOVE_TO) {
            double dx = this.posX - entity.posX;
            double dy = this.posY - entity.posY;
            double dz = this.posZ - entity.posZ;
            double dist = Math.sqrt(dx * dx + dy * dy + dz * dz);

            if (dist < 0.1) {
                this.action = Action.WAIT;
                return;
            }

            dx /= dist;
            dy /= dist;
            dz /= dist;

            entity.motionX += (dx * this.speed - entity.motionX) * 0.1;
            entity.motionY += (dy * this.speed - entity.motionY) * 0.1;
            entity.motionZ += (dz * this.speed - entity.motionZ) * 0.1;

            entity.rotationYaw = -((float) MathHelper.atan2(dx, dz)) * (180F / (float)Math.PI);
            entity.renderYawOffset = entity.rotationYaw;
        }
    }
}
