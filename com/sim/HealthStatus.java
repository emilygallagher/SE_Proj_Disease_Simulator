package com.sim;

import javafx.scene.paint.Color;

/**
 * Contains the various states that can describe the health of a person in the simulation.
 *
 * @author Emily Gallagher
 * @version 1.3
 * @since 0.2
 */
public enum HealthStatus
{
    //----- ENUMS -----
    /** Status describing a {@code Person} who has not been infected with the {@code Disease}. */
    HEALTHY(false, false, true, new Color(0.0, 1.0, 0.0, 1.0), Color.BLACK),
    
    /**
     * Status describing a {@code Person} who was recently infected with the {@code Disease} and
     * is currently in the incubation period.
     */
    INCUBATING(true, true, true, new Color(1.0, 1.0, 0.0, 1.0), Color.BLACK),
    
    /**
     * Status describing a {@code Person} who is currently infected with the {@code Disease}, but
     * does not display symptoms.
     */
    ASYMPTOMATIC(true, true, true, new Color(1.0, 0.0, 1.0, 1.0), Color.BLACK),
    
    /**
     * Status describing a {@code Person} who is currently infected with the {@code Disease} and is
     * displaying symptoms.
     */
    SYMPTOMATIC(true, true, true, new Color(1.0, 0.0, 0.0, 1.0), Color.BLACK),
    
    /**
     * Status describing a {@code Person} who is currently infected with the {@code Disease}, but
     * is practicing self-isolation in order to prevent it from spreading to others.
     */
    SELF_ISOLATING(true, false, false, new Color(0.8, 0.8, 0.8, 1.0), new Color(0.6, 0.6, 0.6,
        1.0)),
    
    /**
     * Status describing a {@code Person} who was infected, but no longer has the {@code Disease}.
     */
    CURED(false, false, true, new Color(0.0, 0.0, 1.0, 1.0), Color.BLACK),
    
    /** Status describing a {@code Person} who has died because of the {@code Disease}. */
    DEAD(false, false, false, new Color(0.5, 0.5, 0.5, 0.5), new Color(0.25, 0.25, 0.25, 1.0));
    
    //----- FIELDS -----
    /** Is the {@code Person} infected while in this {@code HealthStatus}? */
    private boolean isInfected_;
    
    /** Can the {@code Person} infect others who pass them while in this {@code HealthStatus}? */
    private boolean canInfect_;
    
    /** Can the {@code Person} move while in this {@code HealthStatus}? */
    private boolean canMove_;
    
    /** The color used for a {@code Person}'s fill when they have this {@code HealthStatus}. */
    private Color fillColor_;
    
    /** The color used for a {@code Person}'s outline when they have this {@code HealthStatus}. */
    private Color strokeColor_;
    
    //----- CONSTRUCTORS -----
    /**
     * Sole Constructor:<br />
     * Creates a new {@code HealthStatus} with the specified parameters.
     *
     * @param isInfected Whether or not a {@code Person} with this {@code HealthStatus} is infected.
     * @param canInfect Whether or not a {@code Person} with this {@code HealthStatus} can infect
     *                  others who pass by them.
     * @param canMove Whether or not a {@code Person} with this {@code HealthStatus} can move.
     * @param fillColor The color used for a {@code Person}'s fill when they have this
     *                  {@code HealthStatus}.
     * @param strokeColor The color used for a {@code Person}'s outline when they have this
     *                    {@code HealthStatus}.
     */
    HealthStatus(boolean isInfected, boolean canInfect, boolean canMove, Color fillColor,
        Color strokeColor)
    {
        isInfected_ = isInfected;
        canInfect_ = canInfect;
        canMove_ = canMove;
        fillColor_ = fillColor;
        strokeColor_ = strokeColor;
    }
    
    //----- METHODS -----
    public boolean isInfected()
    {
        return isInfected_;
    }
    
    public boolean canInfect()
    {
        return canInfect_;
    }
    
    /**
     * Can a {code Person} with this {@code HealthStatus} by infected by an infected person?
     *
     * @return Whether or not a {@code Person} with this {@code HealthStatus} can be infected by
     *         another person.
     */
    public boolean canBeInfected()
    {
        return this == HealthStatus.HEALTHY;
    }
    
    public boolean canMove()
    {
        return canMove_;
    }
    
    public Color getFillColor()
    {
        return fillColor_;
    }
    
    public Color getStrokeColor()
    {
        return strokeColor_;
    }
}