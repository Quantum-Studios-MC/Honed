package quantumstudios.honed.data.trait;

import java.util.List;
import java.util.Map;

public class TraitDefinition {
    public String id;
    public String displayName;
    public String description;
    public String effectType;          // "SPEED_BONUS", "BLEED_ON_HIT", "DURABILITY_MULT", etc.
    public Map<String, Float> params;  // effect parameters, meaning depends on effectType
    public List<String> synergies;     // traits that work well with this one
    public List<String> conflicts;     // traits incompatible with this one
    public float synergyMultiplier = 1.25f;   // multiplier when synergies are present
    public float conflictMultiplier = 0.5f;   // multiplier when conflicts are present
}
 