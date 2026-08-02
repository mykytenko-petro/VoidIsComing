package com.voidiscoming.client.Model;

import com.voidiscoming.common.VoidIsComing;
import net.minecraft.client.render.entity.model.EntityModelLayer;

public class ModModelLayers {
    public static final EntityModelLayer STONE_GOLEM =
            new EntityModelLayer(VoidIsComing.id("stone_golem"), "main");

    public static final EntityModelLayer VOID_PIG =
            new EntityModelLayer(VoidIsComing.id("void_pig"), "main");

    public static final EntityModelLayer VOID_COW =
            new EntityModelLayer(VoidIsComing.id("void_cow"), "main");
}