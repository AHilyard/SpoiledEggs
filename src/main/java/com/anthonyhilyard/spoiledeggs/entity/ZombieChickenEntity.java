package com.anthonyhilyard.spoiledeggs.entity;

import com.anthonyhilyard.spoiledeggs.item.ItemRegistry;
import com.anthonyhilyard.spoiledeggs.sound.SoundRegistry;

import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.entity.passive.FoxEntity;
import net.minecraft.entity.passive.OcelotEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.ai.goal.FollowParentGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.PanicGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.merchant.villager.AbstractVillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;


public class ZombieChickenEntity extends ChickenEntity
{
	public ZombieChickenEntity(EntityType<? extends ChickenEntity> entityType, World world)
	{
		super(entityType, world);
	}

	@Override
	protected void registerGoals()
	{
		this.goalSelector.addGoal(0, new SwimGoal(this));
		this.goalSelector.addGoal(1, new PanicGoal(this, 1.4D));
		this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.0D, false));
		this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.1D));
		this.goalSelector.addGoal(5, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
		this.goalSelector.addGoal(6, new LookAtGoal(this, PlayerEntity.class, 6.0F));
		this.goalSelector.addGoal(7, new LookRandomlyGoal(this));

		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, AbstractVillagerEntity.class, true));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, IronGolemEntity.class, true));
		this.targetSelector.addGoal(5, new NearestAttackableTargetGoal<>(this, AnimalEntity.class, 10, false, false, (target) -> {
			return target instanceof FoxEntity || target instanceof OcelotEntity;
		}));
	}

	@Override
	public CreatureAttribute getMobType()
	{
		return CreatureAttribute.UNDEAD;
	}

	@Override
	protected SoundEvent getAmbientSound()
	{
		return SoundRegistry.ZOMBIE_CHICKEN_AMBIENT;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSource)
	{
		return SoundRegistry.ZOMBIE_CHICKEN_HURT;
	}

	@Override
	protected SoundEvent getDeathSound()
	{
		return SoundRegistry.ZOMBIE_CHICKEN_DEATH;
	}

	@Override
	public boolean isFood(ItemStack item)
	{
		return false;
	}

	@Override
	public void aiStep()
	{
		// Ugly hack time...  We want to run the base chicken's aiStep, except we don't want to be able to lay eggs.
		// So we'll put in a temporary egg timer before running super.aiStep() so we never lay normal eggs.
		int tempEggTime = eggTime;
		eggTime = 2;

		super.aiStep();

		// Restore the actual egg timer.
		eggTime = tempEggTime;
		
		if (!this.level.isClientSide && this.isAlive() && !this.isBaby() && !this.isChickenJockey() && --this.eggTime <= 0)
		{
			this.playSound(SoundRegistry.ZOMBIE_CHICKEN_EGG, 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
			this.spawnAtLocation(ItemRegistry.SPOILED_EGG_ITEM);
			this.eggTime = this.random.nextInt(6000) + 8000;
		}
	}

	public static void spawn(World level, double x, double y, double z, float rot)
	{
		ZombieChickenEntity zombieChicken = EntityRegistry.ZOMBIE_CHICKEN.create(level);
		zombieChicken.setAge(-6000);
		zombieChicken.moveTo(x, y, z, rot, 0.0F);
		level.addFreshEntity(zombieChicken);
	}
}
