package quantumstudios.honed.api;

import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.entity.EntityLivingBase;
import quantumstudios.honed.api.TraitContext;

public interface ITraitEffect {
    default void onMine(ItemStack stack, World world, IBlockState state, BlockPos pos, EntityLivingBase miner) {}
    default void onHit(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker, float damage) {}
    default void onTick(ItemStack stack, EntityLivingBase holder) {}
    default void onAssembled(ItemStack stack) {}
    default float modifyStat(TraitContext ctx, TraitContext.TraitInstance self, String stat, float currentValue) { return currentValue; }
    default void onMine(TraitContext ctx, TraitContext.TraitInstance self, ItemStack stack, World world, IBlockState state, BlockPos pos, EntityLivingBase miner) {}
    default void onHit(TraitContext ctx, TraitContext.TraitInstance self, ItemStack stack, EntityLivingBase target, EntityLivingBase attacker, float damage) {}
}
