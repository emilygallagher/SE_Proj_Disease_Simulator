package com.sim;

import javafx.geometry.Point2D;

import static com.sim.Utils.copy;

public class Movement
{
    //----- FIELDS -----
    private Point2D currentLocation_;
    private Point2D targetLocation_;
    private Point2D velocity_;
    
    //----- CONSTRUCTORS -----
    public Movement()
    {
        this(Point2D.ZERO, Point2D.ZERO, 0.0);
    }
    
    public Movement(Movement movement) throws NullPointerException
    {
        this(movement.currentLocation_, movement.currentLocation_, movement.getSpeed());
    }
    
    public Movement(Point2D startingLocation, Point2D targetLocation, double speed)
        throws NullPointerException
    {
        currentLocation_ = copy(startingLocation);
        targetLocation_ = copy(targetLocation);
        
        recalculateVelocity(speed);
    }
    
    //----- METHODS -----
    // Get & Set
    public Point2D getCurrentLocation()
    {
        return currentLocation_;
    }
    
    public void setCurrentLocation(double x, double y)
    {
        currentLocation_ = new Point2D(x, y);
        recalculateVelocity(getSpeed());
    }
    
    public void setCurrentLocation(Point2D location)
    {
        currentLocation_ = copy(location);
        recalculateVelocity(getSpeed());
    }
    
    public Point2D getTargetLocation()
    {
        return targetLocation_;
    }
    
    public void setTargetLocation(double x, double y)
    {
        targetLocation_ = new Point2D(x, y);
        recalculateVelocity(getSpeed());
    }
    
    public void setTargetLocation(Point2D location)
    {
        targetLocation_ = copy(location);
        recalculateVelocity(getSpeed());
    }
    
    public Point2D getVelocity()
    {
        return velocity_;
    }
    
    protected void setVelocity(double x, double y)
    {
        velocity_ = new Point2D(x, y);
    }
    
    protected void setVelocity(Point2D velocity)
    {
        velocity_ = copy(velocity);
    }
    
    protected void recalculateVelocity()
    {
        recalculateVelocity(getSpeed());
    }
    
    protected void recalculateVelocity(double speed)
    {
        Point2D direction = targetLocation_.subtract(currentLocation_).normalize();
        
        velocity_ = direction.multiply(speed);
    }
    
    public double getSpeed()
    {
        return velocity_.magnitude();
    }
    
    public void setSpeed(double speed)
    {
        Point2D direction = getDirection();
        
        setVelocity(direction.multiply(speed));
    }
    
    public Point2D getDirection()
    {
        return velocity_.normalize();
    }
    
    // Movement
    public void move(Point2D minXY, Point2D maxXY, double minSpeed,
        double maxSpeed)
    {
        double distance = currentLocation_.distance(targetLocation_);
        
        // If the moving object would reach or move past the target position...
        if (distance <= getSpeed())
        {
            currentLocation_ = targetLocation_;
            randomizeTarget(minXY, maxXY, minSpeed, maxSpeed);
        }
        else
        {
            currentLocation_ = currentLocation_.add(velocity_);
        }
    }
    
    void randomizeTarget(Point2D minXY, Point2D maxXY, double minSpeed,
        double maxSpeed)
    {
        double speed = Utils.getRandomRange(minSpeed, maxSpeed);
        Point2D randomTargetLocation = Utils.getRandomPoint2D(minXY, maxXY);
        
        setTargetLocation(randomTargetLocation);
        recalculateVelocity(speed);
    }
}
