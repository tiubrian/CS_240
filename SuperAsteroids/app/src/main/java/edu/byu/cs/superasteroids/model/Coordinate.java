package edu.byu.cs.superasteroids.model;

/**
 * This class abstracts the notion of a coordinate.
 * It reduces code redundancy and provides a useful place to stick future coordinate geometry functions.
 */

public class Coordinate {
 public int x;
 public int y;

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

 public Coordinate scale(float xscale, float yscale)
 {
  return new Coordinate(x*xscale, y*yscale);
 }
 
 public Coordinate increment(Coordinate other)
 {
  return new Coordinate(other.x+x, other.y+y);
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