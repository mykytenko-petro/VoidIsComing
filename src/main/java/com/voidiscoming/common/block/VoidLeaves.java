package com.voidiscoming.common.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Blocks;
import net.minecraft.block.LeavesBlock;

public class VoidLeaves extends LeavesBlock {

    public VoidLeaves() {
        super(FabricBlockSettings.copyOf(Blocks.OAK_LEAVES));
        this.setDefaultState(this.getDefaultState().with(PERSISTENT, true));
    }
}