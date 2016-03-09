package edu.byu.cs.superasteroids.model;
import edu.byu.cs.superasteroids.database.*;
import edu.byu.cs.superasteroids.content.ContentManager;
import android.util.Log;

import java.util.ArrayList;

public abstract class SpaceshipPart
{

 public GameImage image;
 
 public int getImageId()
 {
   return image.getImageId();
 }

 public static final String tag = "superasteroidspart";
 
 /**
 * Return the minimum and maximum x and y values, relative to the center of the image.
 * For parts of type AttachablePart, these functions will be overriden to work relative to the center of the main body.
 */
 public int maxX()
 {
   return image.getWidth() / 2;
 }
 
 public int maxY()
 {
  return image.getHeight() / 2;
 }
 
 public int minX()
 {
  return -image.getWidth() / 2;
 }
 
 public int minY()
 {
  return -image.getHeight() / 2;
 }

 @Override
 public boolean equals(Object o) {
  if (this == o) return true;
  if (o == null || getClass() != o.getClass()) return false;

  SpaceshipPart that = (SpaceshipPart) o;

  Log.e(tag, " this image " +image.toString()+" other image "+that.image.toString());
  if (!image.equals(that.image)) Log.e(tag, "images don't match");
  return image.equals(that.image);

 }

 @Override
 public int hashCode() {
  return image.hashCode();
 }
}