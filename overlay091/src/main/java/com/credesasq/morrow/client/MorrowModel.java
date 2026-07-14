package com.credesasq.morrow.client;

import com.credesasq.morrow.MorrowMod;
import com.credesasq.morrow.entity.MorrowEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;

/**
 * Rounded cartoon sphere with a center-pivoted body. The pivot is at the physical center of the ball,
 * which makes distance-based rolling look like a real ball touching the ground instead of a model
 * spinning around its feet.
 */
public final class MorrowModel extends HierarchicalModel<MorrowEntity> {
    public static final ModelLayerLocation LAYER_LOCATION =
            new ModelLayerLocation(new ResourceLocation(MorrowMod.MOD_ID, "verity_ball"), "main");

    private final ModelPart root;
    private final ModelPart ball;
    private final ModelPart gloss;

    private final ModelPart face0;
    private final ModelPart face1;
    private final ModelPart face2;
    private final ModelPart face3;
    private final ModelPart face0Open;
    private final ModelPart face0Blink;
    private final ModelPart face2Open;
    private final ModelPart face2Blink;
    private final ModelPart face3Open;
    private final ModelPart face3Blink;
    private final ModelPart face0PupilL;
    private final ModelPart face0PupilR;
    private final ModelPart face2PupilL;
    private final ModelPart face2PupilR;
    private final ModelPart face3PupilL;
    private final ModelPart face3PupilR;
    private final ModelPart face0Mouth;
    private final ModelPart face1Mouth;
    private final ModelPart face2Mouth;
    private final ModelPart face3MouthC;
    private final ModelPart face3MouthL;
    private final ModelPart face3MouthR;

    private final ModelPart orbit;
    private final ModelPart orbitLeft;
    private final ModelPart orbitRight;
    private final ModelPart orbitTop;
    private final ModelPart orbitBottom;
    private final ModelPart fins;
    private final ModelPart finLeft;
    private final ModelPart finRight;
    private final ModelPart spikes;

    private final ModelPart monster;
    private final ModelPart monsterHead;
    private final ModelPart monsterJaw;
    private final ModelPart monsterEye;
    private final ModelPart monsterPupil;
    private final ModelPart monsterTorso;
    private final ModelPart monsterNeck;
    private final ModelPart monsterPelvis;
    private final ModelPart leftArm;
    private final ModelPart rightArm;
    private final ModelPart leftForearm;
    private final ModelPart rightForearm;
    private final ModelPart leftHand;
    private final ModelPart rightHand;
    private final ModelPart leftLeg;
    private final ModelPart rightLeg;
    private final ModelPart leftShin;
    private final ModelPart rightShin;
    private final ModelPart leftFoot;
    private final ModelPart rightFoot;
    private final ModelPart horns;
    private final ModelPart tendrils;
    private final ModelPart leftTendril;
    private final ModelPart rightTendril;
    private final ModelPart backTendril;
    private final ModelPart leftClaw;
    private final ModelPart rightClaw;

    public MorrowModel(ModelPart bakedRoot) {
        this.root = bakedRoot.getChild("verity");
        this.ball = root.getChild("ball");
        this.gloss = ball.getChild("gloss");

        this.face0 = ball.getChild("face_smile");
        this.face1 = ball.getChild("face_happy");
        this.face2 = ball.getChild("face_surprised");
        this.face3 = ball.getChild("face_grumpy");
        this.face0Open = face0.getChild("open");
        this.face0Blink = face0.getChild("blink");
        this.face2Open = face2.getChild("open");
        this.face2Blink = face2.getChild("blink");
        this.face3Open = face3.getChild("open");
        this.face3Blink = face3.getChild("blink");
        this.face0PupilL = face0Open.getChild("pupil_l");
        this.face0PupilR = face0Open.getChild("pupil_r");
        this.face2PupilL = face2Open.getChild("pupil_l");
        this.face2PupilR = face2Open.getChild("pupil_r");
        this.face3PupilL = face3Open.getChild("pupil_l");
        this.face3PupilR = face3Open.getChild("pupil_r");
        this.face0Mouth = face0.getChild("mouth");
        this.face1Mouth = face1.getChild("mouth");
        this.face2Mouth = face2.getChild("mouth");
        this.face3MouthC = face3.getChild("mouth_c");
        this.face3MouthL = face3.getChild("mouth_l");
        this.face3MouthR = face3.getChild("mouth_r");

        this.orbit = root.getChild("orbit");
        this.orbitLeft = orbit.getChild("left");
        this.orbitRight = orbit.getChild("right");
        this.orbitTop = orbit.getChild("top");
        this.orbitBottom = orbit.getChild("bottom");
        this.fins = root.getChild("fins");
        this.finLeft = fins.getChild("left");
        this.finRight = fins.getChild("right");
        this.spikes = root.getChild("spikes");

        this.monster = root.getChild("monster");
        this.monsterPelvis = monster.getChild("pelvis");
        this.monsterTorso = monster.getChild("torso");
        this.monsterNeck = monster.getChild("neck");
        this.monsterHead = monster.getChild("head");
        this.monsterEye = monsterHead.getChild("eye");
        this.monsterPupil = monsterEye.getChild("pupil");
        this.monsterJaw = monsterHead.getChild("jaw");
        this.horns = monsterHead.getChild("horns");
        this.leftArm = monster.getChild("left_arm");
        this.rightArm = monster.getChild("right_arm");
        this.leftForearm = leftArm.getChild("forearm");
        this.rightForearm = rightArm.getChild("forearm");
        this.leftHand = leftForearm.getChild("hand");
        this.rightHand = rightForearm.getChild("hand");
        this.leftClaw = leftHand.getChild("claws");
        this.rightClaw = rightHand.getChild("claws");
        this.leftLeg = monster.getChild("left_leg");
        this.rightLeg = monster.getChild("right_leg");
        this.leftShin = leftLeg.getChild("shin");
        this.rightShin = rightLeg.getChild("shin");
        this.leftFoot = leftShin.getChild("foot");
        this.rightFoot = rightShin.getChild("foot");
        this.tendrils = monster.getChild("tendrils");
        this.leftTendril = tendrils.getChild("left");
        this.rightTendril = tendrils.getChild("right");
        this.backTendril = tendrils.getChild("back");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition meshRoot = mesh.getRoot();
        PartDefinition verity = meshRoot.addOrReplaceChild("verity", CubeListBuilder.create(),
                PartPose.offset(0.0F, 24.0F, 0.0F));

        // Clean rounded yellow sphere. Several intersecting rounded volumes create a much
        // smoother silhouette than a normal Minecraft cube while keeping the center pivot exact.
        PartDefinition ball = verity.addOrReplaceChild("ball",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-5.65F, -5.65F, -5.65F, 11.3F, 11.3F, 11.3F,
                                new CubeDeformation(0.58F))
                        .texOffs(0, 26).addBox(-6.75F, -4.35F, -4.35F, 13.5F, 8.7F, 8.7F,
                                new CubeDeformation(0.20F))
                        .texOffs(50, 0).addBox(-4.35F, -6.75F, -4.35F, 8.7F, 13.5F, 8.7F,
                                new CubeDeformation(0.20F))
                        .texOffs(50, 26).addBox(-4.35F, -4.35F, -6.75F, 8.7F, 8.7F, 13.5F,
                                new CubeDeformation(0.20F)),
                PartPose.offset(0.0F, -8.15F, 0.0F));

