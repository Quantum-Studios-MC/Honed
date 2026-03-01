package quantumstudios.honed.item.part;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class ItemHonedPart extends Item {
    public ItemHonedPart() {
        this.setMaxStackSize(1);
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
        this.setCreativeTab(CreativeTabs.TOOLS);
    }

    public static final String[] NAME_LOOKUP = {
        "handle", "binder", "pickaxe_head", "sword_head", "shovel_head", "axe_head"
    };

    @Override
    public String getTranslationKey(ItemStack stack) {
        int meta = stack.getMetadata();
        if (meta < 0 || meta >= NAME_LOOKUP.length) return "item.honed.part";
        return "item.honed.part_" + NAME_LOOKUP[meta];
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        if (this.isInCreativeTab(tab)) {
            for (int part = 0; part < NAME_LOOKUP.length; part++) {
                items.add(new ItemStack(this, 1, part)); //TODO: different materials
            }
        }
    }

    public static int getMetaForPartType(String partType) {
        for (int i = 0; i < NAME_LOOKUP.length; i++) {
            if (NAME_LOOKUP[i].equals(partType)) {
                return i;
            }
        }
        return 0;
    }

    public static String getPartTypeForMeta(int meta) {
        if (meta < 0 || meta >= NAME_LOOKUP.length) return "handle";
        return NAME_LOOKUP[meta];
    }
}
