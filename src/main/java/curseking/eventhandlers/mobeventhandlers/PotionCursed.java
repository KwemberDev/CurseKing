package curseking.eventhandlers.mobeventhandlers;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AbstractAttributeMap;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.potion.Potion;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import java.util.UUID;

public class PotionCursed extends Potion {

    public enum CurseType {
        DECAY,
        SLOTH,
        STARVING
    }

    private final CurseType curseType;

    public PotionCursed(CurseType type) {
        super(true, 0x000000);
        this.curseType = type;
        setPotionName("effect.cursed_" + type.name().toLowerCase());
        setRegistryName(new ResourceLocation("curseking", "cursed_" + type.name().toLowerCase()));
    }

    public CurseType getCurseType() {
        return this.curseType;
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return true;
    }

    @Override
    public void performEffect(EntityLivingBase entity, int amplifier) {
        // === Particles (client only) ===
        if (entity.world.isRemote) {
            EnumParticleTypes particle = EnumParticleTypes.SMOKE_NORMAL;

            switch (curseType) {
                case DECAY:
                    particle = EnumParticleTypes.SPELL_MOB;
                    break;
                case SLOTH:
                    particle = EnumParticleTypes.DRIP_WATER;
                    break;
                case STARVING:
                    particle = EnumParticleTypes.REDSTONE;
                    break;
            }

            double x = entity.posX + (entity.world.rand.nextDouble() - 0.5) * entity.width;
            double y = entity.posY + entity.world.rand.nextDouble() * entity.height;
            double z = entity.posZ + (entity.world.rand.nextDouble() - 0.5) * entity.width;
            entity.world.spawnParticle(particle, x, y, z, 0, 0, 0);
        }
    }

    @Override
    public void applyAttributesModifiersToEntity(EntityLivingBase entity, @Nonnull AbstractAttributeMap attributeMap, int amplifier) {
        super.applyAttributesModifiersToEntity(entity, attributeMap, amplifier);

        if (entity instanceof EntityLiving) {
            IAttributeInstance attr;

            switch (curseType) {
                case SLOTH:
                    attr = entity.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED);
                    if (attr != null && attr.getModifier(UUID.fromString("1a4d5a4b-9381-4236-8f60-b3d80b55b349")) == null) {
                        attr.applyModifier(new AttributeModifier(UUID.fromString("1a4d5a4b-9381-4236-8f60-b3d80b55b349"),
                                "curse_sloth_slowdown", 0.15, 1));
                    }
                    break;
                case DECAY:
                    attr = entity.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH);
                    if (attr != null && attr.getModifier(UUID.fromString("1a4d5a4b-9381-4236-8f60-b3d80b55b958")) == null) {
                        attr.applyModifier(new AttributeModifier(UUID.fromString("1a4d5a4b-9381-4236-8f60-b3d80b55b958"),
                                "curse_decay", 0.25, 1));
                    }
                    break;
                case STARVING:
                    attr = entity.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
                    if (attr != null && attr.getModifier(UUID.fromString("1a4d5a4b-9381-4236-8f60-b3d80b55b245")) == null) {
                        attr.applyModifier(new AttributeModifier(UUID.fromString("1a4d5a4b-9381-4236-8f60-b3d80b55b245"),
                                "curse_starving", 0.15, 1));
                    }
            }
        }
    }

    @Override
    public void removeAttributesModifiersFromEntity(EntityLivingBase entity, @Nonnull AbstractAttributeMap attributeMap, int amplifier) {
        super.removeAttributesModifiersFromEntity(entity, attributeMap, amplifier);

        if (entity instanceof EntityLiving) {
            IAttributeInstance attr;

            switch (curseType) {
                case SLOTH:
                    attr = entity.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED);
                    if (attr != null) {
                        attr.removeModifier(UUID.fromString("1a4d5a4b-9381-4236-8f60-b3d80b55b349"));
                    }
                    break;
                case DECAY:
                    attr = entity.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH);
                    if (attr != null) {
                        attr.removeModifier(UUID.fromString("1a4d5a4b-9381-4236-8f60-b3d80b55b958"));
                    }
                    break;
                case STARVING:
                    attr = entity.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE);
                    if (attr != null) {
                        attr.removeModifier(UUID.fromString("1a4d5a4b-9381-4236-8f60-b3d80b55b245"));
                    }
            }
        }
    }
}
