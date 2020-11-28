package com.sim;

public class State
{
    //----- FIELDS -----
    private HealthStatus healthStatus_;
    private long dayCounter_;
    private boolean checkInfection_;
    
    //----- CONSTRUCTORS -----
    public State()
    {
        this(HealthStatus.HEALTHY);
    }
    
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
                    // TODO: Infection chance
                    //if ()
                    {
                        setHealthStatus(HealthStatus.INCUBATING);
                        setDayCounter(0L);
                    }
        
                    checkInfection_ = false;
                }
                break;
            case INCUBATING:
                if (dayCounter_ >= disease.getIncubationDays())
                {
                    HealthStatus healthStatus =
                        rand <= disease.getAsymptomaticRate() - totalModifier ? HealthStatus.ASYMPTOMATIC
                        : isSelfIsolationActive ? HealthStatus.SELF_ISOLATING
                        : HealthStatus.SYMPTOMATIC;
                    setHealthStatus(healthStatus);
                    setDayCounter(0L);
                }
                break;
            case ASYMPTOMATIC:
            case SYMPTOMATIC:
            case SELF_ISOLATING:
                if (dayCounter_ >= disease.getInfectionDays())
                {
                    HealthStatus healthStatus =
                        rand <= disease.getDeathRate() - totalModifier ? HealthStatus.DEAD
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