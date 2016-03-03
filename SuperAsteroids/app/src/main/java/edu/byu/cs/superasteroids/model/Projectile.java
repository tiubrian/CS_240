package edu.byu.cs.superasteroids.model;
import edu.byu.cs.superasteroids.model.ViewPort.Wall;
import android.util.Log;
import java.util.*;

/**
 * The laser thing that blows up asteroids.
 * This class is responisble for much of the fun in the project.
 */
public class Projectile extends GameObject {

    GameImage image;
    public int damage;
    Cannon cannon;
    public final static String tag = "superasteroidsprojectile";
    public static float initSpeed = (float)30.0;
    public static float initScale = (float).2;
    
    public Projectile(Cannon cannon)
    {
      this.cannon = cannon;
      this.state = new MovingState();
      this.image = cannon.attackImage;
      this.scale = initScale;
      this.damage = cannon.damage;
      SpaceShip ship = AsteroidsModel.getInstance().ship;
      state.setPos(cannon.getWorldEmitPoint());
      Log.e(tag, "cannon Emit Point: "+ cannon.getWorldEmitPoint().toString());
      state.setPolar(ship.theta-90, initSpeed);
      this.setTheta(ship.theta);
    }

    public int getDamage()
    {
      return damage;
    }
    
    public void onWallCollision(Wall w)
    {
      Log.e(tag, "Hit wall "+ViewPort.wallToString(w));
      this.removeFromGame();
    }
    
    public void initDimension()
    {
      this.dim = image.getDimensions();
    }
    
    @Override
    public void update(double elapsedTime)
    {
      super.update(elapsedTime);
      state.update(elapsedTime);
      
      checkAsteroidCollision();
    }

  public void checkAsteroidCollision()
  {
    Iterator<Asteroid> it = AsteroidsModel.getInstance().getAsteroids().iterator();
    
    while (it.hasNext())
    {
      Asteroid a = it.next();
      if (collidesWith(a)) {
	a.touch(this);
	this.touch(a);
	Log.e(tag, "Collided with Asteroid!");
      }
    }
  }

  @Override
  public void touch(Asteroid a)
  {
    Log.e(tag,"I GOT ONE!!!!");
    removeFromGame();
  }

  
    @Override
    public int getImageId()
    {
      return image.getImageId();
    }

  @Override
  public float getXImageScale()
  {
    return image.getXScale();
  }
  
  @Override
  public float getYImageScale()
  {
    return image.getYScale();
  }
   
    
    @Override
    public void draw()
    {
      super.draw();
    }

}
