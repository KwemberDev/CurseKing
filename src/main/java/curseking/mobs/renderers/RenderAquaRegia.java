package curseking.mobs.renderers;

import curseking.mobs.EntityAquaRegia;
import curseking.mobs.geomodels.ModelAquaRegia;
import net.minecraft.client.renderer.entity.RenderManager;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class RenderAquaRegia extends GeoEntityRenderer<EntityAquaRegia> {
    public RenderAquaRegia(RenderManager renderManager) {
        super(renderManager, new ModelAquaRegia());
        this.shadowSize = 0.7F;
    }
}