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
        //Common
        public IntValue overworldId;

        //YAMDA dim
        public BooleanValue grassEnable;
        public BooleanValue day;
        public IntValue worldHeight;
        public BooleanValue canSleepHere;
        public BooleanValue disableHostileMobs;

        //YAMDA nether dim
        public IntValue netherWorldHeight;
        public BooleanValue disableNetherHostileMobs;

        public ConfigValues(ForgeConfigSpec.Builder builder) {
            builder.comment("YAMDA Config.")
                    .push("common");

            overworldId = builder
                    .comment("Overworld dim ID")
                    .defineInRange("overworldId", 0, -1000, 1000);

            builder.pop();

            builder.comment("Overworld style mining dim")
                    .push("YAMDA dim");

            grassEnable = builder
                    .comment("Should the layers top of the world be dirt and grass")
                    .define("grass_enable", true);
            day = builder
                    .comment("Should it always be day")
                    .define("day", true);
            worldHeight = builder
                    .comment("Height of the world")
                    .defineInRange("world_height", 70, 5, 256);

            canSleepHere = builder
                    .comment("Should you be able to sleep in the dimension")
                    .define("canSleepHere", true);

            disableHostileMobs = builder
                    .comment("Should hostile mobs spawns be disabled?")
                    .define("disableHostileMobs", true);

            builder.pop();

            builder.comment("Nether style mining dim")
                    .push("YAMDA Nether dim");
            netherWorldHeight = builder
                    .comment("Height of the world")
                    .defineInRange("netherWorldHeight", 128, 64, 128);
            disableNetherHostileMobs = builder
                    .comment("Should hostile mob spawns be disabled?")
                    .define("disableNetherHostileMobs", true);

            builder.pop();
        }

        public int getOverworldId() {
            return overworldId.get();
        }
    }
}
