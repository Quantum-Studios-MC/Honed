package quantumstudios.honed.registry;

import quantumstudios.honed.item.part.ItemHonedPart;
import quantumstudios.honed.item.tool.ItemHonedAxe;
import quantumstudios.honed.item.tool.ItemHonedPickaxe;
import quantumstudios.honed.item.tool.ItemHonedShovel;
import quantumstudios.honed.item.tool.ItemHonedSword;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.IForgeRegistry;

public final class HonedItems {
    public static Item PART;
    public static Item PICKAXE;
    public static Item SWORD;
    public static Item SHOVEL;
    public static Item AXE;

    public static void register(RegistryEvent.Register<Item> event) {
        IForgeRegistry<Item> registry = event.getRegistry();
        PART = new ItemHonedPart();
        PART.setRegistryName("honed", "part");
        PART.setTranslationKey("honed.part");
        registry.register(PART);
        PICKAXE = new ItemHonedPickaxe();
        PICKAXE.setRegistryName("honed", "pickaxe");
        PICKAXE.setTranslationKey("honed.pickaxe");
        registry.register(PICKAXE);
        SWORD = new ItemHonedSword();
        SWORD.setRegistryName("honed", "sword");
        SWORD.setTranslationKey("honed.sword");
        registry.register(SWORD);
        SHOVEL = new ItemHonedShovel();
        SHOVEL.setRegistryName("honed", "shovel");
        SHOVEL.setTranslationKey("honed.shovel");
        registry.register(SHOVEL);
        AXE = new ItemHonedAxe();
        AXE.setRegistryName("honed", "axe");
        AXE.setTranslationKey("honed.axe");
        registry.register(AXE);
    }
}
