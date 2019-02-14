package com.sunekaer.mods.yamda.dimension;

import com.sunekaer.mods.yamda.YAMDA;
import com.sunekaer.mods.yamda.config.YAMDAConfig;
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

    @Override
    public boolean canRespawnHere ()
    {
        return false;
    }

    @Override
    public float calculateCelestialAngle ( long par1, float par3){
        if (YAMDAConfig.day){
            int j = 6000;
            float f1 = (j + par3) / 24000.0F - 0.25F;

            if (f1 < 0.0F) {
                f1 += 1.0F;
            }

            if (f1 > 1.0F) {
                f1 -= 1.0F;
            }

            float f2 = f1;
            f1 = 1.0F - (float) ((Math.cos(f1 * Math.PI) + 1.0D) / 2.0D);
            f1 = f2 + (f1 - f2) / 3.0F;
            return f1;
        }else{
            int i = (int)(par1 % 24000L);
            float f = ((float)i + par3) / 24000.0F - 0.25F;

            if (f < 0.0F)
            {
                ++f;
            }

            if (f > 1.0F)
            {
                --f;
            }

            float f1 = 1.0F - (float)((Math.cos((double)f * Math.PI) + 1.0D) / 2.0D);
            f = f + (f1 - f) / 3.0F;
            return f;
        }
    }
}
