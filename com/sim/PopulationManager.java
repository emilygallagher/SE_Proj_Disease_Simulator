package com.sim;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;

import static java.util.Collections.unmodifiableList;

/**
 * A manager object that tracks and controls all {@code Person} objects that are a part of the
 * simulation.
 *
 * @author Emily Gallagher
 * @version 1.2
 * @since 0.2
 */
public class PopulationManager
{
    //----- FIELDS -----
    /** The {@code SimManager} that tracks and controls the entire simulation. */
    private final SimManager simManager_;
    
    /** Contains all of the {@code Person} objects that are a part of this simulation. */
    private List<Person> people_;
    
    /** The minimum x and y locations a {@code Person} can travel to. */
    private Point2D minXY_;
    
    /** The maximum x and y locations a {@code Person} can travel to. */
    private Point2D maxXY_;
    
    /** The minimum speed at which a {@code Person} can move at. */
    private double minSpeed_;
    
    /** The maximum speed at which a {@code Person} can move at. */
    private double maxSpeed_;
    
    //----- CONSTRUCTORS -----/
    
    /**
     * Sole Constructor:<br />
     * Creates a new {@code PopulationManager} that is associated with the specified
     * {@code SimManager} object.
     *
     * @param simManager The {@code SimManager} that manages the simulation.
     */
    PopulationManager(SimManager simManager)
    {
        simManager_ = simManager;
        people_ = null;
    }
    
    //----- METHODS -----
    // Get
    
    /**
     * Returns an unmodifiable view of the {@code List} containing all of the {@code Person}
     * objects associated with the simulation.
     *
     * @return An unmodifiable {@code List} view of all associated {@code Person} objects.
     */
    public List<Person> getPeopleUnmodifiable()
    {
        return unmodifiableList(people_);
    }
    
    public SimManager getSimManager()
    {
        return simManager_;
    }
    
    /**
     * Returns the {@code SimSettings} associated with the {@code SimManager}.
     *
     * @return The {@code SimManager}'s {@code SimSettings} object.
     */
    SimSettings getSimSettings()
    {
        return simManager_.getSimSettings();
    }
    
    Point2D getMinXY()
    {
        return minXY_;
    }
    
    Point2D getMaxXY()
    {
        return maxXY_;
    }
    
    double getMinSpeed()
    {
        return getSimSettings().getMinMoveSpeed();
    }
    
    double getMaxSpeed()
    {
        return getSimSettings().getMaxMoveSpeed();
    }
    
    // Initialization
    public boolean isInitialized()
    {
        return people_ != null;
    }
    
    public void initialize()
    {
        int totalPopulation = getSimSettings().getTotalPopulation();
        int startingInfected = getSimSettings().getStartingInfected();
        
        people_ = new ArrayList<>(totalPopulation);
        
        Canvas canvas = simManager_.getCanvas();
        double radius = getSimSettings().getPersonRadius();
        minXY_ = new Point2D(radius, radius);
        maxXY_ = new Point2D(canvas.getWidth() - radius, canvas.getHeight() - radius);
        
        for (int i = 0; i < totalPopulation; i++)
        {
            HealthStatus healthStatus = i < startingInfected
                ? HealthStatus.INCUBATING
                : HealthStatus.HEALTHY;
            Person person = new Person();
            
            person.getMovement().setCurrentLocation(Utils.getRandomPoint2D(minXY_, maxXY_));
            person.getMovement().randomizeTarget(minXY_, maxXY_, getMinSpeed(), getMaxSpeed());
            person.getState().setHealthStatus(healthStatus);
            
            people_.add(person);
        }
    }
    
    /** Updates all of the {@code Person} objects contained in the {@code people_ List}. */
    public void updateAll()
    {
        Disease disease = getSimSettings().getDisease();
        double modifier = getSimSettings().getTotalModifier();
        boolean isSelfIsolationActive = getSimSettings().isSelfIsolationActive();
        
        for (Person person : people_)
        {
            // Update States on each new day.
            if (getSimManager().isNewDay())
            {
                person.getState().update(disease, modifier, isSelfIsolationActive);
            }
            
            // Update the Person's location if they are able to move.
            if (person.getState().getHealthStatus().canMove())
            {
                person.getMovement().move(getMinXY(), getMaxXY(), getMinSpeed(), getMaxSpeed());
            }
        }
        
        /*
        Check new infection:
        If a healthy person passes a contagious person, flag them to be checked for infection at
        the start of the new day.
         */
        for (int i = 0; i < people_.size() - 1; i++)
        {
            Person personI = people_.get(i);
            
            for (int j = i + 1; j < people_.size(); j++)
            {
                Person personJ = people_.get(j);
                tryInfection(personI, personJ);
                tryInfection(personJ, personI);
            }
        }
    }
    
    /**
     * Check if the {@code nonInfected Person} can be flagged for possible infection and flag
     * them if true.
     *
     * @param infected The infected {@code Person}.
     * @param nonInfected The non-infected {@code Person}.
     */
    private void tryInfection(Person infected, Person nonInfected)
    {
        Point2D infectedLocation = infected.getMovement().getCurrentLocation();
        Point2D nonInfectedLocation = infected.getMovement().getCurrentLocation();
        double peopleDistance = infectedLocation.distance(nonInfectedLocation);
        double spreadDistance = getSimSettings().getDisease().getSpreadDistance();
        HealthStatus infectedStatus = infected.getState().getHealthStatus();
        State nonInfectedState = nonInfected.getState();
    
        if (peopleDistance <= spreadDistance
            && infectedStatus.canInfect()
            && nonInfectedState.getHealthStatus().canBeInfected())
        {
            nonInfectedState.setCheckInfection(true);
        }
    }
    
    // Status Counts
    
    /**
     * Returns a formatted {@code String} with the counts of how many {@code Person} objects
     * currently have each of the different {@code HealthStatus}.
     *
     * @return A formatted {@code String} of the results from {@code statusCountsArray()}.
     * @see #statusCountsArray()
     */
    public String statusCountsFormatted()
    {
        int[] counts = statusCountsArray();
        String str = "\n";
        
        HealthStatus[] statuses = HealthStatus.values();
        
        for (int i = 0; i < counts.length; i++)
        {
            str += String.format("%1$-15s:%2$4d\n", statuses[i], counts[i]);
        }
        
        return str;
    }
    
    /**
     * Returns an array with number of {@code Person} objects with each of the different
     * {@code HealthStatus}es. The index of each {@code HealthStatus}' result is equivalent to
     * their ordinal value.
     *
     * @return The number of times each {@code HealthStatus} currently appears in the simulation.
     */
    public int[] statusCountsArray()
    {
        int len = HealthStatus.values().length;
        int[] counts = new int[len];
        
        for (Person person : people_)
        {
            int ordinal = person.getState().getHealthStatus().ordinal();
            
            counts[ordinal]++;
        }
        
        return counts;
    }
}