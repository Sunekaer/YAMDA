package com.sunekaer.mods.yamda.client;

import com.sunekaer.mods.yamda.YAMDA;
import com.sunekaer.mods.yamda.item.YAMDAItems;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(modid = YAMDA.MOD_ID, value = Side.CLIENT)
public class YAMDAClientEventHandler {

    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event)
    {
        ModelLoader.setCustomModelResourceLocation(YAMDAItems.PORTAL, 0, new ModelResourceLocation(YAMDAItems.PORTAL.getRegistryName(), "normal"));

    }
}
