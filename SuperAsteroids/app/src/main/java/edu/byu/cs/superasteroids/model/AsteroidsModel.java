package edu.byu.cs.superasteroids.model;
import edu.byu.cs.superasteroids.database.*;
import android.database.sqlite.*;
import android.content.Context;
import android.util.Log;

import java.util.ArrayList;


public class AsteroidsModel
{
	public static AsteroidsModel singleton;
	public ArrayList<Cannon>  cannons;
	public ArrayList<ExtraPart> extra_parts;
	public ArrayList<MainBody> main_bodies;
	public ArrayList<Engine> engines;
	public ArrayList<PowerCore> power_cores;
	public ArrayList<Level> levels;
	public Context context;
	
	public void setContext(Context c)
	{
		this.context = c;
  }
	
	public void populate()
	{
		DbOpenHelper helper = new DbOpenHelper(this.context);
		SQLiteDatabase db = helper.getWritableDatabase();
		ModelDAO DAO = new ModelDAO(db);
		
		cannons = DAO.getCannons();
		main_bodies = DAO.getMainBodies();
		engines = DAO.getEngines();
		power_cores = DAO.getPowerCores();
		extra_parts = DAO.getExtraParts();
		levels = DAO.getLevels();
	}
}