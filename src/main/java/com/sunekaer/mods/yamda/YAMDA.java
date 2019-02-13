package com.sunekaer.mods.yamda;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.DimensionType;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(
        modid = YAMDA.MOD_ID,
        name = YAMDA.MOD_NAME,
        version = YAMDA.VERSION
)

public class YAMDA {
    public static final String MOD_ID = "yamda";
    public static final String MOD_NAME = "YAMDA";
    public static final String VERSION = "0.0.0.yamda";

    public static final CreativeTabs TAB = new CreativeTabs(MOD_ID)
    {
        @Override
        public ItemStack createIcon()
        {
            return new ItemStack(Items.CARROT);
        }
    };

    public static DimensionType MiningDim;

    private static void registerDimensionTypes() {
        MiningDim = DimensionType.register("YAMDA Mining Dim", "_MiningDim", YAMDAConfig.dimensionId, YAMDAWorldProvider.class, false);
    }

    private static void registerDimensions() {
        DimensionManager.registerDimension(YAMDAConfig.dimensionId, MiningDim);
    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent e) {
        registerDimensionTypes();
        registerDimensions();
    }

}
