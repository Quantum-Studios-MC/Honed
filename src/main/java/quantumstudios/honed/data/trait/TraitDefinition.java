package quantumstudios.honed.data.trait;

import java.util.Map;

public class TraitDefinition {
    public String id;
    public String displayName;
    public String description;
    public String effectType;          // "SPEED_BONUS", "BLEED_ON_HIT", "DURABILITY_MULT", etc.
    public Map<String, Float> params;  // effect parameters, meaning depends on effectType
}
 