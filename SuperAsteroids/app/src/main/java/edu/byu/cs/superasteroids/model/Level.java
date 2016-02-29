package edu.byu.cs.superasteroids.model;
import java.util.*;
import org.json.*;
import android.util.Log;

/**
 * This is the model class for a level in SuperAsteroids.
 * A level has a set of moving asteroids, a background, a hint (which never really helps), and spatial dimensions.
 */
public class Level {
        public final static String tag = "superasteroidslevel";
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
	public String title;

	public ArrayList<Asteroid> asteroids;
	public ArrayList<BackgroundObject> background_objects;

	public Level()
	{
		asteroids = new ArrayList<Asteroid>();
		background_objects = new ArrayList<BackgroundObject>();
	}

	public Level(JSONObject obj) throws JSONException {
		this();
		number = Integer.parseInt(obj.getString("number"));
		title = obj.getString("title");
		hint = obj.getString("hint");
		width = Integer.parseInt(obj.getString("width"));
		height = Integer.parseInt(obj.getString("height"));
		musicFile = obj.getString("music");

	}

	public String toString() {
		StringBuilder res = new StringBuilder();
		res.append(" number: ");
		res.append(number);
		res.append(" title: ");
		res.append(title);
		res.append(" hint: ");
		res.append(hint);
		res.append(" width: ");
		res.append(width);
		res.append(" height: ");
		res.append(height);
		res.append(" music: ");
		res.append(musicFile);
		return res.toString();
	}

	
	public void update(double elapsedTime)
	{
	  int i;
	  for (i = 0; i < background_objects.size(); i++)
	  {
	    background_objects.get(i).update(elapsedTime);
	  }

	  for (i = 0; i < asteroids.size(); i++)
	  {
	    asteroids.get(i).update(elapsedTime);
	  }	  
	}

	public void draw()
	{
	  Log.e(tag, "drawing level with "+asteroids.size()+" asteroids");
	  int i;
	  for (i = 0; i < background_objects.size(); i++)
	  {
	    background_objects.get(i).draw();
	  }

	  for (i = 0; i < asteroids.size(); i++)
	  {
	    asteroids.get(i).draw();
	  }
	  
	  purge();
	}

	public void purge()
	{
	  for (Iterator<Asteroid> it = asteroids.iterator(); it.hasNext();)
	  {
	    Asteroid a = it.next();
	    if (a.deleted)
	    {
	      Log.e(tag, "deleting asteroid ");
	      it.remove();
	    }
	  }
	}

	
	public void initAsteroidStates()
	{
	  for (int i = 0; i < asteroids.size(); i++)
	  {
	    asteroids.get(i).initRandom();
	  }
	}
	
	public ArrayList<Asteroid> getAsteroids()
	{
	  return this.asteroids;
	}
	
	public void addAsteroids(AsteroidType type, int number)
	{
	  Log.e(tag, "adding "+ number + " asteroids.");
	  int i = 0;
	  for (i=0; i<number; i++)
	  {
	    Asteroid a = new Asteroid(type);
	    asteroids.add(a);
	  }
	}

	public void addBackgroundObject(BackgroundObject obj)
	{
	  background_objects.add(obj);
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