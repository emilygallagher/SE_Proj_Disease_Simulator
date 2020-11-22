package com.sim;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

/**
 *
 *
 * @author Emily Gallagher
 * @version %I%
 * @since 1.0
 */
public class SimManager
{
    //----- FIELDS -----
    // CONSTANTS
    /** The length of a single simulation day in terms of frames. */
    public static final int DAY_LENGTH = 90;
    
    // INSTANCE
    /**
     * Is the simulation running in debug mode? Should be permanently set to false for public
     * release.
     */
    private boolean isDebugActive_;
    
    /**
     * The maximum number of days to simulate. The simulation should automatically stop after the
     * day count surpasses this number.
     */
    private int maxDays_;
    
    /** Whether or not the simulation is currently running. */
    private boolean isRunning_;
    
    /** The number of frames that have passed since the simulation started. */
    private int frameCount_;
    
    /** The disease currently being simulated. */
    private Disease disease_;
    
    /** The cumulative modifier of all activated precautionary measures. */
    private double precautionsModifier_;
    
    /** The JavaFX {@code Canvas} to render to. */
    private Canvas canvas_;
    
    /** Manager object for the entire simulation population. */
    private PopulationManager populationManager_;
    
    //----- CONSTRUCTORS -----
    
    public SimManager()
    {
        this(null);
    }
    
    public SimManager(Disease disease)
    {
        disease_ = disease;
    }
    
    //----- METHODS -----
    // Get & Set
    public boolean isDebugActive()
    {
        return isDebugActive_;
    }
    
    public void setDebugMode(boolean isDebugActive)
    {
        isDebugActive_ = isDebugActive;
    }
    
    public int getMaxDays()
    {
        return maxDays_;
    }
    
    public void setMaxDays(int days)
    {
        maxDays_ = days;
    }
    
    public boolean isRunning()
    {
        return isRunning_;
    }
    
    public int getFrameCount()
    {
        return frameCount_;
    }
    
    public int getCurrentDay()
    {
        return frameCount_ / DAY_LENGTH;
    }
    
    public Disease getDisease()
    {
        return disease_;
    }
    
    public void setDisease(Disease disease)
    {
        disease_ = disease;
    }
    
    public double getPrecautionsModifier()
    {
        return precautionsModifier_;
    }
    
    public void setPrecautionsModifier(double modifier)
    {
        precautionsModifier_ = modifier;
    }
    
    public Canvas getCanvas()
    {
        return canvas_;
    }
    
    public void setCanvas(Canvas canvas)
    {
        canvas_ = canvas;
    }
    
    public GraphicsContext getGC()
    {
        return canvas_.getGraphicsContext2D();
    }
    
    public double getMaxX()
    {
        return canvas_.getWidth();
    }
    
    public double getMaxY()
    {
        return canvas_.getHeight();
    }
    
    public PopulationManager getPopulationManager()
    {
        return populationManager_;
    }
    
    // Playback Control
    public void startSimulation()
    {
        // check if all required settings are filled.
        if (!isRunning_ )
        {
            // generate population
        }
        
    }
    
    public void pauseSimulation()
    {
        if (isRunning())
        {
        
        }
    }
    
    public void resetSimulation()
    {
    
    }
    
    public void update(double dt)
    {
        if (getCurrentDay() < maxDays_ || maxDays_ < 0)
        {
        
        }
    }
    
    // Draw
    private void draw()
    {
        if (isDebugActive_)
        {
            debugDraw();
        }
        
        
    }
    
    private void debugDraw()
    {
    
    }
}