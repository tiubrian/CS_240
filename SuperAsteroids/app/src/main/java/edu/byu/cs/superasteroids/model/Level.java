package edu.byu.cs.superasteroids.model;
import java.util.ArrayList;

/**
 * This is the model class for a level in SuperAsteroids.
 * A level has a set of moving asteroids, a background, a hint (which never really helps), and spatial dimensions.
 */
public class Level {
	/**
	 * The level number, or order of the level in the sequence of levels. Supposedly correlated with level difficulty.
	 */
	public int number;
	/**
	 * Width of the level world.
	 */
	public int width;
	/**
	 * Height of the level world.
	 */
	public int height;
	/**
	 * ????? will figure out when I code it
	 */
	public String musicFile;
	/**
	 * Clever misdirection.
	 */
	public String hint;
	/**
	 * A list of asteroids.
	 */
	public ArrayList<Asteroid> asteroids;
	public ArrayList<BackgroundObject> background_objects;

	public Level()
	{
		asteroids = new ArrayList<Asteroid>();
	}

	public void update()
	{

	}

	public void draw()
	{

	}



	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public String getMusicFile() {
		return musicFile;
	}

	public void setMusicFile(String musicFile) {
		this.musicFile = musicFile;
	}

	public String getHint() {
		return hint;
	}

	public void setHint(String hint) {
		this.hint = hint;
	}
}