package com.sim;

import java.util.Random;
import javafx.geometry.Point2D;

/**
 * Global utilities class.
 *
 * @author Emily Gallagher
 * @version 1.1
 * @since 0.2
 */
public final class Utils
{
    //----- FIELDS -----
    /** Globally accessible {@code Random} object. */
    public static final Random RANDOM = new Random();
    
    //----- CONSTRUCTORS -----
    /**
     * Sole Private Constructor:<br />
     * Prevents this class from being instantiated.
     */
    private Utils() { }
    
    //----- METHODS -----
    // Random
    /**
     * Generates a random integer between the specified min and max values.
     *
     * @param min The minimum value the generated number can be.
     * @param max The maximum value the generated number can be.
     * @return The generated value.
     * @throws IllegalArgumentException Thrown if min is greater than max.
     */
    public static int getRandomRange(int min, int max) throws IllegalArgumentException
    {
        if (min > max)
        {
            throw new IllegalArgumentException("The minimum value cannot be less than the max "
                + "value.");
        }
        else
        {
            return (RANDOM.nextInt() % (max - min)) + min;
        }
    }
    
    /**
     * Generates a random long integer value between the specified min and max values.
     *
     * @param min The minimum value the generated number can be.
     * @param max The maximum value the generated number can be.
     * @return The generated value.
     * @throws IllegalArgumentException Thrown if min is greater than max.
     */
    public static long getRandomRange(long min, long max) throws IllegalArgumentException
    {
        if (min > max)
        {
            throw new IllegalArgumentException("The minimum value cannot be less than the max "
                + "value.");
        }
        else
        {
            return (RANDOM.nextLong() % (max - min)) + min;
        }
    }
    
    /**
     * Generates a random floating-point value between the specified min and max
     * values.
     *
     * @param min The minimum value the generated number can be.
     * @param max The maximum value the generated number can be.
     * @return The generated value.
     * @throws IllegalArgumentException Thrown if min is greater than max.
     */
    public static float getRandomRange(float min, float max) throws IllegalArgumentException
    {
        if (min > max)
        {
            throw new IllegalArgumentException("The minimum value cannot be less than the max "
                + "value.");
        }
        else
        {
            return (RANDOM.nextFloat() * (max - min)) + min;
        }
    }
    
    /**
     * Generates a random double-precision floating-point value between the specified min and max
     * values.
     *
     * @param min The minimum value the generated number can be.
     * @param max The maximum value the generated number can be.
     * @return The generated value.
     * @throws IllegalArgumentException Thrown if min is greater than max.
     */
    public static double getRandomRange(double min, double max) throws IllegalArgumentException
    {
        if (min > max)
        {
            throw new IllegalArgumentException("The minimum value cannot be less than the max "
                + "value.");
        }
        else
        {
            return (RANDOM.nextDouble() * (max - min)) + min;
        }
    }
    
    /**
     * Generates a {@code Point2D} whose x and y values are between 0 and the specified maximum
     * x and y values.
     *
     * @param maxXY The maximum x and y values.
     * @return The generated {@code Point2D}.
     * @throws IllegalArgumentException Thrown if either x or y in maxXY is less than 0.
     */
    public static Point2D getRandomPoint2D(Point2D maxXY) throws IllegalArgumentException
    {
        return getRandomPoint2D(Point2D.ZERO, maxXY);
    }
    
    /**
     * Generates a {@code Point2D} whose x and y values are between specified minimum and maximum
     * x and y values.
     *
     * @param minXY The minimum x and y values.
     * @param maxXY The maximum x and y values.
     * @return The generated {@code Point2D}.
     * @throws IllegalArgumentException Thrown if minimum value for either x or y is greater than
     *                                  the maximum value.
     */
    public static Point2D getRandomPoint2D(Point2D minXY, Point2D maxXY)
        throws IllegalArgumentException
    {
        if (minXY.getX() > maxXY.getX() || minXY.getY() > maxXY.getY())
        {
            throw new IllegalArgumentException("The minimum value cannot be less than the max "
                + "value.");
        }
        else
        {
            double x = getRandomRange(minXY.getX(), maxXY.getX());
            double y = getRandomRange(minXY.getY(), maxXY.getY());
            return new Point2D(x, y);
        }
    }
}