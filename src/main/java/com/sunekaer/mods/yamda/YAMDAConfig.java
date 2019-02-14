package com.sunekaer.mods.yamda;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = YAMDA.MOD_ID)
@Config(modid = YAMDA.MOD_ID)
public class YAMDAConfig {

    @Config.Name("Top layer grass")
    @Config.Comment("Should the layers top of the world be dirt and grass")
    public static boolean grass_enable = true;

    @Config.Name("Always day")
    @Config.Comment("Should it always be day")
    public static boolean day = true;

    @Config.Name("World Height")
    @Config.RangeInt(min = 5, max = 256)
    @Config.Comment("Height of the world")
    public static int world_height = 70;

    @Config.Name("Dimension ID")
    @Config.RangeInt(min = -1000, max = 1000)
    @Config.Comment("Dimension id")
    public static int dimensionId = -10;

    @SubscribeEvent
    public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event)
    {
        if (event.getModID().equals(YAMDA.MOD_ID))
        {
            ConfigManager.sync(YAMDA.MOD_ID, Config.Type.INSTANCE);
        }
    }
}
