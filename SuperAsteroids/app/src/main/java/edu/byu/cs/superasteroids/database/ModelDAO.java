package edu.byu.cs.superasteroids.database;
import edu.byu.cs.superasteroids.model.*;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import android.util.Log;

/**
 * This class contains all methods to access the database.
 * "Dao called Dao is not Dao" -- The Dao De Ching (or the Tao Te Ching), first line of the first chapter.
 * So I call it ModelDAO :).
 *
 */

public class ModelDAO {
	private SQLiteDatabase db;
	private long lastInsertID;
	public final static String dbtag = "superasteroidsdbfoo";

	public ModelDAO(SQLiteDatabase db)
	{
		this.db = db;
		lastInsertID = -1;
	}

	/**
	 * Get level number number :);
	 * @param number
	 * @return
	 */
	public Level getLevelByNumber(int number)
	{
		Level result = new Level();
		return result;
	}

	private AsteroidType getAsteroidTypeFromCursor(Cursor cursor)
	{
	  AsteroidType asteroid_type = new AsteroidType();
	  asteroid_type.id = cursor.getInt(0);
	  asteroid_type.name = cursor.getString(1);
	  asteroid_type.image = new GameImage(cursor.getString(2),
	   cursor.getInt(3),
	   cursor.getInt(4));
	  
	  asteroid_type.type = cursor.getString(5);
	  return asteroid_type;
	}
	
	public ArrayList<Level> getLevels()
	{
		ArrayList<Level> res = new ArrayList<Level>();
		final String sel_SQL = "select number, title, hint, width, height, music, number"
			+" from level";
		
		
		Cursor cursor = db.rawQuery(sel_SQL, null);
		try {
			cursor.moveToFirst();
			while (!cursor.isAfterLast()) {
				Level level = new Level();
				int number = cursor.getInt(0);
				level.title = cursor.getString(1);
				level.hint = cursor.getString(2);
				level.width = cursor.getInt(3);
				level.height = cursor.getInt(4);
				level.musicFile = cursor.getString(5);
				
				addTypesToLevel(number, level);
				addObjectsToLevel(number, level);
				res.add(level);
				cursor.moveToNext();
			}
		}
		finally {
			cursor.close();
		}

		return res;
		
	}
	
	
	public ArrayList<Engine> getEngines()
	{
		ArrayList<Engine> result = new ArrayList<Engine>();
		final String SQL = "select baseSpeed, baseTurnRate, attachPoint, image, imageWidth, imageHeight"
			+ " from engine";

		Cursor cursor = db.rawQuery(SQL,null);
		try {
			cursor.moveToFirst();
			while (!cursor.isAfterLast()) {
				Engine engine = new Engine();

				engine.baseSpeed = cursor.getInt(0);
				engine.baseTurnRate = cursor.getInt(1);
				engine.attachPoint = new Coordinate(cursor.getString(2));
				engine.image = new GameImage(cursor.getString(3), cursor.getInt(4), cursor.getInt(5));

				result.add(engine);

				cursor.moveToNext();
			}
		}
		finally {
			cursor.close();
		}

		return result;
	}

	
	public ArrayList<PowerCore> getPowerCores()
	{
		ArrayList<PowerCore> result = new ArrayList<PowerCore>();
		final String SQL = "select cannonBoost, engineBoost, image"
			+ " from power_core";

		Cursor cursor = db.rawQuery(SQL,null);
		try {
			cursor.moveToFirst();
			while (!cursor.isAfterLast()) {
				PowerCore power_core = new PowerCore();

				power_core.cannonBoost = cursor.getInt(0);
				power_core.engineBoost = cursor.getInt(1);
				power_core.image = new GameImage(cursor.getString(2), 0, 0);

				result.add(power_core);

				cursor.moveToNext();
			}
		}
		finally {
			cursor.close();
		}

		return result;
	}

	
		/**
		 * Really ought to have been called LeftWing, but ...
		 */
	public ArrayList<ExtraPart> getExtraParts()
	{
		ArrayList<ExtraPart> result = new ArrayList<ExtraPart>();
		final String SQL = "select attachPoint, image, imageWidth, imageHeight from extra_part";

		Cursor cursor = db.rawQuery(SQL, null);
		try {
			cursor.moveToFirst();
			while (!cursor.isAfterLast()) {
				ExtraPart extra_part = new ExtraPart();

				extra_part.attachPoint = new Coordinate(cursor.getString(0));
				extra_part.image = new GameImage(cursor.getString(1), cursor.getInt(2), cursor.getInt(3));

				result.add(extra_part);

				cursor.moveToNext();
			}
		}
		finally {
			cursor.close();
		}

		return result;
	}

