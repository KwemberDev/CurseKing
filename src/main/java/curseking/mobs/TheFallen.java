package curseking.mobs;
// Made with Blockbench 4.12.4
// Exported for Minecraft version 1.7 - 1.12
// Paste this class into your mod and generate all required imports


import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class TheFallen extends ModelBase {
	private final ModelRenderer bb_main;
	private final ModelRenderer cube_r1;
	private final ModelRenderer cube_r2;
	private final ModelRenderer cube_r3;
	private final ModelRenderer cube_r4;
	private final ModelRenderer cube_r5;
	private final ModelRenderer cube_r6;
	private final ModelRenderer cube_r7;
	private final ModelRenderer cube_r8;
	private final ModelRenderer cube_r9;
	private final ModelRenderer cube_r10;

	public TheFallen() {
		textureWidth = 32;
		textureHeight = 32;

		bb_main = new ModelRenderer(this);
		bb_main.setRotationPoint(0.0F, 24.0F, 0.0F);
		bb_main.cubeList.add(new ModelBox(bb_main, 0, 0, -9.0F, -38.0F, 6.0F, 18, 4, 4, 0.0F, false));
		bb_main.cubeList.add(new ModelBox(bb_main, 0, 0, -8.0F, -36.0F, 7.0F, 2, 16, 2, 0.0F, false));
		bb_main.cubeList.add(new ModelBox(bb_main, 0, 0, 6.0F, -36.0F, 7.0F, 2, 16, 2, 0.0F, false));
		bb_main.cubeList.add(new ModelBox(bb_main, 0, 0, -8.0F, -16.0F, 7.0F, 2, 16, 2, 0.0F, false));
		bb_main.cubeList.add(new ModelBox(bb_main, 0, 0, -8.0F, -2.0F, 4.0F, 2, 2, 4, 0.0F, false));
		bb_main.cubeList.add(new ModelBox(bb_main, 0, 0, 6.0F, -2.0F, 4.0F, 2, 2, 4, 0.0F, false));
		bb_main.cubeList.add(new ModelBox(bb_main, 0, 0, 6.0F, -16.0F, 7.0F, 2, 16, 2, 0.0F, false));
		bb_main.cubeList.add(new ModelBox(bb_main, -4, -4, -3.25F, -72.0F, -2.0F, 6, 6, 6, 0.0F, false));

		cube_r1 = new ModelRenderer(this);
		cube_r1.setRotationPoint(12.0F, -49.0F, 0.0F);
		bb_main.addChild(cube_r1);
		setRotationAngle(cube_r1, -0.2618F, 0.0F, 0.0F);
		cube_r1.cubeList.add(new ModelBox(cube_r1, 0, 0, -1.0F, -2.0F, -1.0F, 2, 20, 2, 0.0F, false));
		cube_r1.cubeList.add(new ModelBox(cube_r1, 0, 0, -25.0F, -2.0F, -1.0F, 2, 20, 2, 0.0F, false));

		cube_r2 = new ModelRenderer(this);
		cube_r2.setRotationPoint(12.0F, -50.0F, 1.0F);
		bb_main.addChild(cube_r2);
		setRotationAngle(cube_r2, 0.8599F, -0.2332F, -0.2622F);
		cube_r2.cubeList.add(new ModelBox(cube_r2, -10, -10, -1.0F, -2.0F, -1.0F, 2, 2, 12, 0.0F, false));

		cube_r3 = new ModelRenderer(this);
		cube_r3.setRotationPoint(-12.0F, -50.0F, 1.0F);
		bb_main.addChild(cube_r3);
		setRotationAngle(cube_r3, 0.8599F, 0.2332F, 0.2622F);
		cube_r3.cubeList.add(new ModelBox(cube_r3, -10, -10, -1.0F, -2.0F, -1.0F, 2, 2, 12, 0.0F, false));

		cube_r4 = new ModelRenderer(this);
		cube_r4.setRotationPoint(1.0F, -58.0F, 7.0F);
		bb_main.addChild(cube_r4);
		setRotationAngle(cube_r4, 1.7716F, -0.8846F, -1.6142F);
		cube_r4.cubeList.add(new ModelBox(cube_r4, -8, -8, -1.0F, -2.0F, -9.0F, 2, 2, 10, 0.0F, false));

		cube_r5 = new ModelRenderer(this);
		cube_r5.setRotationPoint(-1.0F, -58.0F, 7.0F);
		bb_main.addChild(cube_r5);
		setRotationAngle(cube_r5, 1.7716F, 0.8846F, 1.6142F);
		cube_r5.cubeList.add(new ModelBox(cube_r5, -8, -8, -1.0F, -2.0F, -9.0F, 2, 2, 10, 0.0F, false));

		cube_r6 = new ModelRenderer(this);
		cube_r6.setRotationPoint(1.0F, -58.0F, 6.5F);
		bb_main.addChild(cube_r6);
		setRotationAngle(cube_r6, 0.4363F, 0.0F, 0.0F);
		cube_r6.cubeList.add(new ModelBox(cube_r6, 0, 0, -2.0F, -11.0F, 0.0F, 2, 11, 2, 0.0F, false));

		cube_r7 = new ModelRenderer(this);
		cube_r7.setRotationPoint(1.0F, -48.0F, 7.5F);
		bb_main.addChild(cube_r7);
		setRotationAngle(cube_r7, 0.1309F, 0.0F, 0.0F);
		cube_r7.cubeList.add(new ModelBox(cube_r7, 0, 0, -2.0F, -11.0F, 0.0F, 2, 11, 2, 0.0F, false));

		cube_r8 = new ModelRenderer(this);
		cube_r8.setRotationPoint(-5.5F, -13.0F, 6.5F);
		bb_main.addChild(cube_r8);
		setRotationAngle(cube_r8, 0.3927F, 0.0F, 0.0F);
		cube_r8.cubeList.add(new ModelBox(cube_r8, 0, 0, -3.0F, -5.0F, 0.0F, 3, 5, 3, 0.0F, false));
		cube_r8.cubeList.add(new ModelBox(cube_r8, 0, 0, 11.0F, -5.0F, 0.0F, 3, 5, 3, 0.0F, false));

		cube_r9 = new ModelRenderer(this);
		cube_r9.setRotationPoint(-5.5F, -18.0F, 4.5F);
		bb_main.addChild(cube_r9);
		setRotationAngle(cube_r9, -0.3927F, 0.0F, 0.0F);
		cube_r9.cubeList.add(new ModelBox(cube_r9, 0, 0, -3.0F, -5.0F, 0.0F, 3, 5, 3, 0.0F, false));
		cube_r9.cubeList.add(new ModelBox(cube_r9, 0, 0, 11.0F, -5.0F, 0.0F, 3, 5, 3, 0.0F, false));

		cube_r10 = new ModelRenderer(this);
		cube_r10.setRotationPoint(1.0F, -38.0F, 7.0F);
		bb_main.addChild(cube_r10);
		setRotationAngle(cube_r10, -0.0436F, 0.0F, 0.0F);
		cube_r10.cubeList.add(new ModelBox(cube_r10, 0, 0, -2.0F, -11.0F, 0.0F, 2, 11, 2, 0.0F, false));
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