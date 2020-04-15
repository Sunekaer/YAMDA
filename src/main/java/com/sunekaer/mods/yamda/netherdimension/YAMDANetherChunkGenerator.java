package com.sunekaer.mods.yamda.netherdimension;

import com.sunekaer.mods.yamda.YAMDA;
import com.sunekaer.mods.yamda.config.YAMDAConfig;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityClassification;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeManager;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.*;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.template.TemplateManager;

import javax.annotation.Nullable;
import java.util.List;

public class YAMDANetherChunkGenerator extends ChunkGenerator<GenerationSettings> {

    public YAMDANetherChunkGenerator(IWorld world, BiomeProvider biomeProvider, GenerationSettings settings) {
        super(world, biomeProvider, settings);
    }

    @Override
    public void decorate(WorldGenRegion region) {
        int i = region.getMainChunkX();
        int j = region.getMainChunkZ();
        int k = i * 16;
        int l = j * 16;
        BlockPos blockpos = new BlockPos(k, 0, l);
        Biome biome = YAMDA.netherBiome;
        SharedSeedRandom sharedseedrandom = new SharedSeedRandom();
        long i1 = sharedseedrandom.setDecorationSeed(region.getSeed(), k, l);

        biome.decorate(GenerationStage.Decoration.UNDERGROUND_DECORATION, this, region, i1, sharedseedrandom, blockpos);
        biome.decorate(GenerationStage.Decoration.UNDERGROUND_ORES, this, region, i1, sharedseedrandom, blockpos);
    }

    @Override
    public void func_225551_a_(WorldGenRegion p_225551_1_, IChunk chunk) {
        BlockState bedrock = Blocks.BEDROCK.getDefaultState();
        BlockState netherrack = Blocks.NETHERRACK.getDefaultState();
        int x1, y1, z1;
        int worldHeight = 128;

        BlockPos.Mutable pos = new BlockPos.Mutable();

        for (x1 = 0; x1 < 16; x1++) {
            for (z1 = 0; z1 < 16; z1++) {
                chunk.setBlockState(pos.setPos(x1, 0, z1), bedrock, false);
            }
        }

        for (x1 = 0; x1 < 16; x1++) {
            for (y1 = 1; y1 < YAMDAConfig.CONFIG.netherWorldHeight.get() - 1; y1++) {
                for (z1 = 0; z1 < 16; z1++) {
                    chunk.setBlockState(pos.setPos(x1, y1, z1), netherrack, false);
                }
            }
        }

        for (x1 = 0; x1 < 16; x1++) {
            for (z1 = 0; z1 < 16; z1++) {
                chunk.setBlockState(pos.setPos(x1, worldHeight - 1, z1), bedrock, false);
            }
        }
    }

    @Override
    public int getGroundHeight() {
        return this.world.getSeaLevel() + 1;
    }


    @Override
    public int func_222529_a(int p_222529_1_, int p_222529_2_, Heightmap.Type p_222529_3_) {
        return 0;
    }

    @Override
    public void func_225550_a_(BiomeManager p_225550_1_, IChunk p_225550_2_, GenerationStage.Carving p_225550_3_) {
    }

    @Override
    public List<Biome.SpawnListEntry> getPossibleCreatures(EntityClassification creatureType, BlockPos pos) {
        if (YAMDAConfig.CONFIG.disableHostileMobs.get()){
            return null;
        } else {
            return this.world.getBiome(pos).getSpawns(creatureType);
        }
    }

    @Nullable
    @Override
    public BlockPos findNearestStructure(World worldIn, String name, BlockPos pos, int radius, boolean p_211403_5_) {
        return null;
    }

    @Override
    public boolean hasStructure(Biome biomeIn, Structure<? extends IFeatureConfig> structureIn) {
        return false;
    }

    @Override
    public void generateStructures(BiomeManager p_227058_1_, IChunk p_227058_2_, ChunkGenerator<?> p_227058_3_, TemplateManager p_227058_4_) {
    }


    @Override
    public void makeBase(IWorld iWorld, IChunk iChunk) {

    }

    @Nullable
    @Override
    public <C extends IFeatureConfig> C getStructureConfig(Biome biomeIn, Structure<C> structureIn) {
        return null;
    }
}
