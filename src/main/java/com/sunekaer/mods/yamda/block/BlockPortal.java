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
        super(Material.PORTAL);
        setHardness(2F);
    }

    @Override
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos){
            if (worldIn.provider.getDimension() == YAMDAConfig.overworldId){
                return worldIn.getBlockState(pos).getBlock().isReplaceable(worldIn, pos);
            } else if (worldIn.provider.getDimension() == YAMDAConfig.dimensionId) {
                return worldIn.getBlockState(pos).getBlock().isReplaceable(worldIn, pos);
            } else {
                return false;
            }
    }


    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if(!worldIn.isRemote) {
            World otherWorld = worldIn.getMinecraftServer().getWorld(YAMDAConfig.dimensionId);
            World overWorld = worldIn.getMinecraftServer().getWorld(YAMDAConfig.overworldId);
            otherWorld.getBlockState(pos);
            overWorld.getBlockState(pos);
            BlockPos otherWorldPos = otherWorld.getHeight(pos);
            BlockPos overWorldPos = overWorld.getHeight(pos);


            //TODO Make it look around on X and Z also

            //FROM OVERWORLD TO MINING DIM
            if (worldIn.provider.getDimension() == YAMDAConfig.overworldId) {
                boolean foundBlock = false;
                BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos(pos.getX(), 0, pos.getZ());

                for (int y = 0; y < 256; y++) {
                    mutableBlockPos.setY(y);
                    if (otherWorld.getBlockState(mutableBlockPos).getBlock() == YAMDABlocks.PORTAL) {
                        otherWorldPos = new BlockPos(pos.getX(), y + 1, pos.getZ());
                        foundBlock = true;
                        break;
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
                boolean foundBlock = false;
                BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos(pos.getX(), 0, pos.getZ());

                for (int y = 0; y < 256; y++) {
                    mutableBlockPos.setY(y);
                    if (overWorld.getBlockState(mutableBlockPos).getBlock() == YAMDABlocks.PORTAL) {
                        overWorldPos = new BlockPos(pos.getX(), y + 1, pos.getZ());
                        foundBlock = true;
                        break;
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