package edu.byu.cs.superasteroids.model;
import android.graphics.PointF;
import android.util.Log;	

/**
 * This class abstracts the notion of a coordinate.
 * It reduces code redundancy and provides a useful place to stick future coordinate geometry functions.
 */

public class Coordinate {
 public int x;
 public int y;
 public final static String tag = "superasteroidscoord";

 public Coordinate(int x_coord, int y_coord)
 {
	 x = x_coord;
	 y = y_coord;
 }

 public Coordinate(float x_coord, float y_coord)
 {
	 x = (int)x_coord;
	 y = (int)y_coord;
 }

 public Coordinate(PointF p)
 {
   this(p.x, p.y);
 }
 
 //distance under the L-infinity norm
 public static int sup_dist(Coordinate a, Coordinate b)
 {
  return Math.max(Math.abs(a.x-b.x), Math.abs(a.y-b.y));
 }
 
 public Coordinate(String s)
 {
   String[] res = s.split(",");
   x = Integer.parseInt(res[0]);
   y = Integer.parseInt(res[1]);
 }

 public static Coordinate add(Coordinate a, Coordinate b)
 {
  return new Coordinate(a.x+b.x, a.y+b.y);
 }

 public static Coordinate subtract(Coordinate a, Coordinate b)
 {
  return new Coordinate(a.x-b.x, a.y-b.y);
 }

 public void subtractFromSelf(Coordinate other)
 {
   this.x -= other.x;
   this.y -= other.y;
 }
 
 public void addToSelf(Coordinate other)
 {
   this.x += other.x;
   this.y += other.y;
 }
 
 public PointF toPointF()
 {
   return new PointF((float)x, (float)y);
 }
 
 
 public Coordinate scale(float xscale, float yscale)
 {
  return new Coordinate(x*xscale, y*yscale);
 }

 public void rescale(float scale)
 {
   x = (int)(scale*x);
   y = (int)(scale*y);
 }
 
 
 public Coordinate increment(Coordinate other)
 {
  return new Coordinate(other.x+x, other.y+y);
 }
 
 
 
 /**
 *Rotate the vector described by coordinate clockwise rotation degrees.
 *@param rotation: The rotation angle, in degrees.
 */
 public void rotate(float rotation)
 {
//   Log.e(tag, "Old ": this.toString() + " rotation");
   rotation = (float)Math.toRadians(rotation);
   int newx = (int)( Math.cos(rotation) * x - Math.sin(rotation) * y);
   int newy = (int)(  Math.sin(rotation) * x + Math.cos(rotation) * y);
   x = newx;
   y = newy;
   
 }

/**
 *Rotate the vector described by coordinate clockwise rotation degrees about anchor.
 *@param rotation: The rotation angle, in degrees.
 */

 public void rotateAboutAnchor(float rotationDegrees, Coordinate anchor)
 {
   Log.e(tag, "Old: "+this.toString() +
     " rotationDeg: " + Float.toString(rotationDegrees)
     + " anchor "+anchor);
   this.subtractFromSelf(anchor);
   this.rotate(rotationDegrees);
   this.addToSelf(anchor);
   Log.e(tag, "New: "+this.toString());
 }
 

 public float norm()
 {
  return (float)Math.sqrt(x*x + y*y);
 }
 
 public float getAngle()
 {
  return (float)Math.toDegrees(Math.atan2(y, x));
 }
 
 public boolean equals(Coordinate other)
 {
	 return (x == other.x) && (y == other.y); 
 }
 
 public void reset(int x, int y)
 {
    this.x = x;
    this.y = y;
 }
 
 public void reset(float x, float y)
 {
   this.x = (int)x;
   this.y = (int)y;
 }
 
 public String toString()
 {
  StringBuilder res = new StringBuilder();
  res.append(x);
  res.append(",");
  res.append(y);
  return res.toString();
 }

 public int getX() {
  return x;
 }

 public void setX(int x) {
  this.x = x;
 }

 public int getY() {
  return y;
 }

 public void setY(int y) {
  this.y = y;
 }
}