        // Intentionally empty: the original minimalist character has no glossy stickers,
        // cheeks, orbiting pieces or decorative fins over the face.
        PartDefinition gloss = ball.addOrReplaceChild("gloss", CubeListBuilder.create(), PartPose.ZERO);

        addSmileFace(ball);
        addHappyFace(ball);
        addSurprisedFace(ball);
        addGrumpyFace(ball);

        PartDefinition orbit = verity.addOrReplaceChild("orbit", CubeListBuilder.create(),
                PartPose.offset(0.0F, -9.45F, 0.0F));
        orbit.addOrReplaceChild("left", cyan().addBox(-1.25F, -1.25F, -1.25F, 2.5F, 2.5F, 2.5F,
                        new CubeDeformation(0.12F)), PartPose.offset(-9.0F, -0.5F, 0.0F));
        orbit.addOrReplaceChild("right", cyan().addBox(-1.05F, -1.05F, -1.05F, 2.1F, 2.1F, 2.1F,
                        new CubeDeformation(0.12F)), PartPose.offset(9.0F, 0.65F, 0.0F));
        orbit.addOrReplaceChild("top", white().addBox(-0.80F, -0.80F, -0.80F, 1.6F, 1.6F, 1.6F,
                        new CubeDeformation(0.10F)), PartPose.offset(0.0F, -8.7F, 1.5F));
        orbit.addOrReplaceChild("bottom", cyan().addBox(-0.72F, -0.72F, -0.72F, 1.44F, 1.44F, 1.44F,
                        new CubeDeformation(0.10F)), PartPose.offset(0.0F, 8.2F, -1.4F));
        orbit.addOrReplaceChild("front", white().addBox(-0.62F, -0.62F, -0.62F, 1.24F, 1.24F, 1.24F,
                        new CubeDeformation(0.08F)), PartPose.offset(5.9F, -5.0F, -5.0F));
        orbit.addOrReplaceChild("back", cyan().addBox(-0.62F, -0.62F, -0.62F, 1.24F, 1.24F, 1.24F,
                        new CubeDeformation(0.08F)), PartPose.offset(-5.9F, 5.0F, 5.0F));

        PartDefinition fins = verity.addOrReplaceChild("fins", CubeListBuilder.create(), PartPose.ZERO);
        fins.addOrReplaceChild("left",
                cyan().addBox(-0.9F, -3.5F, -2.0F, 1.8F, 7.0F, 4.0F,
                        new CubeDeformation(-0.08F)),
                PartPose.offsetAndRotation(-7.5F, -9.45F, 0.8F, 0.0F, 0.15F, -0.55F));
        fins.addOrReplaceChild("right",
                cyan().addBox(-0.9F, -3.5F, -2.0F, 1.8F, 7.0F, 4.0F,
                        new CubeDeformation(-0.08F)),
                PartPose.offsetAndRotation(7.5F, -9.45F, 0.8F, 0.0F, -0.15F, 0.55F));

        PartDefinition spikes = verity.addOrReplaceChild("spikes", CubeListBuilder.create(), PartPose.ZERO);
        spikes.addOrReplaceChild("top",
                white().addBox(-0.75F, -5.2F, -0.75F, 1.5F, 5.2F, 1.5F,
                        new CubeDeformation(-0.08F)),
                PartPose.offset(0.0F, -15.5F, 0.0F));
        spikes.addOrReplaceChild("left",
                cyan().addBox(-0.7F, -4.0F, -0.7F, 1.4F, 4.0F, 1.4F,
                        new CubeDeformation(-0.08F)),
                PartPose.offsetAndRotation(-5.6F, -14.0F, 1.8F, 0.12F, 0.0F, -0.72F));
        spikes.addOrReplaceChild("right",
                cyan().addBox(-0.7F, -4.0F, -0.7F, 1.4F, 4.0F, 1.4F,
                        new CubeDeformation(-0.08F)),
                PartPose.offsetAndRotation(5.6F, -14.0F, 1.8F, 0.12F, 0.0F, 0.72F));

