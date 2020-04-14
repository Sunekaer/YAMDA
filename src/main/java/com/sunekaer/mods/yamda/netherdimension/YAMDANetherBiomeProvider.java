package com.sunekaer.mods.yamda.netherdimension;

import com.google.common.collect.Sets;
import com.sunekaer.mods.yamda.YAMDA;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.biome.provider.SingleBiomeProviderSettings;
import net.minecraft.world.gen.feature.structure.Structure;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class YAMDANetherBiomeProvider extends BiomeProvider {
    private static final List<Biome> SPAWN = Collections.singletonList(YAMDA.netherBiome);
    private final Biome biome;

    public YAMDANetherBiomeProvider(SingleBiomeProviderSettings settings) {
        super((Set<Biome>) YAMDA.netherBiome);
        this.biome = YAMDA.netherBiome;

    }

    @Override
    public List<Biome> getBiomesToSpawnIn() {
        return SPAWN;
    }


    @Override
    @Nullable
    public BlockPos func_225531_a_(int x, int z, int range, int p_225531_4_, List<Biome> biomes, Random random) {
        return biomes.contains(this.biome) ? new BlockPos(x - range + random.nextInt(range * 2 + 1), 0, z - range + random.nextInt(range * 2 + 1)) : null;
    }

    @Override
    public boolean hasStructure(Structure<?> structureIn) {
        return false;
    }

//    @Override
//    public Set<BlockState> getSurfaceBlocks() {
//        if (this.topBlocksCache.isEmpty()) {
//            this.topBlocksCache.add(this.biome.getSurfaceBuilderConfig().getTop());
//        }
//
//        return this.topBlocksCache;
//    }

    @Override
    public Set<Biome> func_225530_a_(int p_225530_1_, int p_225530_2_, int p_225530_3_, int p_225530_4_) {
        return Sets.newHashSet(this.biome);
    }


    @Override
    public Biome getNoiseBiome(int i, int i1, int i2) {
        return YAMDA.netherBiome;
    }
}
