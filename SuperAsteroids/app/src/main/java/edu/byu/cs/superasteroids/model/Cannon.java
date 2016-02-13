package edu.byu.cs.superasteroids.model;
import java.util.ArrayList;
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
 public String attackSound;

 /**
  * The list of projectiles currently in motion.
  */
 public ArrayList<Projectile> projectiles;

 public Cannon()
 {
 }

 public void update()
 {

 }

 public void draw()
 {

 }

 public String getAttackImageName() {
  return image.getName();
 }

 public void setAttackImageName(String name) {
  this.image.setName(name);
 }

 public int getAttackImageHeight()
 {
  return image.getHeight();
 }

 public int getAttackImageWidth()
 {
  return image.getWidth();
 }

 public String getImageName() {
  return image.getName();
 }

 public void setImageName(String name) {
  this.image.setName(name);
 }

 public int getImageHeight()
 {
  return image.getHeight();
 }

 public int getImageWidth()
 {
  return image.getWidth();
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

 public String getAttackSound() {
  return attackSound;
 }

 public void setAttackSound(String attackSound) {
  this.attackSound = attackSound;
 }
}