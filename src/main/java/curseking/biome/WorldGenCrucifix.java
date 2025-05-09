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

public class WorldGenCrucifix implements IWorldGenerator {

    @Override
    public void generate(Random rand, int chunkX, int chunkZ, World world, IChunkGenerator generator, IChunkProvider provider) {
        if (world.provider.getDimension() != 0) return;

        for (int i = 0; i < 2; i++) {

            int x = chunkX * 16 + rand.nextInt(16);
            int z = chunkZ * 16 + rand.nextInt(16);
            BlockPos topPos = world.getHeight(new BlockPos(x, 0, z));

            Biome biome = world.getBiome(topPos);
            if (!(biome instanceof BiomeGraveForgottenDeity)) return;

            BlockPos groundPos = topPos.down();
            if (world.getBlockState(groundPos).getBlock() != ModBlocks.graveSand) return;

            if (!world.isAirBlock(topPos) || !world.isAirBlock(topPos.up())) return;

            world.setBlockState(topPos, ModBlocks.crucifixBlock.getDefaultState());
            ModBlocks.crucifixBlock.onBlockPlacedBy(world, topPos, ModBlocks.crucifixBlock.getDefaultState(), null, ItemStack.EMPTY);
        }
    }
}
