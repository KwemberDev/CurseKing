package curseking.mobs.projectiles;

import curseking.config.CurseKingConfig;
import curseking.mobs.EntityAquaRegia;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IProjectile;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.*;
import net.minecraft.world.World;

import java.util.List;

public class EntityWaterProjectileGravity extends Entity implements IProjectile {

    private boolean inGround = false;
    private BlockPos stuckPos;
    private float storedYaw;
    private float storedPitch;
    int ticksInGround = 0;

    public EntityWaterProjectileGravity(World world) {
        super(world);
        this.setSize(0.5F, 0.5F);
    }

    public EntityWaterProjectileGravity(World world, double x, double y, double z) {
        this(world);
        this.setPosition(x, y, z);
    }

    @Override
    protected void entityInit() {}

    @Override
    public void shoot(double x, double y, double z, float velocity, float inaccuracy) {
        float f = MathHelper.sqrt(x * x + y * y + z * z);
        x /= f;
        y /= f;
        z /= f;

        x += this.rand.nextGaussian() * 0.0075D * inaccuracy;
        y += this.rand.nextGaussian() * 0.0075D * inaccuracy;
        z += this.rand.nextGaussian() * 0.0075D * inaccuracy;

        x *= velocity;
        y *= velocity;
        z *= velocity;

        this.motionX = x;
        this.motionY = y;
        this.motionZ = z;

        float horizMag = MathHelper.sqrt(x * x + z * z);
        this.rotationYaw = (float)(MathHelper.atan2(x, z) * (180D / Math.PI));
        this.rotationPitch = (float)(MathHelper.atan2(y, horizMag) * (180D / Math.PI));
        this.prevRotationYaw = this.rotationYaw;
        this.prevRotationPitch = this.rotationPitch;
    }

    @Override
    public void onUpdate() {
        super.onUpdate();

        if (this.inGround) {
            this.motionX = 0;
            this.motionY = 0;
            this.motionZ = 0;
            this.rotationYaw = storedYaw;
            this.rotationPitch = storedPitch;
            if (this.ticksExisted - ticksInGround > 100) {
                this.setDead();
            }
            return;
        }

        if (this.ticksExisted % 8 == 0) {
            this.world.spawnParticle(EnumParticleTypes.DRIP_WATER, this.posX + (rand.nextFloat() * 0.2) - 0.1, this.posY + (rand.nextFloat() * 0.2) - 0.1, this.posZ + (rand.nextFloat() * 0.2) - 0.1, 0, 0, 0);

        }

        Vec3d currentPos = new Vec3d(this.posX, this.posY, this.posZ);
        Vec3d nextPos = new Vec3d(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
        RayTraceResult blockHit = this.world.rayTraceBlocks(currentPos, nextPos);

        Entity hitEntity = null;
        List<Entity> entities = this.world.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().expand(motionX, motionY, motionZ).grow(1D));
        double closestDistance = Integer.MAX_VALUE;

        for (Entity entity : entities) {
            if (entity.canBeCollidedWith() && !(entity instanceof EntityAquaRegia) && !(entity instanceof EntityWaterProjectileGravity)) {
                AxisAlignedBB aabb = entity.getEntityBoundingBox().grow(0.3D);
                RayTraceResult entityHit = aabb.calculateIntercept(currentPos, nextPos);
                if (entityHit != null) {
                    double distance = currentPos.distanceTo(entityHit.hitVec);
                    if (distance < closestDistance) {
                        hitEntity = entity;
                        closestDistance = distance;
                    }
                }
            }
        }

        if (hitEntity != null) {
            hitEntity.attackEntityFrom((new EntityDamageSourceIndirect("arrow", this, this)).setProjectile(), CurseKingConfig.mobSettings.aquaRegiusBossStats.secondaryBossAttackDamage);
            this.setDead();
            return;
        }

        if (blockHit != null) {
            this.inGround = true;
            this.motionX = 0;
            this.motionY = 0;
            this.motionZ = 0;
            this.setPosition(blockHit.hitVec.x, blockHit.hitVec.y, blockHit.hitVec.z);
            storedYaw = this.rotationYaw;
            storedPitch = this.rotationPitch;
            return;
        }

        this.posX += this.motionX;
        this.posY += this.motionY;
        this.posZ += this.motionZ;

        this.motionY -= 0.05D;

        float horizSpeed = MathHelper.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
        this.rotationYaw = (float)(MathHelper.atan2(this.motionX, this.motionZ) * (180D / Math.PI));
        this.rotationPitch = (float)(MathHelper.atan2(this.motionY, horizSpeed) * (180D / Math.PI));

        this.setPosition(this.posX, this.posY, this.posZ);

        if (this.ticksExisted > 150) {
            this.setDead();
        }
    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound compound) {
        inGround = compound.getBoolean("InGround");
        if (compound.hasKey("Pos")) {
            NBTTagList pos = compound.getTagList("Pos", 6);
            this.posX = pos.getDoubleAt(0);
            this.posY = pos.getDoubleAt(1);
            this.posZ = pos.getDoubleAt(2);
        }
    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound compound) {
        compound.setBoolean("InGround", inGround);
    }

    @Override
    public boolean canBeCollidedWith() {
        return true;
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (!this.world.isRemote) {
            this.setDead();
        }
        return true;
    }
}
