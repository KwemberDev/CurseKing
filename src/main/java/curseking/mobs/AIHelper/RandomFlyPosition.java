package curseking.mobs.AIHelper;

import net.minecraft.entity.EntityLiving;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.Random;

public class RandomFlyPosition {

    public static Vec3d getRandomFlyablePosition(EntityLiving entity, int range, int flyHeight) {
        World world = entity.world;
        BlockPos origin = entity.getPosition();
        Random rand = entity.getRNG();

        for (int attempts = 0; attempts < 75; attempts++) {
            int dx = rand.nextInt(range * 2 + 1) - range;
            int dz = rand.nextInt(range * 2 + 1) - range;
            int x = origin.getX() + dx;
            int z = origin.getZ() + dz;

            int groundY = entity.world.getPrecipitationHeight(new BlockPos(x, 64, z)).getY();
            int y = groundY + rand.nextInt(flyHeight + 1);

            BlockPos targetPos = new BlockPos(x, y, z);

            if (world.isAirBlock(targetPos) && !world.getBlockState(targetPos.down()).getMaterial().isLiquid()) {
                return new Vec3d(x + 0.5, y + 0.5, z + 0.5);
            }
        }

        return entity.getPositionVector();
    }
}
