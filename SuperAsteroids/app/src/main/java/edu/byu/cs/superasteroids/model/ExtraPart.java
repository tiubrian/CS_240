package edu.byu.cs.superasteroids.model;
import org.json.*;

/**
 * A part of the spaceship
 * Really just the left wing, but for consistency its called extra part.
 */
public class ExtraPart
{
	public GameImage image;
  public Coordinate attachPoint;

	public ExtraPart()
	{
	}
	
	public ExtraPart(JSONObject obj) throws JSONException {
		attachPoint = new Coordinate(obj.getString("attachPoint"));
		image = new GameImage(obj.getString("image"),
					Integer.parseInt(obj.getString("imageWidth")),
					Integer.parseInt(obj.getString("imageHeight")));
	}

	
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
