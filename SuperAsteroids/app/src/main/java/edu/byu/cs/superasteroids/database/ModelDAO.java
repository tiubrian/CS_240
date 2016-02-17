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

	public ArrayList<Engine> getEngines()
	{
		ArrayList<Engine> result = new ArrayList<Engine>();
		final String SQL = "select baseSpeed, baseTurnRate, attachPoint, image, imageWidth, imageHeight"
			+ "from engine";

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
		Log.e("superasteroidsfoo","Clearing Cannon");
		db.rawQuery("delete from cannon;",null);
	}
	
	public boolean addLevelAsteroid(long level_number, long asteroid_id, int number_of_asteroids)
	{
		ContentValues values = new ContentValues();
		values.put("level_number", level_number);
		values.put("number_of_asteroids", number_of_asteroids);
		values.put("asteroidId", asteroid_id);
		values.put("position", position);
		return do_insert("level_object", values);
	}

	public boolean addLevelObject(long level_number, long object_id, String position, Float scale)
	{
		ContentValues values = new ContentValues();
		values.put("level_number", level_number);
		values.put("scale", scale);
		values.put("objectId", object_id);
		values.put("position", position);
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
		values.put("number", level.number);
		values.put("title", level.title);
		values.put("hint", level.hint);
		values.put("width", level.width);
		values.put("height", level.height);
		values.put("music", level.musicFile);
		return do_insert("level", values);
	}

	public boolean addExtraPart(ExtraPart extra_part)
	{
		ContentValues values = new ContentValues();
		values.put("attachPoint", extra_part.attachPoint.toString());
		values.put("image", extra_part.getImageName());
		values.put("imageWidth", extra_part.getImageWidth());
		values.put("imageHeight", extra_part.getImageHeight());

		return do_insert("extra_part", values);
	}

	public boolean addMainBody(MainBody main_body)
	{
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
		values.put("id", asteroid_type.id);
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
