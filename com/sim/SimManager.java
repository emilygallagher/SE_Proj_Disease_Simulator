package com.sim;

import java.util.List;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * A manager object that controls the logic of the simulation.
 *
 * @author Emily Gallagher
 * @version 1.0
 * @since 0.03
 */
public class SimManager
{
    //----- FIELDS -----
    // INSTANCE
    /** Object containing settings on how to set up and operate the simulation. */
    private SimSettings simSettings_;
    
    /** Manager object for the entire simulation population. */
    private PopulationManager populationManager_;
    
    /** The JavaFX {@code Canvas} to render to. */
    private Canvas canvas_;
    
    /** The JavaFX {@code GraphicsContext} associated with the referenced {@code Canvas}. */
    private GraphicsContext gc_;
    
    /** Whether or not the simulation is currently running. */
    private boolean isRunning_;
    
    /** The number of frames that have passed since the simulation started. */
    private long frameCount_;
    
    //----- CONSTRUCTORS -----
    public SimManager()
    {
        this(new SimSettings(), null);
    }
    
    public SimManager(SimSettings simSettings)
    {
        this(simSettings, null);
    }
    
    public SimManager(SimSettings simSettings, Canvas canvas)
    {
        simSettings_ = simSettings;
        setCanvas(canvas);
        
        populationManager_ = new PopulationManager(this);
        isRunning_ = false;
        frameCount_ = 0;
    }
    
    //----- METHODS -----
    // Get & Set
    public SimSettings getSimSettings()
    {
        return simSettings_;
    }
    
    public void setSimSettings(SimSettings simSettings)
    {
        simSettings_ = simSettings;
    }
    
    public PopulationManager getPopulationManager()
    {
        return populationManager_;
    }
    
    public Canvas getCanvas()
    {
        return canvas_;
    }
    
    public void setCanvas(Canvas canvas)
    {
        canvas_ = canvas;
        gc_ = canvas_ != null ? canvas_.getGraphicsContext2D() : null;
    }
    
    public GraphicsContext getGC()
    {
        return gc_;
    }
    
    public boolean isRunning()
    {
        return isRunning_;
    }
    
    public long getFrameCount()
    {
        return frameCount_;
    }
    
    public long getDay()
    {
        return frameCount_ / simSettings_.getDayLength();
    }
    
    // Playback Control
    public void startSimulation()
    {
        // TODO
    }
    
    public void pauseSimulation()
    {
        // TODO
    }
    
    public void resetSimulation()
    {
        // TODO
    }
    
    public void update()
    {
        // TODO
        frameCount_++;
        populationManager_.updateAll();
        draw();
    }
    
    // Draw
    private void draw()
    {
        if (canvas_ != null)
        {
            List<Person> people = populationManager_.getPeopleUnmodifiable();
            double radius = simSettings_.getPersonRadius();
            double diameter = radius * 2;
            
            gc_.setFill(Color.WHITE);
            gc_.fillRect(0.0, 0.0, canvas_.getWidth(), canvas_.getHeight());
            
            if (simSettings_.isDebugActive())
            {
                debugDraw(people);
            }
    
            for (Person person : people)
            {
                double x = person.getCurrentPosition().getX() - radius;
                double y = person.getCurrentPosition().getY() - radius;
                HealthState state = person.getState();
                
                gc_.setFill(state.getFillColor());
                gc_.setStroke(state.getStrokeColor());
                gc_.setLineWidth(1.0);
                gc_.fillOval(x, y, diameter, diameter);
                gc_.strokeOval(x, y, diameter, diameter);
                
                // Additional debug draw that marks the real position of the person.
                if (simSettings_.isDebugActive())
                {
                    gc_.setFill(Color.BLACK);
                    gc_.fillOval(person.getCurrentPosition().getX(),
                        person.getCurrentPosition().getY(), 2.0, 2.0);
                }
            }
    
            // TODO
        }
    }
    
    private void debugDraw(List<Person> people)
    {
        // TODO
    }
}