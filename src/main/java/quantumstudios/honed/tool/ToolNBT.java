package quantumstudios.honed.tool;

import net.minecraft.item.ItemStack;
import java.util.List;
import java.util.ArrayList;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants;

import java.util.ArrayList;
import java.util.List;

public final class ToolNBT {
    // Keys
    public static final String CONSTRUCTION = "honed_construction";
    public static final String PARTS        = "parts";
    public static final String MATERIAL     = "material";
    public static final String QUALITY      = "quality";
    public static final String AFFIX_TRAITS = "affixTraits";
    public static final String STATS        = "honed:stats";
    public static final String XP           = "honed:xp";
    public static final String HEAT         = "honed:heat";
    public static final String TEMPERATURE  = "temperature";
    public static final String FORGING      = "honed:forging";
    public static final String RECIPE_ID    = "recipeId";
    public static final String STEPS        = "steps";
    public static final String QUALITY_PTS  = "qualityPoints";
    public static final String PART_TAG     = "honed:part";
    public static final String PART_TYPE    = "type";

    // Read
    public static String getMaterial(ItemStack stack, String slot) {
        if (!stack.hasTagCompound()) return "";
        NBTTagCompound nbt = stack.getTagCompound();
        if (nbt.hasKey(CONSTRUCTION)) {
            NBTTagCompound construction = nbt.getCompoundTag(CONSTRUCTION);
            if (construction.hasKey(PARTS)) {
                NBTTagCompound parts = construction.getCompoundTag(PARTS);
                if (parts.hasKey(slot)) {
                    NBTTagCompound slotTag = parts.getCompoundTag(slot);
                    return slotTag.getString(MATERIAL);
                }
            }
        }
        return "";
    }

    public static int getQuality(ItemStack stack, String slot) {
        if (!stack.hasTagCompound()) return 0;
        NBTTagCompound nbt = stack.getTagCompound();
        if (nbt.hasKey(CONSTRUCTION)) {
            NBTTagCompound construction = nbt.getCompoundTag(CONSTRUCTION);
            if (construction.hasKey(PARTS)) {
                NBTTagCompound parts = construction.getCompoundTag(PARTS);
                if (parts.hasKey(slot)) {
                    NBTTagCompound slotTag = parts.getCompoundTag(slot);
                    return slotTag.getInteger(QUALITY);
                }
            }
        }
        return 0;
    }

    public static List<String> getAffixTraits(ItemStack stack) {
        List<String> traits = new ArrayList<>();
        if (!stack.hasTagCompound()) return traits;
        NBTTagCompound nbt = stack.getTagCompound();
        if (nbt.hasKey(CONSTRUCTION)) {
            NBTTagCompound construction = nbt.getCompoundTag(CONSTRUCTION);
            if (construction.hasKey(AFFIX_TRAITS)) {
                NBTTagList list = construction.getTagList(AFFIX_TRAITS, Constants.NBT.TAG_STRING);
                for (int i = 0; i < list.tagCount(); i++) {
                    traits.add(list.getStringTagAt(i));
                }
            }
        }
        return traits;
    }

    public static boolean isAssembled(ItemStack stack) {
        if (!stack.hasTagCompound()) return false;
        NBTTagCompound nbt = stack.getTagCompound();
        if (nbt.hasKey(CONSTRUCTION)) {
            return nbt.getCompoundTag(CONSTRUCTION).getBoolean("assembled");
        }
        return false;
    }

    public static float getStat(ItemStack stack, String stat) {
        if (!stack.hasTagCompound()) return 0.0f;
        NBTTagCompound nbt = stack.getTagCompound();
        if (nbt.hasKey(STATS)) {
            return nbt.getCompoundTag(STATS).getFloat(stat);
        }
        return 0.0f;
    }

    public static void setStat(ItemStack stack, String stat, float value) {
        if (!stack.hasTagCompound()) stack.setTagCompound(new NBTTagCompound());
        NBTTagCompound nbt = stack.getTagCompound();
        if (!nbt.hasKey(STATS)) nbt.setTag(STATS, new NBTTagCompound());
        nbt.getCompoundTag(STATS).setFloat(stat, value);
    }

