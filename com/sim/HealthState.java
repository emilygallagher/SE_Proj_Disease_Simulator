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
    /** State for a healthy person that has not been infected with the disease yet. */
    HEALTHY(Color.GREEN, Color.BLACK),
    
    /**
     * State for a person that is infected with the disease and is currently in the incubation
     * phase of the infection.
     */
    ASYMPTOMATIC_INCUBATING(Color.YELLOW, Color.BLACK),
    
    /**
     * State for a person that is infected with the disease and is currently in the symptomatic
     * phase of the infection, but does not show symptoms.
     */
    ASYMPTOMATIC_EXTENDED(ASYMPTOMATIC_INCUBATING),
    
    /**
     * State for a person that is infected with the disease, is currently in the symptomatic phase
     * of the infection, and shows symptoms.
     */
    SYMPTOMATIC(Color.RED, Color.BLACK),
    
    /**
     * State for a person that is infected with the disease and is currently in the symptomatic
     * phase of the infection, but isolates themselves away from other to prevent the spread of
     * the disease.
     */
    SELF_ISOLATING(Color.LIGHTPINK, Color.BLACK),
    
    /**
     * State for a person who was infected with the disease, but has been cured and cannot be
     * infected again.
     */
    CURED(Color.BLUE, Color.BLACK),
    
    /** State for a person who died due to the effects of the disease. */
    DECEASED(Color.DARKGRAY, Color.BLACK);
    
    //----- FIELDS -----
    /** The color of the outline given to a person currently in this state. */
    private final Color strokeColor_;
    
    /** The color of the person currently in this state. */
    private final Color fillColor_;
    
    //----- CONSTRUCTORS -----
    
    /**
     * Copy Constructor:<br />
     * Creates a copy of the specified {@code HealthState}.
     *
     * @param state The {@code HealthState} to be copied.
     */
    HealthState(HealthState state)
    {
        this(state.fillColor_, state.strokeColor_);
    }
    
    /**
     * Sole Constructor:<br />
     * Creates a new {@code HealthState} with the specified parameters.
     *
     * @param fillColor The color given to a person currently in this {@code HealthState}.
     * @param strokeColor The color of the outline given to a person currently in this
     *                    {@code HealthState}.
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