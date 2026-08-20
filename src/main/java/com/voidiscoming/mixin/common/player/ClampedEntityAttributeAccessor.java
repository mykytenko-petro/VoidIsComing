package com.voidiscoming.mixin.common.player;

import net.minecraft.entity.attribute.ClampedEntityAttribute;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ClampedEntityAttribute.class)
public interface ClampedEntityAttributeAccessor {

    @Accessor("maxValue")
    void setMaxValue(double maxValue);
}