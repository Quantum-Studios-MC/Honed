package quantumstudios.honed;

import quantumstudios.honed.Tags;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraftforge.event.RegistryEvent;
import quantumstudios.honed.registry.HonedItems;
import quantumstudios.honed.data.loader.JsonDataLoader;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.IForgeRegistry;

@Mod(modid = Tags.MOD_ID, name = Tags.MOD_NAME, version = Tags.VERSION)
@Mod.EventBusSubscriber(modid = Tags.MOD_ID)
public class Honed {

    public static final Logger LOGGER = LogManager.getLogger(Tags.MOD_NAME);

    /**
     * <a href="https://cleanroommc.com/wiki/forge-mod-development/event#overview">
     *     Take a look at how many FMLStateEvents you can listen to via the @Mod.EventHandler annotation here
     * </a>
     */
    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        LOGGER.info("Hello From {}!", Tags.MOD_NAME);
        JsonDataLoader.setConfigPath(event.getModConfigurationDirectory().toPath().resolve("honed"));
        JsonDataLoader.load();
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        HonedItems.register(event);
    }
}
