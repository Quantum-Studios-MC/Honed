package quantumstudios.honed.item.tool;

import net.minecraft.item.ItemStack;
import java.util.Collections;
import java.util.Set;

public class ItemHonedPickaxe extends ItemHonedTool {
    public ItemHonedPickaxe() {
        super("pickaxe");
    }

    @Override
    public Set<String> getToolClasses(ItemStack stack) {
        return Collections.singleton("pickaxe");
    }
}
