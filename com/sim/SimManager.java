package com.sim;

import java.util.List;
import javafx.geometry.Point2D;
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
    
    public boolean isNewDay()
    {
        return getFrameCount() % simSettings_.getDayLength() == 0;
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
            
            gc_.setFill(Color.WHITE);
            gc_.fillRect(0.0, 0.0, canvas_.getWidth(), canvas_.getHeight());
    
            for (Person person : people)
            {
                Point2D position = person.getMovement().getCurrentLocation();
                
                // Debug draw that displays the area in which the disease can spread over.
                if (simSettings_.isDebugActive() && person.getState().getHealthStatus().canInfect())
                {
                    drawSpread(position);
                }
                
                drawPerson(position, person.getState().getHealthStatus());
                
                // Debug draw that marks the exact center position of the person.
                if (simSettings_.isDebugActive())
                {
                    drawPoint(position);
                }
            }
    
            // TODO
        }
    }
    
    private void drawPerson(Point2D position, HealthStatus healthStatus)
    {
        double radius = simSettings_.getPersonRadius();
        drawCircle(position, radius, healthStatus.getFillColor(), healthStatus.getStrokeColor(), 1.0);
    }
    
    private void drawSpread(Point2D position)
    {
        double radius = simSettings_.getDisease().getSpreadDistance();
        double r = Color.INDIANRED.getRed();
        double g = Color.INDIANRED.getGreen();
        double b = Color.INDIANRED.getBlue();
        drawCircle(position, radius, new Color(r, g, b, 0.4), null, 0.0);
    }
    
    private void drawPoint(Point2D position)
    {
        drawCircle(position, 2, Color.BLACK, null, 0.0);
    }
    
    private void drawCircle(Point2D center, double radius, Color fillColor, Color strokeColor,
        double strokeWidth)
    {
        double diameter = radius * 2;
        double x = center.getX() - radius;
        double y = center.getY() - radius;
        
        if (fillColor != null)
        {
            gc_.setFill(fillColor);
            gc_.fillOval(x, y, diameter, diameter);
        }
        
        if (strokeColor != null && strokeWidth > 0)
        {
            gc_.setStroke(strokeColor);
            gc_.setLineWidth(strokeWidth);
            gc_.strokeOval(x, y, diameter, diameter);
        }
    }
}