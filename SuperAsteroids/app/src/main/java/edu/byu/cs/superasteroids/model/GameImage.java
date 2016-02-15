package edu.byu.cs.superasteroids.model;

/**
 * This class abstracts the notion of an image with height and width.
 * It's called GameImage to not be confused with regular images.
 * Basically this is going to belong to objects that must be manually drawn to the screen.
 */
public class GameImage
{
 private String name;
 private int height;
 private int width;

 public boolean equals(GameImage other)
 {
	 return (name==other.getName()) && (height==other.getHeight()) && (width==other.getWidth());
 }
 
 public int getWidth() {
  return width;
 }

 public void setWidth(int width) {
  this.width = width;
 }

 public void setHeight(int height) {

  this.height = height;
 }

 public int getHeight() {

  return height;
 }

 public String getName() {

  return name;
 }

 public void setName(String name) {
  this.name = name;
 }

 public GameImage()
 {

 }

 public GameImage(String imagefile, int imageWidth, int imageHeight)
 {
  name = imagefile;
  height = imageHeight;
  width = imageWidth;
 }

}
