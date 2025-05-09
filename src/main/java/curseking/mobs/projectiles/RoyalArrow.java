package curseking.mobs.projectiles;// Made with Blockbench 4.12.4
// Exported for Minecraft version 1.7 - 1.12
// Paste this class into your mod and generate all required imports


import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class RoyalArrow extends ModelBase {
	private final ModelRenderer bb_main;

	public RoyalArrow() {
		textureWidth = 32;
		textureHeight = 32;

		bb_main = new ModelRenderer(this);
		bb_main.setRotationPoint(0.0F, 24.0F, 0.0F);
		bb_main.cubeList.add(new ModelBox(bb_main, 2, 1, -0.5F, -32.0F, -0.5F, 1, 2, 1, 0.0F, false));
		bb_main.cubeList.add(new ModelBox(bb_main, -1, 0, -1.0F, -31.0F, -1.0F, 2, 2, 2, 0.0F, false));
		bb_main.cubeList.add(new ModelBox(bb_main, -2, -1, -1.5F, -30.0F, -1.5F, 3, 6, 3, 0.0F, false));
		bb_main.cubeList.add(new ModelBox(bb_main, 0, 0, -1.0F, -25.0F, -1.0F, 2, 2, 2, 0.0F, false));
		bb_main.cubeList.add(new ModelBox(bb_main, 0, 0, -1.0F, -9.0F, -1.0F, 2, 4, 2, 0.0F, false));
		bb_main.cubeList.add(new ModelBox(bb_main, 1, 1, -0.5F, -6.0F, -0.5F, 1, 2, 1, 0.0F, false));
		bb_main.cubeList.add(new ModelBox(bb_main, 1, 1, -1.0F, -4.0F, -0.5F, 2, 2, 1, 0.0F, false));
		bb_main.cubeList.add(new ModelBox(bb_main, 0, 0, -0.5F, -4.0F, -1.0F, 1, 2, 2, 0.0F, false));
		bb_main.cubeList.add(new ModelBox(bb_main, 1, 1, -0.5F, -4.0F, -0.5F, 1, 4, 1, 0.0F, false));
		bb_main.cubeList.add(new ModelBox(bb_main, 2, 1, -0.5F, -24.0F, -0.5F, 1, 16, 1, 0.0F, false));
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		bb_main.render(f5);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}