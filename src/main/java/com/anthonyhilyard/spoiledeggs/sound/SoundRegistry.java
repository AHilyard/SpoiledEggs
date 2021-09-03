package com.anthonyhilyard.spoiledeggs.sound;

import com.anthonyhilyard.iceberg.registry.AutoRegistry;

import net.minecraft.util.SoundEvent;

public class SoundRegistry extends AutoRegistry
{
	public static final SoundEvent ZOMBIE_CHICKEN_AMBIENT = registerSound("entity.zombie_chicken.ambient");
	public static final SoundEvent ZOMBIE_CHICKEN_DEATH = registerSound("entity.zombie_chicken.death");
	public static final SoundEvent ZOMBIE_CHICKEN_EGG = registerSound("entity.zombie_chicken.egg");
	public static final SoundEvent ZOMBIE_CHICKEN_HURT = registerSound("entity.zombie_chicken.hurt");
}
