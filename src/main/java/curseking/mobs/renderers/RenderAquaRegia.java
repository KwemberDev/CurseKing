package curseking.mobs.renderers;

import curseking.mobs.EntityAquaRegia;
import curseking.mobs.EntityTheFallen;
import curseking.mobs.geomodels.ModelAquaRegia;
import curseking.mobs.geomodels.ModelTheFallen;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

import static curseking.CurseKing.MODID;

public class RenderAquaRegia extends GeoEntityRenderer<EntityAquaRegia> {
    public RenderAquaRegia(RenderManager renderManager) {
        super(renderManager, new ModelAquaRegia());
        this.shadowSize = 0.7F;
    }
}