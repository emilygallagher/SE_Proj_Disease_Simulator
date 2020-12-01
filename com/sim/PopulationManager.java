package com.sim;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;

import static java.util.Collections.unmodifiableList;

public class PopulationManager
{
    //----- FIELDS -----
    private final SimManager simManager_;
    
    private List<Person> people_;
    
    private Point2D minXY_;
    
    private Point2D maxXY_;
    
    private double minSpeed_;
    
    private double maxSpeed_;
    
    //----- CONSTRUCTORS -----
    PopulationManager(SimManager simManager)
    {
        simManager_ = simManager;
        people_ = null;
    }
    
    //----- METHODS -----
    // Get
    public List<Person> getPeopleUnmodifiable()
    {
        return unmodifiableList(people_);
    }
    
    public SimManager getSimManager()
    {
        return simManager_;
    }
    
    SimSettings getSimSettings()
    {
        return simManager_.getSimSettings();
    }
    
    Point2D getMinXY()
    {
        double radius = getSimSettings().getPersonRadius();
        
        return new Point2D(radius, radius);
    }
    
    Point2D getMaxXY()
    {
        Canvas canvas = simManager_.getCanvas();
        double radius = getSimSettings().getPersonRadius();
        
        return new Point2D(canvas.getWidth() - radius, canvas.getHeight() - radius);
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
        
        for (int i = 0; i < totalPopulation; i++)
        {
            HealthStatus healthStatus = i < startingInfected
                ? HealthStatus.INCUBATING
                : HealthStatus.HEALTHY;
            Person person = new Person();
            Point2D minXY = getMinXY();
            Point2D maxXY = getMaxXY();
            
            person.getMovement().setCurrentLocation(Utils.getRandomPoint2D(minXY, maxXY));
            person.getMovement().randomizeTarget(minXY, maxXY, getMinSpeed(), getMaxSpeed());
            person.getState().setHealthStatus(healthStatus);
            
            people_.add(person);
        }
    }
    
    public void updateAll()
    {
        Disease disease = getSimSettings().getDisease();
        double modifier = getSimSettings().getTotalModifier();
        boolean isSelfIsolationActive = getSimSettings().isSelfIsolationActive();
        
        for (Person person : people_)
        {
            if (getSimManager().isNewDay())
            {
                person.getState().update(disease, modifier, isSelfIsolationActive);
            }
            
            if (person.getState().getHealthStatus().canMove())
            {
                person.getMovement().move(getMinXY(), getMaxXY(), getMinSpeed(), getMaxSpeed());
            }
        }
        
        // TODO: Check new infection
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