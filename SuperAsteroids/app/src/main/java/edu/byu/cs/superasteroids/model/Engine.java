package edu.byu.cs.superasteroids.model;
import org.json.*;
import android.util.Log;
/**
 * The engine of the space ship. Has a lateral speed, turnrate, and image.
 */
public class Engine extends AttachablePart {
 public int baseSpeed;
 public int baseTurnRate;
 public static final String tag = "superasteroidsengine";
 /**
  * Describes where to attach the engine image to the main body of the ship.
  */
// public Coordinate attachPoint;
//  public GameImage image;

 public Engine()
 {
 }

 @Override
 public boolean equals(Object o) {
  if (this == o) return true;
  if (o == null || getClass() != o.getClass()) return false;
  if (!super.equals(o)) {Log.e(tag, "super eq failed in engine"); return false;}

  Engine engine = (Engine) o;

  if (baseSpeed != engine.baseSpeed) return false;
  return baseTurnRate == engine.baseTurnRate;

 }

 @Override
 public int hashCode() {
  int result = super.hashCode();
  result = 31 * result + baseSpeed;
  result = 31 * result + baseTurnRate;
  return result;
 }

 public Engine(JSONObject obj) throws JSONException
 {
	 baseSpeed = Integer.parseInt(obj.getString("baseSpeed"));
	 baseTurnRate = Integer.parseInt(obj.getString("baseTurnRate"));
	 attachPoint = new Coordinate(obj.getString("attachPoint"));
	 image = new GameImage(obj.getString("image"), 
								Integer.parseInt(obj.getString("imageWidth")),
								Integer.parseInt(obj.getString("imageHeight")) );
	 
 }
 
 public String toString() {
	StringBuilder res = new StringBuilder();
	res.append(" baseSpeed: ");
	res.append(baseSpeed);
	res.append(" baseTurnRate: ");
	res.append(baseTurnRate);
	res.append(" attachPoint: ");
	res.append(attachPoint.toString());
	res.append(" image: ");
	res.append(image.toString());
	return res.toString();
}

 public void setAttach(MainBody body)
 {
  this.bodyAttach = body.engineAttach;
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





 public int getBaseSpeed() {
  return baseSpeed;
 }

 public void setBaseSpeed(int baseSpeed) {
  this.baseSpeed = baseSpeed;
 }

 public int getBaseTurnRate() {
  return baseTurnRate;
 }

 public void setBaseTurnRate(int baseTurnRate) {
  this.baseTurnRate = baseTurnRate;
 }

 public Coordinate getAttachPoint() {
  return attachPoint;
 }

 public void setAttachPoint(Coordinate attachPoint) {
  this.attachPoint = attachPoint;
 }

 public GameImage getImage() {
  return image;
 }

 public void setImage(GameImage image) {
  this.image = image;
 }
}