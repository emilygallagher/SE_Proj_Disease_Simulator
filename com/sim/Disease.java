package com.sim;

/**
 * Class that contains the attributes of a real-life or custom disease to be used when infecting
 * people in the simulation.
 *
 * @author Emily Gallagher
 * @version 1.0
 * @since 0.2
 */
public class Disease
{
    //----- FIELDS -----
    /** The name of the disease. */
    private String name_;
    
    /** The maximum distance a disease can spread from one person to another in pixels. */
    private double spreadDistance_;
    
    /** How likely a disease is going to spread from one person to another. */
    private float infectionRate_;
    
    /** How likely a disease will not show symptoms during the infected period. */
    private float asymptomaticRate_;
    
    /** How likely a disease is to kill someone. */
    private float deathRate_;
    
    /** The number of days the disease remains in someone's body before showing symptoms. */
    private int incubationDays_;
    
    /** The number of days the disease causes symptoms to show after the incubation period ends. */
    private int infectionDays_;
    
    //----- CONSTRUCTORS -----
    
    /**
     * Default Constructor:<br />
     * Creates a new Disease with default values (0) and the name "New Disease".
     */
    public Disease()
    {
        this("New Disease", 0.0, 0.0f, 0.0f, 0.0f, 0, 0);
    }
    
    /**
     * Copy Constructor: <br />
     * Creates a deep copy of the specified Disease object.
     *
     * @param disease The disease to copy.
     */
    public Disease(Disease disease)
    {
        this(disease.name_, disease.spreadDistance_, disease.infectionRate_,
            disease.asymptomaticRate_, disease.deathRate_, disease.incubationDays_,
            disease.infectionDays_);
    }
    
    /**
     * Parameterized Constructor:<br />
     * Creates a new Disease with the specified parameters.
     *
     * @param name The name of the disease.
     * @param spreadDistance The maximum distance a disease can spread from one person to another
     *                       in pixels.
     * @param infectionRate How likely a disease is going to spread from one person to another.
     * @param asymptomaticRate How likely a disease will not show symptoms during the infected
     *                         period.
     * @param deathRate How likely a disease will be the cause of death for a person.
     * @param incubationDays The number of days the disease remains in someone's body before
     *                       showing symptoms.
     * @param infectionDays The number of days the disease causes symptoms to show after the
     *                      incubation period ends.
     */
    public Disease(String name, double spreadDistance, float infectionRate, float asymptomaticRate,
        float deathRate, int incubationDays, int infectionDays)
    {
        name_ = name;
        spreadDistance_ = spreadDistance;
        infectionRate_ = infectionRate;
        asymptomaticRate_ = asymptomaticRate;
        deathRate_ = deathRate;
        incubationDays_ = incubationDays;
        infectionDays_ = infectionDays;
    }
    
    //----- METHODS -----
    // Get & Set
    public String getName()
    {
        return name_;
    }
    
    public void setName(String name)
    {
        this.name_ = name;
    }
    
    public double getSpreadDistance()
    {
        return spreadDistance_;
    }
    
    public void setSpreadDistance(double distance)
    {
        spreadDistance_ = distance;
    }
    
    public float getInfectionRate()
    {
        return infectionRate_;
    }
    
    public void setInfectionRate(float rate)
    {
        infectionRate_ = rate;
    }
    
    public float getAsymptomaticRate()
    {
        return asymptomaticRate_;
    }
    
    public void setAsymptomaticRate(float rate)
    {
        asymptomaticRate_ = rate;
    }
    
    public float getDeathRate()
    {
        return deathRate_;
    }
    
    public void setDeathRate(float rate)
    {
        deathRate_ = rate;
    }
    
    public int getIncubationDays()
    {
        return incubationDays_;
    }
    
    public void setIncubationDays(int days)
    {
        incubationDays_ = days;
    }
    
    public int getInfectionDays()
    {
        return infectionDays_;
    }
    
    public void setInfectionDays(int days)
    {
        infectionDays_ = days;
    }
}