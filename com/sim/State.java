package com.sim;

/**
 * Class to keep track of a {@code Person}'s current health and how long they have been in that
 * state.
 *
 * @author Emily Gallagher
 * @version 1.0
 * @since 0.6
 */
public class State
{
    //----- FIELDS -----
    /** The current status of the {@code Person}'s health. */
    private HealthStatus healthStatus_;
    
    /** The number of days this {@code healthStatus_} has had it's current value. */
    private long dayCounter_;
    
    /** Should this object be checked for infection during the next infection check (new day). */
    private boolean checkInfection_;
    
    //----- CONSTRUCTORS -----
    /**
     * Default Constructor:<br />
     * Creates a new {@code State} object with {@code healthStatus_} set to {@code HEALTHY} and
     * all other fields set to their default values.
     */
    public State()
    {
        this(HealthStatus.HEALTHY);
    }
    
    /**
     * Parameterized Constructor:<br />
     * Creates a new {@code State} object with {@code healthStatus_} set to the specified
     * {@code HealthStatus} value and all other fields set to their default values.
     *
     * @param healthStatus The starting {@code HealthStatus} for this {@code State}.
     */
    public State(HealthStatus healthStatus)
    {
        healthStatus_ = healthStatus;
        dayCounter_ = 0L;
        checkInfection_ = false;
    }
    
    //----- METHODS -----
    // Get & Set
    public HealthStatus getHealthStatus()
    {
        return healthStatus_;
    }
    
    public void setHealthStatus(HealthStatus healthStatus)
    {
        healthStatus_ = healthStatus;
    }
    
    public long getDayCounter()
    {
        return dayCounter_;
    }
    
    public void setDayCounter(long dayCount)
    {
        dayCounter_ = dayCount;
    }
    
    public void incrementDayCounter()
    {
        dayCounter_++;
    }
    
    public boolean getCheckInfection()
    {
        return checkInfection_;
    }
    
    public void setCheckInfection(boolean checkInfection)
    {
        checkInfection_ = checkInfection;
    }
    
    // Update
    
    /**
     * Updates this object based upon the simulation's currently selected {@code Disease},
     * cumulative infection rate modifier, and whether or not the self-isolation preventative
     * measure is active or not.
     *
     * @param disease
     * @param totalModifier
     * @param isSelfIsolationActive
     */
    public void update(Disease disease, double totalModifier, boolean isSelfIsolationActive)
    {
        double rand = Utils.RANDOM.nextDouble();
        
        incrementDayCounter();
        
        switch (getHealthStatus())
        {
            // If healthy, check if the disease is now incubating within the person.
            case HEALTHY:
                if (checkInfection_)
                {
                    if (rand <= disease.getInfectionRate() * totalModifier)
                    {
                        setHealthStatus(HealthStatus.INCUBATING);
                        setDayCounter(0L);
                    }
        
                    checkInfection_ = false;
                }
                break;
            /*
            If incubating the disease, find whether the State should change to be
            asymptomatic, symptomatic, or if they should self-isolate themselves.
             */
            case INCUBATING:
                if (dayCounter_ >= disease.getIncubationDays())
                {
                    HealthStatus healthStatus =
                        rand <= disease.getAsymptomaticRate() ? HealthStatus.ASYMPTOMATIC
                        : isSelfIsolationActive ? HealthStatus.SELF_ISOLATING
                        : HealthStatus.SYMPTOMATIC;
                    setHealthStatus(healthStatus);
                    setDayCounter(0L);
                }
                break;
            /*
            If the illness has reached its limit, find whether the State should change to Dead or
            Cured, based on the Disease's death rate.
             */
            case ASYMPTOMATIC:
            case SYMPTOMATIC:
            case SELF_ISOLATING:
                if (dayCounter_ >= disease.getInfectionDays())
                {
                    HealthStatus healthStatus =
                        rand <= disease.getDeathRate() ? HealthStatus.DEAD
                        : HealthStatus.CURED;
                    setHealthStatus(healthStatus);
                    setDayCounter(0L);
                }
                break;
            default:
                break;
        }
    }
}