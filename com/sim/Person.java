package com.sim;

import com.sim.State;

/**
 * A representation of a person in the simulation.
 *
 * @author Emily Gallagher
 * @version 1.3
 * @since 0.2
 */
public class Person
{
    //----- FIELDS -----
    private Movement movement_;
    private State state_;
    
    //----- CONSTRUCTORS -----
    public Person()
    {
        movement_ = new Movement();
        state_ = new State();
    }
    
    public Person(Movement movement)
    {
        this(movement, HealthStatus.HEALTHY);
    }
    
    public Person(Movement movement, HealthStatus healthStatus)
    {
        movement_ = new Movement(movement);
        state_ = new State(healthStatus);
    }
    
    //----- METHODS -----
    public Movement getMovement()
    {
        return movement_;
    }
    
    public State getState()
    {
        return state_;
    }
}