package curseking.mobs.helperentities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntityWhirl extends EntityThrowable {
    private int stayTicks = 0;
    private boolean stopped = false;

    public EntityWhirl(World worldIn) {
        super(worldIn);
        this.setSize(1.0F, 7.0F);
        this.isImmuneToFire = true;
    }

    public EntityWhirl(World worldIn, double x, double y, double z, double velX, double velY, double velZ) {
        this(worldIn);
        this.setPosition(x, y, z);
        this.motionX = velX;
        this.motionY = velY;
        this.motionZ = velZ;
    }

    @Override
    protected void entityInit() {
    }

    @Override
    protected void onImpact(RayTraceResult result) {
        if (result.typeOfHit == RayTraceResult.Type.BLOCK && !stopped) {
            this.motionX = 0;
            this.motionY = 0;
            this.motionZ = 0;
            this.stopped = true;
            this.stayTicks = 300; // Stay for 60 ticks (3 seconds)
        }
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        if (stopped) {
            this.motionX = 0;
            this.motionY = 0;
            this.motionZ = 0;
            if (--stayTicks <= 0) {
                this.setDead();
            }
        }
        if (this.ticksExisted > 400) {
            this.setDead();
        }
        if (!this.world.isRemote) {
            for (Entity entity : this.world.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox())) {
                if (entity instanceof net.minecraft.entity.EntityLivingBase) {
                    entity.attackEntityFrom(net.minecraft.util.DamageSource.causeThrownDamage(this, this.getThrower()), 6.0F);
                }
            }
        }
    }

    @Override
    public float getGravityVelocity() {
        return 0.0F;
    }
}