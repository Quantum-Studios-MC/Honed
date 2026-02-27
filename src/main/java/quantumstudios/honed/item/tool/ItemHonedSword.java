package quantumstudios.honed.item.tool;

import net.minecraft.item.ItemStack;

import java.util.Collections;
import java.util.Set;

public class ItemHonedSword extends ItemHonedTool {
    public ItemHonedSword() {
        super("sword");
    }

    @Override
    public Set<String> getToolClasses(ItemStack stack) {
        return Collections.singleton("sword");
    }
}