    public static int getXp(ItemStack stack) {
        if (!stack.hasTagCompound()) return 0;
        return stack.getTagCompound().getInteger(XP);
    }

    public static float getTemperature(ItemStack stack) {
        if (!stack.hasTagCompound()) return 0f;
        NBTTagCompound nbt = stack.getTagCompound();
        if (nbt.hasKey(HEAT)) {
            return nbt.getCompoundTag(HEAT).getFloat(TEMPERATURE);
        }
        return 0f;
    }

    public static List<String> getForgingSteps(ItemStack stack) {
        List<String> steps = new ArrayList<>();
        if (!stack.hasTagCompound()) return steps;
        NBTTagCompound nbt = stack.getTagCompound();
        if (nbt.hasKey(FORGING)) {
            NBTTagCompound forging = nbt.getCompoundTag(FORGING);
            if (forging.hasKey(STEPS)) {
                NBTTagList list = forging.getTagList(STEPS, Constants.NBT.TAG_STRING);
                for (int i = 0; i < list.tagCount(); i++) {
                    steps.add(list.getStringTagAt(i));
                }
            }
        }
        return steps;
    }

    public static int getQualityPoints(ItemStack stack) {
        if (!stack.hasTagCompound()) return 0;
        NBTTagCompound nbt = stack.getTagCompound();
        if (nbt.hasKey(FORGING)) {
            return nbt.getCompoundTag(FORGING).getInteger(QUALITY_PTS);
        }
        return 0;
    }

    public static String getPartType(ItemStack stack) {
        if (!stack.hasTagCompound()) return "";
        NBTTagCompound nbt = stack.getTagCompound();
        if (nbt.hasKey(PART_TAG)) {
            return nbt.getCompoundTag(PART_TAG).getString(PART_TYPE);
        }
        return "";
    }

    public static String getPartMaterial(ItemStack stack) {
        if (!stack.hasTagCompound()) return "";
        NBTTagCompound nbt = stack.getTagCompound();
        if (nbt.hasKey(PART_TAG)) {
            return nbt.getCompoundTag(PART_TAG).getString(MATERIAL);
        }
        return "";
    }

    // Write
    public static void setMaterial(ItemStack stack, String slot, String materialId) {
        NBTTagCompound nbt = getOrCreateTag(stack);
        if (!nbt.hasKey(CONSTRUCTION)) nbt.setTag(CONSTRUCTION, new NBTTagCompound());
        NBTTagCompound construction = nbt.getCompoundTag(CONSTRUCTION);
        if (!construction.hasKey(PARTS)) construction.setTag(PARTS, new NBTTagCompound());
        NBTTagCompound parts = construction.getCompoundTag(PARTS);
        if (!parts.hasKey(slot)) parts.setTag(slot, new NBTTagCompound());
        NBTTagCompound slotTag = parts.getCompoundTag(slot);
        slotTag.setString(MATERIAL, materialId);
    }

    public static void setQuality(ItemStack stack, String slot, int quality) {
        NBTTagCompound nbt = getOrCreateTag(stack);
        if (!nbt.hasKey(CONSTRUCTION)) nbt.setTag(CONSTRUCTION, new NBTTagCompound());
        NBTTagCompound construction = nbt.getCompoundTag(CONSTRUCTION);
        if (!construction.hasKey(PARTS)) construction.setTag(PARTS, new NBTTagCompound());
        NBTTagCompound parts = construction.getCompoundTag(PARTS);
        if (!parts.hasKey(slot)) parts.setTag(slot, new NBTTagCompound());
        NBTTagCompound slotTag = parts.getCompoundTag(slot);
        slotTag.setInteger(QUALITY, quality);
    }

