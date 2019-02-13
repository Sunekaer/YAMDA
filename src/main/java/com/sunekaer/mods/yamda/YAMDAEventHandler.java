package com.sunekaer.mods.yamda;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber(modid = YAMDA.MOD_ID)
public class YAMDAEventHandler {
    private static Block withName(Block block, String name)
    {
        block.setCreativeTab(YAMDA.TAB);
        block.setRegistryName(name);
        block.setTranslationKey(YAMDA.MOD_ID + "." + name);
        return block;
    }

    private static Item withName(Item item, String name)
    {
        item.setCreativeTab(YAMDA.TAB);
        item.setRegistryName(name);
        item.setTranslationKey(YAMDA.MOD_ID + "." + name);
        return item;
    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event)
    {
        IForgeRegistry<Block> r = event.getRegistry();
        //r.register(withName(new BlockXenOre(), "xen_ore"));

    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event)
    {
        IForgeRegistry<Item> r = event.getRegistry();
        //r.register(withName(new ItemBoneMeal(SoilType.SAND), "wet_bone_meal"));
    }

}
