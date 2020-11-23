package com.sim;

import javafx.geometry.Point2D;

/**
 * A representation of a person used in the simulation.
 *
 * @author Emily Gallagher
 * @version 1.2
 * @since 0.2
 */
public class Person
{
    //----- FIELDS -----
    private final PopulationManager populationManager_;
    private Point2D currentPosition_;
    private Point2D targetPosition_;
    private Point2D velocity_;
    private HealthState state_;
    private int stateTimer_;
    
    //----- CONSTRUCTORS -----
    public Person(PopulationManager populationManager)
    {
        populationManager_ = populationManager;
        currentPosition_ = Point2D.ZERO;
        targetPosition_ = Point2D.ZERO;
        velocity_ = Point2D.ZERO;
        state_ = HealthState.HEALTHY;
        stateTimer_ = 0;
    }
    
    public Person(PopulationManager populationManager, Point2D startingPosition,
        Point2D targetPosition, double speed, HealthState startingState)
    {
        populationManager_ = populationManager;
        currentPosition_ = startingPosition;
        targetPosition_ = targetPosition;
        state_ = startingState;
        stateTimer_ = 0;
        
        recalculateVelocity(speed);
    }
    
    //----- METHODS -----
    public PopulationManager getPopulationManager()
    {
        return populationManager_;
    }
    
    public SimManager getSimManager()
    {
        return populationManager_ != null ? populationManager_.getSimManager() : null;
    }
    
    public SimSettings getSimSettings()
    {
        var simManager = getSimManager();
        return simManager != null ? simManager.getSimSettings() : null;
    }
    
    public Point2D getCurrentPosition()
    {
        return currentPosition_;
    }
    
    public void setCurrentPosition(double x, double y)
    {
        currentPosition_ = new Point2D(x, y);
    }
    
    public void setCurrentPosition(Point2D position)
    {
        currentPosition_ = new Point2D(position.getX(), position.getY());
    }
    
    public Point2D getTargetPosition()
    {
        return targetPosition_;
    }
    
    public void setTargetPosition(double x, double y)
    {
        targetPosition_ = new Point2D(x, y);
    }
    
    public void setTargetPosition(Point2D position)
    {
        targetPosition_ = new Point2D(position.getX(), position.getY());
    }
    
    public Point2D getVelocity()
    {
        return velocity_;
    }
    
    public void setVelocity(double x, double y)
    {
        velocity_ = new Point2D(x, y);
    }
    
    public void recalculateVelocity()
    {
        recalculateVelocity(getSpeed());
    }
    
    public void recalculateVelocity(double speed)
    {
        // Get the non-normalized directional vector,
        var velocity = targetPosition_.subtract(currentPosition_);
        
        // normalize (remove speed from) the direction vector,
        velocity = velocity.normalize();
        
        // and lastly, multiply in the desired speed to get the velocity vector.
        velocity = velocity.multiply(speed);
        
        setVelocity(velocity);
    }
    
    public void setVelocity(Point2D velocity)
    {
        velocity_ = new Point2D(velocity.getX(), velocity.getY());
    }
    
    public void setVelocity(Point2D direction, double speed)
    {
        velocity_ = direction.normalize().multiply(speed);
    }
    
    public Point2D getDirection()
    {
        return velocity_.normalize();
    }
    
    public double getSpeed()
    {
        return velocity_.magnitude();
    }
    
    public HealthState getState()
    {
        return state_;
    }
    
    public void setState(HealthState state)
    {
        state_ = state;
    }
    
    public int getStateTimer()
    {
        return stateTimer_;
    }
    
    public void setStateTimer(int frameCount)
    {
        stateTimer_ = frameCount;
    }
    
    public void incrementStateTimer()
    {
        stateTimer_++;
    }
    
    // Move
    public void move()
    {
        var distance = getCurrentPosition().distance(getTargetPosition());
        
        // If the person would reach or move past the target position this frame...
        if (distance <= getSpeed())
        {
            var canvas = getSimManager().getCanvas();
            var settings = getSimSettings();
            
            // Get the bounds in which the person can travel between.
            var offset = settings.getPersonRadius();
            var minXY = new Point2D(offset, offset);
            var maxXY = new Point2D(canvas.getWidth() - offset, canvas.getHeight() - offset);
            
            // Generate new speed.
            var minSpeed = settings.getMinMoveSpeed();
            var maxSpeed = settings.getMaxMoveSpeed();
            var speed = Utils.getRandomRange(minSpeed, maxSpeed);
            
            setCurrentPosition(getTargetPosition());
            
            /*
            New target position must be at least 50 pixels away from the person's current position.
             */
            do
            {
                setTargetPosition(Utils.getRandomPoint2D(minXY, maxXY));
            } while(currentPosition_.distance(targetPosition_) < 50);
            
            recalculateVelocity(speed);
        }
        else
        {
            setCurrentPosition(currentPosition_.add(velocity_));
        }
    }
}