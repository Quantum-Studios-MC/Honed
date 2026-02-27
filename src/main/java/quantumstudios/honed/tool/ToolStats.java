package quantumstudios.honed.tool;

import quantumstudios.honed.data.material.MaterialDefinition;
import quantumstudios.honed.data.part.PartSchema;
import quantumstudios.honed.registry.HonedRegistries;
import quantumstudios.honed.item.tool.ItemHonedTool;
import net.minecraft.item.ItemStack;
import java.util.*;

public class ToolStats {
    public static float getStat(ItemStack stack, String stat) {
        if (ToolNBT.isAssembled(stack)) {
            return ToolNBT.getStat(stack, stat);
        } else {
            recalculate(stack);
            return ToolNBT.getStat(stack, stat);
        }
    }

    public static void recalculate(ItemStack stack) {
        if (!(stack.getItem() instanceof ItemHonedTool)) return;
        ItemHonedTool toolItem = (ItemHonedTool) stack.getItem();
        PartSchema schema = HonedRegistries.getPartSchema(toolItem.getToolType());

        if (schema == null || schema.partSlots == null) return;

        // Accumulators per stat
        Map<String, List<Float>> avgValues = new HashMap<>();
        float maxHarvestLevel = 0;
        Map<String, Float> multipliers = new HashMap<>();
        Map<String, Float> additions = new HashMap<>();

        for (String partType : schema.partSlots.keySet()) {
            PartSchema.SlotDef slot = schema.partSlots.get(partType);
            if (slot == null) continue;
            String materialId = ToolNBT.getMaterial(stack, partType);
            if (materialId.isEmpty()) continue;
            MaterialDefinition mat = HonedRegistries.getMaterial(materialId);
            if (mat == null) continue;
            int quality = ToolNBT.getQuality(stack, partType);
            float qualityMult = getQualityMultiplier(quality);

            // Weighted average contribution
            for (Map.Entry<String, Float> stat : mat.stats.entrySet()) {
                avgValues.computeIfAbsent(stat.getKey(), k -> new ArrayList<>())
                         .add(stat.getValue() * slot.statWeight * qualityMult);
            }
            if (slot.isPrimary) {
                maxHarvestLevel = Math.max(maxHarvestLevel, mat.stats.getOrDefault("harvestLevel", 0f));
            }

            // Multipliers from material (handleMultiplier etc.)
            if (mat.statModifiers != null) {
                for (Map.Entry<String, MaterialDefinition.StatModifier> mod : mat.statModifiers.entrySet()) {
                    if ("MUL".equals(mod.getValue().operation)) {
                        multipliers.put(mod.getKey(), multipliers.getOrDefault(mod.getKey(), 1.0f) * mod.getValue().value);
                    } else if ("ADD".equals(mod.getValue().operation)) {
                        additions.put(mod.getKey(), additions.getOrDefault(mod.getKey(), 0.0f) + mod.getValue().value);
                    }
                }
            }
        }

        // Compute final stats
        float totalDurability = 0;
        float miningSpeed = 0;
        float harvestLevel = maxHarvestLevel;
        float attackDamage = 0;
        float attackSpeed = 0;
        float enchantability = 0;
        for (Map.Entry<String, List<Float>> entry : avgValues.entrySet()) {
            float sum = 0;
            for (float val : entry.getValue()) sum += val;
            float avg = sum / entry.getValue().size();
            switch (entry.getKey()) {
                case "durability": totalDurability = avg; break;
                case "miningSpeed": miningSpeed = avg; break;
                case "attackDamage": attackDamage = avg; break;
                case "attackSpeed": attackSpeed = avg; break;
                case "enchantability": enchantability = avg; break;
            }
        }

        // Apply multipliers and additions
        totalDurability *= multipliers.getOrDefault("durability", 1.0f);
        totalDurability += additions.getOrDefault("durability", 0.0f);
        miningSpeed *= multipliers.getOrDefault("miningSpeed", 1.0f);
        miningSpeed += additions.getOrDefault("miningSpeed", 0.0f);
        attackDamage *= multipliers.getOrDefault("attackDamage", 1.0f);
        attackDamage += additions.getOrDefault("attackDamage", 0.0f);
        attackSpeed *= multipliers.getOrDefault("attackSpeed", 1.0f);
        attackSpeed += additions.getOrDefault("attackSpeed", 0.0f);
        enchantability *= multipliers.getOrDefault("enchantability", 1.0f);
        enchantability += additions.getOrDefault("enchantability", 0.0f);

        // Store the stats
        ToolNBT.setStat(stack, "durability", totalDurability);
        ToolNBT.setStat(stack, "miningSpeed", miningSpeed);
        ToolNBT.setStat(stack, "harvestLevel", harvestLevel);
        ToolNBT.setStat(stack, "attackDamage", attackDamage);
        ToolNBT.setStat(stack, "attackSpeed", attackSpeed);
        ToolNBT.setStat(stack, "enchantability", enchantability);
    }

    private static float getQualityMultiplier(int quality) {
        switch (quality) {
            case 0: return 0.5f;
            case 1: return 0.75f;
            case 2: return 1.0f;
            case 3: return 1.25f;
            case 4: return 1.5f;
            default: return quality > 4 ? 1.5f + (quality - 4) * 0.1f : 0.5f;
        }
    }
}
