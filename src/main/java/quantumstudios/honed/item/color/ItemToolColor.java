package quantumstudios.honed.item.color;

import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.ItemStack;

public class ItemToolColor implements IItemColor {
    @Override
    public int colorMultiplier(ItemStack stack, int tintIndex) {
        switch (tintIndex) {
            case 0: return 0xFFFF0000;
            case 1: return 0xFF00FF00;
            case 2: return 0xFF0000FF;
            default:return -1;
        }
    }
}
