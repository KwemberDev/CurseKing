package curseking.biome;

import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class BiomeRegistry {

    public static Biome.BiomeProperties properties = new Biome.BiomeProperties("GraveForgottenDeity");

    public static Biome Grave = new BiomeGraveForgottenDeity(properties);

    public static void registerBiomes() {
        Grave.setRegistryName("grave_forgotten_deity");
        ForgeRegistries.BIOMES.register(Grave);
        BiomeManager.addBiome(BiomeManager.BiomeType.COOL, new BiomeManager.BiomeEntry(Grave, 5));
        BiomeDictionary.addTypes(Grave, BiomeDictionary.Type.MAGICAL,
                                        BiomeDictionary.Type.DEAD, BiomeDictionary.Type.RARE,
                                        BiomeDictionary.Type.WASTELAND);
    }

}
