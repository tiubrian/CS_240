package edu.byu.cs.superasteroids.model;
import edu.byu.cs.superasteroids.drawing.DrawingHelper;

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

    public SpaceShip()
    {
      MainBody body = null;
      Engine engine = null;
      ExtraPart extra_part = null;
      PowerCore power_core = null;
      Cannon cannon = null;
      MovingState state = new MovingState();
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
      state.setPos(c.x, c.y);
    }
    
    
    
    public void update()
    {

    }

    public static float builder_rot_degs = (float)0.0;
    public static float builder_xscale = (float).5;
    public static float builder_yscale = (float).5;
    public static int builder_alpha = 200;    
    
    public static void drawShipImage(Coordinate pos, int id)
    {
      DrawingHelper.drawImage(id, (float)pos.getX(), (float)pos.getY(),  builder_rot_degs,  builder_xscale, 
	builder_yscale, builder_alpha);     
    }
    
    public void builder_draw(float x, float y)
    {
      //O for the relative origin
      Coordinate O = new Coordinate(x, y);
      if (body != null) drawShipImage(O, body.getImageId());
      if (engine != null) drawShipAttachment(O, body.engineAttach, engine);
      if (cannon != null) drawShipAttachment(O, body.cannonAttach, cannon);
      if (extra_part != null) drawShipAttachment(O, body.extraAttach, extra_part);
    }
    
    
    public void drawShipAttachment(Coordinate O, Coordinate attach, AttachablePart part)
    {
     //do magic 
      Coordinate offset = part.getOffset(body, attach).scale(builder_xscale, builder_yscale);
//      Log.e(tag, "Adding part "+part.toString()+ " with offset "+offset.toString());
      drawShipImage(Coordinate.add(O, offset),
        part.getImageId() );
    }
    
    public void draw()
    {
    }

    

    
}
