package com.anthonyhilyard.spoiledeggs.renderer;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.model.ChickenModel;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

import com.anthonyhilyard.spoiledeggs.Loader;
import com.anthonyhilyard.spoiledeggs.entity.ZombieChickenEntity;

public class ZombieChickenRenderer extends MobRenderer<ZombieChickenEntity, ChickenModel<ZombieChickenEntity>>
{
	private static final ResourceLocation ZOMBIE_CHICKEN_TEXTURE = new ResourceLocation(Loader.MODID, "textures/entity/zombiechicken.png");

	public ZombieChickenRenderer(EntityRendererManager manager)
	{
		super(manager, new ChickenModel<>(), 0.3f);
	}

	@Override
	public ResourceLocation getTextureLocation(ZombieChickenEntity entity)
	{
		return ZOMBIE_CHICKEN_TEXTURE;
	}

	@Override
	protected float getBob(ZombieChickenEntity livingBase, float partialTicks)
	{
		float f = MathHelper.lerp(partialTicks, livingBase.oFlap, livingBase.flap);
		float f1 = MathHelper.lerp(partialTicks, livingBase.oFlapSpeed, livingBase.flapSpeed);
		return (MathHelper.sin(f) + 1.0F) * f1;
	}
}