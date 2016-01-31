package edu.byu.cs.superasteroids.model;
import java.util.ArrayList;

public class Level {
	public int number;
	public int width;
	public int height;
	public String musicFile;
	public String hint;
	public String height;
	public ArrayList<LevelAsteroid> level_asteroids;

	public void add_asteroid(LevelAsteroid asteroid)
	{
		level_asteroids.add(asteroid);
	}
  

}