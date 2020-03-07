package com.sunekaer.mods.yamda.dimension;

import com.google.common.collect.Sets;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.biome.provider.SingleBiomeProviderSettings;
import net.minecraft.world.gen.feature.structure.Structure;

import javax.annotation.Nullable;
import java.util.*;

public class YAMDABiomeProvider extends BiomeProvider {
    private static final List<Biome> SPAWN = Collections.singletonList(Biomes.PLAINS);
    private final Biome biome;

    public YAMDABiomeProvider(SingleBiomeProviderSettings settings) {
        super((Set<Biome>) Biomes.PLAINS);
        this.biome = Biomes.PLAINS;

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
    public Biome func_225526_b_(int i, int i1, int i2) {
        return Biomes.PLAINS;
    }
}
