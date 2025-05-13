package curseking.mobs;

import curseking.CurseKing;
import curseking.biome.BiomeRegistry;
import curseking.config.CurseKingConfig;
import curseking.mobs.AIHelper.EntityAIStayInBiome;
import curseking.proxy.ModSounds;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import software.bernie.geckolib3.GeckoLib;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

import static curseking.CurseKing.MODID;

public class EntityTheFallen extends EntityMob implements IAnimatable {

    private final AnimationFactory factory = new AnimationFactory(this);

    private static final DataParameter<Boolean> ATTACKING = EntityDataManager.createKey(EntityTheFallen.class, DataSerializers.BOOLEAN);

    public boolean isAttacking() { return this.dataManager.get(ATTACKING); }
    public void setAttacking(boolean attacking) { this.dataManager.set(ATTACKING, attacking); }


    public void startAttackAnimation() {
        CurseKing.logger.debug("SETTING ATTACK TO TRUE.");
        this.setAttacking(true);
        AnimationController<?> controller = this.getFactory()
                .getOrCreateAnimationData(0)
                .getAnimationControllers()
                .get("controller");
        if (controller != null) controller.markNeedsReload();
    }

    public EntityTheFallen(World worldIn) {
        super(worldIn);
        this.setSize(0.95F, 3.2F);
        this.experienceValue = 10;
        this.isImmuneToFire = false;
        this.enablePersistence();
    }

    public void doMeleeAttack(EntityLivingBase target) {
        target.attackEntityFrom(DamageSource.causeMobDamage(this), (float)this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue());
    }

    @Override
    protected void initEntityAI() {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(2, new curseking.mobs.AIHelper.EntityAIFallenAttack(this));
        this.tasks.addTask(5, new EntityAIWanderAvoidWater(this, 0.5D));
        this.tasks.addTask(6, new EntityAIStayInBiome(this, BiomeRegistry.Grave, 1D));
        this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 16.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));

        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<>(this, EntityLivingBase.class, 20, true, true, this::shouldAttackTarget));
    }

    private boolean shouldAttackTarget(EntityLivingBase target) {
        return !(target instanceof EntityTheFallen);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(ATTACKING, false);
    }

    @Override
    protected ResourceLocation getLootTable() {
        return new ResourceLocation(MODID, "entities/the_fallen");
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return ModSounds.FALLEN_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.ENTITY_WITHER_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.FALLEN_DYING;
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(CurseKingConfig.mobSettings.TheFallenHealth);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(CurseKingConfig.mobSettings.TheFallenMovementSpeed);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(CurseKingConfig.mobSettings.TheFallenAttackDamage);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(32.0D);
        this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(0.5D);
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(10D);
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).setBaseValue(4.0D);
    }

    @Override
    public int getTalkInterval()
    {
        return 300;
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "controller", 0, this::predicate));
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        CurseKing.logger.debug(isAttacking());
        if (isAttacking()) {
            CurseKing.logger.debug("SETTING ATTACK ANIMATION.");
            event.getController().setAnimationSpeed(1.3D);
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.fallen.attack", false));
        } else {
            event.getController().clearAnimationCache();
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.fallen.idle", true));
        }
        return PlayState.CONTINUE;
    }
}
