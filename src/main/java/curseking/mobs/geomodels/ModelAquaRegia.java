package curseking.mobs.geomodels;

import curseking.mobs.EntityAquaRegia;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ModelAquaRegia extends AnimatedGeoModel<EntityAquaRegia> {
    @Override
    public ResourceLocation getModelLocation(EntityAquaRegia object) {
        return new ResourceLocation("curseking", "geo/aqua_regia.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EntityAquaRegia object) {
        return new ResourceLocation("curseking", "textures/entities/aqua_regia.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EntityAquaRegia animatable) {
        return new ResourceLocation("curseking", "animations/aqua_regia.animation.json");
    }
}