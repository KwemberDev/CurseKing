package curseking.mobs;

import curseking.CurseKing;
import curseking.config.CurseKingConfig;
import curseking.mobs.AIHelper.*;
import curseking.mobs.soundhelper.MovingEntitySound;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityFlying;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
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
import software.bernie.geckolib3.core.AnimationState;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.lang.reflect.Field;

import static curseking.CurseKing.MODID;

public class EntityAquaRegia extends EntityFlying implements IAnimatable {

    private final AnimationFactory factory = new AnimationFactory(this);

    private final BossInfoServer bossInfo = (BossInfoServer)(new BossInfoServer(this.getDisplayName(), BossInfo.Color.BLUE, BossInfo.Overlay.PROGRESS).setCreateFog(true));
    private static final DataParameter<Boolean> ATTACKING = EntityDataManager.<Boolean>createKey(EntityAquaRegia.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> AGGRO = EntityDataManager.<Boolean>createKey(EntityAquaRegia.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> HOSTILEPOS = EntityDataManager.<Boolean>createKey(EntityAquaRegia.class, DataSerializers.BOOLEAN);
    public static final DataParameter<String> CURRENTSTATE = EntityDataManager.createKey(EntityAquaRegia.class, DataSerializers.STRING);
    private static final DataParameter<Integer> ATTACKTIMER = EntityDataManager.createKey(EntityAquaRegia.class, DataSerializers.VARINT);

    private static final int ARROW_SHOT_DURATION = 40;
    private static final int WATERSPOUT_DURATION = 60;
    private static final int ARROW_RAIN_DURATION = 60;

    public enum AquaRegiaState {
        NEUTRAL_IDLE,
        HOSTILE_POSITION,
        HOSTILE_INIT,
        HOSTILE_ARROW_SHOT,
        HOSTILE_WATERSPOUT,
        HOSTILE_ARROW_RAIN
    }

    public EntityAquaRegia(World worldIn) {
        super(worldIn);
        this.setSize(0.90F, 2F);
        this.experienceValue = 100;
        this.isImmuneToFire = true;
        this.moveHelper = new EntityFlyHelper(this);
        this.enablePersistence();
        this.setEntityBoundingBox(this.getEntityBoundingBox().expand(0,0.4D, 0));
    }

    @Override
    protected void initEntityAI() {
        this.tasks.addTask(7, new EntityAIAquaRegiaFly(this, CurseKingConfig.mobSettings.aquaRegiusBossStats.aquaRegiusSpeed, 80));
        this.tasks.addTask(9, new AILookClosest(this));
        this.tasks.addTask(10, new AIEntityIdle(this));

        this.tasks.addTask(6, new EntityAIAquaRegiaAttack(this));

        this.targetTasks.addTask(1, new EntityAIHurtByTargetFlying(this, false));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTargetFlying(this, EntityWither.class));
    }

    public void setAquaRegiaState(AquaRegiaState state) {
        if (!this.getDataManager().get(CURRENTSTATE).equals(state.name())) {
            this.getDataManager().set(CURRENTSTATE, state.name());
        }
    }

    public AquaRegiaState getAquaRegiaState() {
        return AquaRegiaState.valueOf(this.getDataManager().get(CURRENTSTATE));
    }

    @Override
    protected void entityInit()
    {
        super.entityInit();
        this.dataManager.register(ATTACKING, Boolean.FALSE);
        this.dataManager.register(AGGRO, Boolean.FALSE);
        this.dataManager.register(HOSTILEPOS, Boolean.FALSE);
        this.dataManager.register(CURRENTSTATE, AquaRegiaState.NEUTRAL_IDLE.name());
        this.dataManager.register(ATTACKTIMER, 0);
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
        CurseKing.logger.debug("1. {}", this.getAttackTarget());
        if (this.getAttackTarget() != null) {
            CurseKing.logger.debug("2. {}", this.getAttackTarget().isEntityAlive());
        }
        String currentState = this.getDataManager().get(CURRENTSTATE);

        // Only set HOSTILE_POSITION if not in an attack state
        if (this.getAttackTarget() != null && this.getAttackTarget().isEntityAlive()
                && currentState.equals(AquaRegiaState.NEUTRAL_IDLE.name())) {
            this.getDataManager().set(AGGRO, true);
            this.getDataManager().set(CURRENTSTATE, AquaRegiaState.HOSTILE_POSITION.name());
        } else if (this.getAttackTarget() != null && !this.getAttackTarget().isEntityAlive()
                && currentState.equals(AquaRegiaState.HOSTILE_POSITION.name())) {
            this.getDataManager().set(AGGRO, false);
            this.getDataManager().set(CURRENTSTATE, AquaRegiaState.NEUTRAL_IDLE.name());
            this.getDataManager().set(HOSTILEPOS, false);
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

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "controller", 0, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        AnimationController<?> controller = event.getController();
        boolean playedHostilePosition = this.getDataManager().get(HOSTILEPOS);
        CurseKing.logger.debug("3. CURRENT STATE: {}", AquaRegiaState.valueOf(this.getDataManager().get(CURRENTSTATE)));

        switch (AquaRegiaState.valueOf(this.getDataManager().get(CURRENTSTATE))) {
            case NEUTRAL_IDLE:
                controller.setAnimationSpeed(1.5f);
                controller.setAnimation(new AnimationBuilder().addAnimation("animation.aqua_regia.neutral_idle", true));
                return PlayState.CONTINUE;

            case HOSTILE_INIT:
                controller.setAnimation(new AnimationBuilder().addAnimation("animation.aqua_regia.hostile_position", false));
                return PlayState.CONTINUE;

            case HOSTILE_POSITION:
                controller.setAnimation(new AnimationBuilder().addAnimation("animation.aqua_regia.hostile_idle", true));
                return PlayState.CONTINUE;

            case HOSTILE_ARROW_SHOT:
                controller.setAnimation(new AnimationBuilder()
                        .addAnimation("animation.aqua_regia.attack_arrow_shot", false)
                        .addAnimation("animation.aqua_regia.hostile_static", false));
                if (this.getDataManager().get(ATTACKTIMER) >= ARROW_SHOT_DURATION) {
                    this.setAquaRegiaState(AquaRegiaState.HOSTILE_POSITION);
                    this.getDataManager().set(ATTACKTIMER, 0);
                }
                this.getDataManager().set(ATTACKTIMER, this.getDataManager().get(ATTACKTIMER) + 1);
                return PlayState.CONTINUE;

            case HOSTILE_WATERSPOUT:
                controller.setAnimation(new AnimationBuilder()
                        .addAnimation("animation.aqua_regia.attack_waterspout", false)
                        .addAnimation("animation.aqua_regia.hostile_static", false));
                if (this.getDataManager().get(ATTACKTIMER) >= WATERSPOUT_DURATION) {
                    this.setAquaRegiaState(AquaRegiaState.HOSTILE_POSITION);
                    this.getDataManager().set(ATTACKTIMER, 0);
                }
                this.getDataManager().set(ATTACKTIMER, this.getDataManager().get(ATTACKTIMER) + 1);
                return PlayState.CONTINUE;

            case HOSTILE_ARROW_RAIN:
                controller.setAnimationSpeed(0.5F);
                controller.setAnimation(new AnimationBuilder()
                        .addAnimation("animation.aqua_regia.attack_arrow_rain", false)
                        .addAnimation("animation.aqua_regia.hostile_static", false));
                if (this.getDataManager().get(ATTACKTIMER) >= ARROW_RAIN_DURATION) {
                    controller.setAnimationSpeed(1F);
                    this.setAquaRegiaState(AquaRegiaState.HOSTILE_POSITION);
                    this.getDataManager().set(ATTACKTIMER, 0);
                }
                this.getDataManager().set(ATTACKTIMER, this.getDataManager().get(ATTACKTIMER) + 1);
                return PlayState.CONTINUE;
        }
        return PlayState.CONTINUE;
    }

    private boolean isAttackState(String state) {
        return state.equals(AquaRegiaState.HOSTILE_ARROW_SHOT.name())
                || state.equals(AquaRegiaState.HOSTILE_WATERSPOUT.name())
                || state.equals(AquaRegiaState.HOSTILE_ARROW_RAIN.name());
    }
}
