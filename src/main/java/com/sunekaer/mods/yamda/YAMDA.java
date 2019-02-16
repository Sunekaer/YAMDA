package com.sunekaer.mods.yamda;

import com.sunekaer.mods.yamda.config.YAMDAConfig;
import com.sunekaer.mods.yamda.dimension.YAMDAWorldProvider;
import com.sunekaer.mods.yamda.item.YAMDAItems;
import net.minecraft.creativetab.CreativeTabs;
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
            return new ItemStack(YAMDAItems.PORTAL);
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
