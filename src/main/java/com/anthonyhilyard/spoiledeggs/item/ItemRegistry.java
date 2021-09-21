package com.anthonyhilyard.spoiledeggs.item;

import com.anthonyhilyard.iceberg.registry.AutoRegistry;
import com.anthonyhilyard.spoiledeggs.Loader;
import com.anthonyhilyard.spoiledeggs.entity.EntityRegistry;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.SpawnEggItem;

public class ItemRegistry extends AutoRegistry
{
	public static final Item SPOILED_EGG_ITEM = new SpoiledEggItem();
	public static final Item ZOMBIE_CHICKEN_SPAWN_EGG = new SpawnEggItem(EntityRegistry.ZOMBIE_CHICKEN, 0x9FA653, 0x799C65, new Item.Properties().tab(ItemGroup.TAB_MISC)).setRegistryName(Loader.MODID, "spawn_egg_zombie_chicken");
}
