package edu.byu.cs.superasteroids.model;

/**
 * A power core. A part of the space ship. This boosts the power of the engine and cannon.
 */
public class PowerCore {
 public int cannonBoost;
 public int engineBoost;
 public GameImage image;


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
