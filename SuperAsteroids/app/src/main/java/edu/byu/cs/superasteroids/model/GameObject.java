package edu.byu.cs.superasteroids.model;

import edu.byu.cs.superasteroids.drawing.DrawingHelper;
import edu.byu.cs.superasteroids.content.ContentManager;
import edu.byu.cs.superasteroids.model.ViewPort.Wall;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.util.Log;
import java.util.ArrayList;

abstract class GameObject {
  MovingState state;
  float scale;
  /**
  * The orientation to draw the object at.
  */
  float theta;
  Coordinate dim;
  boolean deleted;
  public final static String tag = "superasteroidsbounded";
  
  
  public GameObject()
  {
   theta = (float)0.0;
   dim = null;
   scale = (float)1.;
   deleted = false;
  }
  
  public abstract void onWallCollision(Wall w);
  
  public abstract void initDimension();
  
  public void removeFromGame()
  {
   deleted = true;
  }
  
  /**
  * Functions to simluate collision behavior. By default they do nothing.
  * The touch function should only modify the object, not the argument.
  */
  
  public void touch(Projectile p)
  {
   
  }

  public void touch(SpaceShip s)
  {
   
  }
 
  public void touch(Asteroid a)
  {
   
  }

  
  /**
  * Get the corners of the object, in world coordinates.
  */
  public ArrayList<Coordinate> getCorners()
  {
    ArrayList<Coordinate> corners = getUnrotatedCorners();
    Coordinate center = getCenter();

    for (int i = 0; i < corners.size(); i++)
    {
      //Becuase of android's insistence on clockwise rotation
      corners.get(i).rotateAboutAnchor(theta, center);
    }
    
    return corners;
  }
  
  public ArrayList<Coordinate> getUnrotatedCorners()
  {
    Coordinate center = getCenter();
    ArrayList<Coordinate> corners = new ArrayList<Coordinate>();
    corners.add(new Coordinate(center.x - dim.x/2, center.y - dim.y/2));
    corners.add(new Coordinate(center.x + dim.x/2, center.y - dim.y/2));
    corners.add(new Coordinate(center.x + dim.x/2, center.y + dim.y/2));
    corners.add(new Coordinate(center.x - dim.x/2, center.y + dim.y/2));
    
    for (int i = 0; i < corners.size(); i++)
    {
      corners.get(i).rescale(scale);
    }
    return corners;
  }
  
  public void checkWallCollision()
  {
    ArrayList<Coordinate> corners = getCorners();
    
    for (int i = 0; i < corners.size(); i++)
    {
      Wall result = ViewPort.wallViolated(corners.get(i));
      // only handle wall collision if it actually occured
      // only handle one wall collision, not two
      if (result != Wall.NONE) { onWallCollision(result); break;}
    }
  }
  
  public Coordinate getCenter()
  {
    return state.getPos();
  }
  
  /**
  * Get the center of the object, in view coordinates
  */
  public Coordinate getViewCenter()
  {
    return ViewPort.fromWorld(getCenter());
  }

  public float getRotation()
  {
    return this.theta;
  }
  
  public void update(double elapsedTime)
  {
    if (dim == null) initDimension();
    checkWallCollision();
  }
  
  public boolean collidesWith(GameObject other)
  {
    int i;
    ArrayList<Coordinate> other_corners = other.getCorners();
    for (i = 0; i < other_corners.size(); i++)
    {
      if (containsPoint(other_corners.get(i))) return true;
    }
    
    ArrayList<Coordinate> corners = getCorners();
    for (i = 0; i < corners.size(); i++)
    {
      if (other.containsPoint(corners.get(i))) return true;
    }
    return false;
  }
  
  public boolean containsPoint(Coordinate point)
  {
    if (dim == null) initDimension();
    Coordinate off = Coordinate.subtract(point, getCenter());
    // The idea is that the unrotated corners live in 
    off.rotate(-this.theta);
    if ((off.x > scale*dim.x/2) || (off.x < - scale*dim.x / 2)) return false;
    if ((off.y > scale*dim.y/2) || (off.y < - scale*dim.y / 2)) return false;
    return true;
  }
  
  public int getImageId()
  {
    return -1;
  }
  
  public static final int alpha = 255;
  
  public void draw()
  {
    Coordinate view_center = ViewPort.fromWorld(getCenter());
    Log.e(tag, "Drawing Asteroid with Center "+view_center.toString() +
    " id "+Integer.toString(getImageId()));
    DrawingHelper.drawImage(getImageId(), (float)view_center.x, (float)view_center.y,
      theta, scale, scale, alpha);
  }
  
}