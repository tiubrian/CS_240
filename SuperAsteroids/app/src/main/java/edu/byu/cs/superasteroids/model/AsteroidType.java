package edu.byu.cs.superasteroids.model;
import org.json.*;

/**
 * An asteroidType. Used to model different types of asteroids, without resorting to inheritance.
 */
public class AsteroidType {
 public String name;
 public String type;
 public GameImage image;
 public int id;

	public AsteroidType(JSONObject obj) throws JSONException {
		name = obj.getString("name");
		image = new GameImage(obj.getString("image"),
					Integer.parseInt(obj.getString("imageWidth")),
					Integer.parseInt(obj.getString("imageHeight")));
		type = obj.getString("type");
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
