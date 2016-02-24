package edu.byu.cs.superasteroids.model;
import org.json.*;

/**
 * The main body of the ship. Has information on where to attach the other parts, and of course an image.
 */
public class MainBody  extends SpaceshipPart {
 /**
  * Where to attach the cannon.
  */
 public Coordinate cannonAttach;
 /**
  * Where to attach the engine.
  */
 public Coordinate engineAttach;
 /**
  * Where to attach the extra part (AKA left wing).
  */
 public Coordinate extraAttach;
//  public GameImage image;

 public MainBody()
 {
 }
 
	public MainBody(JSONObject obj) throws JSONException {
		cannonAttach = new Coordinate(obj.getString("cannonAttach"));
		engineAttach = new Coordinate(obj.getString("engineAttach"));
		extraAttach = new Coordinate(obj.getString("extraAttach"));
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

 public String toString() {
	StringBuilder res = new StringBuilder();
	res.append(" cannonAttach: ");
	res.append(cannonAttach.toString());
	res.append(" engineAttach: ");
	res.append(engineAttach.toString());
	res.append(" extraAttach: ");
	res.append(extraAttach.toString());
	res.append(" image: ");
	res.append(image.toString());
	return res.toString();
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


 public Coordinate getCannonAttach() {
  return cannonAttach;
 }

 public void setCannonAttach(Coordinate cannonAttach) {
  this.cannonAttach = cannonAttach;
 }

 public Coordinate getEngineAttach() {
  return engineAttach;
 }

 public void setEngineAttach(Coordinate engineAttach) {
  this.engineAttach = engineAttach;
 }

 public Coordinate getExtraAttach() {
  return extraAttach;
 }

 public void setExtraAttach(Coordinate extraAttach) {
  this.extraAttach = extraAttach;
 }

 public GameImage getImage() {
  return image;
 }

 public void setImage(GameImage image) {
  this.image = image;
 }
}
