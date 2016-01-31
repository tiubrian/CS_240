package edu.byu.cs.superasteroids.database;
import edu.byu.cs.superasteroids.model.*;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

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
