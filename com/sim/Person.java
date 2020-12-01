package com.sim;

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
    /** Contains the {@code Person}'s current {@code Movement} parameters. */
    private Movement movement_;
    
    /** Contains the {@code Person}'s current {@code HealthStatus} and related fields. */
    private State state_;
    
    //----- CONSTRUCTORS -----
    /**
     * Default Constructor:<br />
     * Creates a new {@code Person} with all fields set to non-{@code null}, default values.
     */
    public Person()
    {
        movement_ = new Movement();
        state_ = new State();
    }
    
    /**
     * Partial Constructor:<br />
     * Creates a new {@code Person} with the specified {@code Movement} parameters and their
     * {@code HealthStatus} set to {@code HEALTHY}.
     *
     * @param movement The {@code Movement} settings for this {@code Person}.
     */
    public Person(Movement movement)
    {
        this(movement, HealthStatus.HEALTHY);
    }
    
    /**
     * Full Constructor:<br />
     * Creates a new {@code Person} with the specified {@code Movement} parameters
     * {@code HealthStatus}.
     *
     * @param movement The {@code Movement} settings for this {@code Person}.
     * @param healthStatus The starting {@code HealthStatus} for this {@code Person}.
     */
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