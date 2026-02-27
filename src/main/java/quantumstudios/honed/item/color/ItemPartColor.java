package quantumstudios.honed.item.color;

import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.ItemStack;

public class ItemPartColor implements IItemColor {
    @Override
    public int colorMultiplier(ItemStack stack, int tintIndex) {
        return -1;
    }
}
