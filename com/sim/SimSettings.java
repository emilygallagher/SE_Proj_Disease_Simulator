package com.sim;

/**
 * An object that holds various settings that can be tweaked to have the simulation run
 * differently.
 *
 * @author Emily Gallagher
 * @version 1.0
 * @since 0.3
 */
public class SimSettings
{
    //----- FIELDS -----
    /** The disease that will be used in the simulation. */
    private Disease disease_;
    
    /** The cumulative modifier of all activated precautionary measures. */
    private double totalModifier_;
    
    /**
     * When active, people will self-isolate when they show symptoms to prevent the potential
     * spread of the disease they are infected with.
     */
    private boolean isSelfIsolationActive_;
    
    /** The total number of people to test in the simulation. */
    private int totalPopulation_;
    
    /** The number of people that start off infected with the disease (incubation period). */
    private int startingInfected_;
    
    /**
     * The maximum number of simulation days to simulate. The simulation should automatically end
     * after the day count reaches or surpasses this number. Any value less than 0 means unlimited.
     */
    private int maxDays_;
    
    /** The length of a single simulation day in terms of frames. */
    private int dayLength_;
    
    /**
     * The radius of a circle drawn on the {@code Canvas} that represents a {@code Person} in the
     * simulation.
     */
    private double personRadius_;
    
    /** The minimum speed at which a {@code Person} in the simulation can move. */
    private double minMoveSpeed_;
    
    /** The maximum speed at which a {@code Person} in the simulation can move. */
    private double maxMoveSpeed_;
    
    /**
     * Is the simulation currently running in debug mode? This should be permanently set to false
     * in the release version.
     */
    private boolean isDebugActive_;
    
    //----- CONSTRUCTORS -----
    
    /**
     * Default Constructor:<br />
     * Creates a new {@code SimSettings} object with null/default settings.
     */
    public SimSettings()
    {
        this(null, 0.0, false, 0, 0, -1, 90, 1.0, 2.0, 7.5, false);
    }
    
    /**
     * Copy Constructor:<br />
     * Creates a copy of another {@code SimSettings} object.
     *
     * @param simSettings The {@code SimSettings} object to copy.
     */
    public SimSettings(SimSettings simSettings)
    {
        this(simSettings.disease_, simSettings.totalModifier_, simSettings.isSelfIsolationActive_,
            simSettings.totalPopulation_, simSettings.startingInfected_, simSettings.maxDays_,
            simSettings.dayLength_, simSettings.personRadius_, simSettings.minMoveSpeed_,
            simSettings.maxMoveSpeed_, simSettings.isDebugActive_);
    }
    
    /**
     * Parameterized Constructor:<br />
     * Creates a new {@code SimSettings} object with the specified parameters.
     *
     * @param disease The {@code Disease} to be used during the simulation.
     * @param totalModifier The cumulative modifier of all activated precautionary measures.
     * @param isSelfIsolationActive Whether or not a {@code Person} should isolate themselves
     *                              when they begin to show symptoms in order to prevent the
     *                              potential spread of the disease.
     * @param totalPopulation The total number of people in this simulation.
     * @param startingInfected The number of people that start the simulation already infected.
     * @param maxDays The maximum number of days to simulate. A negative number means infinite.
     * @param dayLength The length of a simulation day, measured in frames.
     * @param personRadius The radius of a circle drawn on the {@code Canvas} to represent a
     *                     {@code Person} in the simulation.
     * @param minMoveSpeed The minimum speed at which a {@code Person} in the simulation can move.
     * @param maxMoveSpeed The maximum speed at which a {@code Person} in the simulation can move.
     */
    public SimSettings(Disease disease, double totalModifier, boolean isSelfIsolationActive,
        int totalPopulation, int startingInfected, int maxDays, int dayLength, double personRadius,
        double minMoveSpeed, double maxMoveSpeed)
    {
        this(disease, totalModifier, isSelfIsolationActive, totalPopulation, startingInfected,
            maxDays, dayLength, personRadius, minMoveSpeed, maxMoveSpeed, false);
    }
    
