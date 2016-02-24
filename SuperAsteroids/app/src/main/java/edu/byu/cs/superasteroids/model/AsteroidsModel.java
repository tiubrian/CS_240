package edu.byu.cs.superasteroids.model;
import edu.byu.cs.superasteroids.database.*;
import edu.byu.cs.superasteroids.content.ContentManager;
import android.database.sqlite.*;
import android.content.Context;
import android.util.Log;

import java.util.ArrayList;


public class AsteroidsModel
{
	private static AsteroidsModel singleton = null;
	public ArrayList<Cannon>  cannons;
	public ArrayList<ExtraPart> extra_parts;
	public ArrayList<MainBody> main_bodies;
	public ArrayList<Engine> engines;
	public ArrayList<PowerCore> power_cores;
	public ArrayList<Level> levels;
	public SpaceShip ship;
	public static ContentManager manager = ContentManager.getInstance();
	public static String tag = "superasteroidsastmodel";
	
	public static AsteroidsModel getInstance()
	{
	  if (singleton == null) singleton = new AsteroidsModel();
	  return singleton;
	}
	
	public AsteroidsModel()
	{
	  ship = new SpaceShip();
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

	
}