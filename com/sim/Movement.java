package com.sim;

import javafx.geometry.Point2D;

import static com.sim.Utils.copy;

/**
 * Contains various fields and control methods associated with movement within the simulation.
 *
 * @author Emily Gallagher
 * @version 1.0
 * @since 0.6
 */
public class Movement
{
    //----- FIELDS -----
    /** The current location of the object. */
    private Point2D currentLocation_;
    
    /** The target location that the object is moving towards. */
    private Point2D targetLocation_;
    
    /** The current velocity (speed and direction) with which the object is moving. */
    private Point2D velocity_;
    
    //----- CONSTRUCTORS -----
    
    /**
     * Default Constructor:<br />
     * Creates a new, unmoving {@code Movement} object.
     */
    public Movement()
    {
        this(Point2D.ZERO, Point2D.ZERO, 0.0);
    }
    
    /**
     * Copy Constructor:<br />
     * Creates a copy of the specified {@code Movement} object.
     *
     * @param movement The {@code Movement} object to copy.
     * @throws NullPointerException Thrown if {@code movement} is {@code null}.
     */
    public Movement(Movement movement) throws NullPointerException
    {
        this(movement.currentLocation_, movement.currentLocation_, movement.getSpeed());
    }
    
    /**
     * Parameterized Constructor:<br />
     * Creates a new {@code Movement} object using the specified parameters.
     *
     * @param startingLocation The starting location of the object.
     * @param targetLocation The target location of the object.
     * @param speed The speed at which this object travels.
     * @throws NullPointerException If either {@code startingLocation} or {@code targetLocation}
     *                              are {@code null}.
     */
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
    
    /**
     * Recalculates the {@code velocity_} of this {@code Movement} object, using its current speed.
     */
    protected void recalculateVelocity()
    {
        recalculateVelocity(getSpeed());
    }
    
    /**
     * Recalculates the {@code velocity_} of this {@code Movement} object, using the specified
     * speed as its new speed.
     *
     * @param speed The new speed at which the object travels at.
     */
    protected void recalculateVelocity(double speed)
    {
        Point2D direction = targetLocation_.subtract(currentLocation_).normalize();
        
        velocity_ = direction.multiply(speed);
    }
    
    /**
     * Returns the speed at which this object is currently travelling at. Equivalent to the
     * magnitude of the velocity.
     *
     * @return The current speed.
     */
    public double getSpeed()
    {
        return velocity_.magnitude();
    }
    
    /**
     * Changes the speed at which the object travels at.
     *
     * @param speed The new speed of the object.
     */
    public void setSpeed(double speed)
    {
        Point2D direction = getDirection();
        
        setVelocity(direction.multiply(speed));
    }
    
    /**
     * Returns the direction in which the object is traveling in, with speed removed. Equivalent
     * to the normalized velocity.
     *
     * @return The direction the object is traveling in.
     */
    public Point2D getDirection()
    {
        return velocity_.normalize();
    }
    
    // Movement
    /**
     * Moves the object based on their current velocity and changes their target location if they
     * reach their current target location.
     *
     * @param minXY The minimum x and y values the object can travel to.
     * @param maxXY The maximum x and y values the object can travel to.
     * @param minSpeed The minimum speed at which the object can travel.
     * @param maxSpeed The maximum speed at which the object can travel.
     */
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
    
    /**
     * Randomizes the target location and speed at which this object is traveling.
     *
     * @param minXY The minimum x and y values the object can travel to.
     * @param maxXY The maximum x and y values the object can travel to.
     * @param minSpeed The minimum speed at which the object can travel.
     * @param maxSpeed The maximum speed at which the object can travel.
     */
    void randomizeTarget(Point2D minXY, Point2D maxXY, double minSpeed,
        double maxSpeed)
    {
        double speed = Utils.getRandomRange(minSpeed, maxSpeed);
        Point2D randomTargetLocation = Utils.getRandomPoint2D(minXY, maxXY);
        
        setTargetLocation(randomTargetLocation);
        recalculateVelocity(speed);
    }
}
