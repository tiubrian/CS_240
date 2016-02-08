package edu.byu.cs.superasteroids.model;

/**
 * Models a level-asteroidType relationship. Contains the number of asteroids to generate at startup, as well as the type.
 */
public class LevelAsteroid {
 /**
  * The number of asteroids to make.
  */
 public int number;
 /**
  * The type of asteroids to make.
  */
 public AsteroidType type;

 public int getNumber() {
  return number;
 }

 public void setNumber(int number) {
  this.number = number;
 }

 public AsteroidType getType() {
  return type;
 }

 public void setType(AsteroidType type) {
  this.type = type;
 }
}