package edu.byu.cs.superasteroids.model;
import edu.byu.cs.superasteroids.drawing.DrawingHelper;
import edu.byu.cs.superasteroids.game.InputManager;
import edu.byu.cs.superasteroids.model.ViewPort.Wall;
import android.graphics.PointF;
import android.util.Log;
import java.util.ArrayList;

/**
 * The spaceship that meshes all of the parts.
 */
public class SpaceShip extends GameObject {
    public MainBody body;
    public Engine engine;
    public ExtraPart extra_part;
    public PowerCore power_core;
    public Cannon cannon;
    public final static String tag = "superasteroidsship";
    public static float builder_rotation = (float)0.0;
    public static float builder_xscale = (float).5;
    public static float builder_yscale = (float).5;
    public static int default_alpha = 255;    
    
    public static float default_xscale = (float).5;
    public static float default_yscale = (float).5;
    

    
    public SpaceShip()
    {
      super();
      MainBody body = null;
      Engine engine = null;
      ExtraPart extra_part = null;
      power_core = null;
      cannon = null;
      state = new MovingState();
      scale = (float)0.5;
//      Log.e(tag, "Created state" + state.getPos().toString());
    }
    
    
    public void setBody(MainBody body)
    {
      this.body = body;
      if (cannon != null) cannon.setBody(body);
      if (engine != null) engine.setBody(body);
      if (extra_part != null) extra_part.setBody(body);
    }
    
    public void setEngine(Engine engine)
    {
      this.engine = engine;
      engine.setBody(this.body);
    }
    
    public void setCannon(Cannon cannon)
    {
      this.cannon = cannon;
      cannon.setBody(this.body);
    }
    
    public void setExtraPart(ExtraPart extra_part)
    {
      this.extra_part = extra_part;
      extra_part.setBody(this.body);
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
      else if (state.getPos() == null) Log.e(tag, "somehow state.getPos() is null");
      state.setPos(c.x, c.y);
    }
    

    public ArrayList<AttachablePart> getAttachableParts()
    {
      ArrayList<AttachablePart> parts = new ArrayList<AttachablePart>();
      parts.add(engine);
      parts.add(cannon);
      parts.add(extra_part);
      return parts;
    }
    
    public void initDimension()
    {
      int max_x = body.maxX();
      int max_y = body.maxY();
      int min_y = body.minY();
      int min_x = body.minX();
      ArrayList<AttachablePart> parts = getAttachableParts();
      
      for (int i = 0; i < parts.size(); i++)
      {
        AttachablePart p = parts.get(i);
        int tx = p.maxX();
        int ty = p.maxY();
        if (tx > max_x) max_x = tx;
        if (ty > max_y) max_y = ty;
        
        tx = p.minY();
        ty = p.minX();
        if (tx < min_x) min_x = tx;
        if (ty < min_y) min_y = ty;
      }
      
      this.dim = new Coordinate(max_x - min_x,max_y - min_y);
    }
    
    public static void drawShipImage(Coordinate pos, int id, float rotation, float xscale, float yscale)
    {
      DrawingHelper.drawImage(id, (float)pos.getX(), (float)pos.getY(), rotation,  xscale, 
	yscale, default_alpha);
    }
    
    public void builder_draw(float x, float y)
    {
      drawShip(new Coordinate(x, y), builder_rotation, builder_xscale, builder_yscale); 
    }
    
    
    public void drawShipAttachment(Coordinate O, AttachablePart part, float rotation, float xscale, float yscale)
    {
     //do magic 
      Coordinate offset = part.getOffset().scale(xscale, yscale);
//      Log.e(tag, "Adding part "+part.toString()+ " with offset "+offset.toString());
      offset.rotate(rotation);
  //    Log.e(tag, "Angle "+Float.toString(rotation)+" Rotated Offset: "+offset.toString());
      drawShipImage(Coordinate.add(O, offset),
	part.getImageId(), rotation, xscale, yscale);
    }
    
    
    public void drawShip(Coordinate center, float rotation, float xscale, float yscale)
    {
      if (body != null) drawShipImage(center, body.getImageId(), rotation, xscale, yscale);
      if (engine != null) drawShipAttachment(center, engine, rotation, xscale, yscale);
      if (cannon != null) drawShipAttachment(center, cannon, rotation, xscale, yscale);
      if (extra_part != null) drawShipAttachment(center, extra_part, rotation, xscale, yscale);
    }
    
    @Override
    public void draw()
    {
      Log.e(tag, "drawing ship world center "+getCenter().toString()+" view center "+getViewCenter().toString());
      drawShip(getViewCenter(), getRotation(), scale, scale);
//      drawShip(getViewCenter(), (float)45., default_xscale, default_yscale);
      //so we can draw the projectiles
      cannon.draw();
    }

    public static float speedScale = (float).1;
    public static float minDirectionDist = (float)30.;
    
    @Override
    public void update(double elapsedTime)
    {
      super.update(elapsedTime);
      
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
      cannon.update(elapsedTime);
      state.update(elapsedTime);
      ViewPort.setCenter(getCenter());
    }

    public void onWallCollision(Wall wall)
    {
      Log.e(tag, " collided with wall "+ViewPort.wallToString(wall));
      //TODO: figure out something more intelligent to do here
      state.stopMoving();
    }
    
    public float getSpeed()
    {
      return (float)(engine.baseSpeed + power_core.engineBoost)*speedScale;
    }
}
