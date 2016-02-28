package edu.byu.cs.superasteroids.model;
import android.util.Log;
/**
 * Represents the state of a moving object.
 */
public class MovingState {
    public static final String tag = "superasteroidsstate";
    public Float dx;
    public Float dy;
    private float x;
    private float y;
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
     x = (float)0.;
     y = (float)0.;
    }
    
    public MovingState(Float x_init, Float y_init)
    {
     this();
     x = x_init;
     y = y_init;
    }

    
    
    public MovingState(Float x_init, Float y_init, Float dx, Float dy)
    {
     this(x_init, y_init);
     this.dx = dx;
     this.dy = dy;
    }	

    public MovingState(int x_init, int y_init)
    {
     this();
     x = (float)x_init;
     y = (float)y_init;
    }

    
    public MovingState(int x_init, int y_init, Float dx, Float dy)
    {
     this(x_init, y_init);
     this.dx = dx;
     this.dy = dy;
    }	

    
    public void randomDirection(float newSpeed)
    {
      theta = (float)(2*Math.PI*Math.random());
      dx = (float)Math.sin(theta)*newSpeed;
      dy = (float)Math.cos(theta)*newSpeed;      
    }
    
    public void stopMoving()
    {
     dx = (float)0.0;
     dy = (float)0.0;
    }
    
    public void setPolar(float angle, float speed)
    {
      theta = (float)Math.toRadians(angle);
      dx = (float)Math.sin(theta)*speed;
      dy = (float)Math.cos(theta)*speed;
    }
    
    public void update(double elapsedTime)
    {
      x += dx;
      y += dy;
    }

    public void draw()
    {

    }
    
    public float getX()
    {
      return x;
    }
    
    public float getY() {
      return y;
    }
    
    public void setPos(int x, int y)
    {
     Log.e(tag, "setting pos to "+Integer.toString(x)+" "+Integer.toString(y));
     this.x = x;
     this.y = y;
    }

    public void setPos(Coordinate coord)
    {
     this.x = coord.x;
     this.y = coord.y;
    }
    
    public Coordinate getPos()
    {
     return new Coordinate(x, y);
    }
}