	public ArrayList<MainBody> getMainBodies()
	{
		ArrayList<MainBody> result = new ArrayList<MainBody>();
		final String SQL = "select cannonAttach, engineAttach, extraAttach, image, imageWidth, imageHeight from main_body";

		Cursor cursor = db.rawQuery(SQL, null);
		try {
			cursor.moveToFirst();
			while (!cursor.isAfterLast()) {
				MainBody main_body = new MainBody();

				main_body.cannonAttach = new Coordinate(cursor.getString(0));
				main_body.engineAttach = new Coordinate(cursor.getString(1));
				main_body.extraAttach = new Coordinate(cursor.getString(2));
				main_body.image = new GameImage(cursor.getString(3), cursor.getInt(4), cursor.getInt(5));

				result.add(main_body);

				cursor.moveToNext();
			}
		}
		finally {
			cursor.close();
		}
		return result;
	}

	public ArrayList<Cannon> getCannons()
	{
		ArrayList<Cannon> result = new ArrayList<Cannon>();
		final String SQL = "select attachPoint, emitPoint, image, imageWidth, imageHeight, attackImage, attackImageWidth, attackImageHeight, attackSound, damage from cannon";

		Cursor cursor = db.rawQuery(SQL,null);
		try {
			cursor.moveToFirst();
			while (!cursor.isAfterLast()) {
				Cannon cannon = new Cannon();

				cannon.attachPoint = new Coordinate(cursor.getString(0));
				cannon.emitPoint = new Coordinate(cursor.getString(1));
				cannon.image = new GameImage(cursor.getString(2), cursor.getInt(3), cursor.getInt(4));
				cannon.attackImage = new GameImage(cursor.getString(5),cursor.getInt(6), cursor.getInt(7));
				cannon.attackSound = cursor.getString(8);
				cannon.damage = cursor.getInt(9);

				result.add(cannon);

				cursor.moveToNext();
			}
		}
		finally {
			cursor.close();
		}

		return result;
	}

	public void clearCannons()
	{
		Log.e(dbtag,"Clearing Cannon");
		db.rawQuery("delete from cannon;",null);
	}

	public void close()
	{
	 if (db != null) db.close();
	}
	
	public void addObjectsToLevel(int number, Level level)
	{
  	  String[] params = new String[] {Integer.toString(number)};
	  final String sql = "select image, scale, position from background_object, level_object "
	  + " where level_number = ? and objectId = Id";
	  Cursor cur = db.rawQuery(sql, params);
	  try {
	    cur.moveToFirst();
	    while (!cur.isAfterLast())
	    {
	      BackgroundObject obj = new BackgroundObject(cur.getString(0),
	        (float)cur.getDouble(1),
	        cur.getString(2));
	      level.addBackgroundObject(obj);
	      cur.moveToNext();
	    }
	  }
	  finally {cur.close();}	
	}
	
	public void addTypesToLevel(int number, Level level)
	{
	  String sql = "select id, name, image, imageWidth, imageHeight, type "
	    + ", number_of_asteroids from level_asteroid, asteroid_type where "
	    + " level_asteroid.asteroidId = asteroid_type.id and "
	    + " level_asteroid.level_number = ?";
	  String[] params = new String[] {Integer.toString(number)};
	  Cursor cur =  db.rawQuery(sql, params);
	  try {
	    cur.moveToFirst();
	    while (!cur.isAfterLast())
	    {
	      int num = cur.getInt(6);
	      AsteroidType type = getAsteroidTypeFromCursor(cur);
	      level.addAsteroids(type, num);		    
	      cur.moveToNext();
	    }
	  }
	  finally {cur.close();}
	}

	public boolean addBackgroundObject(String image)
	{
		ContentValues values = new ContentValues();
		values.put("image", image);
		Log.e(dbtag, "adding bgobject");
		return do_insert("background_object", values);
	}

	
	public boolean addLevelAsteroid(long level_number, long asteroid_id, int number_of_asteroids)
	{
		ContentValues values = new ContentValues();
		values.put("level_number", level_number);
		values.put("number_of_asteroids", number_of_asteroids);
		values.put("asteroidId", asteroid_id);
		StringBuilder res = new StringBuilder();
		res.append(asteroid_id);
		res.append(" level num: ");
		res.append(level_number);
		Log.e(dbtag,res.toString());
		return do_insert("level_asteroid", values);
	}

	
	
