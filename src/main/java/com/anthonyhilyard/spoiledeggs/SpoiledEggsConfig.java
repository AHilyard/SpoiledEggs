package com.anthonyhilyard.spoiledeggs;

import org.apache.commons.lang3.tuple.Pair;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.config.ModConfig;

import com.electronwill.nightconfig.core.Config;

public class SpoiledEggsConfig
{
	public static final ForgeConfigSpec SPEC;
	public static final SpoiledEggsConfig INSTANCE;
	static
	{
		Config.setInsertionOrderPreserved(true);
		Pair<SpoiledEggsConfig, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(SpoiledEggsConfig::new);
		SPEC = specPair.getRight();
		INSTANCE = specPair.getLeft();
	}

	public final BooleanValue naturalHatching;
	public final BooleanValue zombieJockeys;

	public SpoiledEggsConfig(ForgeConfigSpec.Builder build)
	{
		build.comment("Client Configuration").push("client").push("options");

		naturalHatching = build.comment(" If zombie chickens can hatch from old spoiled eggs on the ground.  Turn off if you don't want zombie chickens appearing in chicken farms!").define("natural_hatching", false);
		zombieJockeys = build.comment(" If chicken jockeys should spawn on zombie chickens instead of normal ones.").define("zombie_jockeys", true);

		build.pop().pop();
	}

	@SubscribeEvent
	public static void onLoad(ModConfig.Loading e)
	{
	}
}