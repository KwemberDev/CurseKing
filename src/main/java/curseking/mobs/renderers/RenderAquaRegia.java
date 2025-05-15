package curseking.mobs.renderers;

import curseking.mobs.AquaRegia;
import curseking.mobs.EntityAquaRegia;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

import static curseking.CurseKing.MODID;

public class RenderAquaRegia extends RenderLiving<EntityAquaRegia> {
    public RenderAquaRegia(RenderManager renderManager) {
        super(renderManager, new AquaRegia(), 0.7F);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityAquaRegia entity) {
        return new ResourceLocation(MODID, "textures/entities/aqua_regia.png");
    }
}
