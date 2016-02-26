package edu.byu.cs.superasteroids.model;
import edu.byu.cs.superasteroids.drawing.DrawingHelper;
import edu.byu.cs.superasteroids.game.InputManager;
import android.graphics.PointF;
import android.util.Log;

/**
 * The spaceship that meshes all of the parts.
 */
public class SpaceShip {
    public MainBody body;
    public Engine engine;
    public ExtraPart extra_part;
    public PowerCore power_core;
    public Cannon cannon;
    public MovingState state;
    public final static String tag = "superasteroidsship";
    public float theta;

    public SpaceShip()
    {
      MainBody body = null;
      Engine engine = null;
      ExtraPart extra_part = null;
      power_core = null;
      cannon = null;
      state = new MovingState();
      theta = (float)0.0;
//      Log.e(tag, "Created state" + state.pos.toString());
    }
    
    public boolean isComplete()
    {
     return ((body != null) &&
     (engine != null) &&
     (extra_part != null) &&
     (power_core != null) && (cannon != null));
    }
    
    public void setCenter(int x, int y)
    {
      state.setPos(x, y);
    }

    public void setCenter(Coordinate c)
    {
//      Log.e(tag, "setting center to "+c.toString());
      if (state == null) Log.e(tag, "state is null");
      else if (state.pos == null) Log.e(tag, "somehow state.pos is null");
      state.setPos(c.x, c.y);
    }
    
    /**
    * Get the center of the ship, in view coordinates
    */
    public Coordinate getViewCenter()
    {
      return ViewPort.fromWorld(state.pos);
    }

    public Coordinate getCenter()
    {
     return state.pos;
    }

    public static float builder_rotation = (float)0.0;
    public static float builder_xscale = (float).5;
    public static float builder_yscale = (float).5;
    public static int default_alpha = 255;    
    
    public static float default_xscale = (float).5;
    public static float default_yscale = (float).5;
    
    
    public static void drawShipImage(Coordinate pos, int id, float rotation, float xscale, float yscale)
    {
      DrawingHelper.drawImage(id, (float)pos.getX(), (float)pos.getY(), rotation,  xscale, 
	yscale, default_alpha);
    }
    
    public void builder_draw(float x, float y)
    {
      drawShip(new Coordinate(x, y), builder_rotation, builder_xscale, builder_yscale); 
    }
    
    
    public void drawShipAttachment(Coordinate O, Coordinate attach, AttachablePart part, float rotation, float xscale, float yscale)
    {
     //do magic 
      Coordinate offset = part.getOffset(body, attach).scale(xscale, yscale);
//      Log.e(tag, "Adding part "+part.toString()+ " with offset "+offset.toString());
      offset.rotate(rotation);
  //    Log.e(tag, "Angle "+Float.toString(rotation)+" Rotated Offset: "+offset.toString());
      drawShipImage(Coordinate.add(O, offset),
	part.getImageId(), rotation, xscale, yscale);
    }
    
    
    public void drawShip(Coordinate center, float rotation, float xscale, float yscale)
    {
      if (body != null) drawShipImage(center, body.getImageId(), rotation, xscale, yscale);
      if (engine != null) drawShipAttachment(center, body.engineAttach, engine, rotation, xscale, yscale);
      if (cannon != null) drawShipAttachment(center, body.cannonAttach, cannon, rotation, xscale, yscale);
      if (extra_part != null) drawShipAttachment(center, body.extraAttach, extra_part, rotation, xscale, yscale);
    }
    
    public void draw()
    {
//      Log.e(tag, "drawing ship");
      drawShip(getViewCenter(), getRotation(), default_xscale, default_yscale);
    }

    public static float speedScale = (float).1;
    public static float minDirectionDist = (float)30.;
    
    public void update()
    {
      if (InputManager.movePoint != null) {
        Coordinate movePoint = ViewPort.toWorld(new Coordinate(InputManager.movePoint));
        Log.e(tag, "center: "+ getCenter().toString() + " movePoint: "+movePoint.toString());
        Coordinate direction = Coordinate.subtract(movePoint, getCenter());
        this.theta = 90 + direction.getAngle(); //hack
        float norm = direction.norm();
        state.dx = direction.x*getSpeed()/norm;
        state.dy = direction.y*getSpeed()/norm;
        //avoid erratic oscillations
        if (norm < minDirectionDist) state.stopMoving(); 
   //     Log.e(tag, "dir "+direction.toString()+" velocity: dx "+Float.toString(state.dx)+" "+Float.toString(state.dy));
      }
      else {
       state.stopMoving();
      }
      state.update();
      ViewPort.setCenter(getCenter());
    }

    
    public float getSpeed()
    {
      return (float)(engine.baseSpeed + power_core.engineBoost)*speedScale;
    }
    
    public float getRotation()
    {
      return this.theta;
    }
}
