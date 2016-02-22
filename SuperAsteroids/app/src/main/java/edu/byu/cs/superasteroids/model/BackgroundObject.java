package edu.byu.cs.superasteroids.model;

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

}