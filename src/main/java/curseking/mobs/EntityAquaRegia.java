package curseking.mobs;

import curseking.config.CurseKingConfig;
import curseking.mobs.AIHelper.*;
import curseking.mobs.soundhelper.MovingEntitySound;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityFlying;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateFlying;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.BossInfo;
import net.minecraft.world.BossInfoServer;
import net.minecraft.world.World;

import static curseking.CurseKing.MODID;

public class EntityAquaRegia extends EntityFlying {

    private final BossInfoServer bossInfo = (BossInfoServer)(new BossInfoServer(this.getDisplayName(), BossInfo.Color.BLUE, BossInfo.Overlay.PROGRESS).setCreateFog(true));
    private static final DataParameter<Boolean> ATTACKING = EntityDataManager.<Boolean>createKey(EntityGhast.class, DataSerializers.BOOLEAN);

    public EntityAquaRegia(World worldIn) {
        super(worldIn);
        this.setSize(0.9F, 2F);
        this.experienceValue = 100;
        this.isImmuneToFire = true;
        this.moveHelper = new EntityFlyHelper(this);
        this.enablePersistence();
    }

    @Override
    protected void initEntityAI() {
        this.tasks.addTask(8, new EntityAIAquaRegiaFly(this, CurseKingConfig.mobSettings.aquaRegiusBossStats.aquaRegiusSpeed, 80));
        this.tasks.addTask(9, new AILookClosest(this));
        this.tasks.addTask(10, new AIEntityIdle(this));

        this.tasks.addTask(7, new EntityAIAquaRegiaAttack(this));

        this.targetTasks.addTask(1, new EntityAIHurtByTargetFlying(this, false));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTargetFlying(this, EntityWither.class));
    }

    @Override
    protected void entityInit()
    {
        super.entityInit();
        this.dataManager.register(ATTACKING, Boolean.valueOf(false));
        Minecraft.getMinecraft().getSoundHandler().playSound(new MovingEntitySound(this, SoundEvents.MUSIC_CREDITS));

    }

    @Override
    protected PathNavigate createNavigator(World worldIn) {
        return new PathNavigateFlying(this, worldIn);
    }


    @Override
    public boolean isImmuneToExplosions() {
        return true;
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        if (this.ticksExisted % 3 == 0) {
            double x = this.posX + (this.world.rand.nextDouble() - 0.5) * this.width;
            double y = this.posY + this.world.rand.nextDouble() * this.height;
            double z = this.posZ + (this.world.rand.nextDouble() - 0.5) * this.width;
            this.world.spawnParticle(EnumParticleTypes.DRIP_WATER, x, y, z, 0, 0, 0);
        }
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (source == DamageSource.LIGHTNING_BOLT) {
            return false;
        }
        return super.attackEntityFrom(source, amount);
    }

    @Override
    protected ResourceLocation getLootTable() {
        return new ResourceLocation(MODID, "entities/aqua_regia");
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_VEX_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.ENTITY_GUARDIAN_HURT_LAND;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_ELDER_GUARDIAN_DEATH_LAND;
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(CurseKingConfig.mobSettings.aquaRegiusBossStats.aquaRegiusHealth);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(40.0D);
        this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(0.4F);
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(2.0F);
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).setBaseValue(5.0D);
    }

    @Override
    public void onDeath(DamageSource source) {
        super.onDeath(source);
    }

    @Override
    public boolean isOnLadder() {
        return false;
    }

    @Override
    public void fall(float distance, float damageMultiplier) {
        // no fall damage
    }

    @Override
    public void addTrackingPlayer(EntityPlayerMP player) {
        super.addTrackingPlayer(player);
        bossInfo.addPlayer(player);
    }

    @Override
    public void removeTrackingPlayer(EntityPlayerMP player) {
        super.removeTrackingPlayer(player);
        bossInfo.removePlayer(player);
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        bossInfo.setPercent(this.getHealth() / this.getMaxHealth());
    }

    @Override
    public boolean isNonBoss() {
        return false;
    }
}
