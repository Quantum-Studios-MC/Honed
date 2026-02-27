package quantumstudios.honed.trait;

import quantumstudios.honed.api.ITraitEffect;
import quantumstudios.honed.registry.HonedRegistries;
import quantumstudios.honed.tool.ToolNBT;
import quantumstudios.honed.item.tool.ItemHonedTool;
import quantumstudios.honed.data.part.PartSchema;
import quantumstudios.honed.data.material.MaterialDefinition;
import quantumstudios.honed.data.trait.TraitDefinition;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.entity.EntityLivingBase;
import java.util.*;

public final class TraitEffectRegistry {
    private static final Map<String, ITraitEffect> EFFECTS = new HashMap<>();

    public static void register(String effectType, ITraitEffect effect) {
        EFFECTS.put(effectType, effect);
    }

    public static void onMine(ItemStack stack, World world, IBlockState state, BlockPos pos, EntityLivingBase miner) {
        getActiveEffects(stack).forEach(e -> e.onMine(stack, world, state, pos, miner));
    }

    public static void onHit(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker, float damage) {
        getActiveEffects(stack).forEach(e -> e.onHit(stack, target, attacker, damage));
    }

    public static void onTick(ItemStack stack, EntityLivingBase holder) {
        getActiveEffects(stack).forEach(e -> e.onTick(stack, holder));
    }

    public static void onToolAssembled(ItemStack stack) {
        getActiveEffects(stack).forEach(e -> e.onAssembled(stack));
    }

    private static List<ITraitEffect> getActiveEffects(ItemStack stack) {
        List<ITraitEffect> effects = new ArrayList<>();
        if (!(stack.getItem() instanceof ItemHonedTool)) return effects;
        ItemHonedTool tool = (ItemHonedTool) stack.getItem();
        PartSchema schema = HonedRegistries.getPartSchema(tool.getToolType());

        if (schema == null) return effects;

        Set<String> allTraitIds = new HashSet<>();
        for (String partType : schema.partSlots.keySet()) {
            PartSchema.SlotDef slot = schema.partSlots.get(partType);
            if (slot == null) continue;
            String materialId = ToolNBT.getMaterial(stack, partType);
            MaterialDefinition mat = HonedRegistries.getMaterial(materialId);
            if (mat != null && mat.traitIds != null) {
                allTraitIds.addAll(mat.traitIds);
            }
        }
        allTraitIds.addAll(ToolNBT.getAffixTraits(stack));
        for (String traitId : allTraitIds) {
            TraitDefinition traitDef = HonedRegistries.getTrait(traitId);
            if (traitDef != null) {
                ITraitEffect effect = EFFECTS.get(traitDef.effectType);
                if (effect != null) {
                    effects.add(effect);
                }
            }
        }
        return effects;
    }
}
