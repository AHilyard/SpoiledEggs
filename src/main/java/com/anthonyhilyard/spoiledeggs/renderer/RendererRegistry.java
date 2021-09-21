package com.anthonyhilyard.spoiledeggs.renderer;

import com.anthonyhilyard.iceberg.registry.RendererRegistrar;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.SpriteRenderer;


public class RendererRegistry extends RendererRegistrar
{
	static
	{
		registerRenderer("spoiled_egg", (manager) -> new SpriteRenderer<>(manager, Minecraft.getInstance().getItemRenderer()));
		registerRenderer("zombie_chicken", (manager) -> new ZombieChickenRenderer(manager));
	}
}
