package com.sunekaer.mods.yamda;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = YAMDA.MOD_ID)
@Config(modid = YAMDA.MOD_ID)
public class YAMDAConfig {

    @Config.Name("dimension_id")
    @Config.RangeInt(min = -1000, max = 1000)
    @Config.Comment("Dimension id")
    public static int dimensionId = -10;

    @Config.Name("caves_enable")
    @Config.Comment("Should there be generated caves)")
    public static boolean caves_enable = true;

    @Config.Name("world_height")
    @Config.RangeInt(min = 5, max = 256)
    @Config.Comment("Should there be generated caves)")
    public static int world_height = 70;

    @SubscribeEvent
    public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event)
    {
        if (event.getModID().equals(YAMDA.MOD_ID))
        {
            ConfigManager.sync(YAMDA.MOD_ID, Config.Type.INSTANCE);
        }
    }
}
