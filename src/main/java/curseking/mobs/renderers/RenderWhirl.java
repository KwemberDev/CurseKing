package curseking.mobs.renderers;

import curseking.mobs.EntityTheFallen;
import curseking.mobs.geomodels.ModelTheFallen;
import curseking.mobs.geomodels.ModelWhirl;
import curseking.mobs.helperentities.EntityWhirl;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import software.bernie.geckolib3.core.IAnimatableModel;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.util.Color;
import software.bernie.geckolib3.geo.render.built.GeoModel;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;
import software.bernie.geckolib3.renderers.geo.GeoProjectilesRenderer;

import java.util.Collections;

public class RenderWhirl extends GeoEntityRenderer<EntityWhirl> {
    public RenderWhirl(RenderManager renderManager) {
        super(renderManager, new ModelWhirl());
        this.shadowSize = 0.7F;
    }
}