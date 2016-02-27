package edu.byu.cs.superasteroids.model;
import edu.byu.cs.superasteroids.drawing.DrawingHelper;

import android.util.Log;

/**
 * A background object for a level.
 * Has an image, scale, and position
 */

public class BackgroundObject
{
	public String image;
	public GameImage image_obj;
	public Float scale;
	public Coordinate position;
	public static final String tag = "superasteroidsbgobject";
	
	public BackgroundObject(String image, Float scale, Coordinate position)
	{
		this.image = image;
		image_obj = new GameImage(image,0,0);
		this.scale = scale;
		this.position = position;
	}
	
	public BackgroundObject(String image, Float scale, String position)
	{
		this.image = image;
		image_obj = new GameImage(image,0,0);
		this.scale = scale;
		this.position = new Coordinate(position);
	}

	
	public void draw()
	{
	  Coordinate viewpos = ViewPort.fromWorld(position);
//	  Log.e(tag, "Got viewpos: "+viewpos.toString() + 
//	    " scale: "+Float.toString(scale)+" imname: "+image);
	  DrawingHelper.drawImage(image_obj.getImageId(), (float)viewpos.x, (float)viewpos.y,
	    (float)0.0, (float)scale, (float)scale, 255);
	}
	
	public void update(double elapsedTime)
	{
	}
	
	
}