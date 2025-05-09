package curseking.proxy;

import curseking.*;
import curseking.biome.BiomeRegistry;
import curseking.biome.WorldGenCrucifix;
import curseking.biome.WorldGenCrucifixSmall;
import curseking.gui.GuiHandler;
import curseking.mobs.EntityAquaRegia;
import curseking.mobs.EntityTheFallen;
import curseking.mobs.projectiles.EntityLightningOrb;
import curseking.mobs.projectiles.EntityWaterProjectile;
import curseking.mobs.projectiles.EntityWaterProjectileGravity;
import curseking.tileentities.TileEntityCrystallinePurifier;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

import static curseking.CurseKing.MODID;
import static curseking.CurseKing.instance;
import static curseking.ModItems.*;

@Mod.EventBusSubscriber
public class CommonProxy {

    public void preInit(FMLPreInitializationEvent event) {
        CapabilityManager.INSTANCE.register(ICurseData.class, new CurseDataStorage(), CurseDataImpl::new);
        EntityRegistry.registerModEntity(
                new ResourceLocation(MODID, "the_fallen"),
                EntityTheFallen.class,
                "TheFallen",
                6565,
                instance,
                64, 3, true,
                0x111111, 0x550000
        );
        EntityRegistry.registerModEntity(
                new ResourceLocation(MODID, "aqua_regia"),
                EntityAquaRegia.class,
                "AquaRegia",
                6566,
                instance,
                64, 3, true,
                0x111FF1, 0x55FFFF
        );
        EntityRegistry.registerModEntity(new ResourceLocation(MODID, "lightning_orb"), EntityLightningOrb.class, "LightningOrb", 6655, instance, 64, 10, true);
        EntityRegistry.registerModEntity(new ResourceLocation(MODID, "water_orb"), EntityWaterProjectile.class, "WaterOrb", 6656, instance, 64, 30, true);
        EntityRegistry.registerModEntity(new ResourceLocation(MODID, "water_orb_gravity"), EntityWaterProjectileGravity.class, "WaterOrbGravity", 6657, instance, 64, 100, true);
    }

    public void init(FMLInitializationEvent event) {
        BiomeRegistry.registerBiomes();

        GameRegistry.registerWorldGenerator(new WorldGenCrucifix(), 10);
        GameRegistry.registerWorldGenerator(new WorldGenCrucifixSmall(), 9);
    }

    public void postInit(FMLPostInitializationEvent event) {

    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        event.getRegistry().register(ModBlocks.crystallinePurifierBlock);
        event.getRegistry().register(ModBlocks.crucifixBlock);
        event.getRegistry().register(ModBlocks.graveSand);
        event.getRegistry().register(ModBlocks.graveSoil);
        event.getRegistry().register(ModBlocks.smallCrucifix);

        GameRegistry.registerTileEntity(TileEntityCrystallinePurifier.class, MODID + ".crystallinePurifier");
        NetworkRegistry.INSTANCE.registerGuiHandler(CurseKing.instance, new GuiHandler());

    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().register(NEUTRAL_DIVINITY_STONE);
        event.getRegistry().register(STONE_OF_HUNGER);
        event.getRegistry().register(STONE_OF_DECAY);
        event.getRegistry().register(STONE_OF_SLOWNESS);
        event.getRegistry().register(STONE_OF_SATIETY);
        event.getRegistry().register(STONE_OF_NIMBLE);
        event.getRegistry().register(STONE_OF_IRON_SKIN);
        event.getRegistry().register(ASHES_OF_DIVINITY);
        event.getRegistry().register(SHATTERED_PURITY);
        event.getRegistry().register(FLUTE);

        event.getRegistry().register(new ItemBlock(ModBlocks.crystallinePurifierBlock).setRegistryName(ModBlocks.crystallinePurifierBlock.getRegistryName()));
        event.getRegistry().register(new ItemBlock(ModBlocks.crucifixBlock).setRegistryName(ModBlocks.crucifixBlock.getRegistryName()));
        event.getRegistry().register(new ItemBlock(ModBlocks.graveSand).setRegistryName(ModBlocks.graveSand.getRegistryName()));
        event.getRegistry().register(new ItemBlock(ModBlocks.graveSoil).setRegistryName(ModBlocks.graveSoil.getRegistryName()));
        event.getRegistry().register(new ItemBlock(ModBlocks.smallCrucifix).setRegistryName(ModBlocks.smallCrucifix.getRegistryName()));
    }
}
