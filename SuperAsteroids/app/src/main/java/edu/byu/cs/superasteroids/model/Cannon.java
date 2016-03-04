package edu.byu.cs.superasteroids.model;
import java.util.*;
import org.json.*;
import edu.byu.cs.superasteroids.game.InputManager;
import android.util.Log;

/**
 * The cannon of the ship (where all the fun stuff happens).
 * Shoots projectiles to destroy defenseless (but dangerous nonetheless) asteroids.
 */
public class Cannon  extends AttachablePart {
 public final static String tag = "superasteroidscannon";
 /**
 Enforce a 1/5 second delay between fires.
 */
 public static final double coolDown = .2;
 /**
  * Damage dealt per projectile fired. Strongly correlated (coefficient=.9) with the awesomeness of the cannon.
  */
 public int damage;
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
 /**
 * The time since we last fired the cannon.
 */
 public double lastFired;  

 public GameSound fireSound;
 
	public Cannon()
	{
	 projectiles = new ArrayList<Projectile>();
	 //Make sure that we can initially fire the cannon.
	 lastFired = 2*coolDown;
	 fireSound = null;
	}
 
	public Cannon(JSONObject obj) throws JSONException {
	        this();
		attachPoint = new Coordinate(obj.getString("attachPoint"));
		emitPoint = new Coordinate(obj.getString("emitPoint"));
		image = new GameImage(obj.getString("image"),
					Integer.parseInt(obj.getString("imageWidth")),
					Integer.parseInt(obj.getString("imageHeight")));


		attackImage = new GameImage(obj.getString("attackImage"),
					Integer.parseInt(obj.getString("attackImageWidth")),
					Integer.parseInt(obj.getString("attackImageHeight")));
		attackSound = obj.getString("attackSound");
		damage = Integer.parseInt(obj.getString("damage"));

	}

	public boolean equals(Cannon other)
	{
		return (damage==other.damage)
			&& (attackSound==other.attackSound)
			&& (attackImage.equals(other.attackImage))
			&& (image.equals(other.image))
			&& (attachPoint.equals(other.attachPoint))
			&& (emitPoint.equals(other.emitPoint));
	}
	
	public String toString() {
		StringBuilder res = new StringBuilder();
		res.append(" attachPoint: ");
		res.append(attachPoint.toString());
		res.append(" emitPoint: ");
		res.append(emitPoint.toString());
		res.append(" image: ");
		res.append(image.toString());
		res.append(" attackImage: ");
		res.append(attackImage.toString());
		res.append(" attackSound: ");
		res.append(attackSound);
		res.append(" damage: ");
		res.append(damage);
		return res.toString();
}

/*	
	public String toString()
	{
		StringBuilder res = new StringBuilder("Damage ");
		res.append(damage);
		res.append("emitPoint "+emitPoint.toStrng()+" attachPoint "+attachPoint.toString()+" image "+image.toString() + " attackImage "+attackImage.toString());
		return res.toString();
	}*/
	
 public void setAttach(MainBody body)
 {
  this.bodyAttach = body.cannonAttach;
 }

 public void initSound()
 {
   if (fireSound == null) fireSound = new GameSound(attackSound);
 }
 
 public void load()
 {
   initSound();
   fireSound.load();
 }
 
 public Coordinate getWorldEmitPoint()
 {
   SpaceShip ship = AsteroidsModel.getShip();
   Coordinate centerToEmit = Coordinate.subtract(emitPoint, image.Center());
   Coordinate shipCenterToEmit = Coordinate.add(getOffset(), centerToEmit);
   shipCenterToEmit.rescale(ship.scale);
   shipCenterToEmit.rotate(ship.theta);
   Log.e(tag, " relativeEmit: "+shipCenterToEmit.toString());
   return Coordinate.add(shipCenterToEmit, ship.getCenter());
 }
 
 public void playFireSound()
 {
   initSound();
   fireSound.play();
 }
	
public void update(double elapsedTime)
 {
   lastFired += elapsedTime;
   if (InputManager.firePressed) 
   {
     if (lastFired > coolDown) {
       playFireSound();
       fireProjectile();
       lastFired = 0.0;
       Log.e(tag, "Firing ");
     }
     
     Log.e(tag, " Fire Pressed");
   }
   
   for (int i = 0; i < projectiles.size(); i++)
   {
     projectiles.get(i).update(elapsedTime);
   }
   
   purge();
 }

 public void purge()
 {
   for (Iterator<Projectile> it = projectiles.iterator(); it.hasNext();)
   {
     Projectile p = it.next();
     if (p.deleted)
     {
       Log.e(tag, "deleting projectile ");
       it.remove();
     }
   }
 }
 
 public void draw()
 {
   for (int i = 0; i < projectiles.size(); i++)
   {
     projectiles.get(i).draw();
   }
 }

 public void fireProjectile()
 {
   Projectile p = new Projectile(this);
   projectiles.add(p);
 }
 
 public String getAttackImageName() {
  return attackImage.getName();
 }

 public void setAttackImageName(String name) {
  this.attackImage.setName(name);
 }

 public int getAttackImageHeight()
 {
  return attackImage.getHeight();
 }

 public int getAttackImageWidth()
 {
  return attackImage.getWidth();
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