	public boolean addLevelObject(long level_number, long object_id, String position, Double scale)
	{
		ContentValues values = new ContentValues();
		values.put("level_number", level_number);
		values.put("scale", scale);
		values.put("objectId", object_id);
		values.put("position", position);
		Log.e(dbtag, "adding level object "+position);
		return do_insert("level_object", values);
	}

	private boolean do_insert(String tablename,ContentValues values)
	{
		lastInsertID = db.insert(tablename, null, values);
		if (lastInsertID >= 0) {
			return true;
		}
		else {
			return false;
		}
	}

	public boolean addLevel(Level level)
	{
		ContentValues values = new ContentValues();
		values.put("title", level.title);
		values.put("hint", level.hint);
		values.put("width", level.width);
		values.put("height", level.height);
		values.put("music", level.musicFile);
		Log.e(dbtag, level.toString());
		return do_insert("level", values);
	}

	public boolean addExtraPart(ExtraPart extra_part)
	{
		Log.e(dbtag, extra_part.toString());
		ContentValues values = new ContentValues();
		values.put("attachPoint", extra_part.attachPoint.toString());
		values.put("image", extra_part.getImageName());
		values.put("imageWidth", extra_part.getImageWidth());
		values.put("imageHeight", extra_part.getImageHeight());

		lastInsertID = db.insert("extra_part", null, values);
		if (lastInsertID >= 0) {
			return true;
		}
		else {
			return false;
		}
		
//		return do_insert("extra_part", values);
	}

	public boolean addMainBody(MainBody main_body)
	{
		Log.e(dbtag, main_body.toString());
		ContentValues values = new ContentValues();
		values.put("cannonAttach", main_body.cannonAttach.toString());
		values.put("engineAttach", main_body.engineAttach.toString());
		values.put("extraAttach", main_body.extraAttach.toString());
		values.put("image", main_body.getImageName());
		values.put("imageWidth", main_body.getImageWidth());
		values.put("imageHeight", main_body.getImageHeight());

		  lastInsertID = db.insert("main_body", null, values);
		if (lastInsertID >= 0) {
			return true;
		}
		else {
			return false;
		}
	}

	public boolean addCannon(Cannon cannon)
	{
		Log.e(dbtag, cannon.toString());
		ContentValues values = new ContentValues();
		values.put("attachPoint", cannon.attachPoint.toString());
		values.put("emitPoint", cannon.emitPoint.toString());
		values.put("image", cannon.getImageName());
		values.put("imageWidth", cannon.getImageWidth());
		values.put("imageHeight", cannon.getImageHeight());
		values.put("attackImage", cannon.getAttackImageName());
		values.put("attackImageWidth", cannon.getAttackImageWidth());
		values.put("attackImageHeight", cannon.getAttackImageHeight());
		values.put("attackSound", cannon.attackSound);
		values.put("damage", cannon.damage);

		  lastInsertID = db.insert("cannon", null, values);
		if (lastInsertID >= 0) {
			return true;
		}
		else {
			return false;
		}
	}

	public boolean addPowerCore(PowerCore power_core)
	{
		Log.e(dbtag, power_core.toString());
		ContentValues values = new ContentValues();
		values.put("cannonBoost", power_core.cannonBoost);
		values.put("engineBoost", power_core.engineBoost);
		values.put("image", power_core.getImageName());

		 lastInsertID = db.insert("power_core", null, values);
		if (lastInsertID >= 0) {
			return true;
		}
		else {
			return false;
		}
	}

	public boolean addEngine(Engine engine)
	{
		ContentValues values = new ContentValues();
		Log.e(dbtag, engine.toString());
		values.put("baseSpeed", engine.baseSpeed);
		values.put("baseTurnRate", engine.baseTurnRate);
		values.put("attachPoint", engine.attachPoint.toString());
		values.put("image", engine.getImageName());
		values.put("imageWidth", engine.getImageWidth());
		values.put("imageHeight", engine.getImageHeight());

		lastInsertID = db.insert("engine", null, values);
		if (lastInsertID >= 0) {
			return true;
		}
		else {
			return false;
		}
	}

	public boolean addAsteroidType(AsteroidType asteroid_type)
	{
		ContentValues values = new ContentValues();
		Log.e(dbtag, asteroid_type.toString());
		values.put("name", asteroid_type.name);
		values.put("image", asteroid_type.getImageName());
		values.put("imageWidth", asteroid_type.getImageWidth());
		values.put("imageHeight", asteroid_type.getImageHeight());
		values.put("type", asteroid_type.type);

		lastInsertID = db.insert("asteroid_type", null, values);
		if (lastInsertID >= 0) {
			return true;
		}
		else {
			return false;
		}
	}

	public long getLastInsertID()
	{
		return lastInsertID;
	}
	
}
