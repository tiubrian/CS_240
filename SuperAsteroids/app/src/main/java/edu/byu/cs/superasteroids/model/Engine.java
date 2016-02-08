package edu.byu.cs.superasteroids.model;

/**
 * The engine of the space ship. Has a lateral speed, turnrate, and image.
 */
public class Engine {
 public int baseSpeed;
 public int baseTurnRate;
 /**
  * Describes where to attach the engine image to the main body of the ship.
  */
 public Coordinate attachPoint;
 public GameImage image;

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