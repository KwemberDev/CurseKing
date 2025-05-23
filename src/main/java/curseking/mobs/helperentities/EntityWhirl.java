package curseking.mobs.helperentities;

import curseking.mobs.EntityAquaRegia;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class EntityWhirl extends EntityLivingBase implements IAnimatable {
    private int stayTicks = 0;
    private boolean stopped = false;

    private double motX;
    private double motY;
    private double motZ;

    private boolean grav = false;

    private final AnimationFactory factory = new AnimationFactory(this);


    public static final DataParameter<Boolean> DONE_SPAWNING = EntityDataManager.createKey(EntityWhirl.class, DataSerializers.BOOLEAN);
    public static final DataParameter<Boolean> HAS_GRAVITY = EntityDataManager.createKey(EntityWhirl.class, DataSerializers.BOOLEAN);

    public EntityWhirl(World worldIn) {
        super(worldIn);
        this.setSize(1.0F, 5.0F);
        this.isImmuneToFire = true;
        this.setNoGravity(true);
    }

    public EntityWhirl(World worldIn, double x, double y, double z, double velX, double velY, double velZ, boolean gravity) {
        this(worldIn);
        this.setPosition(x, y, z);
        this.motionX = velX;
        this.motionY = velY;
        this.motionZ = velZ;
        this.motX = velX;
        this.motY = velY;
        this.motZ = velZ;
        this.getDataManager().set(HAS_GRAVITY, gravity);
        this.grav = gravity;
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(DONE_SPAWNING, false);
        this.dataManager.register(HAS_GRAVITY, grav);
        this.setNoGravity(true);
    }

    @Override
    public void onUpdate() {
        this.isAirBorne = false;
        super.onUpdate();

        if (!stopped) {
            double nextX = posX + motionX;
            double nextY = posY + motionY;
            double nextZ = posZ + motionZ;

            RayTraceResult result = world.rayTraceBlocks(
                    new net.minecraft.util.math.Vec3d(posX, posY, posZ),
                    new net.minecraft.util.math.Vec3d(nextX, nextY, nextZ)
            );

            if (result != null && result.typeOfHit == RayTraceResult.Type.BLOCK) {
                this.motionX = 0;
                this.motionY = 0;
                this.motionZ = 0;
                this.stopped = true;
                this.stayTicks = 300;
                this.setPosition(result.hitVec.x, result.hitVec.y, result.hitVec.z);
            }
        }

        if (stopped) {
            this.motionX = 0;
            this.motionY = 0;
            this.motionZ = 0;
            if (--stayTicks <= 0) {
                this.setDead();
            }
        }
        if (this.ticksExisted > 410) {
            this.setDead();
        }
        if (!this.world.isRemote) {
            for (Entity entity : this.world.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox())) {
                if (entity instanceof net.minecraft.entity.EntityLivingBase && !(entity instanceof EntityAquaRegia)) {
                    entity.attackEntityFrom(net.minecraft.util.DamageSource.causeMobDamage((EntityLivingBase) entity), 6.0F);
                }
            }
        }
        if (this.ticksExisted > 10) {
            this.dataManager.set(DONE_SPAWNING, true);
        }

        if (!stopped) {
            this.motionX = motX;
            this.motionY = motY;
            this.motionZ = motZ;
        }

        if (this.getDataManager().get(HAS_GRAVITY)) {
            this.motionY -= 0.04D;
            this.motY -= 0.04D;
        }
    }

    @Override
    public boolean attackEntityFrom(net.minecraft.util.DamageSource source, float amount) {
        // Only allow damage from commands (OUT_OF_WORLD is used by /kill and similar)
        return source == net.minecraft.util.DamageSource.OUT_OF_WORLD && super.attackEntityFrom(source, amount);
    }

    @Override
    public boolean hasNoGravity() {
        return true;
    }

    @Override
    public boolean canBePushed() {
        return false;
    }

    @Override
    public void applyEntityCollision(Entity entityIn) {
        // Do nothing to prevent being moved by collisions
    }

    @Override
    public EnumHandSide getPrimaryHand() {
        return null;
    }

    @Override
    public Iterable<ItemStack> getArmorInventoryList() {
        return java.util.Collections.emptyList();
    }

    @Override
    public ItemStack getItemStackFromSlot(EntityEquipmentSlot slotIn) {
        return ItemStack.EMPTY;
    }

    @Override
    public void setItemStackToSlot(EntityEquipmentSlot slotIn, ItemStack stack) {

    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "whirl", 0, this::predicate));

    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if (!this.dataManager.get(DONE_SPAWNING)) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.waterspout.spawn", false));
        } else {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.waterspout.spin", true));
        }
        return PlayState.CONTINUE;
    }
}