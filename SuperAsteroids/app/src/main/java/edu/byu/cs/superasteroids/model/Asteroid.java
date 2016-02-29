package edu.byu.cs.superasteroids.model;

import edu.byu.cs.superasteroids.model.ViewPort.Wall;
import android.util.Log;

/**
 * An Asteroid -- the mortal enemy of our spaceship
 * Has an AsteroidType with info , and probably will have other information in the future.
 */
public class Asteroid extends GameObject
{
        public static float initSpeed = (float)10.0;
	public AsteroidType type;
	public final static String tag = "superasteroidsasteroid";
	public Asteroid()
	{
	  super();
	  this.state = new MovingState();
	}

	public Asteroid(AsteroidType type)
	{
  	  this();
	  this.type = type;
	}
	
	public void initRandom()
	{
//	  state.setPos((int)(Math.random()*ViewPort.worldDim.x), (int)(Math.random()*ViewPort.worldDim.y));
          state.setPos(new Coordinate(1750,1500));
	  Log.e(tag, "supposedly Random initial position "+ state.getPos().toString());
	  state.randomDirection(initSpeed);
	  Log.e(tag, "Asteroid is Moving with dx = "+Float.toString(state.dx)+" dy="+Float.toString(state.dy));
	}
	
	@Override
	public void update(double elapsedTime)
	{
	 Log.e(tag, " updating asteroid");
	 super.update(elapsedTime);
	 //Move
	 state.update(elapsedTime);
	 //Rescale (if needed)
	 type.update(elapsedTime, this);
	}

	@Override
	public void onWallCollision(Wall wall)
	{
	  switch (wall)
	  {
	   case RIGHT:
	    //make sure we only flip once
	    //If I just naively toggled the sign, it could get 'stuck' to the wall
	    if (state.dx > 0) state.dx = -state.dx;
	    break;
	   case LEFT:
	    if (state.dx < 0) state.dx = -state.dx;
 	   break;
 	   case TOP:
	    if (state.dy < 0) state.dy = -state.dy;
 	   break;
	   case BOTTOM:
	    if (state.dy > 0) state.dy = -state.dy;
	    break;
	   default:
	    break;
	  }
	}
	
	public void initDimension()
	{
	 this.dim = type.image.getDimensions();
	}
	
	@Override
	public void draw()
	{
	  super.draw();
	  Log.e(tag, "drawing Asteroid at location "+getCenter().toString() + " view"+getViewCenter().toString());
	}
	
	@Override
	public int getImageId()
	{
	  return type.getImageId();
	}
}
