import javafx.scene.paint.Color;

public enum HealthState
{
    //----- ENUMS -----
    HEALTHY(Color.BLACK, Color.GREEN),
    ASYMPTOMATIC(Color.BLACK, Color.YELLOW),
    SYMPTOMATIC(Color.BLACK, Color.RED),
    SELF_ISOLATING(Color.BLACK, Color.LIGHTPINK),
    CURED(Color.BLACK, Color.BLUE),
    DECEASED(Color.BLACK, Color.DARKGRAY);
    
    //----- FIELDS -----
    private final Color outlineColor;
    private final Color fillColor;
    
    //----- CONSTRUCTORS -----
    private HealthState(Color outlineColor, Color fillColor)
    {
        this.outlineColor = outlineColor;
        this.fillColor = fillColor;
    }
    
    private HealthState(HealthState state)
    {
        this(state.getOutlineColor(), state.getFillColor());
    }
    
    //----- METHODS -----
    // Get & Set
    public Color getOutlineColor()
    {
        return outlineColor;
    }
    
    public Color getFillColor()
    {
        return fillColor;
    }
}