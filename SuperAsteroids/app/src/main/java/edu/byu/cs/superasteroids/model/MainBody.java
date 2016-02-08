package edu.byu.cs.superasteroids.model;

/**
 * The main body of the ship. Has information on where to attach the other parts, and of course an image.
 */
public class MainBody {
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
 public GameImage image;

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
