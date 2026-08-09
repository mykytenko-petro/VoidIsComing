package com.voidiscoming.client.animation.stoneGolem;

import net.minecraft.client.render.entity.animation.Animation;
import net.minecraft.client.render.entity.animation.AnimationHelper;
import net.minecraft.client.render.entity.animation.Keyframe;
import net.minecraft.client.render.entity.animation.Transformation;

public class StoneGolemSlamAnimation {

        public static final Animation GROUND_SLAM = Animation.Builder.create(1.0928F)
                .addBoneAnimation("face", new Transformation(Transformation.Targets.ROTATE,
                        new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
                        new Keyframe(0.5461F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 10.0F), Transformation.Interpolations.LINEAR)
                ))
                .addBoneAnimation("face", new Transformation(Transformation.Targets.TRANSLATE,
                        new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
                        new Keyframe(0.5461F, AnimationHelper.createTranslationalVector(3.5F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
                ))
                .addBoneAnimation("Body", new Transformation(Transformation.Targets.ROTATE,
                        new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
                        new Keyframe(0.5461F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 12.5F), Transformation.Interpolations.LINEAR)
                ))
                .addBoneAnimation("Body", new Transformation(Transformation.Targets.TRANSLATE,
                        new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
                        new Keyframe(0.5461F, AnimationHelper.createTranslationalVector(0.0F, -2.0F, 0.0F), Transformation.Interpolations.LINEAR)
                ))
                .addBoneAnimation("Left Hands", new Transformation(Transformation.Targets.ROTATE,
                        new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
                        new Keyframe(0.5461F, AnimationHelper.createRotationalVector(0.0F, 0.0F, -60.0F), Transformation.Interpolations.LINEAR),
                        new Keyframe(0.6826F, AnimationHelper.createRotationalVector(0.0F, 0.0F, -10.0F), Transformation.Interpolations.LINEAR)
                ))
                .addBoneAnimation("Left Hands", new Transformation(Transformation.Targets.TRANSLATE,
                        new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
                        new Keyframe(0.5461F, AnimationHelper.createTranslationalVector(19.0F, 19.5F, 0.0F), Transformation.Interpolations.LINEAR),
                        new Keyframe(0.6826F, AnimationHelper.createTranslationalVector(13.0F, -3.0F, 0.0F), Transformation.Interpolations.LINEAR)
                ))
                .addBoneAnimation("Right Hands", new Transformation(Transformation.Targets.ROTATE,
                        new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
                        new Keyframe(0.5461F, AnimationHelper.createRotationalVector(0.0F, 0.0F, -62.5F), Transformation.Interpolations.LINEAR),
                        new Keyframe(0.6826F, AnimationHelper.createRotationalVector(0.0F, 0.0F, -27.5F), Transformation.Interpolations.LINEAR)
                ))
                .addBoneAnimation("Right Hands", new Transformation(Transformation.Targets.TRANSLATE,
                        new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.5F, 0.5F, -0.5F), Transformation.Interpolations.LINEAR),
                        new Keyframe(0.5461F, AnimationHelper.createTranslationalVector(19.0F, 20.5F, -0.5F), Transformation.Interpolations.LINEAR),
                        new Keyframe(0.6826F, AnimationHelper.createTranslationalVector(16.0F, 1.5F, -0.5F), Transformation.Interpolations.LINEAR)
                ))
                .build();
}