package quantumstudios.honed.registry;

import quantumstudios.honed.data.forging.ForgingRecipe;
import quantumstudios.honed.data.material.MaterialDefinition;
import quantumstudios.honed.data.part.PartSchema;
import quantumstudios.honed.data.trait.TraitDefinition;
import quantumstudios.honed.data.tool.ToolSchema;
import quantumstudios.honed.data.component.ComponentType;
import quantumstudios.honed.Honed;
import java.util.HashMap;
import java.util.Map;

public final class HonedRegistries {
    public static final Map<String, MaterialDefinition> MATERIALS  = new HashMap<>();
    public static final Map<String, TraitDefinition>    TRAITS     = new HashMap<>();
    public static final Map<String, PartSchema>         PART_SCHEMAS    = new HashMap<>();
    public static final Map<String, ToolSchema>         TOOL_SCHEMAS    = new HashMap<>();
    public static final Map<String, ForgingRecipe>      FORGING    = new HashMap<>();
    public static final Map<String, ComponentType>      COMPONENT_TYPES = new HashMap<>();

    public static MaterialDefinition getMaterial(String id) {
        MaterialDefinition mat = MATERIALS.getOrDefault(id, MATERIALS.get("honed:fallback"));
        if (mat == null) {
            Honed.LOGGER.warn("Unknown material ID: {}", id);
            return MaterialDefinition.EMPTY;
        }
        return mat;
    }

    public static TraitDefinition getTrait(String id) {
        return TRAITS.get(id);
    }

    public static PartSchema getPartSchema(String partType) {
        return PART_SCHEMAS.get(partType);
    }

    public static ToolSchema getToolSchema(String id) {
        return TOOL_SCHEMAS.get(id);
    }

    public static ForgingRecipe getForgingRecipe(String id) {
        return FORGING.get(id);
    }

    public static ComponentType getComponentType(String id) {
        return COMPONENT_TYPES.get(id);
    }

    private HonedRegistries() {}
}
