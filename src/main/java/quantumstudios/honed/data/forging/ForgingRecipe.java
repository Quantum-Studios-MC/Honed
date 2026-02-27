package quantumstudios.honed.data.forging;

public class ForgingRecipe {
    public String id;
    public String inputMaterialId;   // or use an ore dict tag
    public String outputPartType;    // "head", "binding", etc.
    public int tier;
    public String[] rules;           // e.g. ["hit_any", "draw_not_last", "punch_last"]
    public int optimalSteps;         // for quality bonus calculation (from TFC forging bonus idea)
}
