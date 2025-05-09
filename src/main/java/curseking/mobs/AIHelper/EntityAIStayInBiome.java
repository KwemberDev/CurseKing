package curseking.mobs.AIHelper;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;

public class EntityAIStayInBiome extends EntityAIWanderAvoidWater {
    private final EntityCreature entity;
    private final Biome targetBiome;

    public EntityAIStayInBiome(EntityCreature entity, Biome targetBiome, double speed) {
        super(entity, speed);
        this.entity = entity;
        this.targetBiome = targetBiome;
    }

    @Override
    public boolean shouldExecute() {
        BlockPos pos = new BlockPos(this.entity);
        Biome currentBiome = this.entity.world.getBiome(pos);

        // Only allow wandering if the entity is in the target biome
        return currentBiome == this.targetBiome && super.shouldExecute();
    }
}