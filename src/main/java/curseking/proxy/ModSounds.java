package curseking.proxy;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class ModSounds {
    public static SoundEvent FALLEN_AMBIENT;
    public static SoundEvent FALLEN_DYING;

    @SubscribeEvent
    public static void registerSounds(RegistryEvent.Register<SoundEvent> event) {
        FALLEN_AMBIENT = register(event, "entity.the_fallen.ambient");
        FALLEN_DYING = register(event, "entity.the_fallen.death");
    }

    private static SoundEvent register(RegistryEvent.Register<SoundEvent> event, String name) {
        ResourceLocation loc = new ResourceLocation("curseking", name);
        SoundEvent sound = new SoundEvent(loc).setRegistryName(loc);
        event.getRegistry().register(sound);
        return sound;
    }
}