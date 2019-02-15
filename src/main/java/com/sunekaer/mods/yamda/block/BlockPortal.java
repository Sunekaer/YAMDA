package com.sunekaer.mods.yamda.block;

import com.sunekaer.mods.yamda.config.YAMDAConfig;
import com.sunekaer.mods.yamda.util.WorldTeleporter;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockPortal extends Block {

    public BlockPortal() {
        super(Material.ROCK);
        setHardness(2F);
    }

    @Override
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos){
            if (worldIn.provider.getDimension() == YAMDAConfig.overworldId){
                return super.canPlaceBlockAt(worldIn, pos);
            } else if (worldIn.provider.getDimension() == YAMDAConfig.dimensionId) {
                return super.canPlaceBlockAt(worldIn, pos);
            } else {
                return false;
            }
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if(!worldIn.isRemote) {
            //FROM OVERWORLD TO MINING DIM
            if (worldIn.provider.getDimension() == YAMDAConfig.overworldId) {
                World otherWorld = worldIn.getMinecraftServer().getWorld(YAMDAConfig.dimensionId);
                otherWorld.getBlockState(pos);
                BlockPos otherWorldPos = otherWorld.getHeight(pos);
                boolean foundBlock = false;
                BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos(0, 0, 0);

                for (int y = 0; y < 256; y++) {
                    for (int x = pos.getX() - 6; x < pos.getX() + 6; x++) {
                        for (int z = pos.getZ() - 6; z < pos.getZ() + 6; z++) {
                            mutableBlockPos.setPos(x,y,z);
                            if (otherWorld.getBlockState(mutableBlockPos).getBlock() == YAMDABlocks.PORTAL) {
                                otherWorldPos = new BlockPos(x, y + 1, z);
                                foundBlock = true;
                                break;
                            }
                        }
                    }
                }
                if (foundBlock){
                    WorldTeleporter.of(otherWorldPos.getX() + 0.5, otherWorldPos.getY(), otherWorldPos.getZ() + 0.5, YAMDAConfig.dimensionId).teleport(playerIn);
                }
                if (!foundBlock){
                    otherWorld.setBlockState(otherWorldPos.down(), YAMDABlocks.PORTAL.getDefaultState());
                    WorldTeleporter.of(otherWorldPos.getX() + 0.5, otherWorldPos.getY(), otherWorldPos.getZ() + 0.5, YAMDAConfig.dimensionId).teleport(playerIn);
                }
            }

            //FROM MINING DIM TO OVERWORLD
            if (worldIn.provider.getDimension() == YAMDAConfig.dimensionId) {
                World overWorld = worldIn.getMinecraftServer().getWorld(YAMDAConfig.overworldId);
                overWorld.getBlockState(pos);
                BlockPos overWorldPos = overWorld.getHeight(pos);
                boolean foundBlock = false;
                BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos(0, 0, 0);

                for (int y = 0; y < 256; y++) {
                    for (int x = pos.getX() - 6; x < pos.getX() + 6; x++) {
                        for (int z = pos.getZ() - 6; z < pos.getZ() + 6; z++) {
                            mutableBlockPos.setPos(x, y, z);
                            if (overWorld.getBlockState(mutableBlockPos).getBlock() == YAMDABlocks.PORTAL) {
                                overWorldPos = new BlockPos(x, y + 1, z);
                                foundBlock = true;
                                break;
                            }
                        }
                    }
                }
                if (foundBlock){
                    WorldTeleporter.of(overWorldPos.getX() + 0.5, overWorldPos.getY(), overWorldPos.getZ() + 0.5, YAMDAConfig.overworldId).teleport(playerIn);
                }
                if (!foundBlock){
                    overWorld.setBlockState(overWorldPos.down(), YAMDABlocks.PORTAL.getDefaultState());
                    WorldTeleporter.of(overWorldPos.getX() + 0.5, overWorldPos.getY(), overWorldPos.getZ() + 0.5, YAMDAConfig.overworldId).teleport(playerIn);
                }
            }
        }
        return true;
    }
}