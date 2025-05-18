package curseking.mobs.projectiles.renderer;

import curseking.mobs.projectiles.EntityWaterProjectile;
import curseking.mobs.projectiles.RoyalArrow;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

import static curseking.CurseKing.MODID;

public class RenderOrb extends Render<EntityWaterProjectile> {
    private final ModelBase model = new RoyalArrow();


    public RenderOrb(RenderManager renderManager) {
        super(renderManager);
        this.shadowSize = 0.25F;
    }

    @Override
    public void doRender(EntityWaterProjectile entity, double x, double y, double z, float entityYaw, float partialTicks) {
        net.minecraft.client.renderer.GlStateManager.pushMatrix();
        net.minecraft.client.renderer.GlStateManager.translate(x, y+0.5, z);

        net.minecraft.client.renderer.GlStateManager.rotate(-entityYaw, 0.0F, 1.0F, 0.0F);
        net.minecraft.client.renderer.GlStateManager.rotate(entity.rotationPitch, 1.0F, 0.0F, 0.0F);

        net.minecraft.client.renderer.GlStateManager.rotate(90F, 1.0F, 0.0F, 0.0F);

        bindEntityTexture(entity);
        model.render(entity, 0, 0, 0, entityYaw, entity.rotationPitch, 0.0625F);

        net.minecraft.client.renderer.GlStateManager.popMatrix();
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntityWaterProjectile entity) {
        return new ResourceLocation(MODID, "textures/entities/aqua_regia.png");
    }
}