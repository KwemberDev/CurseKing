package curseking.mobs.renderers;

import curseking.mobs.geomodels.ModelWhirl;
import curseking.mobs.helperentities.EntityWhirl;
import net.minecraft.client.renderer.entity.RenderManager;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class RenderWhirl extends GeoEntityRenderer<EntityWhirl> {
    public RenderWhirl(RenderManager renderManager) {
        super(renderManager, new ModelWhirl());
        this.shadowSize = 0.7F;
    }
}