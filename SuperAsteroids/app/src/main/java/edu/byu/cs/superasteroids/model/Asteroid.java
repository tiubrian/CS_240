package edu.byu.cs.superasteroids.model;

import edu.byu.cs.superasteroids.model.ViewPort.Wall;
import android.util.Log;

/**
 * An Asteroid -- the mortal enemy of our spaceship
 * Has an AsteroidType with info , and probably will have other information in the future.
 */
public class Asteroid extends BoundedObject
{
        public static float speed;
	public AsteroidType type;
	public Asteroid()
	{
	  this.state = new MovingState();
	}

	public Asteroid(AsteroidType type)
	{
  	  this.state = new MovingState();
	  this.type = type;
	}
	
	public void initRandom()
	{
	  state.setPos((int)(Math.random()*ViewPort.worldDim.x), (int)(Math.random()*ViewPort.worldDim.y));
	  state.randomDirection(speed);
	}
	
	public void update(double elapsedTime)
	{
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
	    if (state.dx <0) state.dx = -state.dx;
	    break;
	   case LEFT:
	    if (state.dx > 0) state.dx = -state.dx;
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
	 this.dim = type.image.getDimension();
	}
	
	public void draw()
	{
	  super.draw();
	}
	
	@Override
	public int getImageId()
	{
	  return type.getImageId();
	}
}
