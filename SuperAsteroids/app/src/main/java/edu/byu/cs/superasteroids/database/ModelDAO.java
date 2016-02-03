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
		final String SQL = "select baseSpeed, baseTurnRate, attachPoint, image, imageWidth, imageHeight" 
			+ "from engine";
		
		Cursor cursor = db.rawQuery(SQL);
		try {
			cursor.moveToFirst();
			while (!cursor.isAfterLast()) {
				Engine engine = new Engine();
				
				engine.baseSpeed = cursor.getInt(0);
				engine.baseTurnRate = cursor.getInt(1);
				engine.attachPoint = new Coordinate(cursor.getString(2));
				engine.image = new GameImage(cursor.getString(3),cursor.getInt(4),cursor.getInt(5));
			
				cursor.add(engine);
				
				cursor.moveToNext();
			}
			
		finally {
			cursor.close();
		}

		return result;
	}
	
	public ArrayList<ExtraPart> getExtraParts()
	{
		ArrayList<ExtraPart> result = new ArrayList<ExtraPart>();
		final String SQL = "select attachPoint, image, imageWidth, imageHeight from extra_part";
		
		Cursor cursor = db.rawQuery(SQL);
		try {
			cursor.moveToFirst();
			while (!cursor.isAfterLast()) {
				ExtraPart extra_part = new ExtraPart();
				
				extra_part.attachPoint = new Coordinate(cursor.getString(0));
				extra_part.image = new GameImage(cursor.getString(1),cursor.getInt(2),cursor.getInt(3));

				cursor.add(extra_part);
				
				cursor.moveToNext();
			}
			
		finally {
			cursor.close();
		}

		return result;
	}
	
	public ArrayList<MainBody> getMainBodies()
	{
		ArrayList<MainBody> result = new ArrayList<MainBody>();
		Cursor cursor = db.rawQuery(SQL);
		try {
			cursor.moveToFirst();
			while (!cursor.isAfterLast()) {
				MainBody main_body = new MainBody();
				
				main_body.cannonAttach = new Coordinate(cursor.getString(0));
				main_body.engineAttach = new Coordinate(cursor.getString(1));
				main_body.extraAttach = new Coordinate(cursor.getString(2));
				main_body.image = new GameImage(cursor.getString(3),cursor.getInt(4), cursor.getInt(5));

				cursor.add(main_body);
				
				cursor.moveToNext();
			}
			
		finally {
			cursor.close();
		}
		return result;
	}
	
	public ArrayList<Cannon> getCannons()
	{
		ArrayList<Cannon> result = new ArrayList<Cannon>();

		Cursor cursor = db.rawQuery(SQL);
		try {
			cursor.moveToFirst();
			while (!cursor.isAfterLast()) {
				Cannon cannon = new Cannon();
				
				cannon.attachPoint = cursor.getString(0);
				cannon.emitPoint = cursor.getString(1);
				cannon.image = cursor.getString(2);
				cannon.imageWidth = cursor.getInt(3);
				cannon.imageHeight = cursor.getInt(4);
				cannon.attackImage = cursor.getString(5);
				cannon.attackImageWidth = cursor.getInt(6);
				cannon.attackImageHeight = cursor.getInt(7);
				cannon.attackSound = cursor.getString(8);
				cannon.damage = cursor.getInt(9);

				cursor.add(cannon);
				
				cursor.moveToNext();
			}
			
		finally {
			cursor.close();
		}
		
		return result;
	}
	
	public boolean addLevel(Level level)
	{
		return true;
	}
	
	public boolean addExtraPart(ExtraPart extra_part)
	{
		return true;
	}
	
	public boolean addMainBody(MainBody main_body)
	{
		return true;
	}

	public boolean addCannon(Cannon cannon)
	{
		return true;
	}

	public boolean addPowerCore(PowerCore power_core)
	{
		return true;
	}
	
	public boolean addEngine(Engine engine)
	{
		return true;
	}
		
}
