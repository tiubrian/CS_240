package edu.byu.cs.superasteroids.model;

/**
 * The cannon of the ship (where all the fun stuff happens).
 * Shoots projectiles to destroy defenseless (but dangerous nonetheless) asteroids.
 */
public class Cannon {
 public GameImage image;
 /**
  * Damage dealt per projectile fired. Strongly correlated (coefficient=.9) with the awesomeness of the cannon.
  */
 public int damage;
 /**
  * Where the cannon attaches to the ship.
  */
 public Coordinate attachPoint;
 /**
  * Where projectiles are fired from.
  */
 public Coordinate emitPoint;
 /**
  * The projectile image file.
  */
 public GameImage attackImage;
 /**
  * The cannon noise.
  */
 public String attackSoundFile;
 
 public Cannon()
 {
 }

 public GameImage getImage() {
  return image;
 }

 public void setImage(GameImage image) {
  this.image = image;
 }

 public int getDamage() {
  return damage;
 }

 public void setDamage(int damage) {
  this.damage = damage;
 }

 public Coordinate getAttachPoint() {
  return attachPoint;
 }

 public void setAttachPoint(Coordinate attachPoint) {
  this.attachPoint = attachPoint;
 }

 public Coordinate getEmitPoint() {
  return emitPoint;
 }

 public void setEmitPoint(Coordinate emitPoint) {
  this.emitPoint = emitPoint;
 }

 public GameImage getAttackImage() {
  return attackImage;
 }

 public void setAttackImage(GameImage attackImage) {
  this.attackImage = attackImage;
 }

 public String getAttackSoundFile() {
  return attackSoundFile;
 }

 public void setAttackSoundFile(String attackSoundFile) {
  this.attackSoundFile = attackSoundFile;
 }
}