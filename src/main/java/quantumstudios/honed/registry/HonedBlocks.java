package quantumstudios.honed.registry;

import net.minecraft.block.Block;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.IForgeRegistry;
import quantumstudios.honed.block.BlockForgingAnvil;

public final class HonedBlocks {
    public static Block FORGING_ANVIL;

    public static void register(RegistryEvent.Register<Block> event) {
        IForgeRegistry<Block> registry = event.getRegistry();
        FORGING_ANVIL = new BlockForgingAnvil();
        FORGING_ANVIL.setRegistryName("honed", "forging_anvil");
        FORGING_ANVIL.setTranslationKey("honed.forging_anvil");
        registry.register(FORGING_ANVIL);
    }
}
