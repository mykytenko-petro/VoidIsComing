package com.voidiscoming.mixin.common.entity;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.QuadrupedEntityModel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(QuadrupedEntityModel.class)
public interface QuadrupedEntityModelAccessor {

    @Accessor("head")
    ModelPart voidiscoming$getHead();

    @Accessor("leftFrontLeg")
    ModelPart voidiscoming$getLeftFrontLeg();

    @Accessor("rightFrontLeg")
    ModelPart voidiscoming$getRightFrontLeg();

    @Accessor("leftHindLeg")
    ModelPart voidiscoming$getLeftHindLeg();

    @Accessor("rightHindLeg")
    ModelPart voidiscoming$getRightHindLeg();
}