    /**
     * Debug Constructor:<br />
     * Same as the Parameterized Constructor, but also allows debug mode to be activated.
     *
     * @param disease The {@code Disease} to be used during the simulation.
     * @param totalModifier The cumulative modifier of all activated precautionary measures.
     * @param isSelfIsolationActive Whether or not a {@code Person} should isolate themselves
     *                              when they begin to show symptoms in order to prevent the
     *                              potential spread of the disease.
     * @param totalPopulation The total number of people in this simulation.
     * @param startingInfected The number of people that start the simulation already infected.
     * @param maxDays The maximum number of days to simulate. A negative number means infinite.
     * @param dayLength The length of a simulation day, measured in frames.
     * @param personRadius The radius of a circle drawn on the {@code Canvas} to represent a
     *                     {@code Person} in the simulation.
     * @param minMoveSpeed The minimum speed at which a {@code Person} in the simulation can move.
     * @param maxMoveSpeed The maximum speed at which a {@code Person} in the simulation can move.
     * @param isDebugActive Whether or not the simulation should run in debug mode.
     */
    public SimSettings(Disease disease, double totalModifier, boolean isSelfIsolationActive,
        int totalPopulation, int startingInfected, int maxDays, int dayLength, double personRadius,
        double minMoveSpeed, double maxMoveSpeed, boolean isDebugActive)
    {
        disease_ = disease;
        totalModifier_ = totalModifier;
        isSelfIsolationActive_ = isSelfIsolationActive;
        totalPopulation_ = totalPopulation;
        startingInfected_ = startingInfected;
        maxDays_ = maxDays;
        dayLength_ = dayLength;
        personRadius_ = personRadius;
        minMoveSpeed_ = minMoveSpeed;
        maxMoveSpeed_ = maxMoveSpeed;
        isDebugActive_ = isDebugActive;
    }
    
    //----- METHODS -----
    // Get & Set
    public Disease getDisease()
    {
        return disease_;
    }
    
    public void setDisease(Disease disease)
    {
        disease_ = disease;
    }
    
    public double getTotalModifier()
    {
        return totalModifier_;
    }
    
    public void setTotalModifier(double modifier)
    {
        totalModifier_ = modifier;
    }
    
    public boolean isSelfIsolationActive()
    {
        return isSelfIsolationActive_;
    }
    
    public void setSelfIsolationActive(boolean active)
    {
        isSelfIsolationActive_ = active;
    }
    
    public int getTotalPopulation()
    {
        return totalPopulation_;
    }
    
    public void setTotalPopulation(int population)
    {
        totalPopulation_ = population;
    }
    
    public int getStartingInfected()
    {
        return startingInfected_;
    }
    
    public void setStartingInfected(int infectedPopulation)
    {
        startingInfected_ = infectedPopulation;
    }
    
    /**
     * Returns the maximum number of simulation days that the simulator should simulate. If
     * this value is less than 0, there is no limit.
     *
     * @return The maximum number of simulation days.
     */
    public int getMaxDays()
    {
        return maxDays_;
    }
    
    /**
     * Sets the maximum number of simulation days that the simulator should simulate. For
     * no limit, set to any number less than zero.
     *
     * @param days The maximum number of simulation days. Should be negative for no limit.
     */
    public void setMaxDays(int days)
    {
        maxDays_ = days;
    }
    
    public int getDayLength()
    {
        return dayLength_;
    }
    
    public void setDayLength(int frames)
    {
        dayLength_ = frames;
    }
    
    public boolean runsEndlessly()
    {
        return maxDays_ < 0;
    }
    
    public double getPersonRadius()
    {
        return personRadius_;
    }
    
    public void setPersonRadius(double radius)
    {
        personRadius_ = radius;
    }
    
    public double getMinMoveSpeed()
    {
        return minMoveSpeed_;
    }
    
    public void setMinMoveSpeed(double speed)
    {
        minMoveSpeed_ = speed;
    }
    
    public double getMaxMoveSpeed()
    {
        return maxMoveSpeed_;
    }
    
    public void setMaxMoveSpeed(double speed)
    {
        maxMoveSpeed_ = speed;
    }
    
    public boolean isDebugActive()
    {
        return isDebugActive_;
    }
    
    public void setDebugActive(boolean isActive)
    {
        isDebugActive_ = isActive;
    }
}