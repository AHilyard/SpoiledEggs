package com.anthonyhilyard.spoiledeggs.entity;

import com.anthonyhilyard.spoiledeggs.item.ItemRegistry;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.network.IPacket;
import net.minecraft.particles.ItemParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.network.NetworkHooks;

public class EntitySpoiledEgg extends ProjectileItemEntity
{
	public EntitySpoiledEgg(EntityType<? extends EntitySpoiledEgg> type, World worldIn)
	{
		super(type, worldIn);
	}

	public EntitySpoiledEgg(EntityType<? extends EntitySpoiledEgg> type, World worldIn, LivingEntity throwerIn)
	{
		super(type, throwerIn, worldIn);
	}

	public EntitySpoiledEgg(EntityType<? extends EntitySpoiledEgg> type, double x, double y, double z, World worldIn)
	{
		super(type, x, y, z, worldIn);
	}

	@Override
	public IPacket<?> getAddEntityPacket()
	{
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public void handleEntityEvent(byte id)
	{
		if (id == 3)
		{
			for (int i = 0; i < 8; ++i)
			{
				this.level.addParticle(new ItemParticleData(ParticleTypes.ITEM, this.getItem()), this.getX(), this.getY(), this.getZ(), ((double) this.random.nextFloat() - 0.5D) * 0.08D, ((double) this.random.nextFloat() - 0.5D) * 0.08D, ((double) this.random.nextFloat() - 0.5D) * 0.08D);
			}
		}
	}

	@Override
	public void tick()
	{
		super.tick();
		if (this.level.isClientSide)
		{
			if (random.nextInt(4) == 0)
			{
				// Poison particle color: 0x4E9331
				this.level.addParticle(ParticleTypes.ENTITY_EFFECT, this.getX(), this.getY(), this.getZ(), (double)(0x4E) / 255.0D, (double)(0x93) / 255.0D, (double)(0x31) / 255.0D);
			}
		}
	}

	/**
	 * Called when this EntityThrowable hits a block or entity.
	 */
	@Override
	protected void onHit(RayTraceResult result)
	{
		Entity thrower = getOwner();
		if (result.getType() == RayTraceResult.Type.ENTITY)
		{
			Entity entityHit = ((EntityRayTraceResult) result).getEntity();
			if (entityHit.hurt(DamageSource.thrown(this, thrower), 0.0F) && entityHit instanceof LivingEntity)
			{
				((LivingEntity)entityHit).addEffect(new EffectInstance(Effects.POISON, 100));
			}
		}

		if (!this.level.isClientSide)
		{
			if (this.random.nextInt(4) == 0)
			{
				int i = 1;

				if (this.random.nextInt(32) == 0)
				{
					i = 4;
				}

				for (int j = 0; j < i; ++j)
				{
					ZombieChickenEntity.spawn(level, getX(), getY(), getZ(), yRot);
				}
			}

			this.level.broadcastEntityEvent(this, (byte) 3);
			this.remove();
		}
	}

	@Override
	protected Item getDefaultItem()
	{
		return ItemRegistry.SPOILED_EGG_ITEM;
	}
}