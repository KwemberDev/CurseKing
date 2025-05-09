package curseking.mobs.AIHelper;

import curseking.config.CurseKingConfig;
import curseking.mobs.EntityAquaRegia;
import curseking.mobs.projectiles.EntityLightningOrb;
import curseking.mobs.projectiles.EntityWaterProjectile;
import curseking.mobs.projectiles.EntityWaterProjectileGravity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class EntityAIAquaRegiaAttack extends EntityAIBase {
    private final EntityAquaRegia entity;
    private int attackCooldown = 0;
    private int stateTimer = 0;
    private State currentState = State.MOVING;
    private Vec3d targetPosition;

    private int spiralStep = 0;
    private boolean isPerformingSpiral = false;

    private boolean isPerformingSpam = false;
    private int spamStep = 0;

    private enum State {
        MOVING,
        ATTACKING
    }

    public EntityAIAquaRegiaAttack(EntityAquaRegia entity) {
        this.entity = entity;
        this.setMutexBits(3); // Movement and look
    }

    @Override
    public boolean shouldExecute() {
        EntityLivingBase target = entity.getAttackTarget();
        return target != null && target.isEntityAlive();
    }

    @Override
    public void updateTask() {
        EntityLivingBase target = entity.getAttackTarget();
        if (target == null) return;

        switch (currentState) {
            case MOVING:
                if (targetPosition == null || stateTimer <= 0) {
                    Random rand = entity.getRNG();
                    double angle = rand.nextDouble() * 2 * Math.PI;
                    double radius = 5 + rand.nextDouble() * 5;
                    double offsetX = Math.cos(angle) * radius;
                    double offsetZ = Math.sin(angle) * radius;
                    targetPosition = new Vec3d(
                            target.posX + offsetX,
                            target.posY + (3D + rand.nextFloat() * 1),
                            target.posZ + offsetZ
                    );
                    stateTimer = 60; // Time to move to the position
                }

                entity.getMoveHelper().setMoveTo(targetPosition.x, targetPosition.y, targetPosition.z, CurseKingConfig.mobSettings.aquaRegiusBossStats.aquaRegiusAttackMovementSpeed);

                if (entity.getDistance(targetPosition.x, targetPosition.y, targetPosition.z) < 1.0D || stateTimer-- <= 0) {
                    currentState = State.ATTACKING;
                    stateTimer = 150;
                }
                break;

            case ATTACKING:
                entity.getMoveHelper().setMoveTo(entity.posX, entity.posY, entity.posZ, 0.0D);
                entity.getLookHelper().setLookPositionWithEntity(target, 30.0F, 30.0F);

                if (attackCooldown-- <= 0 || isPerformingSpiral) {
                    World world = entity.world;
                    Random rand = entity.getRNG();

                    if (!world.isRemote) {
                        if (rand.nextFloat() > 0.25 && !isPerformingSpiral && !isPerformingSpam) { // 0.25
                            for (int i = 0; i < 5; i++) {
                                EntityLightningOrb orb = new EntityLightningOrb(world, entity);
                                double dx = target.posX - entity.posX;
                                double dy = target.posY + target.getEyeHeight() - orb.posY;
                                double dz = target.posZ - entity.posZ;
                                orb.shoot(dx, dy, dz, 1.4F, 15F);

                                float horizMag = (float) Math.sqrt(dx * dx + dz * dz);
                                float yaw = (float) (Math.atan2(dz, dx) * (180D / Math.PI)) - 90F;
                                float pitch = (float) (-Math.atan2(dy, horizMag) * (180D / Math.PI)) - 20F;
                                orb.rotationYaw = yaw;
                                orb.rotationPitch = pitch;
                                orb.prevRotationYaw = yaw;
                                orb.prevRotationPitch = pitch;

                                world.spawnEntity(orb);
                                stateTimer--;
                            }
                        } else if ((rand.nextFloat() > 0.40 && !isPerformingSpiral) || isPerformingSpam) { // 0.5
                            // NEW: Water Orb Arch Attack
                            isPerformingSpam = true;
                            if (spamStep < 2) {
                                int numProjectiles = 100 + rand.nextInt(30);
                                for (int i = 0; i < numProjectiles; i++) {
                                    double angle = rand.nextDouble() * 2 * Math.PI;
                                    double speed = 0.3 + rand.nextDouble() * 0.8;
                                    double vx = Math.cos(angle) * speed;
                                    double vz = Math.sin(angle) * speed;
                                    double vy = 0.2 + rand.nextDouble() * 0.8;

                                    EntityWaterProjectileGravity projectile = new EntityWaterProjectileGravity(world);
                                    projectile.setPosition(entity.posX, entity.posY + entity.height * 0.7, entity.posZ);
                                    projectile.motionX = vx;
                                    projectile.motionY = vy;
                                    projectile.motionZ = vz;
                                    world.spawnEntity(projectile);
                                }
                                stateTimer--;
                                spamStep++;
                            } else {
                                isPerformingSpam = false;
                                spamStep = 0;
                            }
                        } else {
                            isPerformingSpiral = true;
                            this.entity.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1.0F);
                            if (spiralStep < 300) {
                                for (int i = 0; i < 4; i++) {
                                    double angle = spiralStep * Math.PI / 10;
                                    double radius = 1.5 + 0.05 * spiralStep;
                                    double offsetX = Math.cos(angle) * radius;
                                    double offsetZ = Math.sin(angle) * radius;

                                    float extraOffsetX = (rand.nextFloat() * 2) - 1.0F;
                                    float extraOffsetZ = (rand.nextFloat() * 2) - 1.0F;
                                    float extraOffsetY = (rand.nextFloat() * 2) - 1.0F;

                                    EntityWaterProjectile projectile = new EntityWaterProjectile(world, entity);
                                    projectile.setPositionAndRotation(entity.posX + offsetX + extraOffsetX,
                                            entity.posY + 15.0D + extraOffsetY,
                                            entity.posZ + offsetZ + extraOffsetZ, 0, 90);
                                    world.spawnEntity(projectile);

                                    spawnWater(EnumParticleTypes.DRIP_WATER, world,
                                            entity.posX + offsetX, entity.posY + 5.0D, entity.posZ + offsetZ,
                                            0.0D, 0.0D, 0.0D);
                                    spiralStep++;
                                }
                                stateTimer--;
                            } else if (spiralStep >= 300) {
                                isPerformingSpiral = false;
                                spiralStep = 0;
                                this.entity.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(0.4F);
                                for (EntityWaterProjectile projectile : world.getEntitiesWithinAABB(EntityWaterProjectile.class, entity.getEntityBoundingBox().grow(30.0D))) {
                                    projectile.motionY = -1D;
                                }
                            }
                        }
                    }
                    attackCooldown = 40 + rand.nextInt(20);
                }
                if (!isPerformingSpiral && stateTimer-- <= 0 && !isPerformingSpam) {
                    currentState = State.MOVING;
                    targetPosition = null;
                }
        }
    }

    @SideOnly(Side.CLIENT)
    private void spawnWater(EnumParticleTypes particle, World world, double x, double y, double z, double dx, double dy, double dz) {
        if (world.isRemote) {
            world.spawnParticle(particle, x, y, z, dx, dy, dz);
        }
    }
}