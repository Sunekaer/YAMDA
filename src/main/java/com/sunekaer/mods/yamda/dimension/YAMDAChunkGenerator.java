package com.sunekaer.mods.yamda.dimension;

import com.sunekaer.mods.yamda.config.YAMDAConfig;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityClassification;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.WorldGenRegion;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.template.TemplateManager;

import javax.annotation.Nullable;
import java.util.List;

public class YAMDAChunkGenerator extends ChunkGenerator<GenerationSettings> {

    public YAMDAChunkGenerator(IWorld world, BiomeProvider biomeProvider, GenerationSettings settings) {
        super(world, biomeProvider, settings);
    }

    @Override
    public void decorate(WorldGenRegion region) {
        int i = region.getMainChunkX();
        int j = region.getMainChunkZ();
        int k = i * 16;
        int l = j * 16;
        BlockPos blockpos = new BlockPos(k, 0, l);
        Biome biome = Biomes.MOUNTAINS;
        SharedSeedRandom sharedseedrandom = new SharedSeedRandom();
        long i1 = sharedseedrandom.setDecorationSeed(region.getSeed(), k, l);

        biome.decorate(GenerationStage.Decoration.VEGETAL_DECORATION, this, region, i1, sharedseedrandom, blockpos);
        biome.decorate(GenerationStage.Decoration.UNDERGROUND_ORES, this, region, i1, sharedseedrandom, blockpos);
    }

    @Override
    public void generateSurface(IChunk chunk) {
        BlockState bedrock = Blocks.BEDROCK.getDefaultState();
        BlockState stone = Blocks.STONE.getDefaultState();
        BlockState dirt = Blocks.DIRT.getDefaultState();
        BlockState grass = Blocks.GRASS_BLOCK.getDefaultState();
        int x1, y1, z1;
        int worldHeight = YAMDAConfig.CONFIG.world_height.get();

        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();

        for (x1 = 0; x1 < 16; x1++) {
            for (z1 = 0; z1 < 16; z1++) {
                chunk.setBlockState(pos.setPos(x1, 0, z1), bedrock, false);
            }
        }
        if (YAMDAConfig.CONFIG.grass_enable.get()) {
            for (x1 = 0; x1 < 16; x1++) {
                for (y1 = 1; y1 < worldHeight - 3; y1++) {
                    for (z1 = 0; z1 < 16; z1++) {
                        chunk.setBlockState(pos.setPos(x1, y1, z1), stone, false);
                    }
                }
            }
            for (x1 = 0; x1 < 16; x1++) {
                for (y1 = worldHeight - 3; y1 < worldHeight - 1; y1++) {
                    for (z1 = 0; z1 < 16; z1++) {
                        chunk.setBlockState(pos.setPos(x1, y1, z1), dirt, false);
                    }
                }
            }
            for (x1 = 0; x1 < 16; x1++) {
                for (y1 = worldHeight - 1; y1 < worldHeight; y1++) {
                    for (z1 = 0; z1 < 16; z1++) {
                        chunk.setBlockState(pos.setPos(x1, y1, z1), grass, false);
                    }
                }
            }
        }else{
            for (x1 = 0; x1 < 16; x1++) {
                for (y1 = 1; y1 < worldHeight; y1++) {
                    for (z1 = 0; z1 < 16; z1++) {
                        chunk.setBlockState(pos.setPos(x1, y1, z1), stone, false);
                    }
                }
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
    public void carve(IChunk p_222538_1_, GenerationStage.Carving p_222538_2_) {
    }

    @Override
    public List<Biome.SpawnListEntry> getPossibleCreatures(EntityClassification creatureType, BlockPos pos) {
        return Biomes.MOUNTAINS.getSpawns(creatureType);
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
    public void initStructureStarts(IChunk p_222533_1_, ChunkGenerator<?> p_222533_2_, TemplateManager p_222533_3_) {

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
