package curseking.mobs.AIHelper;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

public class EntityFallenWanderInBiome extends EntityAIBase {
    private final EntityCreature entity;
    private final double speed;
    private final Biome graveBiome;
    private double x, y, z;

    public EntityFallenWanderInBiome(EntityCreature entity, double speed, Biome graveBiome) {
        this.entity = entity;
        this.speed = speed;
        this.graveBiome = graveBiome;
        this.setMutexBits(1);
    }

    @Override
    public boolean shouldExecute() {
        if (entity.getAttackTarget() != null) return false;

        World world = entity.world;
        BlockPos pos = entity.getPosition();
        Biome currentBiome = world.getBiome(pos);

        if (currentBiome == graveBiome) return false;

        BlockPos targetPos = findNearbyGraveBiome(pos, world, 32, 10);
        if (targetPos != null) {
            this.x = targetPos.getX() + 0.5;
            this.y = targetPos.getY();
            this.z = targetPos.getZ() + 0.5;
            return true;
        }
        return false;
    }

    @Override
    public void startExecuting() {
        entity.getNavigator().tryMoveToXYZ(x, y, z, speed);
    }

    @Override
    public boolean shouldContinueExecuting() {
        return !entity.getNavigator().noPath();
    }

    private BlockPos findNearbyGraveBiome(BlockPos origin, World world, int radius, int tries) {
        int minY = Math.max(0, origin.getY() - 10);
        int maxY = Math.min(world.getActualHeight() - 1, origin.getY() + 10);
        for (int i = 0; i < tries; i++) {
            int dx = world.rand.nextInt(radius * 2) - radius;
            int dz = world.rand.nextInt(radius * 2) - radius;
            int x = origin.getX() + dx;
            int z = origin.getZ() + dz;
            for (int y = minY; y <= maxY; y++) {
                BlockPos check = new BlockPos(x, y, z);
                if (world.getBiome(check) == graveBiome) {
                    return check;
                }
            }
        }
        return null;
    }
}