package com.sim;

/**
 * Static class that contains all {@code Disease} and {@code Precaution} object presets.
 *
 * @author Emily Gallagher
 * @version 1.1
 * @since 0.2
 */
public final class Databases
{
    //----- FIELDS -----
    /** Contains all Disease preset information. */
    private static final Disease[] DISEASES_;
    
    /** Contains all Precaution preset information. */
    private static final Precaution[] PRECAUTIONS_;
    
    //----- CONSTRUCTORS -----
    // Initializes static fields.
    static
    {
        DISEASES_ = new Disease[]
        {
            // Initialize all Disease presets here.
            new Disease("Covid-19", 100, .037f, .1f,.018f , 14, 8),
            new Disease("Influenza", 100, .055f, .1f,.0009f , 2, 10)
            
        };
        
        PRECAUTIONS_ = new Precaution[]
        {
            // Initialize all precautionary measure presets here.
           new Precaution("None", 1.0f),
           new Precaution("Mask", 0.3f),
           new Precaution("Distance", 0.2f),
           new Precaution("Masks and Distance", 0.1f)
        };
    }
    
    /**
     * Sole Private Constructor:<br />
     * Prevents the static class from being instantiated.
     */
    private Databases() { }
    
    //----- METHODS -----
    // Get
    /**
     * Returns a copy of the Disease found at the specified index of the Database's disease array.
     *
     * @param index The index of the Disease.
     * @return A copy of the Disease found at the specified index.
     * @throws IndexOutOfBoundsException Thrown if index is less than 0 or greater than the length
     *                                   of the disease array minus 1.
     */
    public static Disease getDiseaseCopy(int index) throws IndexOutOfBoundsException
    {
        return new Disease(DISEASES_[index]);
    }
    
    /**
     * Returns a copy of the first Disease in the Database's disease array with the specified name.
     *
     * @param name The name of the Disease.
     * @return A copy of the first Disease found with the specified name or null if none is found.
     */
    public static Disease getDiseaseCopy(String name)
    {
        for (Disease disease : DISEASES_)
        {
            if (disease.getName() == name)
            {
                return new Disease(disease);
            }
        }
        
        return null;
    }
    
    /**
     * Returns the number of Disease objects stored in the Database.
     *
     * @return The number of Disease objects stored.
     */
    public static int getDiseasesLength()
    {
        return DISEASES_.length;
    }
    
    /**
     * Returns a copy of the Precaution found at the specified index of the Database's
     * precautionary measure array.
     *
     * @param index The index of the Precaution.
     * @return A copy of the Precaution found at the specified index.
     * @throws IndexOutOfBoundsException Thrown if index is less than 0 or greater than the length
     *                                   of the precautionary measure array minus 1.
     */
    public static Precaution getPrecautionCopy(int index) throws IndexOutOfBoundsException
    {
        return new Precaution(PRECAUTIONS_[index]);
    }
    
    /**
     * Returns a copy of the first Precaution in the Database's precautionary measures array with
     * the specified name.
     *
     * @param name The name of the Precaution.
     * @return A copy of the first Precaution found with the specified name or null if none is
     *         found.
     */
    public static Precaution getPrecautionCopy(String name)
    {
        for (Precaution precaution : PRECAUTIONS_)
        {
            if (precaution.getName() == name)
            {
                return new Precaution(precaution);
            }
        }
    
        return null;
    }
    
    /**
     * Returns the number of Precaution objects stored in the Database.
     *
     * @return The number of Precaution objects stored.
     */
    public static int getPrecautionsLength()
    {
        return PRECAUTIONS_.length;
    }
}