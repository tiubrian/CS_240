package edu.byu.cs.superasteroids.database;
import edu.byu.cs.superasteroids.model.*;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class ModelDAO {
/*
 * "Dao called Dao is not Dao" -- The Dao De Ching (or the Tao Te Ching), first line of the first chapter. 
 * So I call it ModelDAO :).
*/
	private SQLiteDatabase db;
	
	public ModelDAO(SQLiteDatabase db)
	{
		this.db = db;
	}
	
	public Level getLevelByNumber(int number)
	{
		Level result = new Level();
		return result;
	}
	
	public ArrayList<Engine> getEngines()
	{
		ArrayList<Engine> result = new ArrayList<Engine>();
		return result;
	}
	
	public ArrayList<ExtraPart> getExtraParts()
	{
		ArrayList<ExtraPart> result = new ArrayList<ExtraPart>();
		return result;

	}
	
	public ArrayList<MainBody> getMainBodies()
	{
		ArrayList<MainBody> result = new ArrayList<MainBody>();
		return result;
	}
	
	public ArrayList<Cannon> getCannons()
	{
		ArrayList<Cannon> result = new ArrayList<Cannon>();
		return result;
	}
	
	public boolean addLevel(Level level)
	{
		return true;
	}
	
	public boolean addExtraPart(ExtraPart extra)
	{
		return true;
	}
	
	public boolean addMainBody(MainBody body)
	{
		return true;
	}

	public boolean addCannon(Cannon cannon)
	{
		return true;
	}

	public boolean addPowerCore(PowerCore core)
	{
		return true;
	}
	
	public boolean addEngine(Engine engine)
	{
		return true;
	}
		
}
