package com.sunekaer.mods.yamda;

import com.sunekaer.mods.yamda.dimension.YAMDABiomeProvider;
import com.sunekaer.mods.yamda.netherdimension.YAMDANetherBiome;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.world.biome.Biome;
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

        event.getRegistry().register(YAMDA.netherDimension);
        DimensionManager.registerDimension(YAMDA.YAMDA_NETHER_DIM, YAMDA.netherDimension, null, true);
    }

    @SubscribeEvent
    public static void onChunkGeneratorTypeRegistry(RegistryEvent.Register<ChunkGeneratorType<?, ?>> event) {
        event.getRegistry().register(YAMDA.generatorType.setRegistryName(MODID, "generator"));
        event.getRegistry().register(YAMDA.netherGeneratorType.setRegistryName(MODID, "nether_generator"));

    }

//    @SubscribeEvent
//    public static void onBiomeProviderTypeRegistry(RegistryEvent.Register<BiomeProviderType<?, ?>> event) {
//        event.getRegistry().register(YAMDA.biomeProviderType.setRegistryName(MODID, "generator"));
//    }

    @SubscribeEvent
    public static void onBlockRegistry(RegistryEvent.Register<Block> event) {
        event.getRegistry().register(YAMDA.portal.setRegistryName(MODID, "portal"));
        event.getRegistry().register(YAMDA.netherPortal.setRegistryName(MODID, "nether_portal"));
    }

    @SubscribeEvent
    public static void onItemRegistry(RegistryEvent.Register<Item> event) {
        event.getRegistry().register(new BlockItem(YAMDA.portal, new Item.Properties().group(YAMDA.GROUP)).setRegistryName(YAMDA.portal.getRegistryName()));
        event.getRegistry().register(new BlockItem(YAMDA.netherPortal, new Item.Properties().group(YAMDA.GROUP)).setRegistryName(YAMDA.netherPortal.getRegistryName()));
    }

    @SubscribeEvent
    public static void onBiomeRegistry(RegistryEvent.Register<Biome> event) {
        event.getRegistry().register(YAMDA.netherBiome);
    }

//    @SubscribeEvent
//    public static void onBiomeProviderType(RegistryEvent.Register<BiomeProviderType> event) {
//        event.getRegistry().register(new BiomeProviderType<>(YAMDABiomeProvider::new, SingleBiomeProviderSettings::new).setRegistryName("yamda:mining"));
//    }
}
