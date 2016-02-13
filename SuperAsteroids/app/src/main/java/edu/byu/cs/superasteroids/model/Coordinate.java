package edu.byu.cs.superasteroids.model;

/**
 * This class abstracts the notion of a coordinate.
 * It reduces code redundancy and provides a useful place to stick future coordinate geometry functions.
 */

public class Coordinate {
 public int x;
 public int y;

 public Coordinate(String s)
 {
   String[] res = s.split(",");
   x = Integer.parseInt(res[0]);
   y = Integer.parseInt(res[1]);
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