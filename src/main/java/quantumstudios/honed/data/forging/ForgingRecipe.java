package quantumstudios.honed.data.forging;

public class ForgingRecipe {
    public String id;
    public String inputItem;         // ore name or minecraft item ID
    public String materialId;        // material output
    public String outputPartType;    // "head", "binding", etc.
    public int tier;
    public String[] rules;           // e.g. ["hit_any", "draw_not_last", "punch_last"]
    public int optimalSteps;         // for quality bonus calculation (from TFC forging bonus idea)
    
    public int[] getActionPattern() {
        return new int[optimalSteps];
    }
    
    public static int[] defaultPatternFor(String type) {
        return new int[]{0, 0, 0, 0, 0};
    }
}
