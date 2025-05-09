package curseking.biome;

import curseking.ModBlocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.Random;

public class WorldGenCrucifixSmall implements IWorldGenerator {

    private final Random random = new Random();

    @Override
    public void generate(Random rand, int chunkX, int chunkZ, World world, IChunkGenerator generator, IChunkProvider provider) {
        if (world.provider.getDimension() != 0) return;

        for (int i = 0; i < 6; i++) {

            int x = chunkX * 16 + random.nextInt(16);
            int z = chunkZ * 16 + random.nextInt(16);
            BlockPos topPos = world.getHeight(new BlockPos(x, 0, z));

            Biome biome = world.getBiome(topPos);
            if (!(biome instanceof BiomeGraveForgottenDeity)) return;

            BlockPos groundPos = topPos.down();
            if (world.getBlockState(groundPos).getBlock() != ModBlocks.graveSand) return;

            if (!world.isAirBlock(topPos) || !world.isAirBlock(topPos.up())) return;

            world.setBlockState(topPos, ModBlocks.smallCrucifix.getDefaultState());
            ModBlocks.smallCrucifix.onBlockPlacedBy(world, topPos, ModBlocks.smallCrucifix.getDefaultState(), null, ItemStack.EMPTY);
        }
    }
}
