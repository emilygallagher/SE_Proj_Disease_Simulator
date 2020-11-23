package com.sim;

import javafx.scene.paint.Color;

/**
 * Contains the various states that can describe the health of a person in the simulation.
 *
 * @author Emily Gallagher
 * @version 1.1
 * @since 0.2
 */
public enum HealthState
{
    //----- ENUMS -----
    HEALTHY(Color.GREEN, Color.BLACK),
    ASYMPTOMATIC(Color.YELLOW, Color.BLACK),
    SYMPTOMATIC(Color.RED, Color.BLACK),
    SELF_ISOLATING(Color.LIGHTPINK, Color.BLACK),
    CURED(Color.BLUE, Color.BLACK),
    DECEASED(Color.DARKGRAY, Color.BLACK);
    
    //----- FIELDS -----
    /** The color of the outline given to a person currently in this state. */
    private final Color strokeColor_;
    
    /** The color of the person currently in this state. */
    private final Color fillColor_;
    
    //----- CONSTRUCTORS -----
    /**
     * Sole Constructor:<br />
     * Creates a new HealthState with the specified parameters.
     *
     * @param fillColor The color given to a person currently in this state.
     * @param strokeColor The color of the outline given to a person currently in this state.
     */
    HealthState(Color fillColor, Color strokeColor)
    {
        this.fillColor_ = fillColor;
        this.strokeColor_ = strokeColor;
    }
    
    //----- METHODS -----
    // Get & Set
    public Color getFillColor()
    {
        return fillColor_;
    }
    
    public Color getStrokeColor()
    {
        return strokeColor_;
    }
}