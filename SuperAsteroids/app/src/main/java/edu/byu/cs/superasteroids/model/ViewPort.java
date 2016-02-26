package edu.byu.cs.superasteroids.model;

import edu.byu.cs.superasteroids.drawing.DrawingHelper;
import edu.byu.cs.superasteroids.content.ContentManager;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.util.Log;

public class ViewPort {
 public static String tag = "superasteroidsviewport";
 public static Coordinate dim;
 public static Coordinate worldDim;
 public static int backgroundImageId;
 /**
 * The coordinate of the upper left corner of the viewport, in world coordinates.
 */
 public static Coordinate offset = new Coordinate(1800,800);
 
 public static boolean inViewPort(Coordinate coord)
 {
   if ((coord.x < offset.x) || (coord.x > offset.x + dim.x)) return false;
   if ((coord.y < offset.y) || (coord.y > offset.y + dim.y)) return false;
   return true;
 }
 
 public static Coordinate fromWorld(Coordinate coord)
 {
   return Coordinate.subtract(coord, offset); 
 }
 
 public static Coordinate toWorld(Coordinate coord)
 {
   return Coordinate.add(offset, coord);
 }
 
 public static void setCenter(Coordinate newCenter)
 {
   Coordinate newOffset = new Coordinate(newCenter.x -  dim.x/2, newCenter.y + dim.y/2);
   if (validOffset(offset)) offset = newOffset;
 }

 
 public static Coordinate getCenter()
 {
   return new Coordinate(offset.x+dim.x/2, offset.y + dim.y/2);
 }
 
 public static boolean validOffset(Coordinate c)
 {
   return boxInWorld(c.x, c.y, dim.x, dim.y); 
 }
 
 public static boolean boxInWorld(int x, int y, int w, int h)
 {
   if (x + w > worldDim.x) return false;
   if (x < worldDim.x) return false;
   if (y + h > worldDim.y) return false;
   if (y < worldDim.y) return false;
   return true;
 }
 
 public static void setWorldDimensions(float newx, float newy)
 {
   worldDim = new Coordinate(newx, newy);
   Log.e(tag, "World Dimensions: " + worldDim.toString());
 }

 public static float xscale = (float)1.;
 public static float yscale = (float)1.;
 
 public static void setBackground(int imageId)
 {
   Log.e(tag, "Calling setBackground");
   dim = new Coordinate(DrawingHelper.getGameViewWidth(),
    DrawingHelper.getGameViewHeight());
   Log.e(tag," computed dimensions: "+dim.toString());
   backgroundImageId = imageId;
   Bitmap image = ContentManager.getInstance().getImage(imageId);
   Log.e(tag, "Got bitmap.");
   Coordinate imDim = new Coordinate(image.getWidth(), image.getHeight());
   Log.e(tag, "Got Dimensions of Background Image: "+imDim.toString());
   xscale = (float)imDim.x/worldDim.x;
   yscale = (float)imDim.y/worldDim.y;
 }
 
 /**
 * Draws a portion of the background image to the screen
 */
 public static void drawBackground()
 {
  // Log.e(tag, "dim: "+dim.toString() + " , offset " + offset.toString());
   Rect src = new Rect((int)(offset.x*xscale), (int)(offset.y*yscale),
    (int)((offset.x+dim.x)*xscale), (int)((offset.y+dim.y)*yscale));
//   Log.e(tag, "drawing background "+src.toString());
   DrawingHelper.drawImage(backgroundImageId, src, null);
 }
}