    public static void setAffixTraits(ItemStack stack, List<String> traitIds) {
        NBTTagCompound nbt = getOrCreateTag(stack);
        if (!nbt.hasKey(CONSTRUCTION)) nbt.setTag(CONSTRUCTION, new NBTTagCompound());
        NBTTagCompound construction = nbt.getCompoundTag(CONSTRUCTION);
        NBTTagList list = new NBTTagList();
        for (String id : traitIds) {
            list.appendTag(new net.minecraft.nbt.NBTTagString(id));
        }
        construction.setTag(AFFIX_TRAITS, list);
    }

    public static void writeStat(ItemStack stack, String statKey, float value) {
        NBTTagCompound nbt = getOrCreateTag(stack);
        if (!nbt.hasKey(STATS)) nbt.setTag(STATS, new NBTTagCompound());
        nbt.getCompoundTag(STATS).setFloat(statKey, value);
    }

    public static void setXp(ItemStack stack, int xp) {
        getOrCreateTag(stack).setInteger(XP, xp);
    }

    public static void setTemperature(ItemStack stack, float temp) {
        NBTTagCompound nbt = getOrCreateTag(stack);
        if (!nbt.hasKey(HEAT)) nbt.setTag(HEAT, new NBTTagCompound());
        nbt.getCompoundTag(HEAT).setFloat(TEMPERATURE, temp);
    }

    public static void addForgingStep(ItemStack stack, String step) {
        NBTTagCompound nbt = getOrCreateTag(stack);
        if (!nbt.hasKey(FORGING)) nbt.setTag(FORGING, new NBTTagCompound());
        NBTTagCompound forging = nbt.getCompoundTag(FORGING);
        NBTTagList list = forging.hasKey(STEPS) ? forging.getTagList(STEPS, Constants.NBT.TAG_STRING) : new NBTTagList();
        list.appendTag(new net.minecraft.nbt.NBTTagString(step));
        forging.setTag(STEPS, list);
    }

    public static void setQualityPoints(ItemStack stack, int pts) {
        NBTTagCompound nbt = getOrCreateTag(stack);
        if (!nbt.hasKey(FORGING)) nbt.setTag(FORGING, new NBTTagCompound());
        nbt.getCompoundTag(FORGING).setInteger(QUALITY_PTS, pts);
    }

    public static void setPartData(ItemStack stack, String type, String materialId, int quality) {
        NBTTagCompound nbt = getOrCreateTag(stack);
        if (!nbt.hasKey(PART_TAG)) nbt.setTag(PART_TAG, new NBTTagCompound());
        NBTTagCompound part = nbt.getCompoundTag(PART_TAG);
        part.setString(PART_TYPE, type);
        part.setString(MATERIAL, materialId);
        part.setInteger(QUALITY, quality);
    }

    public static int getLevel(ItemStack stack) {
        return getStat(stack, "level") != 0.0f ? (int)getStat(stack, "level") : 1;
    }

    public static int getXpProgress(ItemStack stack) {
        return getXp(stack);
    }

    public static int getXpToNextLevel(ItemStack stack) {
        int level = getLevel(stack);
        return (level * 100);
    }

    public static int getPartQuality(ItemStack stack) {
        if (!stack.hasTagCompound()) return 0;
        NBTTagCompound nbt = stack.getTagCompound();
        if (nbt.hasKey(PART_TAG)) {
            return nbt.getCompoundTag(PART_TAG).getInteger(QUALITY);
        }
        return 0;
    }

    public static void addXp(ItemStack stack, int amount) {
        int current = getXp(stack);
        setXp(stack, current + amount);
        int level = getLevel(stack);
        int maxXp = getXpToNextLevel(stack);
        if (current + amount >= maxXp) {
            setXp(stack, 0);
            setStat(stack, "level", level + 1);
        }
    }

    private static NBTTagCompound getOrCreateTag(ItemStack stack) {
        if (!stack.hasTagCompound()) stack.setTagCompound(new NBTTagCompound());
        return stack.getTagCompound();
    }

    private ToolNBT() {}
}
