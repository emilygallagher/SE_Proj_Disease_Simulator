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
 * @version 1.1
 * @since 0.3
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
    
    /**
     * Default Constructor:<br />
     * Creates a new {@code SimManager} with default settings and no {@code Canvas} associated
     * with it.
     */
    public SimManager()
    {
        this(new SimSettings(), null);
    }
    
    /**
     * Partial Constructor:<br />
     * Creates a new {@code SimManager} with the specified {@code SimSettings} and no
     * {@code Canvas} associated with it.
     *
     * @param simSettings The {@code SimSettings} to use in the simulation.
     */
    public SimManager(SimSettings simSettings)
    {
        this(simSettings, null);
    }
    
    /**
     * Full Constructor:<br />
     * Creates a new {@code SimManager} with the specified {@code SimSettings} and {@code Canvas}
     * associated with it.
     *
     * @param simSettings The {@code SimSettings} to use in the simulation.
     * @param canvas The {@code Canvas} to draw onto.
     */
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
    public void newSimulation()
    {
        if (simSettings_ != null && populationManager_ != null && canvas_ != null)
        {
            populationManager_.initialize();
            isRunning_ = false;
            frameCount_ = 0;
        }
        
        draw();
    }
    
    public void startSimulation()
    {
        if (populationManager_.isInitialized())
        {
            isRunning_ = true;
        }
    }
    
    public void pauseSimulation()
    {
        isRunning_ = false;
    }
    
    /** Updates all aspects of the simulation by 1 frame. */
    public void update()
    {
        if (isRunning_)
        {
            frameCount_++;
            populationManager_.updateAll();
            draw();
            
            int maxDays = simSettings_.getMaxDays();
            if (maxDays > 0 && maxDays <= getDay())
            {
                isRunning_ = false;
            }
        }
    }
    
    // Draw
    /**
     * Draws all {@code Person} objects associated with the simulation and any debug drawings
     * based on various settings.
     */
    private void draw()
    {
        if (canvas_ != null)
        {
            List<Person> people = populationManager_.getPeopleUnmodifiable();
            
            gc_.setFill(Color.WHITE);
            gc_.fillRect(0.0, 0.0, canvas_.getWidth(), canvas_.getHeight());
    
            for (Person person : people)
            {
                Point2D location = person.getMovement().getCurrentLocation();
                
                // Debug draw that displays the area in which the disease can spread over.
                if (simSettings_.isDebugActive() && person.getState().getHealthStatus().canInfect())
                {
                    drawSpread(location);
                }
                
                drawPerson(location, person.getState().getHealthStatus());
                
                // Debug draw that marks the exact center location of the person.
                if (simSettings_.isDebugActive())
                {
                    drawPoint(location);
                }
            }
        }
    }
    
    /**
     * Draws a circle that represents a {@code Person} object, centered at the specified
     * location, and with the colors specified by the given {@code HealthStatus}.
     *
     * @param location The location of the center of the circle.
     * @param healthStatus The current {@code HealthStatus} of the {@code Person}.
     */
    private void drawPerson(Point2D location, HealthStatus healthStatus)
    {
        double radius = simSettings_.getPersonRadius();
        drawCircle(location, radius, healthStatus.getFillColor(), healthStatus.getStrokeColor(), 1.0);
    }
    
    /**
     * Draws a circle that represents the full area in which the disease can spread from its
     * current {@code Person} host to another.
     *
     * @param location The location of the center of the circle.
     */
    private void drawSpread(Point2D location)
    {
        double radius = simSettings_.getDisease().getSpreadDistance();
        double r = Color.INDIANRED.getRed();
        double g = Color.INDIANRED.getGreen();
        double b = Color.INDIANRED.getBlue();
        drawCircle(location, radius, new Color(r, g, b, 0.4), null, 0.0);
    }
    
    /**
     * Draws a small point at the specified location.
     *
     * @param location The location to draw the point.
     */
    private void drawPoint(Point2D location)
    {
        drawCircle(location, 2, Color.BLACK, null, 0.0);
    }
    
    /**
     * Draws a circle onto the {@code Canvas} using the specified values.
     *
     * @param center The location of the center of the circle.
     * @param radius The radius of the circle.
     * @param fillColor The color the circle should be filled with. If null, the circle will not
     *                  be filled.
     * @param strokeColor The color the circle's outline should be. If null, the circle will not
     *                    have an outline.
     * @param strokeWidth The width of the circle's outline. If 0, the circle will not have an
     *                    outline.
     */
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