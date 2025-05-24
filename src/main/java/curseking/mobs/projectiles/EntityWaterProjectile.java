package curseking.mobs.projectiles;

import curseking.config.CurseKingConfig;
import curseking.mobs.EntityAquaRegia;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntityWaterProjectile extends EntityThrowable {

    private boolean inGround = false;

    public EntityWaterProjectile(World worldIn) {
        super(worldIn);
        this.setSize(0.75F, 0.75F);
    }

    public EntityWaterProjectile(World worldIn, EntityLivingBase thrower) {
        super(worldIn, thrower);
        this.setSize(0.75F, 0.75F);
    }

    @Override
    protected void onImpact(RayTraceResult result) {
        if (!this.world.isRemote) {
            if (result.entityHit != null && !(result.entityHit instanceof EntityAquaRegia)) {
                result.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), CurseKingConfig.mobSettings.aquaRegiusBossStats.secondaryBossAttackDamage);
                this.setDead();
            }
            if (result.typeOfHit == RayTraceResult.Type.BLOCK) {
                this.motionX = 0;
                this.motionY = 0;
                this.motionZ = 0;
                this.inGround = true;
            }
        }
    }

    @Override
    public void onUpdate() {
        this.setSize(0.75F, 0.75F);
        this.prevRotationYaw = this.rotationYaw;
        this.prevRotationPitch = this.rotationPitch;
        if (!inGround) {
            super.onUpdate();
            if (this.ticksExisted % 10 == 0) {
                this.world.spawnParticle(EnumParticleTypes.DRIP_WATER, this.posX + (rand.nextFloat() * 0.2) - 0.1, this.posY + (rand.nextFloat() * 0.2) - 0.1, this.posZ + (rand.nextFloat() * 0.2) - 0.1, 0, 0, 0);
            }
            this.prevPosY = posY;
        }
        if (this.ticksExisted > 200) {
            this.setDead();
        }
        this.setRotation(0,0);
    }

    @Override
    public void onEntityUpdate() {
        super.onEntityUpdate();
        this.setRotation(0,0);
        this.prevRotationYaw = this.rotationYaw;
        this.prevRotationPitch = this.rotationPitch;
        this.prevPosY = posY;
    }

    @Override
    protected float getGravityVelocity() {
        return 0.0F;
    }
}