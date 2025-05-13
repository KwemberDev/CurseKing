package curseking.mobs.renderers;

import curseking.mobs.EntityTheFallen;
import curseking.mobs.geomodels.ModelTheFallen;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;
import net.minecraft.client.renderer.entity.RenderManager;

public class RenderTheFallen extends GeoEntityRenderer<EntityTheFallen> {
    public RenderTheFallen(RenderManager renderManager) {
        super(renderManager, new ModelTheFallen());
        this.shadowSize = 0.7F;
    }
}