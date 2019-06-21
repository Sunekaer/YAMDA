package com.sunekaer.mods.yamda;

import com.sunekaer.mods.yamda.block.BlockPortal;
import com.sunekaer.mods.yamda.config.YAMDAConfig;
import com.sunekaer.mods.yamda.dimension.YAMDABiomeProvider;
import com.sunekaer.mods.yamda.dimension.YAMDAChunkGenerator;
import com.sunekaer.mods.yamda.dimension.YAMDAModDimension;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.provider.BiomeProviderType;
import net.minecraft.world.biome.provider.SingleBiomeProviderSettings;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.ChunkGeneratorType;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.ModDimension;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

@Mod(YAMDA.MODID)
public class YAMDA {

    public static final String MODID = "yamda";
    public static DimensionType type;

    public static ModDimension dimension = new YAMDAModDimension().setRegistryName(new ResourceLocation("yamda:mining_dim"));
    public static ChunkGeneratorType<GenerationSettings, YAMDAChunkGenerator> generatorType = new ChunkGeneratorType<>(YAMDAChunkGenerator::new, false, GenerationSettings::new);
    public static BiomeProviderType<SingleBiomeProviderSettings, YAMDABiomeProvider> biomeProviderType = new BiomeProviderType<>(YAMDABiomeProvider::new, SingleBiomeProviderSettings::new);

    public static BlockPortal portal = new BlockPortal();

    public static final ItemGroup GROUP = new ItemGroup(MODID) {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(portal);
        }
    };

    public YAMDA() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, YAMDAConfig.configSpec);
    }
}
