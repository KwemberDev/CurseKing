package curseking.biome.biomefoghandler;

import curseking.CurseKing;
import curseking.biome.BiomeGraveForgottenDeity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.terraingen.PopulateChunkEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class BiomeLakeRemover {

    @SubscribeEvent
    public void populationEvent(PopulateChunkEvent.Populate event) {
        if (event.getType() == PopulateChunkEvent.Populate.EventType.LAKE || event.getType() == PopulateChunkEvent.Populate.EventType.LAVA) {
            if (event.getWorld().getBiome(new BlockPos(event.getChunkX() << 4, 64, event.getChunkZ() << 4)) instanceof BiomeGraveForgottenDeity) {
                event.setResult(Event.Result.DENY);
            }
        }
    }
}
