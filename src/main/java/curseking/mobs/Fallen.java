package curseking.mobs;// Made with Blockbench 4.12.4
// Exported for Minecraft version 1.7 - 1.12
// Paste this class into your mod and generate all required imports


import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class Fallen extends ModelBase {
	private final ModelRenderer bone;
	private final ModelRenderer body;
	private final ModelRenderer upper_body;
	private final ModelRenderer waist_r1;
	private final ModelRenderer chest_r1;
	private final ModelRenderer head;
	private final ModelRenderer head_r1;
	private final ModelRenderer mask_r1;
	private final ModelRenderer hood;
	private final ModelRenderer hood_b_r1;
	private final ModelRenderer hood_a_r1;
	private final ModelRenderer arms;
	private final ModelRenderer arm_right;
	private final ModelRenderer elbow_right;
	private final ModelRenderer cube_r1;
	private final ModelRenderer cube_r2;
	private final ModelRenderer hand_right;
	private final ModelRenderer staff;
	private final ModelRenderer arm_left;
	private final ModelRenderer elbow_left;
	private final ModelRenderer cube_r3;
	private final ModelRenderer cube_r4;
	private final ModelRenderer hand_left;
	private final ModelRenderer legs;
	private final ModelRenderer robe_nw_r1;
	private final ModelRenderer robe_s_r1;
	private final ModelRenderer robe_sw_r1;
	private final ModelRenderer robe_w_r1;
	private final ModelRenderer robe_se_r1;
	private final ModelRenderer robe_e_r1;
	private final ModelRenderer robe_ne_r1;
	private final ModelRenderer robe_n_r1;
	private final ModelRenderer leg_right;
	private final ModelRenderer leg_left;

	public Fallen() {
		textureWidth = 128;
		textureHeight = 128;

		bone = new ModelRenderer(this);
		bone.setRotationPoint(0.0F, 20.0F, 0.0F);
		

		body = new ModelRenderer(this);
		body.setRotationPoint(0.0F, 0.0F, 0.0F);
		bone.addChild(body);
		

		upper_body = new ModelRenderer(this);
		upper_body.setRotationPoint(0.0F, -18.3986F, 0.2403F);
		body.addChild(upper_body);
		setRotationAngle(upper_body, 0.2618F, 0.0F, 0.0F);
		

		waist_r1 = new ModelRenderer(this);
		waist_r1.setRotationPoint(-0.5F, -0.5777F, -2.0238F);
		upper_body.addChild(waist_r1);
		setRotationAngle(waist_r1, -0.3054F, 0.0F, 0.0F);
		waist_r1.cubeList.add(new ModelBox(waist_r1, 34, 51, -3.0F, -12.0F, 0.0F, 7, 12, 4, 0.0F, false));

		chest_r1 = new ModelRenderer(this);
		chest_r1.setRotationPoint(0.0F, -14.6014F, 0.7597F);
		upper_body.addChild(chest_r1);
		setRotationAngle(chest_r1, 0.1309F, 0.0F, 0.0F);
		chest_r1.cubeList.add(new ModelBox(chest_r1, 0, 27, -6.5F, -4.0F, -2.0F, 13, 9, 7, 0.0F, false));

		head = new ModelRenderer(this);
		head.setRotationPoint(0.0F, -19.1014F, 1.1597F);
		upper_body.addChild(head);
		

		head_r1 = new ModelRenderer(this);
		head_r1.setRotationPoint(0.0F, -3.0F, -0.7F);
		head.addChild(head_r1);
		setRotationAngle(head_r1, 0.1745F, 0.0F, 0.0F);
		head_r1.cubeList.add(new ModelBox(head_r1, 41, 35, -3.5F, -4.5F, -2.0F, 7, 8, 6, 0.0F, false));

		mask_r1 = new ModelRenderer(this);
		mask_r1.setRotationPoint(0.0F, -4.5F, -3.0F);
		head.addChild(mask_r1);
		setRotationAngle(mask_r1, 0.1745F, 0.0F, 0.0F);
		mask_r1.cubeList.add(new ModelBox(mask_r1, 80, 21, -4.0F, -3.0F, -0.5F, 8, 7, 7, 0.0F, false));

		hood = new ModelRenderer(this);
		hood.setRotationPoint(0.0F, 0.5F, -0.6F);
		head.addChild(hood);
		

		hood_b_r1 = new ModelRenderer(this);
		hood_b_r1.setRotationPoint(-1.0F, -5.7456F, 9.5373F);
		hood.addChild(hood_b_r1);
		setRotationAngle(hood_b_r1, 0.3491F, 0.0F, 0.0F);
		hood_b_r1.cubeList.add(new ModelBox(hood_b_r1, 57, 60, -3.5F, -4.0F, -5.5F, 9, 7, 4, -0.01F, false));

		hood_a_r1 = new ModelRenderer(this);
		hood_a_r1.setRotationPoint(0.0F, -8.0F, 0.8F);
		hood.addChild(hood_a_r1);
		setRotationAngle(hood_a_r1, -0.2618F, 0.0F, 0.0F);
		hood_a_r1.cubeList.add(new ModelBox(hood_a_r1, 1, 1, -4.5F, -2.5F, -4.5F, 9, 11, 10, 0.0F, false));

		arms = new ModelRenderer(this);
		arms.setRotationPoint(0.0F, -16.6014F, 0.7597F);
		upper_body.addChild(arms);
		setRotationAngle(arms, -0.2618F, 0.0F, 0.0F);
		

		arm_right = new ModelRenderer(this);
		arm_right.setRotationPoint(-7.5F, -1.5F, 2.0F);
		arms.addChild(arm_right);
		setRotationAngle(arm_right, 0.0F, 0.0F, -1.5708F);
		arm_right.cubeList.add(new ModelBox(arm_right, 0, 70, -9.0F, -1.5F, -2.0F, 9, 4, 4, 0.0F, false));

		elbow_right = new ModelRenderer(this);
		elbow_right.setRotationPoint(-9.0F, 2.5F, 2.0F);
		arm_right.addChild(elbow_right);
		

		cube_r1 = new ModelRenderer(this);
		cube_r1.setRotationPoint(-5.0379F, -2.0F, -1.0231F);
		elbow_right.addChild(cube_r1);
		setRotationAngle(cube_r1, 1.5708F, 0.0F, 0.0F);
		cube_r1.cubeList.add(new ModelBox(cube_r1, 41, 9, -4.9621F, -2.9769F, -2.0F, 10, 4, 4, 0.0F, false));

		cube_r2 = new ModelRenderer(this);
		cube_r2.setRotationPoint(-5.0379F, -2.0F, -1.0231F);
		elbow_right.addChild(cube_r2);
		setRotationAngle(cube_r2, 1.5708F, 0.4276F, 0.0F);
		cube_r2.cubeList.add(new ModelBox(cube_r2, 61, 0, -4.9396F, -1.1268F, -2.0F, 9, 4, 4, -0.02F, false));

		hand_right = new ModelRenderer(this);
		hand_right.setRotationPoint(0.0F, 0.0F, -2.0F);
		elbow_right.addChild(hand_right);
		hand_right.cubeList.add(new ModelBox(hand_right, 0, 79, -14.0F, -5.0F, -3.0F, 4, 5, 6, 0.0F, false));

		staff = new ModelRenderer(this);
		staff.setRotationPoint(0.0F, 0.0F, 0.0F);
		hand_right.addChild(staff);
		

		arm_left = new ModelRenderer(this);
		arm_left.setRotationPoint(7.5F, -1.5F, 2.0F);
		arms.addChild(arm_left);
		setRotationAngle(arm_left, 0.0F, 0.0F, 1.5708F);
		arm_left.cubeList.add(new ModelBox(arm_left, 0, 70, 0.0F, -1.5F, -2.0F, 9, 4, 4, 0.0F, true));

		elbow_left = new ModelRenderer(this);
		elbow_left.setRotationPoint(9.0F, 2.5F, 2.0F);
		arm_left.addChild(elbow_left);
		

		cube_r3 = new ModelRenderer(this);
		cube_r3.setRotationPoint(5.0373F, -2.0F, -1.0226F);
		elbow_left.addChild(cube_r3);
		setRotationAngle(cube_r3, 1.5708F, 0.0F, 0.0F);
		cube_r3.cubeList.add(new ModelBox(cube_r3, 41, 9, -5.0373F, -2.9774F, -2.0F, 10, 4, 4, 0.0F, true));

		cube_r4 = new ModelRenderer(this);
		cube_r4.setRotationPoint(5.0373F, -2.0F, -1.0226F);
		elbow_left.addChild(cube_r4);
		setRotationAngle(cube_r4, 1.5708F, -0.4276F, 0.0F);
		cube_r4.cubeList.add(new ModelBox(cube_r4, 61, 0, -4.0608F, -1.126F, -2.0F, 9, 4, 4, -0.02F, true));

		hand_left = new ModelRenderer(this);
		hand_left.setRotationPoint(0.0F, 0.0F, -2.0F);
		elbow_left.addChild(hand_left);
		hand_left.cubeList.add(new ModelBox(hand_left, 0, 79, 10.0F, -5.0F, -3.0F, 4, 5, 6, 0.0F, true));

		legs = new ModelRenderer(this);
		legs.setRotationPoint(0.0F, 4.0F, 0.0F);
		body.addChild(legs);
		legs.cubeList.add(new ModelBox(legs, 34, 20, -5.5F, -23.0F, -3.5F, 11, 4, 7, 0.0F, false));

		robe_nw_r1 = new ModelRenderer(this);
		robe_nw_r1.setRotationPoint(6.4006F, -12.0633F, -4.2483F);
		legs.addChild(robe_nw_r1);
		setRotationAngle(robe_nw_r1, -0.2533F, 0.7519F, -0.3622F);
		robe_nw_r1.cubeList.add(new ModelBox(robe_nw_r1, 84, 55, -1.0F, -7.5F, -2.5F, 0, 15, 5, 0.0F, false));

		robe_s_r1 = new ModelRenderer(this);
		robe_s_r1.setRotationPoint(0.0F, -19.0F, 3.5F);
		legs.addChild(robe_s_r1);
		setRotationAngle(robe_s_r1, 0.2618F, 0.0F, 0.0F);
		robe_s_r1.cubeList.add(new ModelBox(robe_s_r1, 34, 68, -5.5F, 0.0F, 0.0F, 11, 13, 0, 0.0F, false));

		robe_sw_r1 = new ModelRenderer(this);
		robe_sw_r1.setRotationPoint(6.4006F, -12.0633F, 4.2483F);
		legs.addChild(robe_sw_r1);
		setRotationAngle(robe_sw_r1, 0.2533F, -0.7519F, -0.3622F);
		robe_sw_r1.cubeList.add(new ModelBox(robe_sw_r1, 84, 55, -1.0F, -7.5F, -2.5F, 0, 15, 5, 0.0F, false));

		robe_w_r1 = new ModelRenderer(this);
		robe_w_r1.setRotationPoint(5.5F, -18.9F, 0.0F);
		legs.addChild(robe_w_r1);
		setRotationAngle(robe_w_r1, 0.0F, 0.0F, -0.3054F);
		robe_w_r1.cubeList.add(new ModelBox(robe_w_r1, 57, 71, 0.0F, -0.1F, -3.5F, 0, 14, 7, 0.0F, false));

		robe_se_r1 = new ModelRenderer(this);
		robe_se_r1.setRotationPoint(-6.4006F, -12.0633F, 4.2483F);
		legs.addChild(robe_se_r1);
		setRotationAngle(robe_se_r1, 0.2533F, 0.7519F, 0.3622F);
		robe_se_r1.cubeList.add(new ModelBox(robe_se_r1, 84, 55, 1.0F, -7.5F, -2.5F, 0, 15, 5, 0.0F, false));

		robe_e_r1 = new ModelRenderer(this);
		robe_e_r1.setRotationPoint(-5.5F, -18.9F, 0.0F);
		legs.addChild(robe_e_r1);
		setRotationAngle(robe_e_r1, 0.0F, 0.0F, 0.3054F);
		robe_e_r1.cubeList.add(new ModelBox(robe_e_r1, 57, 71, 0.0F, -0.1F, -3.5F, 0, 14, 7, 0.0F, false));

		robe_ne_r1 = new ModelRenderer(this);
		robe_ne_r1.setRotationPoint(-6.4006F, -12.0633F, -4.2483F);
		legs.addChild(robe_ne_r1);
		setRotationAngle(robe_ne_r1, -0.2533F, -0.7519F, 0.3622F);
		robe_ne_r1.cubeList.add(new ModelBox(robe_ne_r1, 84, 55, 1.0F, -7.5F, -2.5F, 0, 15, 5, 0.0F, false));

		robe_n_r1 = new ModelRenderer(this);
		robe_n_r1.setRotationPoint(0.0F, -19.0F, -3.5F);
		legs.addChild(robe_n_r1);
		setRotationAngle(robe_n_r1, -0.2618F, 0.0F, 0.0F);
		robe_n_r1.cubeList.add(new ModelBox(robe_n_r1, 34, 68, -5.5F, 0.0F, 0.0F, 11, 16, 0, 0.0F, false));

		leg_right = new ModelRenderer(this);
		leg_right.setRotationPoint(-2.0F, -18.0F, 0.0F);
		legs.addChild(leg_right);
		leg_right.cubeList.add(new ModelBox(leg_right, 17, 44, -2.0F, -3.0F, -2.0F, 4, 21, 4, 0.0F, false));

		leg_left = new ModelRenderer(this);
		leg_left.setRotationPoint(1.0F, -17.0F, 0.0F);
		legs.addChild(leg_left);
		leg_left.cubeList.add(new ModelBox(leg_left, 0, 44, -1.0F, -4.0F, -2.0F, 4, 21, 4, 0.0F, false));
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		bone.render(f5);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}