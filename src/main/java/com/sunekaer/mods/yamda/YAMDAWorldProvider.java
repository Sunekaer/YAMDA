package com.sunekaer.mods.yamda;

import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.gen.IChunkGenerator;

public class YAMDAWorldProvider extends WorldProvider {
    @Override
    public DimensionType getDimensionType() {
        return YAMDA.MiningDim;
    }

    @Override
    public String getSaveFolder() {
        return "YAMDA";
    }

    @Override
    public IChunkGenerator createChunkGenerator() {
        return new YAMDAChunkGenerator(world);
    }
}
