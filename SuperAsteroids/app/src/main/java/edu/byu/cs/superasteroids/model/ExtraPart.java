package edu.byu.cs.superasteroids.model;

/**
 * A part of the spaceship
 * Really just the left wing, but for consistency its called extra part.
 */
public class ExtraPart
{
	public GameImage image;
  public Coordinate attachPoint;

  public void update()
  {

  }

  public void draw()
  {

  }

  public String getImageName() {
    return image.getName();
  }

  public void setImageName(String name) {
    this.image.setName(name);
  }

  public int getImageHeight()
  {
    return image.getHeight();
  }

  public int getImageWidth()
  {
    return image.getWidth();
  }


}
