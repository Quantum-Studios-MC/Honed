package quantumstudios.honed.item.tool;

import net.minecraft.item.ItemStack;
import java.util.Collections;
import java.util.Set;

public class ItemHonedAxe extends ItemHonedTool {
    public ItemHonedAxe() {
        super("axe");
    }

    @Override
    public Set<String> getToolClasses(ItemStack stack) {
        return Collections.singleton("axe");
    }
}
