package edu.byu.cs.superasteroids.model;
import edu.byu.cs.superasteroids.database.*;
import edu.byu.cs.superasteroids.content.ContentManager;
import android.database.sqlite.*;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import edu.byu.cs.superasteroids.drawing.DrawingHelper;
import java.util.ArrayList;
import edu.byu.cs.superasteroids.game.GameActivity;

public class AsteroidsModel
{
	private static AsteroidsModel singleton = null;
	public static Coordinate minimapDim = new Coordinate(150, 150);
	public static Coordinate minimapOff = new Coordinate(650, 10);
	public ArrayList<Cannon>  cannons;
	public ArrayList<ExtraPart> extra_parts;
	public ArrayList<MainBody> main_bodies;
	public ArrayList<Engine> engines;
	public ArrayList<PowerCore> power_cores;
	public ArrayList<Level> levels;
	public Level level;
	public SpaceShip ship;
	public int currentLevelNum;
        public GameActivity activity;
        public boolean level_init;
        public static int safeZoneDim = 200;

	
	public static ContentManager manager = ContentManager.getInstance();
	public static String tag = "superasteroidsastmodel";
	public static GameImage backgroundImage = new GameImage("images/space.bmp");
	
	public static AsteroidsModel getInstance()
	{
	  if (singleton == null) singleton = new AsteroidsModel();
	  return singleton;
	}
	
	public AsteroidsModel()
	{
	  ship = new SpaceShip();
	  currentLevelNum = -1;
	  activity = null;
	  level_init = false;
	}
	
	
	public void setActivity(GameActivity activity)
	{
	  Log.e(tag, "setting activity");
	  this.activity = activity;
	}
	
	public void initLevel(int levelInd)
	{
	  Log.e(tag, "Initiliazing level");
	  if (levelInd >= levels.size()) {endGame(); return;} //TODO: game over now
	  level = levels.get(levelInd);
	  ViewPort.setWorldDimensions((float)level.width, (float)level.height);
	  Log.e(tag, "Set World Dimensions");
	  ViewPort.setBackground(backgroundImage.getImageId());
	  Log.e(tag, "Set background");
	  ship.setCenter(ViewPort.getCenter());
	  Log.e(tag, "Set Center");
	  // create asteroids with random velocities and positions
	  level.initAsteroidStates();
	  currentLevelNum = levelInd;
	  Log.e(tag,"New level num "+currentLevelNum);
	  level_init = true;
	  try {
	   activity.runOnUiThread(new Runnable() {
	     public void run()
	     {
	       Toast.makeText(activity.getApplicationContext(), level.hint, Toast.LENGTH_LONG).show();
	     }
	    });
	  }
	  catch (Exception e)
	  {
	    Log.e(tag, e.toString() + " ex");
	  }
	}
	
	public void endGame()
	{
	  Log.e(tag, "game over");
	}
	
	public Level getCurrentLevel()
	{
	  return levels.get(currentLevelNum);
	}

	public ArrayList<Asteroid> getAsteroids()
	{
	  return getCurrentLevel().getAsteroids();
        }
	
	public Coordinate getMinimapDimension()
	{
	  return minimapDim;
	}
	
	public Coordinate getMinimapOffset()
	{
	  return minimapOff;
	}
	
	public PointF worldToMiniMap(Coordinate center)
	{
	  Coordinate offset = getMinimapOffset();
	  Coordinate dim = getMinimapDimension();
	  Coordinate wdim = ViewPort.worldDim;
	  Coordinate res = Coordinate.add(offset, center.scale(dim.x * ((float)1./wdim.x), dim.y*((float)1./wdim.y)));
	  return res.toPointF();
	}
	
	public static float minimapPSize = (float)4.;
	
	public void draw_minimap()
	{
	  ArrayList<Asteroid> asts = getAsteroids();
	  Coordinate offset = getMinimapOffset();
	  Coordinate dim = getMinimapDimension();
	  Rect r = new Rect(offset.x, offset.y, offset.x+dim.x, offset.y+dim.y);
	  DrawingHelper.drawRectangle(r, Color.RED, 255);
	  
	  for (int i = 0; i < asts.size(); i++)
	  {
	    DrawingHelper.drawPoint(worldToMiniMap(asts.get(i).getCenter()), 
	      minimapPSize, Color.GREEN, 255);
	  }
	  
	  DrawingHelper.drawPoint(worldToMiniMap(ship.getCenter()),
	  minimapPSize, Color.BLUE, 255);
	}
	
