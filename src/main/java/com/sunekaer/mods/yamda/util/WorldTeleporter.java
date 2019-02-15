package com.sunekaer.mods.yamda.util;

import com.sunekaer.mods.yamda.block.BlockPortal;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ITeleporter;

import javax.annotation.Nullable;

public class WorldTeleporter implements ITeleporter {
    public final double posX, posY, posZ;
    public final int dim;

    public static WorldTeleporter of(double x, double y, double z, int dim)
    {
        return new WorldTeleporter(x, y, z, dim);
    }

    public static WorldTeleporter of(Entity entity)
    {
        return new WorldTeleporter(entity.posX, entity.posY, entity.posZ, entity.dimension);
    }

    public static WorldTeleporter of(BlockPos pos, int dim)
    {
        return new WorldTeleporter(pos.getX() + 0.5D, pos.getY() + 0.1D, pos.getZ() + 0.5D, dim);
    }

    private WorldTeleporter(double x, double y, double z, int d)
    {
        posX = x;
        posY = y;
        posZ = z;
        dim = d;
    }

    @Override
    public void placeEntity(World world, Entity entity, float yaw)
    {
        entity.motionX = entity.motionY = entity.motionZ = 0D;
        entity.fallDistance = 0F;

        if (entity instanceof EntityPlayerMP && ((EntityPlayerMP) entity).connection != null)
        {
            ((EntityPlayerMP) entity).connection.setPlayerLocation(posX, posY, posZ, yaw, entity.rotationPitch);
        }
        else
        {
            entity.setLocationAndAngles(posX, posY, posZ, yaw, entity.rotationPitch);
        }
    }

    @Nullable
    public Entity teleport(@Nullable Entity entity)
    {
        if (entity == null || entity.world.isRemote)
        {
            return entity;
        }

        if (dim != entity.dimension)
        {
            return entity.changeDimension(dim, this);
        }

        placeEntity(entity.world, entity, entity.rotationYaw);
        return entity;
    }
}
