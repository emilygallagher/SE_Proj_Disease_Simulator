package com.sim;

/**
 *
 *
 * @author Emily Gallagher
 * @version %I%
 * @since 1.0
 */
public final class Utils
{
    //----- CONSTRUCTORS -----
    private Utils() { }
    
    //----- METHODS -----
    public static int clamp(int value, int min, int max)
    {
        return value >= max ? max
            : value <= min ? min
            : value;
    }
    
    public static float clamp(float value, float min, float max)
    {
        return value >= max ? max
            : value <= min ? min
            : value;
    }
    
    public static double clamp(double value, double min, double max)
    {
        return value >= max ? max
            : value <= min ? min
            : value;
    }
}