	public void draw()
	{
	  ViewPort.drawBackground();
	  level.draw();
	  ship.draw();
	  draw_status();
	  draw_minimap();
	}
	
	public void draw_status()
	{
	  Log.e(tag, "drawing status");
	  DrawingHelper.drawText(new Point(30,30), "Level "+Integer.toString(currentLevelNum+1)
	   + " Lives " + Integer.toString(ship.getLives()), Color.GREEN, (float)30.);
	}
	
	public void update(double elapsedTime)
	{
	  level.update(elapsedTime);
	  ship.update(elapsedTime);
	  Log.e(tag, "level " +Integer.toString(currentLevelNum) +
	    " number of asteroids "+Integer.toString(level.asteroids.size()));
	  if (level.asteroids.size() == 0)
	  {
	    Log.e(tag, "Moving from level "+Integer.toString(currentLevelNum));
	    initLevel(currentLevelNum+1);
	  }
	}
	
	public static SpaceShip getShip()
	{
	 return getInstance().ship;
	}
	
	public void populate(Context context)
	{
		DbOpenHelper helper = new DbOpenHelper(context);
		SQLiteDatabase db = helper.getWritableDatabase();
		ModelDAO DAO = new ModelDAO(db);
		Log.e(tag,"initialized database");
		cannons = DAO.getCannons();
		Log.e(tag,"initialized database");
		main_bodies = DAO.getMainBodies();
		Log.e(tag,"initialized database");
		engines = DAO.getEngines();
		Log.e(tag,"initialized database");
		power_cores = DAO.getPowerCores();
		Log.e(tag,"got power cores");
		extra_parts = DAO.getExtraParts();
		Log.e(tag,"got extras");
		levels = DAO.getLevels();
		Log.e(tag,"got levels");
		DAO.close();
	}
	
	public ArrayList<Integer> getCannonImages()
	{
	  ArrayList<Integer> res = new ArrayList<Integer>();
	  int i = 0;
	  for (i = 0; i < cannons.size(); i++)
	  {
	    res.add(cannons.get(i).getImageId());
	  }
	  return res;
	}

	public ArrayList<Integer> getEngineImages()
	{
	  ArrayList<Integer> res = new ArrayList<Integer>();
	  int i = 0;
	  for (i = 0; i < engines.size(); i++)
	  {
	    res.add(engines.get(i).getImageId());
	  }
	  return res;

	}

	public ArrayList<Integer> getPowerCoreImages()
	{
	  ArrayList<Integer> res = new ArrayList<Integer>();
	  int i = 0;
	  for (i = 0; i < power_cores.size(); i++)
	  {
	    res.add(power_cores.get(i).getImageId());
	  }
	  return res;

	}

	public ArrayList<Integer> getMainBodyImages()
	{
	  ArrayList<Integer> res = new ArrayList<Integer>();
	  int i = 0;
	  for (i = 0; i < main_bodies.size(); i++)
	  {
	    res.add(main_bodies.get(i).getImageId());
	  }
	  return res;

	}

	public ArrayList<Integer> getExtraPartImages()
	{
	  ArrayList<Integer> res = new ArrayList<Integer>();
	  int i = 0;
	  for (i = 0; i < extra_parts.size(); i++)
	  {
	    res.add(extra_parts.get(i).getImageId());
	  }
	  return res;

	}

    public void setShipMainBody(int index){
      ship.setBody(main_bodies.get(index));
    }
    
    public void setShipExtraPart(int index){
      ship.setExtraPart(extra_parts.get(index));
    }
    
    public void setShipCannon(int index){
      ship.setCannon(cannons.get(index));

    }
    
    public void setShipEngine(int index){
      ship.setEngine(engines.get(index));
      Log.e(tag, "Set Ship Engine");

    }
    
    public void setShipPowerCore(int index)
    {
      ship.power_core = power_cores.get(index);    
    }

	
}