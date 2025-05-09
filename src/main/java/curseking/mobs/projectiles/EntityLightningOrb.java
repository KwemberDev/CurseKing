package curseking.mobs.projectiles;

import curseking.config.CurseKingConfig;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntityLightningOrb extends EntityThrowable {

    public EntityLightningOrb(World worldIn) {
        super(worldIn);
    }

    public EntityLightningOrb(World worldIn, EntityLivingBase throwerIn) {
        super(worldIn, throwerIn);
    }

    public EntityLightningOrb(World worldIn, double x, double y, double z) {
        super(worldIn, x, y, z);
    }

    @Override
    protected void onImpact(RayTraceResult result) {
        if (!this.world.isRemote) {
            BlockPos strikePos = result.getBlockPos() != null ? result.getBlockPos() : this.getPosition();

            if (rand.nextFloat() < 0.333F) {
                EntityLightningBolt lightning = new EntityLightningBolt(world, strikePos.getX(), strikePos.getY(), strikePos.getZ(), false);
                world.addWeatherEffect(lightning);
            }

            if (result.entityHit instanceof EntityLivingBase) {
                result.entityHit.attackEntityFrom(DamageSource.LIGHTNING_BOLT, CurseKingConfig.mobSettings.aquaRegiusBossStats.primaryBossAttackDamage);
            }

            this.setDead();
        }
    }

    private boolean rotationLocked = false;
    private float lockedYaw, lockedPitch;

    @Override
    public void onUpdate() {
        super.onUpdate();
        this.setSize(0.75F, 0.75F);

        if (!rotationLocked) {
            lockedYaw = this.rotationYaw;
            lockedPitch = this.rotationPitch;
            rotationLocked = true;
        } else {
            this.rotationYaw = lockedYaw;
            this.prevRotationYaw = lockedYaw;
            this.rotationPitch = lockedPitch;
            this.prevRotationPitch = lockedPitch;
        }

        if (this.ticksExisted % 2 == 0) {
            this.world.spawnParticle(EnumParticleTypes.WATER_BUBBLE, this.posX + (rand.nextFloat() * 0.2) - 0.1, this.posY + (rand.nextFloat() * 0.2) - 0.1, this.posZ + (rand.nextFloat() * 0.2) - 0.1, 0, 0, 0);

        }
        if (this.ticksExisted > 200) {
            this.setDead();
        }
    }

    @Override
    protected float getGravityVelocity() {
        return 0.0F;
    }
}
