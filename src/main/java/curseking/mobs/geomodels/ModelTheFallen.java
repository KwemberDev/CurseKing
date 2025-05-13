package curseking.mobs.geomodels;

import curseking.mobs.EntityTheFallen;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import net.minecraft.util.ResourceLocation;

public class ModelTheFallen extends AnimatedGeoModel<EntityTheFallen> {
    @Override
    public ResourceLocation getModelLocation(EntityTheFallen object) {
        return new ResourceLocation("curseking", "geo/fallen.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EntityTheFallen object) {
        return new ResourceLocation("curseking", "textures/entities/fallen.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EntityTheFallen animatable) {
        return new ResourceLocation("curseking", "animations/fallen.animation.json");
    }
}