        addMonsterParts(verity);
        return LayerDefinition.create(mesh, 128, 128);
    }

    private static void addSmileFace(PartDefinition ball) {
        PartDefinition face = ball.addOrReplaceChild("face_smile", CubeListBuilder.create(), PartPose.ZERO);
        PartDefinition open = face.addOrReplaceChild("open", CubeListBuilder.create(), PartPose.ZERO);
        // Two tiny vertical line eyes.
        open.addOrReplaceChild("pupil_l", black().addBox(-0.30F, -1.20F, -0.18F, 0.60F, 2.40F, 0.36F),
                PartPose.offset(-2.45F, -1.65F, -6.78F));
        open.addOrReplaceChild("pupil_r", black().addBox(-0.30F, -1.20F, -0.18F, 0.60F, 2.40F, 0.36F),
                PartPose.offset(2.45F, -1.65F, -6.78F));
        PartDefinition blink = face.addOrReplaceChild("blink", CubeListBuilder.create(), PartPose.ZERO);
        blink.addOrReplaceChild("eye_l", black().addBox(-1.05F, -0.20F, -0.18F, 2.10F, 0.40F, 0.36F),
                PartPose.offset(-2.45F, -1.60F, -6.78F));
        blink.addOrReplaceChild("eye_r", black().addBox(-1.05F, -0.20F, -0.18F, 2.10F, 0.40F, 0.36F),
                PartPose.offset(2.45F, -1.60F, -6.78F));
        PartDefinition mouth = face.addOrReplaceChild("mouth", CubeListBuilder.create(), PartPose.offset(0.0F, 1.55F, -6.78F));
        mouth.addOrReplaceChild("center", black().addBox(-1.45F, -0.20F, -0.18F, 2.90F, 0.40F, 0.36F),
                PartPose.offset(0.0F, 0.52F, 0.0F));
        mouth.addOrReplaceChild("left", black().addBox(-1.05F, -0.20F, -0.18F, 2.10F, 0.40F, 0.36F),
                PartPose.offsetAndRotation(-2.15F, -0.05F, 0.0F, 0.0F, 0.0F, 0.48F));
        mouth.addOrReplaceChild("right", black().addBox(-1.05F, -0.20F, -0.18F, 2.10F, 0.40F, 0.36F),
                PartPose.offsetAndRotation(2.15F, -0.05F, 0.0F, 0.0F, 0.0F, -0.48F));
    }

    private static void addHappyFace(PartDefinition ball) {
        PartDefinition face = ball.addOrReplaceChild("face_happy", CubeListBuilder.create(), PartPose.ZERO);
        // Minimal ^ ^ eyes.
        face.addOrReplaceChild("eye_l_a", black().addBox(-0.90F, -0.18F, -0.18F, 1.80F, 0.36F, 0.36F),
                PartPose.offsetAndRotation(-3.05F, -1.65F, -6.78F, 0.0F, 0.0F, -0.55F));
        face.addOrReplaceChild("eye_l_b", black().addBox(-0.90F, -0.18F, -0.18F, 1.80F, 0.36F, 0.36F),
                PartPose.offsetAndRotation(-1.65F, -1.65F, -6.78F, 0.0F, 0.0F, 0.55F));
        face.addOrReplaceChild("eye_r_a", black().addBox(-0.90F, -0.18F, -0.18F, 1.80F, 0.36F, 0.36F),
                PartPose.offsetAndRotation(1.65F, -1.65F, -6.78F, 0.0F, 0.0F, -0.55F));
        face.addOrReplaceChild("eye_r_b", black().addBox(-0.90F, -0.18F, -0.18F, 1.80F, 0.36F, 0.36F),
                PartPose.offsetAndRotation(3.05F, -1.65F, -6.78F, 0.0F, 0.0F, 0.55F));
        PartDefinition mouth = face.addOrReplaceChild("mouth", CubeListBuilder.create(), PartPose.offset(0.0F, 1.55F, -6.78F));
        mouth.addOrReplaceChild("center", black().addBox(-1.65F, -0.20F, -0.18F, 3.30F, 0.40F, 0.36F),
                PartPose.offset(0.0F, 0.65F, 0.0F));
        mouth.addOrReplaceChild("left", black().addBox(-1.20F, -0.20F, -0.18F, 2.40F, 0.40F, 0.36F),
                PartPose.offsetAndRotation(-2.45F, -0.10F, 0.0F, 0.0F, 0.0F, 0.55F));
        mouth.addOrReplaceChild("right", black().addBox(-1.20F, -0.20F, -0.18F, 2.40F, 0.40F, 0.36F),
                PartPose.offsetAndRotation(2.45F, -0.10F, 0.0F, 0.0F, 0.0F, -0.55F));
    }

    private static void addSurprisedFace(PartDefinition ball) {
        PartDefinition face = ball.addOrReplaceChild("face_surprised", CubeListBuilder.create(), PartPose.ZERO);
        PartDefinition open = face.addOrReplaceChild("open", CubeListBuilder.create(), PartPose.ZERO);
        open.addOrReplaceChild("pupil_l", black().addBox(-0.30F, -1.15F, -0.18F, 0.60F, 2.30F, 0.36F),
                PartPose.offset(-2.45F, -1.70F, -6.78F));
        open.addOrReplaceChild("pupil_r", black().addBox(-0.30F, -1.15F, -0.18F, 0.60F, 2.30F, 0.36F),
                PartPose.offset(2.45F, -1.70F, -6.78F));
        PartDefinition blink = face.addOrReplaceChild("blink", CubeListBuilder.create(), PartPose.ZERO);
        blink.addOrReplaceChild("eye_l", black().addBox(-1.0F, -0.20F, -0.18F, 2.0F, 0.40F, 0.36F),
                PartPose.offset(-2.45F, -1.65F, -6.78F));
        blink.addOrReplaceChild("eye_r", black().addBox(-1.0F, -0.20F, -0.18F, 2.0F, 0.40F, 0.36F),
                PartPose.offset(2.45F, -1.65F, -6.78F));
        PartDefinition mouth = face.addOrReplaceChild("mouth", CubeListBuilder.create(), PartPose.offset(0.0F, 1.75F, -6.78F));
        mouth.addOrReplaceChild("top", black().addBox(-1.10F, -0.18F, -0.18F, 2.20F, 0.36F, 0.36F), PartPose.offset(0, -0.90F, 0));
        mouth.addOrReplaceChild("bottom", black().addBox(-1.10F, -0.18F, -0.18F, 2.20F, 0.36F, 0.36F), PartPose.offset(0, 0.90F, 0));
        mouth.addOrReplaceChild("left", black().addBox(-0.18F, -0.90F, -0.18F, 0.36F, 1.80F, 0.36F), PartPose.offset(-1.10F, 0, 0));
        mouth.addOrReplaceChild("right", black().addBox(-0.18F, -0.90F, -0.18F, 0.36F, 1.80F, 0.36F), PartPose.offset(1.10F, 0, 0));
    }

    private static void addGrumpyFace(PartDefinition ball) {
        PartDefinition face = ball.addOrReplaceChild("face_grumpy", CubeListBuilder.create(), PartPose.ZERO);
        PartDefinition open = face.addOrReplaceChild("open", CubeListBuilder.create(), PartPose.ZERO);
        open.addOrReplaceChild("pupil_l", black().addBox(-0.30F, -0.95F, -0.18F, 0.60F, 1.90F, 0.36F),
                PartPose.offset(-2.45F, -1.45F, -6.78F));
        open.addOrReplaceChild("pupil_r", black().addBox(-0.30F, -0.95F, -0.18F, 0.60F, 1.90F, 0.36F),
                PartPose.offset(2.45F, -1.45F, -6.78F));
        PartDefinition blink = face.addOrReplaceChild("blink", CubeListBuilder.create(), PartPose.ZERO);
        blink.addOrReplaceChild("eye_l", black().addBox(-1.0F, -0.20F, -0.18F, 2.0F, 0.40F, 0.36F),
                PartPose.offset(-2.45F, -1.40F, -6.78F));
        blink.addOrReplaceChild("eye_r", black().addBox(-1.0F, -0.20F, -0.18F, 2.0F, 0.40F, 0.36F),
                PartPose.offset(2.45F, -1.40F, -6.78F));
        face.addOrReplaceChild("brow_l", black().addBox(-1.20F, -0.20F, -0.18F, 2.40F, 0.40F, 0.36F),
                PartPose.offsetAndRotation(-2.45F, -3.30F, -6.78F, 0.0F, 0.0F, 0.30F));
        face.addOrReplaceChild("brow_r", black().addBox(-1.20F, -0.20F, -0.18F, 2.40F, 0.40F, 0.36F),
                PartPose.offsetAndRotation(2.45F, -3.30F, -6.78F, 0.0F, 0.0F, -0.30F));
        face.addOrReplaceChild("mouth_c", black().addBox(-1.45F, -0.20F, -0.18F, 2.90F, 0.40F, 0.36F),
                PartPose.offset(0.0F, 1.50F, -6.78F));
        face.addOrReplaceChild("mouth_l", black().addBox(-1.05F, -0.20F, -0.18F, 2.10F, 0.40F, 0.36F),
                PartPose.offsetAndRotation(-2.10F, 2.05F, -6.78F, 0.0F, 0.0F, -0.45F));
        face.addOrReplaceChild("mouth_r", black().addBox(-1.05F, -0.20F, -0.18F, 2.10F, 0.40F, 0.36F),
                PartPose.offsetAndRotation(2.10F, 2.05F, -6.78F, 0.0F, 0.0F, 0.45F));
    }

    private static void addMonsterParts(PartDefinition verity) {
        PartDefinition monster = verity.addOrReplaceChild("monster", CubeListBuilder.create(),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        monster.addOrReplaceChild("pelvis",
                dark().addBox(-2.0F, -1.2F, -1.35F, 4.0F, 2.4F, 2.7F,
                        new CubeDeformation(0.12F)),
                PartPose.offset(0.0F, -15.0F, 0.0F));

        PartDefinition torso = monster.addOrReplaceChild("torso", CubeListBuilder.create(),
                PartPose.offset(0.0F, -22.3F, 0.0F));
        torso.addOrReplaceChild("chest",
                dark().addBox(-3.0F, -5.0F, -1.7F, 6.0F, 10.0F, 3.4F,
                        new CubeDeformation(-0.18F)), PartPose.ZERO);
        torso.addOrReplaceChild("sternum",
                red().addBox(-0.50F, -4.4F, -1.92F, 1.0F, 8.8F, 0.72F,
                        new CubeDeformation(0.05F)), PartPose.ZERO);
        for (int i = 0; i < 5; i++) {
            float y = -3.6F + i * 1.8F;
            torso.addOrReplaceChild("rib_l_" + i,
                    white().addBox(-2.7F, -0.30F, -0.30F, 2.7F, 0.60F, 0.60F,
                            new CubeDeformation(-0.03F)),
                    PartPose.offsetAndRotation(-0.35F, y, -1.75F, 0.0F, 0.0F, 0.18F));
            torso.addOrReplaceChild("rib_r_" + i,
                    white().addBox(0.0F, -0.30F, -0.30F, 2.7F, 0.60F, 0.60F,
                            new CubeDeformation(-0.03F)),
                    PartPose.offsetAndRotation(0.35F, y, -1.75F, 0.0F, 0.0F, -0.18F));
        }
        torso.addOrReplaceChild("shoulders",
                dark().addBox(-5.2F, -0.75F, -1.55F, 10.4F, 1.5F, 3.1F,
                        new CubeDeformation(-0.10F)), PartPose.offset(0.0F, -4.5F, 0.0F));

        monster.addOrReplaceChild("neck",
                dark().addBox(-0.95F, -3.7F, -0.95F, 1.9F, 4.5F, 1.9F,
                        new CubeDeformation(-0.10F)),
                PartPose.offset(0.0F, -29.5F, 0.0F));

        PartDefinition head = monster.addOrReplaceChild("head", CubeListBuilder.create(),
                PartPose.offset(0.0F, -34.0F, 0.0F));
        head.addOrReplaceChild("skull",
                dark().addBox(-3.0F, -4.0F, -2.6F, 6.0F, 7.0F, 5.2F,
                        new CubeDeformation(0.18F)), PartPose.ZERO);
        head.addOrReplaceChild("face_plate",
                black().addBox(-2.35F, -2.55F, -2.95F, 4.7F, 3.8F, 0.9F,
                        new CubeDeformation(0.06F)), PartPose.ZERO);

        PartDefinition eye = head.addOrReplaceChild("eye", CubeListBuilder.create(),
                PartPose.offset(0.0F, -1.0F, -3.55F));
        eye.addOrReplaceChild("white",
                white().addBox(-2.05F, -1.15F, -0.30F, 4.1F, 2.3F, 0.60F,
                        new CubeDeformation(0.12F)), PartPose.ZERO);
        eye.addOrReplaceChild("pupil",
                red().addBox(-0.62F, -0.95F, -0.38F, 1.24F, 1.9F, 0.76F,
                        new CubeDeformation(0.08F)), PartPose.offset(0.0F, 0.0F, -0.38F));
        eye.addOrReplaceChild("lid_top",
                dark().addBox(-2.45F, -0.48F, -0.26F, 4.9F, 0.96F, 0.52F),
                PartPose.offsetAndRotation(0.0F, -1.20F, -0.10F, 0.0F, 0.0F, 0.0F));
        eye.addOrReplaceChild("lid_bottom",
                dark().addBox(-2.45F, -0.38F, -0.26F, 4.9F, 0.76F, 0.52F),
                PartPose.offset(0.0F, 1.20F, -0.10F));

        PartDefinition jaw = head.addOrReplaceChild("jaw", CubeListBuilder.create(),
                PartPose.offset(0.0F, 2.7F, -0.2F));
        jaw.addOrReplaceChild("bone",
                dark().addBox(-2.65F, -0.4F, -2.4F, 5.3F, 3.0F, 4.4F,
                        new CubeDeformation(0.08F)), PartPose.ZERO);
        jaw.addOrReplaceChild("mouth",
                black().addBox(-2.05F, -0.1F, -2.82F, 4.1F, 1.9F, 0.72F,
                        new CubeDeformation(0.08F)), PartPose.ZERO);
        for (int i = 0; i < 6; i++) {
            float x = -1.65F + i * 0.66F;
            jaw.addOrReplaceChild("tooth_" + i,
                    white().addBox(-0.20F, -0.15F, -0.34F, 0.40F, 1.15F, 0.68F,
                            new CubeDeformation(-0.02F)),
                    PartPose.offsetAndRotation(x, -0.05F, -3.20F, -0.12F, 0.0F, 0.0F));
        }

        PartDefinition horns = head.addOrReplaceChild("horns", CubeListBuilder.create(), PartPose.ZERO);
        horns.addOrReplaceChild("left",
                white().addBox(-0.48F, -5.0F, -0.48F, 0.96F, 5.0F, 0.96F,
                        new CubeDeformation(-0.06F)),
                PartPose.offsetAndRotation(-2.25F, -3.3F, 0.35F, 0.10F, 0.0F, -0.50F));
        horns.addOrReplaceChild("right",
                white().addBox(-0.48F, -5.0F, -0.48F, 0.96F, 5.0F, 0.96F,
                        new CubeDeformation(-0.06F)),
                PartPose.offsetAndRotation(2.25F, -3.3F, 0.35F, 0.10F, 0.0F, 0.50F));

        PartDefinition leftArm = monster.addOrReplaceChild("left_arm", CubeListBuilder.create(),
                PartPose.offset(-4.3F, -26.0F, 0.0F));
        leftArm.addOrReplaceChild("upper",
                dark().addBox(-1.0F, -0.8F, -1.0F, 2.0F, 9.8F, 2.0F,
                        new CubeDeformation(-0.12F)), PartPose.ZERO);
        PartDefinition leftForearm = leftArm.addOrReplaceChild("forearm", CubeListBuilder.create(),
                PartPose.offset(0.0F, 8.2F, 0.0F));
        leftForearm.addOrReplaceChild("bone",
                dark().addBox(-0.80F, -0.2F, -0.80F, 1.6F, 10.2F, 1.6F,
                        new CubeDeformation(-0.12F)), PartPose.ZERO);
        PartDefinition leftHand = leftForearm.addOrReplaceChild("hand", CubeListBuilder.create(),
                PartPose.offset(0.0F, 9.4F, 0.0F));
        leftHand.addOrReplaceChild("palm",
                dark().addBox(-1.25F, -0.4F, -0.8F, 2.5F, 3.2F, 1.6F,
                        new CubeDeformation(-0.04F)), PartPose.ZERO);
        PartDefinition leftClaws = leftHand.addOrReplaceChild("claws", CubeListBuilder.create(), PartPose.ZERO);
        for (int i = 0; i < 3; i++) {
            leftClaws.addOrReplaceChild("claw_" + i,
                    white().addBox(-0.16F, -0.15F, -0.16F, 0.32F, 3.3F, 0.32F,
                            new CubeDeformation(-0.02F)),
                    PartPose.offsetAndRotation(-0.75F + i * 0.75F, 2.4F, -0.30F,
                            -0.28F, 0.0F, -0.08F + i * 0.08F));
        }

        PartDefinition rightArm = monster.addOrReplaceChild("right_arm", CubeListBuilder.create(),
                PartPose.offset(4.3F, -26.0F, 0.0F));
        rightArm.addOrReplaceChild("upper",
                dark().addBox(-1.0F, -0.8F, -1.0F, 2.0F, 9.8F, 2.0F,
                        new CubeDeformation(-0.12F)), PartPose.ZERO);
        PartDefinition rightForearm = rightArm.addOrReplaceChild("forearm", CubeListBuilder.create(),
                PartPose.offset(0.0F, 8.2F, 0.0F));
        rightForearm.addOrReplaceChild("bone",
                dark().addBox(-0.80F, -0.2F, -0.80F, 1.6F, 10.2F, 1.6F,
                        new CubeDeformation(-0.12F)), PartPose.ZERO);
        PartDefinition rightHand = rightForearm.addOrReplaceChild("hand", CubeListBuilder.create(),
                PartPose.offset(0.0F, 9.4F, 0.0F));
        rightHand.addOrReplaceChild("palm",
                dark().addBox(-1.25F, -0.4F, -0.8F, 2.5F, 3.2F, 1.6F,
                        new CubeDeformation(-0.04F)), PartPose.ZERO);
        PartDefinition rightClaws = rightHand.addOrReplaceChild("claws", CubeListBuilder.create(), PartPose.ZERO);
        for (int i = 0; i < 3; i++) {
            rightClaws.addOrReplaceChild("claw_" + i,
                    white().addBox(-0.16F, -0.15F, -0.16F, 0.32F, 3.3F, 0.32F,
                            new CubeDeformation(-0.02F)),
                    PartPose.offsetAndRotation(-0.75F + i * 0.75F, 2.4F, -0.30F,
                            -0.28F, 0.0F, -0.08F + i * 0.08F));
        }

        PartDefinition leftLeg = monster.addOrReplaceChild("left_leg", CubeListBuilder.create(),
                PartPose.offset(-1.25F, -14.8F, 0.0F));
        leftLeg.addOrReplaceChild("thigh",
                dark().addBox(-1.1F, -0.4F, -1.15F, 2.2F, 8.2F, 2.3F,
                        new CubeDeformation(-0.12F)), PartPose.ZERO);
        PartDefinition leftShin = leftLeg.addOrReplaceChild("shin", CubeListBuilder.create(),
                PartPose.offset(0.0F, 7.2F, 0.0F));
        leftShin.addOrReplaceChild("bone",
                dark().addBox(-0.82F, -0.2F, -0.88F, 1.64F, 7.0F, 1.76F,
                        new CubeDeformation(-0.12F)), PartPose.ZERO);
        leftShin.addOrReplaceChild("foot",
                dark().addBox(-0.85F, -0.65F, -2.6F, 1.7F, 1.3F, 3.5F,
                        new CubeDeformation(0.04F)), PartPose.offset(0.0F, 6.65F, -0.15F));

        PartDefinition rightLeg = monster.addOrReplaceChild("right_leg", CubeListBuilder.create(),
                PartPose.offset(1.25F, -14.8F, 0.0F));
        rightLeg.addOrReplaceChild("thigh",
                dark().addBox(-1.1F, -0.4F, -1.15F, 2.2F, 8.2F, 2.3F,
                        new CubeDeformation(-0.12F)), PartPose.ZERO);
        PartDefinition rightShin = rightLeg.addOrReplaceChild("shin", CubeListBuilder.create(),
                PartPose.offset(0.0F, 7.2F, 0.0F));
        rightShin.addOrReplaceChild("bone",
                dark().addBox(-0.82F, -0.2F, -0.88F, 1.64F, 7.0F, 1.76F,
                        new CubeDeformation(-0.12F)), PartPose.ZERO);
        rightShin.addOrReplaceChild("foot",
                dark().addBox(-0.85F, -0.65F, -2.6F, 1.7F, 1.3F, 3.5F,
                        new CubeDeformation(0.04F)), PartPose.offset(0.0F, 6.65F, -0.15F));

        PartDefinition tendrils = monster.addOrReplaceChild("tendrils", CubeListBuilder.create(), PartPose.ZERO);
        tendrils.addOrReplaceChild("left",
                dark().addBox(-0.45F, 0.0F, -0.45F, 0.9F, 11.0F, 0.9F,
                        new CubeDeformation(-0.08F)),
                PartPose.offsetAndRotation(-2.4F, -23.5F, 1.4F, 0.40F, 0.0F, 0.45F));
        tendrils.addOrReplaceChild("right",
                dark().addBox(-0.45F, 0.0F, -0.45F, 0.9F, 11.0F, 0.9F,
                        new CubeDeformation(-0.08F)),
                PartPose.offsetAndRotation(2.4F, -23.5F, 1.4F, 0.40F, 0.0F, -0.45F));
        tendrils.addOrReplaceChild("back",
                red().addBox(-0.48F, 0.0F, -0.48F, 0.96F, 13.5F, 0.96F,
                        new CubeDeformation(-0.08F)),
                PartPose.offsetAndRotation(0.0F, -20.0F, 1.8F, 0.58F, 0.0F, 0.0F));
    }

    private static CubeListBuilder black() { return CubeListBuilder.create().texOffs(0, 64); }
    private static CubeListBuilder white() { return CubeListBuilder.create().texOffs(0, 80); }
    private static CubeListBuilder dark() { return CubeListBuilder.create().texOffs(0, 96); }
    private static CubeListBuilder cyan() { return CubeListBuilder.create().texOffs(0, 112); }
    private static CubeListBuilder pink() { return CubeListBuilder.create().texOffs(64, 112); }
    private static CubeListBuilder red() { return CubeListBuilder.create().texOffs(96, 112); }

    @Override
    public void setupAnim(MorrowEntity entity, float limbSwing, float limbSwingAmount,
                          float ageInTicks, float netHeadYaw, float headPitch) {
        float motion = Mth.clamp(limbSwingAmount * 1.7F, 0.0F, 1.0F);
        float rollX = entity.isShell() ? 0.0F : limbSwing * 0.72F * motion;
        setupInternal(entity, entity.getStage(), entity.getFace(), false,
                rollX, 0.0F, motion, 0.0F, 0.0F, ageInTicks, netHeadYaw, headPitch);
    }

    public void setupForPlayer(LivingEntity entity, int stage, int face, boolean monsterMode,
                               float rollX, float rollZ, float motion, float impact, float speech,
                               float ageInTicks, float netHeadYaw, float headPitch) {
        setupInternal(entity, stage, face, monsterMode, rollX, rollZ, motion, impact, speech,
                ageInTicks, netHeadYaw, headPitch);
    }

    /** Compatibility overload for older rendering code. */
    public void setupFor(LivingEntity entity, int stage, int face, boolean monsterMode,
                         float limbSwing, float limbSwingAmount, float ageInTicks,
                         float netHeadYaw, float headPitch) {
        float motion = Mth.clamp(limbSwingAmount * 1.7F, 0.0F, 1.0F);
        setupInternal(entity, stage, face, monsterMode, limbSwing * 0.72F * motion, 0.0F,
                motion, 0.0F, 0.0F, ageInTicks, netHeadYaw, headPitch);
    }

    private void setupInternal(LivingEntity entity, int stage, int face, boolean monsterMode,
                               float rollX, float rollZ, float motion, float impact, float speech,
                               float ageInTicks, float netHeadYaw, float headPitch) {
        root.getAllParts().forEach(ModelPart::resetPose);

        int safeStage = Mth.clamp(stage, 0, 4);
        boolean hasFace = face >= 0 && face <= 3;
        ball.visible = !monsterMode;
        face0.visible = !monsterMode && face == 0;
        face1.visible = !monsterMode && face == 1;
        face2.visible = !monsterMode && face == 2;
        face3.visible = !monsterMode && face == 3;
        gloss.visible = false;
        orbit.visible = false;
        fins.visible = false;
        spikes.visible = false;

        monster.visible = monsterMode;
        monsterEye.visible = monsterMode;
        horns.visible = monsterMode && safeStage >= 1;
        tendrils.visible = monsterMode && safeStage >= 2;
        backTendril.visible = monsterMode && safeStage >= 3;
        leftClaw.visible = monsterMode;
        rightClaw.visible = monsterMode;

        int blinkClock = Math.floorMod((int) ageInTicks + entity.getId() * 13, 94);
        boolean blinking = !monsterMode && blinkClock >= 89;
        face0Open.visible = !blinking;
        face0Blink.visible = blinking;
        face2Open.visible = !blinking;
        face2Blink.visible = blinking;
        face3Open.visible = !blinking;
        face3Blink.visible = blinking;

        float lookX = Mth.clamp(netHeadYaw / 65.0F, -1.0F, 1.0F) * 0.30F;
        float lookY = Mth.clamp(headPitch / 50.0F, -1.0F, 1.0F) * 0.22F;
        placePupil(face0PupilL, -2.45F, -1.65F, lookX * 0.35F, lookY * 0.35F);
        placePupil(face0PupilR, 2.45F, -1.65F, lookX * 0.35F, lookY * 0.35F);
        placePupil(face2PupilL, -2.45F, -1.70F, lookX * 0.35F, lookY * 0.35F);
        placePupil(face2PupilR, 2.45F, -1.70F, lookX * 0.35F, lookY * 0.35F);
        placePupil(face3PupilL, -2.45F, -1.45F, lookX * 0.25F, lookY * 0.25F);
        placePupil(face3PupilR, 2.45F, -1.45F, lookX * 0.25F, lookY * 0.25F);

        float talk = Mth.clamp(speech, 0.0F, 1.0F);
        if (talk > 0.0F) {
            float open = 1.0F + talk * 0.72F;
            face0Mouth.yScale = open;
            face1Mouth.yScale = 1.0F + talk * 0.58F;
            face2Mouth.yScale = 1.0F + talk * 0.82F;
            face3MouthC.yScale = 1.0F + talk * 1.25F;
            face3MouthL.zRot += talk * 0.12F;
            face3MouthR.zRot -= talk * 0.12F;
            face0Mouth.y += talk * 0.16F;
            face1Mouth.y += talk * 0.20F;
            face2Mouth.y += talk * 0.24F;
            face3MouthC.y += talk * 0.12F;
        }

        if (!monsterMode) {
            ball.xRot = rollX;
            ball.zRot = rollZ;
            ball.yRot = 0.0F;

            float airStretch = entity.onGround() ? 0.0F
                    : Mth.clamp((float) Math.abs(entity.getDeltaMovement().y) * 0.18F, 0.0F, 0.10F);
            float squash = Mth.clamp(impact * 0.13F + motion * 0.018F, 0.0F, 0.16F);
            ball.xScale = 1.0F + squash * 0.56F - airStretch * 0.22F;
            ball.yScale = 1.0F - squash + airStretch;
            ball.zScale = 1.0F + squash * 0.56F - airStretch * 0.22F;
            ball.y += squash * 1.35F;
        } else {
            boolean airborne = !entity.onGround();
            float stride = Mth.sin(ageInTicks * 0.62F) * motion;
            float opposite = Mth.sin(ageInTicks * 0.62F + Mth.PI) * motion;
            float bounce = Mth.abs(Mth.cos(ageInTicks * 0.62F)) * motion;
            float attack = entity.getAttackAnim(0.0F);
            float attackCurve = Mth.sin(Mth.sqrt(attack) * Mth.PI);

            monster.y = -bounce * 0.30F - (airborne ? 0.28F : 0.0F);
            monsterTorso.xRot = 0.08F + motion * 0.10F + (airborne ? -0.10F : 0.0F);
            monsterTorso.zRot = stride * 0.035F;
            monsterPelvis.yRot = stride * 0.05F;
            monsterNeck.xRot = -0.05F + Mth.sin(ageInTicks * 0.08F) * 0.025F;
            monsterHead.yRot = netHeadYaw * Mth.DEG_TO_RAD * 0.55F;
            monsterHead.xRot = headPitch * Mth.DEG_TO_RAD * 0.45F
                    + Mth.sin(ageInTicks * 0.09F) * 0.035F;
            monsterJaw.xRot = 0.10F + Mth.abs(Mth.sin(ageInTicks * 0.11F)) * 0.12F
                    + attackCurve * 0.42F + talk * 0.38F;

            leftArm.xRot = stride * 0.82F - 0.18F;
            rightArm.xRot = opposite * 0.82F - 0.18F;
            leftArm.zRot = 0.16F + motion * 0.06F;
            rightArm.zRot = -0.16F - motion * 0.06F;
            leftForearm.xRot = -0.20F - Math.max(0.0F, -stride) * 0.35F;
            rightForearm.xRot = -0.20F - Math.max(0.0F, -opposite) * 0.35F;
            leftHand.zRot = 0.08F + Mth.sin(ageInTicks * 0.13F) * 0.08F;
            rightHand.zRot = -0.08F - Mth.sin(ageInTicks * 0.13F + 1.2F) * 0.08F;

            // The attacking arm lashes forward with a long, claw-first arc.
            rightArm.xRot -= attackCurve * 2.15F;
            rightArm.yRot = -attackCurve * 0.38F;
            rightForearm.xRot -= attackCurve * 0.95F;
            monsterTorso.yRot = attackCurve * 0.22F;

            leftLeg.xRot = opposite * 0.92F;
            rightLeg.xRot = stride * 0.92F;
            leftShin.xRot = Math.max(0.0F, stride) * 0.72F;
            rightShin.xRot = Math.max(0.0F, opposite) * 0.72F;
            leftFoot.xRot = -Math.max(0.0F, stride) * 0.30F;
            rightFoot.xRot = -Math.max(0.0F, opposite) * 0.30F;

            if (airborne) {
                leftArm.xRot = -1.05F;
                rightArm.xRot = -1.05F - attackCurve;
                leftForearm.xRot = -0.55F;
                rightForearm.xRot = -0.55F;
                leftLeg.xRot = -0.36F;
                rightLeg.xRot = 0.32F;
                leftShin.xRot = 0.62F;
                rightShin.xRot = 0.45F;
            }

            monsterPupil.x = Mth.clamp(netHeadYaw / 80.0F, -1.0F, 1.0F) * 0.38F;
            monsterPupil.y = -1.0F + Mth.clamp(headPitch / 60.0F, -1.0F, 1.0F) * 0.25F;
            monsterEye.xScale = 1.0F + Mth.sin(ageInTicks * 0.13F) * 0.06F;
            monsterEye.yScale = 1.0F - Mth.sin(ageInTicks * 0.13F) * 0.05F;
            horns.xRot = airborne ? -0.12F : Mth.sin(ageInTicks * 0.07F) * 0.025F;
            leftTendril.zRot = 0.45F + Mth.sin(ageInTicks * 0.13F) * 0.32F + stride * 0.20F;
            rightTendril.zRot = -0.45F - Mth.sin(ageInTicks * 0.13F + 1.7F) * 0.32F - stride * 0.20F;
            backTendril.xRot = 0.58F + Mth.sin(ageInTicks * 0.10F + 2.2F) * 0.26F
                    + (airborne ? 0.32F : 0.0F);
        }

        float pulse = 1.0F + Mth.sin(ageInTicks * 0.11F) * 0.035F;
        gloss.xScale = pulse;
        gloss.yScale = pulse;
        gloss.zScale = pulse;

        orbit.yRot = ageInTicks * (0.052F + safeStage * 0.009F);
        orbit.zRot = Mth.sin(ageInTicks * 0.035F) * 0.16F;
        orbitLeft.y = -0.5F + Mth.sin(ageInTicks * 0.11F) * 1.0F;
        orbitRight.y = 0.65F + Mth.sin(ageInTicks * 0.11F + Mth.PI) * 1.0F;
        orbitTop.x = Mth.sin(ageInTicks * 0.085F) * 1.2F;
        orbitBottom.x = -Mth.sin(ageInTicks * 0.085F) * 1.2F;
        finLeft.zRot = -0.55F - Mth.sin(ageInTicks * 0.16F) * (0.10F + motion * 0.10F);
        finRight.zRot = 0.55F + Mth.sin(ageInTicks * 0.16F + 0.8F) * (0.10F + motion * 0.10F);
        spikes.yRot = Mth.sin(ageInTicks * 0.045F) * 0.10F;
    }

    private static void placePupil(ModelPart pupil, float baseX, float baseY, float lookX, float lookY) {
        pupil.x = baseX + lookX;
        pupil.y = baseY + lookY;
    }

    @Override
    public ModelPart root() {
        return root;
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight,
                               int packedOverlay, float red, float green, float blue, float alpha) {
        root.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}
