package edu.byu.cs.superasteroids.model;
import edu.byu.cs.superasteroids.content.ContentManager;
import android.util.Log;

import java.util.ArrayList;


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
 private int id;
 public static final String tag = "superasteroidsGameImage";

 public String toString()
 {
	 StringBuilder res = new StringBuilder();
	 res.append(" name: ");
	 res.append(name);
	 res.append(" width: ");
	 res.append(width);
	 res.append(" height: ");
	 res.append(height);
	 return res.toString();
 }
 
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
  id = -2;
 }

 public GameImage(String imagefile, int imageWidth, int imageHeight)
 {
  this();
  name = imagefile;
  height = imageHeight;
  width = imageWidth;
 }
 
 public int getImageId()
 {
  if (id < 0)
  {
    id = ContentManager.getInstance().loadImage(name);
    Log.e(tag, "Gave ContentManager "+name+" got "+Integer.toString(id));
    return id;
  }
  else return id;
 }

 public Coordinate Center()
 {
   return new Coordinate(width/2, height/2);
 }
 
}
