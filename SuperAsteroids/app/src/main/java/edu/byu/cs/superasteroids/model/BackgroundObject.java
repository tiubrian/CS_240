package edu.byu.cs.superasteroids.model;

/**
 * A background object for a level.
 * Has an image, scale, and position
 */

public class BackgroundObject
{
	String image;
	Float scale;
	Coordinate position;
	
	public BackgroundObject(String image, Float scale, Coordinate position)
	{
		this.image = image;
		this.scale = scale;
		this.position = position;
	}
}