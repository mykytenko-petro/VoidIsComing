package com.voidiscoming.client.animation.stoneGolem;

import net.minecraft.client.render.entity.animation.Animation;
import net.minecraft.client.render.entity.animation.AnimationHelper;
import net.minecraft.client.render.entity.animation.Keyframe;
import net.minecraft.client.render.entity.animation.Transformation;

public class StoneGolemMoveAnimation {

    public static final Animation MOVE = Animation.Builder.create(0.7609F).looping()
            .addBoneAnimation("Right Legs", new Transformation(Transformation.Targets.ROTATE,
                    new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
                    new Keyframe(0.2853F, AnimationHelper.createRotationalVector(0.0F, 0.0F, -17.5F), Transformation.Interpolations.LINEAR),
                    new Keyframe(0.5231F, AnimationHelper.createRotationalVector(0.0F, 0.0F, -2.5F), Transformation.Interpolations.LINEAR),
                    new Keyframe(0.7608F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 12.5F), Transformation.Interpolations.LINEAR)
            ))
            .addBoneAnimation("Right Legs", new Transformation(Transformation.Targets.TRANSLATE,
                    new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
                    new Keyframe(0.2853F, AnimationHelper.createTranslationalVector(3.0F, 3.0F, 0.0F), Transformation.Interpolations.LINEAR),
                    new Keyframe(0.5231F, AnimationHelper.createTranslationalVector(0.5F, 0.5F, 0.0F), Transformation.Interpolations.LINEAR),
                    new Keyframe(0.7608F, AnimationHelper.createTranslationalVector(-0.5F, -1.5F, 0.0F), Transformation.Interpolations.LINEAR)
            ))
            .addBoneAnimation("Right Legs", new Transformation(Transformation.Targets.SCALE,
                    new Keyframe(0.0F, AnimationHelper.createScalingVector(1.0F, 1.0F, 1.0F), Transformation.Interpolations.LINEAR)
            ))
            .addBoneAnimation("Left Legs", new Transformation(Transformation.Targets.ROTATE,
                    new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
                    new Keyframe(0.2853F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 17.5F), Transformation.Interpolations.LINEAR),
                    new Keyframe(0.5231F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
                    new Keyframe(0.7608F, AnimationHelper.createRotationalVector(0.0F, 0.0F, -12.5F), Transformation.Interpolations.LINEAR)
            ))
            .addBoneAnimation("Left Legs", new Transformation(Transformation.Targets.TRANSLATE,
                    new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
                    new Keyframe(0.2853F, AnimationHelper.createTranslationalVector(0.0F, -2.5F, 0.0F), Transformation.Interpolations.LINEAR),
                    new Keyframe(0.5231F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
                    new Keyframe(0.7608F, AnimationHelper.createTranslationalVector(2.0F, 1.5F, 0.0F), Transformation.Interpolations.LINEAR)
            ))
            .addBoneAnimation("Left Hands", new Transformation(Transformation.Targets.ROTATE,
                    new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 7.5F), Transformation.Interpolations.LINEAR),
                    new Keyframe(0.2853F, AnimationHelper.createRotationalVector(0.0F, 0.0F, -17.5F), Transformation.Interpolations.LINEAR),
                    new Keyframe(0.5231F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
                    new Keyframe(0.7608F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 10.0F), Transformation.Interpolations.LINEAR)
            ))
            .addBoneAnimation("Left Hands", new Transformation(Transformation.Targets.TRANSLATE,
                    new Keyframe(0.0F, AnimationHelper.createTranslationalVector(-1.5F, -1.0F, 0.0F), Transformation.Interpolations.LINEAR),
                    new Keyframe(0.2853F, AnimationHelper.createTranslationalVector(8.0F, 3.5F, 0.0F), Transformation.Interpolations.LINEAR),
                    new Keyframe(0.5231F, AnimationHelper.createTranslationalVector(1.5F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
                    new Keyframe(0.7608F, AnimationHelper.createTranslationalVector(-2.0F, -1.5F, 0.0F), Transformation.Interpolations.LINEAR)
            ))
            .addBoneAnimation("Right Hands", new Transformation(Transformation.Targets.ROTATE,
                    new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, -17.5F), Transformation.Interpolations.LINEAR),
                    new Keyframe(0.2853F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 17.5F), Transformation.Interpolations.LINEAR),
                    new Keyframe(0.5231F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
                    new Keyframe(0.7608F, AnimationHelper.createRotationalVector(0.0F, 0.0F, -12.5F), Transformation.Interpolations.LINEAR)
            ))
            .addBoneAnimation("Right Hands", new Transformation(Transformation.Targets.TRANSLATE,
                    new Keyframe(0.0F, AnimationHelper.createTranslationalVector(7.0F, 3.5F, 0.0F), Transformation.Interpolations.LINEAR),
                    new Keyframe(0.2853F, AnimationHelper.createTranslationalVector(-5.5F, -1.5F, 0.0F), Transformation.Interpolations.LINEAR),
                    new Keyframe(0.5231F, AnimationHelper.createTranslationalVector(0.5F, -0.5F, 0.0F), Transformation.Interpolations.LINEAR),
                    new Keyframe(0.7608F, AnimationHelper.createTranslationalVector(5.0F, 2.0F, 0.0F), Transformation.Interpolations.LINEAR)
            ))
            .build();
}