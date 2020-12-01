package com.sim;

import javafx.scene.paint.Color;

public enum HealthStatus
{
    //----- ENUMS -----
    HEALTHY(false, false, true, new Color(0.0, 1.0, 0.0, 1.0), Color.BLACK),
    
    INCUBATING(true, true, true, new Color(1.0, 1.0, 0.0, 1.0), Color.BLACK),
    
    ASYMPTOMATIC(true, true, true, new Color(1.0, 0.0, 1.0, 1.0), Color.BLACK),
    
    SYMPTOMATIC(true, true, true, new Color(1.0, 0.0, 0.0, 1.0), Color.BLACK),
    
    SELF_ISOLATING(true, false, false, new Color(0.8, 0.8, 0.8, 1.0), new Color(0.6, 0.6, 0.6,
        1.0)),
    
    CURED(false, false, true, new Color(0.0, 0.0, 1.0, 1.0), Color.BLACK),
    
    DEAD(false, false, false, new Color(0.5, 0.5, 0.5, 0.5), new Color(0.25, 0.25, 0.25, 1.0));
    
    //----- FIELDS -----
    private boolean isInfected_;
    private boolean canInfect_;
    private boolean canMove_;
    private Color fillColor_;
    private Color strokeColor_;
    
    //----- CONSTRUCTORS -----
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