package com.voidiscoming.common.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Blocks;
import net.minecraft.block.PillarBlock;

public class VoidLog extends PillarBlock {

    public VoidLog() {
        super(FabricBlockSettings.copyOf(Blocks.OAK_LOG));
    }
}