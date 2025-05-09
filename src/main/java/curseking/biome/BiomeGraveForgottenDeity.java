package curseking.biome;

import curseking.ModBlocks;
import curseking.config.CurseKingConfig;
import curseking.mobs.EntityTheFallen;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BiomeGraveForgottenDeity extends Biome {

    public BiomeGraveForgottenDeity(BiomeProperties properties) {
        super(new BiomeProperties("Grave of the Forgotten Deity")
                .setBaseHeight(0.0F)
                .setHeightVariation(0.0F)
                .setRainfall(0.0F)
                .setTemperature(0.5F));
        this.topBlock = ModBlocks.graveSand.getDefaultState();
        this.fillerBlock = ModBlocks.graveSoil.getDefaultState();
        this.decorator.generateFalls = false;
        this.decorator.treesPerChunk = 0;
        this.decorator.extraTreeChance = 0.0F;
        this.decorator.grassPerChunk = 0;

        this.spawnableCreatureList.clear();         // Passive mobs (e.g., pigs, cows)
        this.spawnableMonsterList.clear();         // Hostile mobs (e.g., zombies, skeletons)
        this.spawnableWaterCreatureList.clear();  // Water mobs (e.g., squids)
        this.spawnableCaveCreatureList.clear();  // Cave mobs (e.g., bats)


        this.spawnableCreatureList.add(new SpawnListEntry(
                EntityTheFallen.class, // The entity class
                35,                   // Spawn weight
                1,                   // Min group size
                2                   // Max group size
        ));
        this.spawnableMonsterList.add(new SpawnListEntry(
                EntityTheFallen.class,
                CurseKingConfig.mobSettings.TheFallenSpawnWeight,
                1,
                2
        ));
    }

    @Override
    public int getSkyColorByTemp(float temp) {
        return 0x000000;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public int getGrassColorAtPos(BlockPos blockPos) {
        return 0x000000;
    }

    @Override
    public int getWaterColorMultiplier() {
        return 0x000000;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public int getFoliageColorAtPos(BlockPos blockPos) {
        return 0x000000;
    }
}
