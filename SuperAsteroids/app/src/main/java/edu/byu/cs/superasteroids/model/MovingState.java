package edu.byu.cs.superasteroids.model;

/**
 * Represents the state of a moving object.
 */
public class MovingState {
    public Coordinate pos;
    public Float dx;
    public Float dy;
    /**
     * The direction of the moving object.
     * This probably can be determined from the x and y velocities, but is included for convenience.
     */
    private Float theta;
    
    public MovingState()
    {
     theta = (float)0.0;
     dx = (float)0.0;
     dy = (float)0.0;
    }
    
    public MovingState(Float x_init, Float y_init)
    {
     this();
     pos = new Coordinate(x_init, y_init);
    }

    
    public MovingState(Float x_init, Float y_init, Float dx, Float dy)
    {
     this();
     pos = new Coordinate(x_init, y_init);
     this.dx = dx;
     this.dy = dy;
    }

    
    public void update()
    {

    }

    public void draw()
    {

    }
    
    public float getX()
    {
      return (float)pos.getX();
    }
    
    public float getY() {
      return (float)pos.getY();
    }
    
    public void setPos(int x, int y)
    {
     pos.setX(x);
     pos.setY(y);
    }

}
