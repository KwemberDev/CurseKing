package curseking.mobs.projectiles.renderer;

import curseking.mobs.helperentities.EntityWhirl;
import curseking.mobs.helperentities.Whirl;
import curseking.mobs.projectiles.RoyalArrow;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

import static curseking.CurseKing.MODID;

public class RenderWhirl extends Render<EntityWhirl> {
    private final ModelBase model = new Whirl();

    public RenderWhirl(RenderManager renderManager) {
        super(renderManager);
        this.shadowSize = 0.5F;
    }

    @Override
    public void doRender(EntityWhirl entity, double x, double y, double z, float entityYaw, float partialTicks) {
        net.minecraft.client.renderer.GlStateManager.pushMatrix();
        net.minecraft.client.renderer.GlStateManager.translate(x, y+1.5, z);

        net.minecraft.client.renderer.GlStateManager.rotate(180F, 0.0F, 0.0F, 1.0F);
        bindEntityTexture(entity);
        model.render(entity, 0, 0, 0, entityYaw, entity.rotationPitch, 0.0625F);

        net.minecraft.client.renderer.GlStateManager.popMatrix();
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntityWhirl entity) {
        return new ResourceLocation(MODID, "textures/entities/whirl.png");
    }
}