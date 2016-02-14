package edu.byu.cs.superasteroids.model;

/**
 * A power core. A part of the space ship. This boosts the power of the engine and cannon.
 */
public class PowerCore {
 public int cannonBoost;
 public int engineBoost;
 public GameImage image;

	public PowerCore(JSONObject obj) {
		cannonBoost = Integer.parseInt(obj.getString("cannonBoost"));
		engineBoost = Integer.parseInt(obj.getString("engineBoost"));
		image = new GameImage(obj.getString("image"), 0, 0);

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


 public int getCannonBoost() {
  return cannonBoost;
 }

 public void setCannonBoost(int cannonBoost) {
  this.cannonBoost = cannonBoost;
 }

 public int getEngineBoost() {
  return engineBoost;
 }

 public void setEngineBoost(int engineBoost) {
  this.engineBoost = engineBoost;
 }

 public GameImage getImage() {
  return image;
 }

 public void setImage(GameImage image) {
  this.image = image;
 }

 public void update()
 {

 }

 public void draw()
 {

 }

}
