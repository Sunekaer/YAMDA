package com.sunekaer.mods.yamda;

import com.sunekaer.mods.yamda.dimension.YAMDAModDimension;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.provider.BiomeProviderType;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.ChunkGeneratorType;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.ModDimension;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.world.RegisterDimensionsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = YAMDA.MODID, bus=Mod.EventBusSubscriber.Bus.MOD)
public class YAMDAEventHandler {

    public static final ResourceLocation MINING_DIM = new ResourceLocation("yamda:mining_dim");

    @SubscribeEvent
    public static void onDimensionTypeRegistry(RegistryEvent.Register<DimensionType> event){
        event.getRegistry().register(YAMDA.type);
    }

    @SubscribeEvent
    public static void onDimensionModRegistry(RegistryEvent.Register<ModDimension> event){
        event.getRegistry().register(YAMDA.dimension);
    }

    @SubscribeEvent
    public static void onChunkGeneratorTypeRegistry(RegistryEvent.Register<ChunkGeneratorType<?, ?>> event){
        event.getRegistry().register(YAMDA.generatorType.setRegistryName(YAMDA.MODID, "generator"));
    }

    @SubscribeEvent
    public static void onBiomeProviderTypeRegistry(RegistryEvent.Register<BiomeProviderType<?, ?>> event){
        event.getRegistry().register(YAMDA.biomeProviderType.setRegistryName(YAMDA.MODID, "generator"));
    }

    @SubscribeEvent
    public static void onBlockRegistry(RegistryEvent.Register<Block> event) {
        event.getRegistry().register(YAMDA.portal.setRegistryName(YAMDA.MODID, "portal"));
    }

    @SubscribeEvent
    public static void onItemRegistry(RegistryEvent.Register<Item> event) {
        event.getRegistry().register(new BlockItem(YAMDA.portal, new Item.Properties().group(YAMDA.GROUP)).setRegistryName(YAMDA.portal.getRegistryName()));
    }

//    @SubscribeEvent
//    public static void onModelRegistry(ModelRegistryEvent event) {
//        net.minecraftforge.client.model.ModelLoader
//        event.getRegistry().register(new BlockItem(portal, new Item.Properties().group(GROUP)).setRegistryName(portal.getRegistryName()));
//    }
}
