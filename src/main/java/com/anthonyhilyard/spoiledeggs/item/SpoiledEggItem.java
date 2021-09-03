package com.anthonyhilyard.spoiledeggs.item;

import com.anthonyhilyard.spoiledeggs.Loader;
import com.anthonyhilyard.spoiledeggs.entity.EntityRegistry;
import com.anthonyhilyard.spoiledeggs.entity.EntitySpoiledEgg;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;

public class SpoiledEggItem extends Item
{
	public SpoiledEggItem()
	{
		super(new Item.Properties().stacksTo(16).tab(ItemGroup.TAB_MISC));
		this.setRegistryName(Loader.MODID, "spoiled_egg");
	}

	@Override
	public ActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn)
	{
		ItemStack itemstack = playerIn.getItemInHand(handIn);

		if (!playerIn.isCreative())
		{
			itemstack.shrink(1);
		}

		worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), SoundEvents.EGG_THROW, SoundCategory.PLAYERS, 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));

		if (!worldIn.isClientSide)
		{
			EntitySpoiledEgg eggEntity = new EntitySpoiledEgg(EntityRegistry.SPOILED_EGG_ENTITY, worldIn, playerIn);
			eggEntity.shootFromRotation(playerIn, playerIn.xRot, playerIn.yRot, 0.0F, 1.5F, 1.0F);
			worldIn.addFreshEntity(eggEntity);
		}

		return new ActionResult<>(ActionResultType.SUCCESS, itemstack);
	}
}
