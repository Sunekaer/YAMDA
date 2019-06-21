package com.sunekaer.mods.yamda.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;
import org.apache.commons.lang3.tuple.Pair;

public class YAMDAConfig {

    public static final ForgeConfigSpec configSpec;
    public static final ConfigValues CONFIG;
    static {
        final Pair<ConfigValues, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(ConfigValues::new);
        configSpec = specPair.getRight();
        CONFIG = specPair.getLeft();
    }

    public static class ConfigValues {
        public BooleanValue grass_enable;
        public BooleanValue day;
        public IntValue world_height;
        public IntValue overworldId;

        public ConfigValues(ForgeConfigSpec.Builder builder) {
            builder.comment("Time Travel Mod common settings")
                    .push("common");

            grass_enable = builder
                    .comment("Should the layers top of the world be dirt and grass")
                    .define("grass_enable", true);
            day = builder
                    .comment("Should it always be day")
                    .define("day", true);

            world_height = builder
                    .comment("Height of the world")
                    .defineInRange("world_height", 70, 5, 256);
            overworldId = builder
                    .comment("Overworld dim ID")
                    .defineInRange("overworldId", 0, -1000, 1000);

            builder.pop();
        }

        public int getOverworldId() {
            return overworldId.get();
        }
    }
}
