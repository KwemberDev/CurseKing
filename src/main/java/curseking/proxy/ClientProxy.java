package curseking.proxy;

import curseking.ModBlocks;
import curseking.ModItems;
import curseking.mobs.EntityAquaRegia;
import curseking.mobs.EntityTheFallen;
import curseking.mobs.RenderAquaRegia;
import curseking.mobs.RenderTheFallen;
import curseking.mobs.projectiles.EntityLightningOrb;
import curseking.mobs.projectiles.EntityWaterProjectile;
import curseking.mobs.projectiles.EntityWaterProjectileGravity;
import curseking.mobs.projectiles.renderer.RenderOrb;
import curseking.mobs.projectiles.renderer.RenderOrbGravity;
import curseking.mobs.projectiles.renderer.RenderOrbLightning;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(Side.CLIENT)
public class ClientProxy extends CommonProxy {

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);
        RenderingRegistry.registerEntityRenderingHandler(EntityTheFallen.class, RenderTheFallen::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityAquaRegia.class, RenderAquaRegia::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityWaterProjectile.class, RenderOrb::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityWaterProjectileGravity.class, RenderOrbGravity::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityLightningOrb.class, RenderOrbLightning::new);
    }

    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);
    }

    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
        ModItems.initModels();
        ModBlocks.initModels();
    }
}
