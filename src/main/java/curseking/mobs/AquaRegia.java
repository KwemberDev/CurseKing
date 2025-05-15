package curseking.mobs;
// Made with Blockbench 4.12.4
// Exported for Minecraft version 1.7 - 1.12
// Paste this class into your mod and generate all required imports

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class AquaRegia extends ModelBase {
	private final ModelRenderer root;
	private final ModelRenderer body;
	private final ModelRenderer head_crest;
	private final ModelRenderer head;
	private final ModelRenderer silver_crest;
	private final ModelRenderer cube_r1;
	private final ModelRenderer cube_r2;
	private final ModelRenderer golden_crest;
	private final ModelRenderer cube_r3;
	private final ModelRenderer cube_r4;
	private final ModelRenderer right_limb;
	private final ModelRenderer right_limb2;
	private final ModelRenderer left_limb;
	private final ModelRenderer left_limb2;
	private final ModelRenderer wings;
	private final ModelRenderer top_wings;
	private final ModelRenderer right2;
	private final ModelRenderer bone13;
	private final ModelRenderer bone14;
	private final ModelRenderer bone15;
	private final ModelRenderer bone16;
	private final ModelRenderer left2;
	private final ModelRenderer bone17;
	private final ModelRenderer bone18;
	private final ModelRenderer bone19;
	private final ModelRenderer bone20;
	private final ModelRenderer bot_wings;
	private final ModelRenderer right3;
	private final ModelRenderer bone21;
	private final ModelRenderer bone22;
	private final ModelRenderer bone23;
	private final ModelRenderer bone24;
	private final ModelRenderer left3;
	private final ModelRenderer bone25;
	private final ModelRenderer bone26;
	private final ModelRenderer bone27;
	private final ModelRenderer bone28;

	public AquaRegia() {
		textureWidth = 128;
		textureHeight = 128;

		root = new ModelRenderer(this);
		root.setRotationPoint(0.0F, 21.0114F, -0.2615F);
		setRotationAngle(root, 0.0873F, 0.0F, 0.0F);
		

		body = new ModelRenderer(this);
		body.setRotationPoint(0.0F, 0.0F, 0.0F);
		root.addChild(body);
		body.cubeList.add(new ModelBox(body, 0, 0, -5.0F, -29.0038F, -7.0872F, 10, 10, 10, 0.0F, false));

		head_crest = new ModelRenderer(this);
		head_crest.setRotationPoint(0.0F, -18.0F, 0.0F);
		body.addChild(head_crest);
		

		head = new ModelRenderer(this);
		head.setRotationPoint(0.0F, -3.0F, 0.0F);
		head_crest.addChild(head);
		head.cubeList.add(new ModelBox(head, 62, 20, -2.5F, -5.5F, -4.5F, 5, 5, 5, 0.0F, false));

		silver_crest = new ModelRenderer(this);
		silver_crest.setRotationPoint(0.0F, -18.0F, -12.0F);
		head_crest.addChild(silver_crest);
		

		cube_r1 = new ModelRenderer(this);
		cube_r1.setRotationPoint(0.0F, 3.5209F, 0.0456F);
		silver_crest.addChild(cube_r1);
		setRotationAngle(cube_r1, 1.2217F, 0.0F, 0.0F);
		cube_r1.cubeList.add(new ModelBox(cube_r1, 41, 6, -2.0F, -2.0F, -5.0F, 4, 1, 6, 0.0F, false));

		cube_r2 = new ModelRenderer(this);
		cube_r2.setRotationPoint(0.0F, 7.5354F, -3.5439F);
		silver_crest.addChild(cube_r2);
		setRotationAngle(cube_r2, 1.6144F, 0.0F, 0.0F);
		cube_r2.cubeList.add(new ModelBox(cube_r2, 55, 8, -1.0F, 0.0F, -3.0F, 2, 0, 3, 0.0F, false));

		golden_crest = new ModelRenderer(this);
		golden_crest.setRotationPoint(0.0F, -10.2909F, -13.5416F);
		head_crest.addChild(golden_crest);
		setRotationAngle(golden_crest, 0.1745F, 0.0F, 0.0F);
		

		cube_r3 = new ModelRenderer(this);
		cube_r3.setRotationPoint(0.0F, 3.7784F, 0.0932F);
		golden_crest.addChild(cube_r3);
		setRotationAngle(cube_r3, 0.5236F, 0.0F, 0.0F);
		cube_r3.cubeList.add(new ModelBox(cube_r3, 48, 15, -1.0F, 0.0F, -3.0F, 2, 0, 3, 0.0F, false));

		cube_r4 = new ModelRenderer(this);
		cube_r4.setRotationPoint(0.0F, -3.3618F, 5.572F);
		golden_crest.addChild(cube_r4);
		setRotationAngle(cube_r4, 0.9163F, 0.0F, 0.0F);
		cube_r4.cubeList.add(new ModelBox(cube_r4, 41, 14, -1.0F, -1.0F, -9.0F, 2, 1, 5, 0.0F, false));

		right_limb = new ModelRenderer(this);
		right_limb.setRotationPoint(-1.0F, -20.0761F, -3.6173F);
		body.addChild(right_limb);
		setRotationAngle(right_limb, 0.3927F, 0.0F, 0.0F);
		right_limb.cubeList.add(new ModelBox(right_limb, 85, 9, -5.0F, -0.0038F, -0.0872F, 5, 12, 0, 0.0F, false));

		right_limb2 = new ModelRenderer(this);
		right_limb2.setRotationPoint(0.0F, 11.9962F, -0.0872F);
		right_limb.addChild(right_limb2);
		setRotationAngle(right_limb2, 0.3927F, 0.0F, 0.0F);
		right_limb2.cubeList.add(new ModelBox(right_limb2, 85, 22, -5.0F, 0.0F, 0.0F, 5, 12, 0, 0.0F, false));

		left_limb = new ModelRenderer(this);
		left_limb.setRotationPoint(1.0F, -20.0761F, -3.6173F);
		body.addChild(left_limb);
		setRotationAngle(left_limb, 0.3927F, 0.0F, 0.0F);
		left_limb.cubeList.add(new ModelBox(left_limb, 85, 9, 0.0F, -0.0038F, -0.0872F, 5, 12, 0, 0.0F, true));

		left_limb2 = new ModelRenderer(this);
		left_limb2.setRotationPoint(0.0F, 11.9962F, -0.0872F);
		left_limb.addChild(left_limb2);
		setRotationAngle(left_limb2, 0.3927F, 0.0F, 0.0F);
		left_limb2.cubeList.add(new ModelBox(left_limb2, 85, 22, 0.0F, 0.0F, 0.0F, 5, 12, 0, 0.0F, true));

		wings = new ModelRenderer(this);
		wings.setRotationPoint(0.0F, -2.0F, 0.0F);
		root.addChild(wings);
		

		top_wings = new ModelRenderer(this);
		top_wings.setRotationPoint(0.0F, -19.0F, 2.0F);
		wings.addChild(top_wings);
		

		right2 = new ModelRenderer(this);
		right2.setRotationPoint(-2.0F, 1.0F, 0.0F);
		top_wings.addChild(right2);
		setRotationAngle(right2, 0.0F, 0.6981F, 0.6109F);
		right2.cubeList.add(new ModelBox(right2, 0, 21, -13.0F, -7.0F, 0.0F, 13, 11, 0, 0.0F, false));

		bone13 = new ModelRenderer(this);
		bone13.setRotationPoint(-13.0F, -7.0F, 0.0F);
		right2.addChild(bone13);
		setRotationAngle(bone13, 0.0F, -0.4363F, 0.0F);
		bone13.cubeList.add(new ModelBox(bone13, 0, 33, -11.0F, 0.0F, 0.0F, 11, 14, 0, 0.0F, false));

		bone14 = new ModelRenderer(this);
		bone14.setRotationPoint(-11.0F, 0.0F, 0.0F);
		bone13.addChild(bone14);
		setRotationAngle(bone14, 0.0F, -0.3491F, 0.0F);
		bone14.cubeList.add(new ModelBox(bone14, 0, 48, -9.0F, 0.0F, 0.0F, 9, 14, 0, 0.0F, false));

		bone15 = new ModelRenderer(this);
		bone15.setRotationPoint(-9.0F, 12.0F, 0.0F);
		bone14.addChild(bone15);
		setRotationAngle(bone15, 0.0F, -0.5236F, 0.0F);
		bone15.cubeList.add(new ModelBox(bone15, 0, 63, -15.0F, -12.0F, 0.0F, 15, 11, 0, 0.0F, false));

		bone16 = new ModelRenderer(this);
		bone16.setRotationPoint(-12.0F, -12.0F, 0.0F);
		bone15.addChild(bone16);
		setRotationAngle(bone16, 0.0F, -0.4363F, 0.0F);
		bone16.cubeList.add(new ModelBox(bone16, 0, 75, -16.7189F, 0.0F, 1.2679F, 14, 7, 0, 0.0F, false));

		left2 = new ModelRenderer(this);
		left2.setRotationPoint(2.0F, 1.0F, 0.0F);
		top_wings.addChild(left2);
		setRotationAngle(left2, 0.0F, -0.6981F, -0.6109F);
		left2.cubeList.add(new ModelBox(left2, 0, 21, 0.0F, -7.0F, 0.0F, 13, 11, 0, 0.0F, true));

		bone17 = new ModelRenderer(this);
		bone17.setRotationPoint(13.0F, -7.0F, 0.0F);
		left2.addChild(bone17);
		setRotationAngle(bone17, 0.0F, 0.4363F, 0.0F);
		bone17.cubeList.add(new ModelBox(bone17, 0, 33, 0.0F, 0.0F, 0.0F, 11, 14, 0, 0.0F, true));

		bone18 = new ModelRenderer(this);
		bone18.setRotationPoint(11.0F, 0.0F, 0.0F);
		bone17.addChild(bone18);
		setRotationAngle(bone18, 0.0F, 0.3491F, 0.0F);
		bone18.cubeList.add(new ModelBox(bone18, 0, 48, 0.0F, 0.0F, 0.0F, 9, 14, 0, 0.0F, true));

		bone19 = new ModelRenderer(this);
		bone19.setRotationPoint(9.0F, 6.0F, 0.0F);
		bone18.addChild(bone19);
		setRotationAngle(bone19, 0.0F, 0.5236F, 0.0F);
		bone19.cubeList.add(new ModelBox(bone19, 0, 63, 0.0F, -6.0F, 0.0F, 15, 11, 0, 0.0F, true));

		bone20 = new ModelRenderer(this);
		bone20.setRotationPoint(12.0F, -6.0F, 0.0F);
		bone19.addChild(bone20);
		setRotationAngle(bone20, 0.0F, 0.4363F, 0.0F);
		bone20.cubeList.add(new ModelBox(bone20, 0, 75, 2.7189F, 0.0F, 1.2679F, 14, 7, 0, 0.0F, true));

		bot_wings = new ModelRenderer(this);
		bot_wings.setRotationPoint(0.0F, -17.6985F, 0.2899F);
		wings.addChild(bot_wings);
		setRotationAngle(bot_wings, 0.3491F, 0.0F, 0.0F);
		

		right3 = new ModelRenderer(this);
		right3.setRotationPoint(0.0F, -1.0F, 0.0F);
		bot_wings.addChild(right3);
		setRotationAngle(right3, 0.0F, 0.2618F, 0.6109F);
		right3.cubeList.add(new ModelBox(right3, 31, 71, -13.0F, -4.0F, 0.0F, 13, 11, 0, 0.0F, false));

		bone21 = new ModelRenderer(this);
		bone21.setRotationPoint(-13.0F, 7.0F, 0.0F);
		right3.addChild(bone21);
		setRotationAngle(bone21, 0.0F, -1.0036F, 0.0F);
		bone21.cubeList.add(new ModelBox(bone21, 31, 56, -11.0F, -14.0F, 0.0F, 11, 14, 0, 0.0F, false));

		bone22 = new ModelRenderer(this);
		bone22.setRotationPoint(-11.0F, 0.0F, 0.0F);
		bone21.addChild(bone22);
		setRotationAngle(bone22, 0.0F, -1.4399F, 0.0F);
		bone22.cubeList.add(new ModelBox(bone22, 31, 41, -9.0F, -14.0F, 0.0F, 9, 14, 0, 0.0F, false));

		bone23 = new ModelRenderer(this);
		bone23.setRotationPoint(-9.0F, 0.0F, 0.0F);
		bone22.addChild(bone23);
		setRotationAngle(bone23, 0.0F, -0.7418F, 0.0F);
		bone23.cubeList.add(new ModelBox(bone23, 31, 29, -15.0F, -11.0F, 0.0F, 15, 11, 0, 0.0F, false));

		bone24 = new ModelRenderer(this);
		bone24.setRotationPoint(-15.0F, 0.0F, 0.0F);
		bone23.addChild(bone24);
		bone24.cubeList.add(new ModelBox(bone24, 31, 21, -14.0F, -7.0F, 0.0F, 14, 7, 0, 0.0F, false));

		left3 = new ModelRenderer(this);
		left3.setRotationPoint(0.0F, -1.0F, 0.0F);
		bot_wings.addChild(left3);
		setRotationAngle(left3, 0.0F, -0.2618F, -0.6109F);
		left3.cubeList.add(new ModelBox(left3, 31, 71, 0.0F, -4.0F, 0.0F, 13, 11, 0, 0.0F, true));

		bone25 = new ModelRenderer(this);
		bone25.setRotationPoint(13.0F, 7.0F, 0.0F);
		left3.addChild(bone25);
		setRotationAngle(bone25, 0.0F, 1.1345F, 0.0F);
		bone25.cubeList.add(new ModelBox(bone25, 31, 56, 0.0F, -14.0F, 0.0F, 11, 14, 0, 0.0F, false));

		bone26 = new ModelRenderer(this);
		bone26.setRotationPoint(11.0F, 0.0F, 0.0F);
		bone25.addChild(bone26);
		setRotationAngle(bone26, 0.0F, 1.4399F, 0.0F);
		bone26.cubeList.add(new ModelBox(bone26, 31, 41, 0.0F, -14.0F, 0.0F, 9, 14, 0, 0.0F, true));

		bone27 = new ModelRenderer(this);
		bone27.setRotationPoint(9.0F, 0.0F, 0.0F);
		bone26.addChild(bone27);
		setRotationAngle(bone27, 0.0F, 0.7418F, 0.0F);
		bone27.cubeList.add(new ModelBox(bone27, 31, 29, 0.0F, -11.0F, 0.0F, 15, 11, 0, 0.0F, true));

		bone28 = new ModelRenderer(this);
		bone28.setRotationPoint(15.0F, 0.0F, 0.0F);
		bone27.addChild(bone28);
		bone28.cubeList.add(new ModelBox(bone28, 31, 21, 0.0F, -7.0F, 0.0F, 14, 7, 0, 0.0F, true));
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		root.render(f5);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}