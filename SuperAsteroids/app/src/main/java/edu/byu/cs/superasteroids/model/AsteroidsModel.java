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
	public static ContentManager manager = ContentManager.getInstance();
	
	public static AsteroidsModel getInstance()
	{
	  if (singleton == null) singleton = new AsteroidsModel();
	  return singleton;
	}
	
	
	
	public void populate(Context context)
	{
		DbOpenHelper helper = new DbOpenHelper(context);
		SQLiteDatabase db = helper.getWritableDatabase();
		ModelDAO DAO = new ModelDAO(db);
		
		cannons = DAO.getCannons();
		main_bodies = DAO.getMainBodies();
		engines = DAO.getEngines();
		power_cores = DAO.getPowerCores();
		extra_parts = DAO.getExtraParts();
		levels = DAO.getLevels();
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