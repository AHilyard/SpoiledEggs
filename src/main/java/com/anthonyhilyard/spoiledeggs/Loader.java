package com.anthonyhilyard.spoiledeggs;

import com.anthonyhilyard.iceberg.registry.AutoRegistry;
import com.anthonyhilyard.spoiledeggs.entity.EntityRegistry;
import com.anthonyhilyard.spoiledeggs.item.ItemRegistry;
import com.anthonyhilyard.spoiledeggs.sound.SoundRegistry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.fml.config.ModConfig;

@Mod(Loader.MODID)
public class Loader
{
	public static final String MODID = "spoiledeggs";
	public static final Logger LOGGER = LogManager.getLogger(MODID);

	public Loader()
	{
		if (FMLEnvironment.dist == Dist.CLIENT)
		{
			MinecraftForge.EVENT_BUS.register(SpoiledEggs.class);
			ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, SpoiledEggsConfig.SPEC);
		}

		// Initialize the auto registry and the various registries.
		AutoRegistry.init(MODID);
		new ItemRegistry();
		new EntityRegistry();
		new SoundRegistry();
	}

}