package edu.byu.cs.superasteroids.model;

/**
 * This class abstracts the notion of a coordinate.
 * It reduces code redundancy and provides a useful place to stick future coordinate geometry functions.
 */

class Coordinate {
 public int x;
 public int y;

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