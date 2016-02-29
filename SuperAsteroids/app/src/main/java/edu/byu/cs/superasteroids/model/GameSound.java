package edu.byu.cs.superasteroids.model;
import edu.byu.cs.superasteroids.content.ContentManager;
import android.util.Log;
import java.io.IOException;

public class GameSound {
  public String name;
  public int Id;
  public final static String tag = "superasteroidssound";
  public float speed;
  public float volume;
  
  public GameSound(String name)
  {
    this.name = name;
    this.Id = -1;
    volume = (float)1.0;
    speed = (float)1.0;
  }
  
  public int getId()
  {
    if (Id >= 0) return Id;
    else
    {
      try { 
	Id = ContentManager.getInstance().loadSound(name);
	Log.e(tag, "Gave audiomanager "+name+" got "+Integer.toString(Id));
      }
      catch (IOException e)
      {
        Log.e(tag, " ioexception loading sound");
//        throw e;
      }
      return Id; 
    }
  }
  
  public void play()
  {
    ContentManager.getInstance().playSound(getId(), volume, speed);
  }
  
}