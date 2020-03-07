package com.sunekaer.mods.yamda;

import com.sunekaer.mods.yamda.dimension.YAMDABiomeProvider;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.world.biome.provider.BiomeProviderType;
import net.minecraft.world.biome.provider.SingleBiomeProviderSettings;
import net.minecraft.world.gen.ChunkGeneratorType;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.ModDimension;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.sunekaer.mods.yamda.YAMDA.MODID;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class YAMDAEventHandler {

    @SubscribeEvent
    public static void onDimensionModRegistry(RegistryEvent.Register<ModDimension> event) {
        event.getRegistry().register(YAMDA.dimension);
        DimensionManager.registerDimension(YAMDA.YAMDA_DIM, YAMDA.dimension, null, true);
    }

    @SubscribeEvent
    public static void onChunkGeneratorTypeRegistry(RegistryEvent.Register<ChunkGeneratorType<?, ?>> event) {
        event.getRegistry().register(YAMDA.generatorType.setRegistryName(MODID, "generator"));
    }

//    @SubscribeEvent
//    public static void onBiomeProviderTypeRegistry(RegistryEvent.Register<BiomeProviderType<?, ?>> event) {
//        event.getRegistry().register(YAMDA.biomeProviderType.setRegistryName(MODID, "generator"));
//    }

    @SubscribeEvent
    public static void onBlockRegistry(RegistryEvent.Register<Block> event) {
        event.getRegistry().register(YAMDA.portal.setRegistryName(MODID, "portal"));
    }

    @SubscribeEvent
    public static void onItemRegistry(RegistryEvent.Register<Item> event) {
        event.getRegistry().register(new BlockItem(YAMDA.portal, new Item.Properties().group(YAMDA.GROUP)).setRegistryName(YAMDA.portal.getRegistryName()));
    }

//    @SubscribeEvent
//    public static void onBiomeProviderType(RegistryEvent.Register<BiomeProviderType> event) {
//        event.getRegistry().register(new BiomeProviderType<>(YAMDABiomeProvider::new, SingleBiomeProviderSettings::new).setRegistryName("yamda:mining"));
//    }
}
