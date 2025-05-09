package curseking.mobs;// Made with Blockbench 4.12.4
// Exported for Minecraft version 1.7 - 1.12
// Paste this class into your mod and generate all required imports


import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class AquaRegia extends ModelBase {

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
	private final ModelRenderer cube_r11;
	private final ModelRenderer cube_r12;

	public AquaRegia() {
		textureWidth = 16;
		textureHeight = 16;

		bb_main = new ModelRenderer(this);
		bb_main.setRotationPoint(0.0F, 24.0F, 0.0F);
		bb_main.cubeList.add(new ModelBox(bb_main, 0, 0, -2.0F, -23.0F, 2.0F, 4, 13, 2, 0.0F, false));
		bb_main.cubeList.add(new ModelBox(bb_main, -3, -3, -2.0F, -32.0F, 0.0F, 4, 4, 5, 0.0F, false));
		bb_main.cubeList.add(new ModelBox(bb_main, 0, 0, -1.0F, -28.0F, 2.0F, 2, 5, 2, 0.0F, false));

		cube_r1 = new ModelRenderer(this);
		cube_r1.setRotationPoint(0.0F, -15.0F, 5.0F);
		bb_main.addChild(cube_r1);
		setRotationAngle(cube_r1, 0.1572F, -0.3614F, -0.4215F);
		cube_r1.cubeList.add(new ModelBox(cube_r1, 2, 2, -1.0F, -2.0F, -1.0F, 12, 2, 0, 0.0F, false));

		cube_r2 = new ModelRenderer(this);
		cube_r2.setRotationPoint(0.0F, -18.0F, 5.0F);
		bb_main.addChild(cube_r2);
		setRotationAngle(cube_r2, 0.1572F, -0.3614F, -0.4215F);
		cube_r2.cubeList.add(new ModelBox(cube_r2, 2, 2, -1.0F, -2.0F, -1.0F, 12, 2, 0, 0.0F, false));

		cube_r3 = new ModelRenderer(this);
		cube_r3.setRotationPoint(-9.5F, -19.5F, 8.0F);
		bb_main.addChild(cube_r3);
		setRotationAngle(cube_r3, 0.1178F, 0.2822F, 0.4481F);
		cube_r3.cubeList.add(new ModelBox(cube_r3, 2, 2, -1.0F, -2.0F, -1.0F, 12, 2, 0, 0.0F, false));

		cube_r4 = new ModelRenderer(this);
		cube_r4.setRotationPoint(-9.5F, -22.5F, 8.0F);
		bb_main.addChild(cube_r4);
		setRotationAngle(cube_r4, 0.1178F, 0.2822F, 0.4481F);
		cube_r4.cubeList.add(new ModelBox(cube_r4, 2, 2, -1.0F, -2.0F, -1.0F, 12, 2, 0, 0.0F, false));

		cube_r5 = new ModelRenderer(this);
		cube_r5.setRotationPoint(-9.5F, -25.5F, 8.0F);
		bb_main.addChild(cube_r5);
		setRotationAngle(cube_r5, 0.1178F, 0.2822F, 0.4481F);
		cube_r5.cubeList.add(new ModelBox(cube_r5, 2, 2, -1.0F, -2.0F, -1.0F, 12, 2, 0, 0.0F, false));

		cube_r6 = new ModelRenderer(this);
		cube_r6.setRotationPoint(0.0F, -21.0F, 5.0F);
		bb_main.addChild(cube_r6);
		setRotationAngle(cube_r6, 0.1572F, -0.3614F, -0.4215F);
		cube_r6.cubeList.add(new ModelBox(cube_r6, 2, 2, -1.0F, -2.0F, -1.0F, 12, 2, 0, 0.0F, false));

		cube_r7 = new ModelRenderer(this);
		cube_r7.setRotationPoint(2.0F, -9.0F, 2.5F);
		bb_main.addChild(cube_r7);
		setRotationAngle(cube_r7, 0.0F, 0.0F, -0.2618F);
		cube_r7.cubeList.add(new ModelBox(cube_r7, 1, 1, -1.0F, -2.0F, 0.0F, 1, 6, 1, 0.0F, false));

		cube_r8 = new ModelRenderer(this);
		cube_r8.setRotationPoint(1.0F, -9.0F, 2.5F);
		bb_main.addChild(cube_r8);
		setRotationAngle(cube_r8, -0.1642F, -0.0594F, -0.0824F);
		cube_r8.cubeList.add(new ModelBox(cube_r8, 1, 1, -1.0F, -2.0F, 0.0F, 1, 6, 1, 0.0F, false));

		cube_r9 = new ModelRenderer(this);
		cube_r9.setRotationPoint(0.0F, -9.0F, 3.5F);
		bb_main.addChild(cube_r9);
		setRotationAngle(cube_r9, 0.3927F, 0.0F, 0.2618F);
		cube_r9.cubeList.add(new ModelBox(cube_r9, 1, 1, -1.0F, -2.0F, 0.0F, 1, 6, 1, 0.0F, false));

		cube_r10 = new ModelRenderer(this);
		cube_r10.setRotationPoint(-1.0F, -9.0F, 2.5F);
		bb_main.addChild(cube_r10);
		setRotationAngle(cube_r10, 0.0F, 0.0F, 0.2618F);
		cube_r10.cubeList.add(new ModelBox(cube_r10, 1, 1, -1.0F, -2.0F, 0.0F, 1, 6, 1, 0.0F, false));

		cube_r11 = new ModelRenderer(this);
		cube_r11.setRotationPoint(4.0F, -12.0F, -2.0F);
		bb_main.addChild(cube_r11);
		setRotationAngle(cube_r11, -0.5236F, 0.4363F, -0.3927F);
		cube_r11.cubeList.add(new ModelBox(cube_r11, 0, 0, -1.0F, -13.0F, -1.0F, 2, 13, 2, 0.0F, false));

		cube_r12 = new ModelRenderer(this);
		cube_r12.setRotationPoint(-4.0F, -12.0F, -2.0F);
		bb_main.addChild(cube_r12);
		setRotationAngle(cube_r12, -0.5236F, -0.4363F, 0.3927F);
		cube_r12.cubeList.add(new ModelBox(cube_r12, 0, 0, -1.0F, -13.0F, -1.0F, 2, 13, 2, 0.0F, false));
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