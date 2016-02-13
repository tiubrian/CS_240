package edu.byu.cs.superasteroids.model;

/**
 * An asteroidType. Used to model different types of asteroids, without resorting to inheritance.
 */
public class AsteroidType {
 public String name;
 public String type;
 public GameImage image;
 public int id;

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