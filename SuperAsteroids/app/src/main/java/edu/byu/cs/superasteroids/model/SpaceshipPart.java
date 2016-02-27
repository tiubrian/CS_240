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
 
}