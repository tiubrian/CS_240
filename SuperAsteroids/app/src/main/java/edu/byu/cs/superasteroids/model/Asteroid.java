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
        public static int defaultHealth = 4;
	public AsteroidType type;
	public final static String tag = "superasteroidsasteroid";
	public int splits;
	public int health;
	public Asteroid()
	{
	  super();
	  this.state = new MovingState();
	}

	public Asteroid(AsteroidType type)
	{
  	  this();
	  this.type = type;
	  this.health = defaultHealth;
	  this.splits = 1;
	}
	
	public void randomPos()
	{
	  state.setPos((int)(Math.random()*ViewPort.worldDim.x), (int)(Math.random()*ViewPort.worldDim.y));
	}
	
	public void initRandom()
	{
	  Coordinate shipCenter = AsteroidsModel.getShip().getCenter();
	  randomPos();
	  while (Coordinate.sup_dist(shipCenter, getCenter()) < AsteroidsModel.safeZoneDim) {
	    randomPos();
	  }
//          state.setPos(new Coordinate(1750,1500));
	  Log.e(tag, "supposedly Random initial position "+ state.getPos().toString());
	  setRandomDirection();
	  Log.e(tag, "Asteroid is Moving with dx = "+Float.toString(state.dx)+" dy="+Float.toString(state.dy));
	}
	
	public void setRandomDirection()
	{
	  state.randomDirection(initSpeed);	 
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
	
	
	public void decrementHealth(int damage)
	{
	  this.health -= damage;
	  if (this.health <= 0) {
	   if (this.splits <= 0) removeFromGame();
	   else {
	     this.splits--;
	     int pieces = type.getSplits();
	     Log.e(tag, "pieces "+Integer.toString(pieces));
	     for (int i = 0; i < pieces; i++)
	     {
	       Asteroid a = new Asteroid(this.type);
	       a.setCenter(this.getCenter());
	       a.splits = this.splits;
	       a.health = defaultHealth/2;
	       a.setRandomDirection();
	       a.scale = this.scale/pieces;
	       Log.e(tag, "On pieces "+Integer.toString(i));
	       AsteroidsModel.getInstance().getCurrentLevel().scheduleAsteroid(a);
	     }
	     removeFromGame();
	   }
	 }	
	}

	
	@Override
	public void touch(Projectile p)
	{
	 Log.e(tag, "HIT BY LASER");
	 decrementHealth(p.getDamage());
	}
	
	@Override 
	public void touch(SpaceShip s)
	{
	 Log.e(tag, "HIT SPACESHIP");
	 decrementHealth(1);
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
	  public float getXImageScale()
	  {
	    return type.image.getXScale();
	  }
	  
	  @Override
	  public float getYImageScale()
	  {
	    return type.image.getYScale();
	  }

	
	
	@Override
	public int getImageId()
	{
	  return type.getImageId();
	}
}
