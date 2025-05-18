package curseking.mobs.geomodels;

import curseking.mobs.EntityTheFallen;
import curseking.mobs.helperentities.EntityWhirl;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ModelWhirl extends AnimatedGeoModel<EntityWhirl> {
    @Override
    public ResourceLocation getModelLocation(EntityWhirl object) {
        return new ResourceLocation("curseking", "geo/watersprout.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EntityWhirl object) {
        return new ResourceLocation("curseking", "textures/entities/watersprout.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EntityWhirl animatable) {
        return new ResourceLocation("curseking", "animations/waterspout.animation.json");
    }
}