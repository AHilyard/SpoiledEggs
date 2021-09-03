package com.anthonyhilyard.spoiledeggs.entity;

import com.anthonyhilyard.iceberg.registry.AutoRegistry;
import com.anthonyhilyard.spoiledeggs.renderer.ZombieChickenRenderer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.SpriteRenderer;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.monster.MonsterEntity;

public class EntityRegistry extends AutoRegistry
{
	public static final EntityType<EntitySpoiledEgg> SPOILED_EGG_ENTITY = registerEntity("spoiled_egg",
			EntityType.Builder.<EntitySpoiledEgg>of(EntitySpoiledEgg::new, EntityClassification.MISC).sized(0.25F, 0.25F).clientTrackingRange(4).updateInterval(10),
			(manager) -> new SpriteRenderer<>(manager, Minecraft.getInstance().getItemRenderer()));

	public static final EntityType<ZombieChickenEntity> ZOMBIE_CHICKEN = registerEntity("zombie_chicken",
			EntityType.Builder.of(ZombieChickenEntity::new, EntityClassification.CREATURE).sized(0.375f, 0.625f),
			(manager) -> new ZombieChickenRenderer(manager),
			() -> { return MonsterEntity.createMonsterAttributes().add(Attributes.MAX_HEALTH, 6).add(Attributes.MOVEMENT_SPEED, 0.25).add(Attributes.FOLLOW_RANGE, 24.0).add(Attributes.ATTACK_DAMAGE, 2.0D); });
}
