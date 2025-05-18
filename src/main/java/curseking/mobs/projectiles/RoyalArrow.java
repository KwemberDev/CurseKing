package curseking.mobs.projectiles;// Made with Blockbench 4.12.4
// Exported for Minecraft version 1.7 - 1.12
// Paste this class into your mod and generate all required imports


import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class RoyalArrow extends ModelBase {
	private final ModelRenderer bb_main;
	private final ModelRenderer cube_r1;
	private final ModelRenderer cube_r2;
	private final ModelRenderer cube_r3;
	private final ModelRenderer cube_r4;
	private final ModelRenderer cube_r5;

	public RoyalArrow() {
		textureWidth = 128;
		textureHeight = 128;

		bb_main = new ModelRenderer(this);
		bb_main.setRotationPoint(0.0F, 24.0F, 0.0F);
		bb_main.cubeList.add(new ModelBox(bb_main, 99, 20, -0.5F, -8.5F, -5.0F, 1, 1, 13, 0.0F, false));

		cube_r1 = new ModelRenderer(this);
		cube_r1.setRotationPoint(0.0F, -8.0F, -10.0F);
		bb_main.addChild(cube_r1);
		setRotationAngle(cube_r1, 0.0F, 0.0F, -2.3562F);
		cube_r1.cubeList.add(new ModelBox(cube_r1, 24, 17, 0.0F, -3.0F, 0.0F, 0, 2, 7, 0.0F, false));
		cube_r1.cubeList.add(new ModelBox(cube_r1, 34, 10, 0.0F, -5.0F, 7.0F, 0, 5, 11, 0.0F, false));

		cube_r2 = new ModelRenderer(this);
		cube_r2.setRotationPoint(0.0F, -8.0F, -10.0F);
		bb_main.addChild(cube_r2);
		setRotationAngle(cube_r2, 0.0F, 0.0F, 2.3562F);
		cube_r2.cubeList.add(new ModelBox(cube_r2, 24, 17, 0.0F, -3.0F, 0.0F, 0, 2, 7, 0.0F, false));
		cube_r2.cubeList.add(new ModelBox(cube_r2, 34, 10, 0.0F, -5.0F, 7.0F, 0, 5, 11, 0.0F, false));

		cube_r3 = new ModelRenderer(this);
		cube_r3.setRotationPoint(0.0F, -8.0F, -2.0F);
		bb_main.addChild(cube_r3);
		setRotationAngle(cube_r3, -1.5708F, 0.0F, 0.0F);
		cube_r3.cubeList.add(new ModelBox(cube_r3, 82, 23, 0.0F, 1.0F, -3.0F, 0, 7, 3, 0.0F, false));

		cube_r4 = new ModelRenderer(this);
		cube_r4.setRotationPoint(0.0F, -8.0F, 4.0F);
		bb_main.addChild(cube_r4);
		setRotationAngle(cube_r4, 0.0F, 0.0F, 0.7854F);
		cube_r4.cubeList.add(new ModelBox(cube_r4, 34, 10, 0.0F, -5.0F, -7.0F, 0, 5, 11, 0.0F, false));

		cube_r5 = new ModelRenderer(this);
		cube_r5.setRotationPoint(0.0F, -8.0F, 4.0F);
		bb_main.addChild(cube_r5);
		setRotationAngle(cube_r5, 0.0F, 0.0F, -0.7854F);
		cube_r5.cubeList.add(new ModelBox(cube_r5, 34, 10, 0.0F, -5.0F, -7.0F, 0, 5, 11, 0.0F, false));
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