package curseking.mobs;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

import static curseking.CurseKing.MODID;

public class RenderTheFallen extends RenderLiving<EntityTheFallen> {
    public RenderTheFallen(RenderManager renderManager) {
        super(renderManager, new TheFallen(), 0.7F);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityTheFallen entity) {
        return new ResourceLocation(MODID, "textures/entity/the_fallen.png");
    }
}
