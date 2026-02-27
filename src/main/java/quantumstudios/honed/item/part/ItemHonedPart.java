package quantumstudios.honed.item.part;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ItemHonedPart extends Item {
    public ItemHonedPart() {
        setMaxStackSize(1);
        setHasSubtypes(false);
    }

    public ItemHonedPart(CreativeTabs tab) {
        setCreativeTab(tab);
    }
}
