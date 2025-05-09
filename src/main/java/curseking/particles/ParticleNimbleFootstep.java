package curseking.particles;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ParticleNimbleFootstep extends Particle {
    private static final ResourceLocation FOOTPRINT_TEXTURE = new ResourceLocation("curseking:textures/particle/footstepnimble.png");
    private int footstepAge;
    private final int footstepMaxAge;
    private final TextureManager currentFootSteps;
    private final float rotationYawLocked;
    private final float randomOffsetX;
    private final float randomOffsetZ;

    public ParticleNimbleFootstep(TextureManager currentFootStepsIn, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, float yaw) {
        super(worldIn, xCoordIn, yCoordIn, zCoordIn, 0.0D, 0.0D, 0.0D);
        this.currentFootSteps = currentFootStepsIn;
        this.footstepMaxAge = 100;
        this.rotationYawLocked = yaw;
        this.randomOffsetX = (rand.nextFloat() - 0.5F) * 0.4F;
        this.randomOffsetZ = (rand.nextFloat() - 0.5F) * 0.4F;
    }

    @Override
    public void renderParticle(BufferBuilder buffer, Entity entityIn, float partialTicks, float rotationX,
                               float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
        float f = ((float) this.footstepAge + partialTicks) / (float) this.footstepMaxAge;
        f = f * f;
        float f1 = 2.0F - f * 2.0F;

        if (f1 > 1.0F) {
            f1 = 1.0F;
        }

        f1 = f1 * 0.4F; // Opacity factor

        GlStateManager.disableLighting();

        float halfSize = 0.125F; // Half size of the footprint
        float f3 = (float) (this.posX - interpPosX) + this.randomOffsetX;
        float f4 = (float) (this.posY - interpPosY);
        float f5 = (float) (this.posZ - interpPosZ) + this.randomOffsetZ;

        float light = this.world.getLightBrightness(new BlockPos(this.posX, this.posY, this.posZ));

        this.currentFootSteps.bindTexture(FOOTPRINT_TEXTURE);
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);

        // Use the stored initial yaw instead of current yaw (no more rotation after spawn)
        float yaw = this.rotationYawLocked;
        float rad = yaw * (float) (Math.PI / 180.0);

        float cos = MathHelper.cos(rad);
        float sin = MathHelper.sin(rad);

        float x1 = -halfSize;
        float z1 = -halfSize;
        float x2 = halfSize;
        float z2 = -halfSize;
        float x3 = halfSize;
        float z3 = halfSize;
        float x4 = -halfSize;
        float z4 = halfSize;

        float rX1 = x1 * cos - z1 * sin;
        float rZ1 = x1 * sin + z1 * cos;
        float rX2 = x2 * cos - z2 * sin;
        float rZ2 = x2 * sin + z2 * cos;
        float rX3 = x3 * cos - z3 * sin;
        float rZ3 = x3 * sin + z3 * cos;
        float rX4 = x4 * cos - z4 * sin;
        float rZ4 = x4 * sin + z4 * cos;

        buffer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
        buffer.pos(f3 + rX1, f4 + 0.01, f5 + rZ1).tex(0.0D, 1.0D).color(light, light, light, f1).endVertex();
        buffer.pos(f3 + rX2, f4 + 0.01, f5 + rZ2).tex(1.0D, 1.0D).color(light, light, light, f1).endVertex();
        buffer.pos(f3 + rX3, f4 + 0.01, f5 + rZ3).tex(1.0D, 0.0D).color(light, light, light, f1).endVertex();
        buffer.pos(f3 + rX4, f4 + 0.01, f5 + rZ4).tex(0.0D, 0.0D).color(light, light, light, f1).endVertex();
        Tessellator.getInstance().draw();

        GlStateManager.disableBlend();
        GlStateManager.enableLighting();
    }

    public void onUpdate() {
        ++this.footstepAge;

        if (this.footstepAge == this.footstepMaxAge) {
            this.setExpired();
        }
    }

    public int getFXLayer() {
        return 3;
    }

    @SideOnly(Side.CLIENT)
    public static class Factory implements IParticleFactory {
        @Override
        public Particle createParticle(int particleID, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn,
                                       double xSpeedIn, double ySpeedIn, double zSpeedIn, int... params) {
            EntityPlayer player = Minecraft.getMinecraft().player;
            return new ParticleNimbleFootstep(Minecraft.getMinecraft().getTextureManager(), worldIn, xCoordIn, yCoordIn, zCoordIn, player.rotationYaw);
        }
    }
}
