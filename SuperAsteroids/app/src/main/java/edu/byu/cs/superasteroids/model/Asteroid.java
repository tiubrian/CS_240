package edu.byu.cs.superasteroids.model;

/**
 * An Asteroid -- the mortal enemy of our spaceship
 * Has an AsteroidType with info , and probably will have other information in the future.
 */
public class Asteroid
{
	public AsteroidType type;
	public MovingState state;
	public Asteroid()
	{
	  this.state = new MovingState();
	}

	public Asteroid(AsteroidType type)
	{
  	  this.state = new MovingState();
	  this.type = type;
	}
	
	public void update()
	{
	 state.update();
	}
	
	public void draw()
	{
	}
}
