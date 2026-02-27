package quantumstudios.honed.api;

import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.entity.EntityLivingBase;

public interface ITraitEffect {
    default void onMine(ItemStack stack, World world, IBlockState state, BlockPos pos, EntityLivingBase miner) {}
    default void onHit(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker, float damage) {}
    default void onTick(ItemStack stack, EntityLivingBase holder) {}
    default void onAssembled(ItemStack stack) {} // for traits that add enchantments or modify NBT at craft time
}
