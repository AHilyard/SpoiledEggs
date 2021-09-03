package com.anthonyhilyard.spoiledeggs;

import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.entity.item.ItemExpireEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraft.loot.ItemLootEntry;
import net.minecraft.loot.LootEntry;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.RandomValueRange;
import net.minecraft.loot.functions.SetCount;
import net.minecraft.item.EggItem;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Random;

import com.anthonyhilyard.spoiledeggs.entity.ZombieChickenEntity;
import com.anthonyhilyard.spoiledeggs.item.ItemRegistry;
import com.anthonyhilyard.spoiledeggs.item.SpoiledEggItem;


@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.FORGE)
public class SpoiledEggs
{
	private static final Random random = new Random();

	@SubscribeEvent
	public static void onItemExpired(ItemExpireEvent event)
	{
		if (event.getEntityItem().getItem() != null && event.getEntityItem().getItem().getItem() != null)
		{
			// If an egg is expiring, there is a chance it will spoil instead.
			// (Exact class matching so that modded eggs that are for some reason a subclass of EggItem don't spoil.)
			if (event.getEntityItem().getItem().getItem().getClass().equals(EggItem.class))
			{
				if (random.nextInt(4) == 0)
				{
					event.getEntityItem().spawnAtLocation(ItemRegistry.SPOILED_EGG_ITEM);
				}
			}
			// If a spoiled egg is expiring, there is a chance a zombie chicken will hatch naturally.
			else if (event.getEntityItem().getItem().getItem() instanceof SpoiledEggItem && SpoiledEggsConfig.INSTANCE.naturalHatching.get())
			{
				if (random.nextInt(4) == 0)
				{
					ZombieChickenEntity.spawn(event.getEntityItem().level, event.getEntityItem().getX(), event.getEntityItem().getY(), event.getEntityItem().getZ(), event.getEntityItem().yRot);
				}
			}
		}
	}

	@SubscribeEvent
	@SuppressWarnings("unchecked")
	public static void onLootTableLoaded(LootTableLoadEvent event)
	{
		// Don't do loot like this.
		try
		{
			if (event.getName().equals(LootTables.VILLAGE_FLETCHER))
			{
				Field entriesField = LootPool.class.getDeclaredField("field_186453_a");
				entriesField.setAccessible(true);

				List<LootEntry> entries = (List<LootEntry>) entriesField.get(event.getTable().getPool("main"));
				entries.add(ItemLootEntry.lootTableItem(ItemRegistry.SPOILED_EGG_ITEM).setWeight(2).apply(SetCount.setCount(RandomValueRange.between(1.0F, 3.0F))).build());
			}
			else if (event.getName().equals(LootTables.SHIPWRECK_SUPPLY))
			{
				Field entriesField = LootPool.class.getDeclaredField("field_186453_a");
				entriesField.setAccessible(true);

				List<LootEntry> entries = (List<LootEntry>) entriesField.get(event.getTable().getPool("main"));
				entries.add(ItemLootEntry.lootTableItem(ItemRegistry.SPOILED_EGG_ITEM).setWeight(7).apply(SetCount.setCount(RandomValueRange.between(2.0F, 6.0F))).build());
			}
		}
		catch (Exception e)
		{
			Loader.LOGGER.warn(e);
		}
	}
}
