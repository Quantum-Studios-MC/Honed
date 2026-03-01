package quantumstudios.honed.data.part;

import java.util.Map;

public class PartSchema {
    public String toolType;           // "pickaxe", "sword", "shovel", "axe"
    public Map<String, SlotDef> partSlots;

    public static class SlotDef {
        public float statWeight = 1.0f;
        public boolean isPrimary = false;
        
        public String getPartType(String slotName) {
            return slotName;
        }
        
        public boolean getPrimary() {
            return isPrimary;
        }
    }
}
