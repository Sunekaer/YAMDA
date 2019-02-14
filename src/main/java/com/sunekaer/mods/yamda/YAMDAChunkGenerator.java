package com.sunekaer.mods.yamda;

import net.minecraft.block.BlockFalling;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.IChunkGenerator;

import javax.annotation.Nullable;
import java.util.List;

public class YAMDAChunkGenerator implements IChunkGenerator {

    private final World world;
    private int worldHeight = YAMDAConfig.world_height;

    public YAMDAChunkGenerator(World world) {
        this.world = world;
    }

    @Override
    public void populate(int x, int z ) {
        Biomes.EXTREME_HILLS.decorate(world, world.rand, new BlockPos(x * 16, 0, z * 16));
        BlockFalling.fallInstantly = true;
    }

    @Override
    public boolean generateStructures(Chunk chunkIn, int x, int z) {
        return false;
    }

    @Override
    public List<Biome.SpawnListEntry> getPossibleCreatures(EnumCreatureType creatureType, BlockPos pos) {
        return Biomes.EXTREME_HILLS.getSpawnableList(creatureType);
    }

    @Override
    public void recreateStructures(Chunk chunkIn, int x, int z) {

    }

    @Override
    public Chunk generateChunk(int x, int z) {
        ChunkPrimer primer = new ChunkPrimer();
        IBlockState bedrock = Blocks.BEDROCK.getDefaultState();
        IBlockState stone = Blocks.STONE.getDefaultState();
        IBlockState dirt = Blocks.DIRT.getDefaultState();
        IBlockState grass = Blocks.GRASS.getDefaultState();
        int x1, y1, z1;
        for (x1 = 0; x1 < 16; x1++) {
            for (z1 = 0; z1 < 16; z1++) {
                primer.setBlockState(x1, 0, z1, bedrock);
            }
        }
        if (YAMDAConfig.grass_enable) {
            for (x1 = 0; x1 < 16; x1++) {
                for (y1 = 1; y1 < worldHeight - 3; y1++) {
                    for (z1 = 0; z1 < 16; z1++) {
                        primer.setBlockState(x1, y1, z1, stone);
                    }
                }
            }
            for (x1 = 0; x1 < 16; x1++) {
                for (y1 = worldHeight - 3; y1 < worldHeight - 1; y1++) {
                    for (z1 = 0; z1 < 16; z1++) {
                        primer.setBlockState(x1, y1, z1, dirt);
                    }
                }
            }
            for (x1 = 0; x1 < 16; x1++) {
                for (y1 = worldHeight - 1; y1 < worldHeight; y1++) {
                    for (z1 = 0; z1 < 16; z1++) {
                        primer.setBlockState(x1, y1, z1, grass);
                    }
                }
            }
        }else{
            for (x1 = 0; x1 < 16; x1++) {
                for (y1 = 1; y1 < worldHeight; y1++) {
                    for (z1 = 0; z1 < 16; z1++) {
                        primer.setBlockState(x1, y1, z1, stone);
                    }
                }
            }
        }

        Chunk chunk = new Chunk(this.world, primer, x, z);

        byte[] biomeArray = chunk.getBiomeArray();
        byte id = (byte) Biome.getIdForBiome(Biomes.EXTREME_HILLS);
        for (int i = 0; i < biomeArray.length; ++i) {
            biomeArray[i] = id;
        }

        chunk.generateSkylightMap();

        return chunk;
    }

    @Nullable
    @Override
    public BlockPos getNearestStructurePos(World worldIn, String structureName, BlockPos position, boolean findUnexplored) {
        return null;
    }

    @Override
    public boolean isInsideStructure(World worldIn, String structureName, BlockPos pos) {
        return false;
    }
}
