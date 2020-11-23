package com.sim;

import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Point2D;

import static java.util.Collections.unmodifiableList;

/**
 * A manager object that controls all of the people within the simulation.
 *
 * @author Emily Gallagher
 * @version 1.1
 * @since 0.2
 */
public class PopulationManager
{
    //----- FIELDS -----
    private SimManager simManager_;
    private List<Person> people_;
    
    //----- CONSTRUCTORS -----
    public PopulationManager(SimManager simManager)
    {
        simManager_ = simManager;
    }
    
    //----- METHODS -----
    // Get
    public SimManager getSimManager()
    {
        return simManager_;
    }
    
    public SimSettings getSimSettings()
    {
        return simManager_.getSimSettings();
    }
    
    public List<Person> getPeopleUnmodifiable()
    {
        return unmodifiableList(people_);
    }
    
    //
    public void initialize()
    {
        var population = simManager_.getSimSettings().getTotalPopulation();
        people_ = new ArrayList<>(population);
        
        for (int i = 0; i < population; i++)
        {
            // Get the bounds in which the person can travel between.
            var canvas = getSimManager().getCanvas();
            var offset = getSimSettings().getPersonRadius();
            var minXY = new Point2D(offset, offset);
            var maxXY = new Point2D(canvas.getWidth() - offset, canvas.getHeight() - offset);
    
            // Generate positions
            var startingPosition = Utils.getRandomPoint2D(minXY, maxXY);
            var targetPosition = Utils.getRandomPoint2D(minXY, maxXY);
    
            // Generate new speed.
            var minSpeed = getSimSettings().getMinMoveSpeed();
            var maxSpeed = getSimSettings().getMaxMoveSpeed();
            var speed = Utils.getRandomRange(minSpeed, maxSpeed);
            
            var state = i < getSimSettings().getStartingInfected() - 1
                ? HealthState.ASYMPTOMATIC : HealthState.HEALTHY;
            
            var person = new Person(this, startingPosition, targetPosition, speed, state);
            people_.add(person);
        }
    }
    
    public void updateAll()
    {
        for (Person person : people_)
        {
            person.move();
        }
        
        //TODO: Update states
        
        //TODO: Try infection
    }
}