package curseking.mobs;

import curseking.biome.BiomeRegistry;
import curseking.config.CurseKingConfig;
import curseking.mobs.AIHelper.EntityAIStayInBiome;
import curseking.proxy.ModSounds;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import static curseking.CurseKing.MODID;

public class EntityTheFallen extends EntityMob {

    public EntityTheFallen(World worldIn) {
        super(worldIn);
        this.setSize(0.95F, 3.2F);
        this.experienceValue = 10;
        this.isImmuneToFire = false;
        this.enablePersistence();
    }

    @Override
    protected void initEntityAI() {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIAttackMelee(this, 1.2D, true));
        this.tasks.addTask(3, new EntityAIStayInBiome(this, BiomeRegistry.Grave, 1D));
        this.tasks.addTask(5, new EntityAIWatchClosest(this, EntityPlayer.class, 16.0F));
        this.tasks.addTask(6, new EntityAILookIdle(this));

        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<>(this, EntityLivingBase.class, 20, true, true, this::shouldAttackTarget));
    }

    private boolean shouldAttackTarget(EntityLivingBase target) {
        return !(target.isEntityUndead() || target instanceof EntityTheFallen);
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

}
