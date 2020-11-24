package com.sim;

import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;

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
        int population = simManager_.getSimSettings().getTotalPopulation();
        people_ = new ArrayList<>(population);
        
        for (int i = 0; i < population; i++)
        {
            // Get the bounds in which the person can travel between.
            Canvas canvas = getSimManager().getCanvas();
            double offset = getSimSettings().getPersonRadius();
            Point2D minXY = new Point2D(offset, offset);
            Point2D maxXY = new Point2D(canvas.getWidth() - offset, canvas.getHeight() - offset);
    
            // Generate positions
            Point2D startingPosition = Utils.getRandomPoint2D(minXY, maxXY);
            Point2D targetPosition = Utils.getRandomPoint2D(minXY, maxXY);
    
            // Generate new speed.
            double minSpeed = getSimSettings().getMinMoveSpeed();
            double maxSpeed = getSimSettings().getMaxMoveSpeed();
            double speed = Utils.getRandomRange(minSpeed, maxSpeed);
            
            HealthState state = i < getSimSettings().getStartingInfected() - 1
                ? HealthState.ASYMPTOMATIC_INCUBATING : HealthState.HEALTHY;
            
            Person person = new Person(this, startingPosition, targetPosition, speed, state);
            people_.add(person);
        }
    }
    
    /**
     * Updates the states and positions of all {@code Person} objects associated with this
     * {@code Population Manager}.
     */
    public void updateAll()
    {
        // Update States & Positions
        for (Person person : people_)
        {
            updateState(person);
            
            if (person.getState() != HealthState.SELF_ISOLATING
                && person.getState() != HealthState.DECEASED)
            {
                person.move();
            }
        }
        
        //TODO: Try Infection
        
    }
    
    private void updateState(Person person)
    {
        HealthState nextState;
        HealthState currentState = person.getState();
        Disease disease = getSimSettings().getDisease();
        
        if (currentState == HealthState.ASYMPTOMATIC_INCUBATING
            && person.getStateTimer() >= disease.getIncubationDays())
        {
            /*
            If the random number is less than or equal to the asymptomatic rate, then the
            person's state becomes the extended asymptomatic state.
             */
            if (Utils.RANDOM.nextFloat() <= disease.getAsymptomaticRate())
            {
                nextState = HealthState.ASYMPTOMATIC_EXTENDED;
            }
            /*
            ... else, if the simulation has self-isolation activated, the state becomes the
            self-isolation state, preventing the person from spreading the disease.
             */
            else if (getSimSettings().isSelfIsolationActive())
            {
                nextState = HealthState.SELF_ISOLATING;
            }
            /*
            ... else, the person displays symptoms and can still move around and spread the
            disease.
             */
            else
            {
                nextState = HealthState.SYMPTOMATIC;
            }
        
            person.setState(nextState);
            person.setStateTimer(0);
        }
        /*
        ... else, if the infected person is in symptomatic phase (even if they aren't actually
        showing any symptoms)...
         */
        else if ((currentState == HealthState.ASYMPTOMATIC_EXTENDED
            || currentState == HealthState.SYMPTOMATIC
            || currentState == HealthState.SELF_ISOLATING)
            && person.getStateTimer() >= disease.getInfectionDays())
        {
            if (Utils.RANDOM.nextFloat() <= disease.getDeathRate())
            {
                nextState = HealthState.DECEASED;
            }
            else
            {
                nextState = HealthState.CURED;
            }
        
            person.setState(nextState);
            person.setStateTimer(0);
        }
        else
        {
            person.incrementStateTimer();
        }
    }
}