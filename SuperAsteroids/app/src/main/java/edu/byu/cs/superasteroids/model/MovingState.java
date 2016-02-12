package edu.byu.cs.superasteroids.model;

/**
 * Represents the state of a moving object.
 */
public class MovingState {
    public Coordinate pos;
    public Float xvelocity;
    public Float yvelocity;
    /**
     * The direction of the moving object.
     * This probably can be determined from the x and y velocities, but is included for convenience.
     */
    public Float theta;
    public void update()
    {

    }

    public void draw()
    {

    }

}
