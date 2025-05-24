package curseking;

import curseking.biome.biomefoghandler.BiomeLakeRemover;
import curseking.command.CommandBless;
import curseking.command.CommandCurse;
import curseking.command.CommandFindForgottenDeityBiome;
import curseking.command.CommandMobStats;
import curseking.creativetabs.CurseKingCreativeTab;
import curseking.eventhandlers.mobeventhandlers.MobEventHandler;
import curseking.eventhandlers.mobeventhandlers.WeatherEventHandler;
import curseking.network.CurseSyncPacket;
import curseking.proxy.CommonProxy;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import org.apache.logging.log4j.Logger;
import software.bernie.geckolib3.GeckoLib;

@Mod(modid = CurseKing.MODID, name = CurseKing.MODNAME, version = CurseKing.MODVERSION, dependencies = "required-after:forge@[11.16.0.1865,)", useMetadata = true)
public class CurseKing {

    public static final String MODID = "curseking";
    public static final String MODNAME = "CurseKing";
    public static final String MODVERSION = "1.0.5";

    @Mod.Instance(MODID)
    public static CurseKing instance;

    @SidedProxy(clientSide = "curseking.proxy.ClientProxy")
    public static CommonProxy proxy;

    public static Logger logger;

    public static final SimpleNetworkWrapper NETWORK = NetworkRegistry.INSTANCE.newSimpleChannel("curseking");

    public static final CreativeTabs CURSEKING_TAB = new CurseKingCreativeTab();

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        GeckoLib.initialize();
        proxy.preInit(event);
        MinecraftForge.TERRAIN_GEN_BUS.register(new BiomeLakeRemover());
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent e) {
        proxy.init(e);
        NETWORK.registerMessage(CurseSyncPacket.Handler.class, CurseSyncPacket.class, 0, Side.CLIENT);
        MinecraftForge.EVENT_BUS.register(new MobEventHandler());
        MinecraftForge.EVENT_BUS.register(new WeatherEventHandler());
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent e) {
        proxy.postInit(e);
    }

    @Mod.EventHandler
    public void onServerStarting(FMLServerStartingEvent event) {
        event.registerServerCommand(new CommandCurse());
        event.registerServerCommand(new CommandBless());
        event.registerServerCommand(new CommandMobStats());
        event.registerServerCommand(new CommandFindForgottenDeityBiome());
    }
}
