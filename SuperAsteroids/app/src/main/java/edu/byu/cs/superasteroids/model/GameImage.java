package edu.byu.cs.superasteroids.model;
import edu.byu.cs.superasteroids.content.ContentManager;
import android.util.Log;
import android.graphics.Bitmap;

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
 private float xscale;
 private float yscale;
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
	 return (name.equals(other.getName())) && (height == other.getHeight()) && (width == other.getWidth());
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
  xscale = (float)1.;
  yscale = (float)1.;
 }

 public GameImage(String imagefile)
 {
  this();
  name = imagefile;
  width = 0;
  height = 0;
 }

 
 public float getXScale()
 {
   if (id <0) getImageId();
   return xscale;
 }
 
 public float getYScale()
 {
   if (id <0) getImageId();
   return yscale;
 }
 
 public GameImage(String imagefile, int imageWidth, int imageHeight)
 {
  this();
  name = imagefile;
  width = imageWidth;
  height = imageHeight;
 }
 
 public int getImageId()
 {
  if (id < 0)
  {
    id = ContentManager.getInstance().loadImage(name);
    Log.e(tag, "Gave ContentManager "+name+" got "+Integer.toString(id));
    Bitmap b = ContentManager.getInstance().getImage(getImageId());
    xscale = width / (float)b.getWidth();
    yscale = height / (float)b.getHeight();    
    return id;
  }
  else return id;
 }

 public Coordinate Center()
 {
   return new Coordinate(width/2, height/2);
 }
 
 public Coordinate getDimensions()
 {
   return new Coordinate(width, height);
 }
}
