package com.sunekaer.mods.yamda.block;

import com.sunekaer.mods.yamda.YAMDA;
import com.sunekaer.mods.yamda.config.YAMDAConfig;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.*;
import net.minecraft.potion.EffectInstance;
import net.minecraft.server.management.PlayerList;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.WorldInfo;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.fml.hooks.BasicEventHooks;

import javax.annotation.Nullable;
import java.util.List;

@SuppressWarnings("deprecation")
public class BlockPortal extends Block {

    public BlockPortal() {
        super(Block.Properties.create(Material.PORTAL).hardnessAndResistance(2F));
    }

    public static void changeDim(ServerPlayerEntity player, BlockPos pos, DimensionType type) { // copy from ServerPlayerEntity#changeDimension
        if (!ForgeHooks.onTravelToDimension(player, type)) return;

        player.invulnerableDimensionChange = true;
        DimensionType dimensiontype = player.dimension;

        ServerWorld serverworld = player.server.getWorld(dimensiontype);
        player.dimension = type;
        ServerWorld serverworld1 = player.server.getWorld(type);
        WorldInfo worldinfo = player.world.getWorldInfo();
        player.connection.sendPacket(new SRespawnPacket(type, WorldInfo.byHashing(worldinfo.getSeed()), worldinfo.getGenerator(), player.interactionManager.getGameType()));
        player.connection.sendPacket(new SServerDifficultyPacket(worldinfo.getDifficulty(), worldinfo.isDifficultyLocked()));
        PlayerList playerlist = player.server.getPlayerList();
        playerlist.updatePermissionLevel(player);
        serverworld.removeEntity(player, true);
        player.revive();
        float f = player.rotationPitch;
        float f1 = player.rotationYaw;

        player.setLocationAndAngles(pos.getX() + .5, pos.getY() + .5, pos.getZ() + .5, f1, f);
        serverworld.getProfiler().endSection();
        serverworld.getProfiler().startSection("placing");
        player.setLocationAndAngles(pos.getX() + .5, pos.getY() + .5, pos.getZ() + .5, f1, f);

        serverworld.getProfiler().endSection();
        player.setWorld(serverworld1);
        serverworld1.addDuringPortalTeleport(player);
        player.connection.setPlayerLocation(pos.getX() + .5, pos.getY() + .5, pos.getZ() + .5, f1, f);
        player.interactionManager.setWorld(serverworld1);
        player.connection.sendPacket(new SPlayerAbilitiesPacket(player.abilities));
        playerlist.sendWorldInfo(player, serverworld1);
        playerlist.sendInventory(player);

        for (EffectInstance effectinstance : player.getActivePotionEffects()) {
            player.connection.sendPacket(new SPlayEntityEffectPacket(player.getEntityId(), effectinstance));
        }

        player.connection.sendPacket(new SPlaySoundEventPacket(1032, BlockPos.ZERO, 0, false));
        player.lastExperience = -1;
        player.lastHealth = -1F;
        player.lastFoodLevel = -1;
        BasicEventHooks.firePlayerChangedDimensionEvent(player, dimensiontype, type);
    }

    @Override
    public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
        return (worldIn.getDimension().getType().getId() == YAMDAConfig.CONFIG.getOverworldId() || worldIn.getDimension().getType() == DimensionType.byName(YAMDA.YAMDA_DIM) && super.isValidPosition(state, worldIn, pos));
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable IBlockReader worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        tooltip.add(new TranslationTextComponent("block.yamda.portal.tooltip"));
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity playerEntity, Hand hand, BlockRayTraceResult hit) {
        if (!worldIn.isRemote) {
            //FROM OVERWORLD TO MINING DIM
            if (worldIn.getDimension().getType().getId() == YAMDAConfig.CONFIG.getOverworldId()) {
                if (DimensionType.byName(YAMDA.YAMDA_DIM) == null) {
                    DimensionManager.registerDimension(YAMDA.YAMDA_DIM, YAMDA.dimension, null, true);
                }
                World otherWorld = worldIn.getServer().getWorld(DimensionType.byName(YAMDA.YAMDA_DIM));
                otherWorld.getBlockState(pos);
                BlockPos otherWorldPos = otherWorld.getHeight(Heightmap.Type.WORLD_SURFACE, pos);
                boolean foundBlock = false;
                BlockPos.Mutable mutableBlockPos = new BlockPos.Mutable(0, 0, 0);

                for (int y = 0; y < 256; y++) {
                    for (int x = pos.getX() - 6; x < pos.getX() + 6; x++) {
                        for (int z = pos.getZ() - 6; z < pos.getZ() + 6; z++) {
                            mutableBlockPos.setPos(x, y, z);
                            if (otherWorld.getBlockState(mutableBlockPos).getBlock() == YAMDA.portal) {
                                otherWorldPos = new BlockPos(x, y + 1, z);
                                foundBlock = true;
                                break;
                            }
                        }
                    }
                }
                if (foundBlock) {
                    changeDim(((ServerPlayerEntity) playerEntity), otherWorldPos, DimensionType.byName(YAMDA.YAMDA_DIM));
                }
                if (!foundBlock) {
                    otherWorld.setBlockState(otherWorldPos.down(), YAMDA.portal.getDefaultState());
                    changeDim(((ServerPlayerEntity) playerEntity), otherWorldPos, DimensionType.byName(YAMDA.YAMDA_DIM));
                }
            }

            //FROM MINING DIM TO OVERWORLD
            if (worldIn.getDimension().getType() == DimensionType.byName(YAMDA.YAMDA_DIM)) {
                World overWorld = worldIn.getServer().getWorld(DimensionType.getById(YAMDAConfig.CONFIG.getOverworldId()));
                overWorld.getBlockState(pos);
                BlockPos overWorldPos = overWorld.getHeight(Heightmap.Type.WORLD_SURFACE, pos);
                boolean foundBlock = false;
                BlockPos.Mutable mutableBlockPos = new BlockPos.Mutable(0, 0, 0);

                for (int y = 0; y < 256; y++) {
                    for (int x = pos.getX() - 6; x < pos.getX() + 6; x++) {
                        for (int z = pos.getZ() - 6; z < pos.getZ() + 6; z++) {
                            mutableBlockPos.setPos(x, y, z);
                            if (overWorld.getBlockState(mutableBlockPos).getBlock() == YAMDA.portal) {
                                overWorldPos = new BlockPos(x, y + 1, z);
                                foundBlock = true;
                                break;
                            }
                        }
                    }
                }
                if (foundBlock) {
                    changeDim(((ServerPlayerEntity) playerEntity), overWorldPos, DimensionType.getById(YAMDAConfig.CONFIG.getOverworldId()));
                }
                if (!foundBlock) {
                        if(overWorldPos.getY() == 0){
                            overWorld.setBlockState(overWorldPos, YAMDA.portal.getDefaultState());
                            changeDim(((ServerPlayerEntity) playerEntity), overWorldPos.offset(Direction.UP, 3), DimensionType.getById(YAMDAConfig.CONFIG.getOverworldId()));
                        }
                    overWorld.setBlockState(overWorldPos.down(), YAMDA.portal.getDefaultState());
                    changeDim(((ServerPlayerEntity) playerEntity), overWorldPos, DimensionType.getById(YAMDAConfig.CONFIG.getOverworldId()));
                }
            }

            return ActionResultType.SUCCESS;
        }
        return ActionResultType.PASS;
    }
}
