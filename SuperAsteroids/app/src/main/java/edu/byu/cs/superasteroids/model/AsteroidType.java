package edu.byu.cs.superasteroids.model;
import org.json.*;
import android.util.Log;

/**
 * An asteroidType. Used to model different types of asteroids, without resorting to inheritance.
 */
public class AsteroidType {
 public String name;
 public String type;
 public GameImage image;
 public int id;
 public static final String tag = "superasteroidsasttype";

  public AsteroidType()
  {
  }
 
	public AsteroidType(JSONObject obj) throws JSONException {
		name = obj.getString("name");
		image = new GameImage(obj.getString("image"),
					Integer.parseInt(obj.getString("imageWidth")),
					Integer.parseInt(obj.getString("imageHeight")));
		type = obj.getString("type");
	}

 @Override
 public boolean equals(Object o) {
  if (this == o) return true;
  if (o == null || getClass() != o.getClass()) return false;

  AsteroidType that = (AsteroidType) o;

  if (!name.equals(that.name)) {Log.e(tag, " name eq failed"); return false;}
  if (!type.equals(that.type)) {Log.e(tag, " type eq failed"); return false;}
  Log.e(tag, "images "+image.equals(that.image));
  return image.equals(that.image);

 }

 @Override
 public int hashCode() {
  int result = name != null ? name.hashCode() : 0;
  result = 31 * result + (type != null ? type.hashCode() : 0);
  result = 31 * result + (image != null ? image.hashCode() : 0);
  return result;
 }

 public String toString() {
		StringBuilder res = new StringBuilder();
		res.append(" id: ");
		res.append(id);
		res.append(" name: ");
		res.append(name);
		res.append(" image: ");
		res.append(image.toString());
		res.append(" type: ");
		res.append(type);
		return res.toString();
	}

	
 public void update(double elapsedTime, Asteroid ast)
 {
  //do stuff here
  if (type.equals("growing")) {ast.setScale(grow(ast.scale)); Log.e(tag, "growing the asteroid");}
 }

 public float grow(float scale)
 {
  if (scale < (float)2.) scale += (float).001;
  return scale;
 }
 
 public void draw()
 {

 }

 public int getSplits()
 {
   Log.e(tag, "Asteroid Type "+type);
   if (type.equals("octeroid")) Log.e(tag, "type octeroid");
   else Log.e(tag, "Type is not octeroid. Type is "+type);
   if (type.equals("octeroid")) return 8;
   else return 2;
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

 public int getImageId()
 {
   return image.getImageId();
 }
 
 
 
}
