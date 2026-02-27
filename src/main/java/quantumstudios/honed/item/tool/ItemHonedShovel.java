package quantumstudios.honed.item.tool;

import net.minecraft.item.ItemStack;
import java.util.Collections;
import java.util.Set;

public class ItemHonedShovel extends ItemHonedTool {
    public ItemHonedShovel() { super("shovel"); }

    @Override
    public Set<String> getToolClasses(ItemStack stack) {
        return Collections.singleton("shovel");
    }
}
