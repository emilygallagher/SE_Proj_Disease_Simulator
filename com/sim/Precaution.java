package com.sim;

/**
 *
 *
 * @author Emily Gallagher
 * @version %I%
 * @since 1.0
 */
public class Precaution
{
    //----- FIELDS -----
    /** The name of the precautionary measure. */
    private String name_;
    
    /** The amount the precautionary measure reduces the chances of a disease spreading. */
    private float modifier_;
    
    //----- CONSTRUCTORS -----
    /**
     * Default Constructor:<br />
     * Creates a new Precaution with the name "New Precautionary Measure" and a modifier of 0.
     */
    public Precaution()
    {
        this("New Precautionary Measure", 0.0f);
    }
    
    /**
     * Copy Constructor:<br />
     * Creates a deep copy of the specified Precaution object.
     *
     * @param precaution The precautionary measure to copy.
     */
    public Precaution(Precaution precaution)
    {
        this(precaution.name_, precaution.modifier_);
    }
    
    /**
     * Parameterized Constructor:<br />
     * Creates a new Precaution with the specified parameters.
     *
     * @param name The name of the precautionary measure.
     * @param modifier The amount the precautionary measure reduces the chances of a disease
     *                 spreading.
     */
    public Precaution(String name, float modifier)
    {
        name_ = name;
        modifier_ = modifier;
    }
    
    //----- METHODS -----
    // Get & Set
    public String getName()
    {
        return name_;
    }
    
    public void setName(String name)
    {
        name_ = name;
    }
    
    public float getModifier()
    {
        return modifier_;
    }
    
    public void setModifier(float modifier)
    {
        modifier_ = modifier;
    }
}