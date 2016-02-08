package edu.byu.cs.superasteroids.database;
import edu.byu.cs.superasteroids.model.*;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class ModelDAO {
/**
 * This class contains all methods to access the database.
 * "Dao called Dao is not Dao" -- The Dao De Ching (or the Tao Te Ching), first line of the first chapter.
 * So I call it ModelDAO :).
 *
*/
	private SQLiteDatabase db;

	public ModelDAO(SQLiteDatabase db)
	{
		this.db = db;
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

		/**
		 * Really ought to have been called LeftWing, but ...
		 */
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
		ContentValues values = new ContentValues();
		values.put("level_number", level_object.level_number);
		values.put("scale", level_object.scale);
		values.put("objectId", level_object.objectId);
		values.put("position", level_object.position);

		 long id = db.insert"level_object", null, values);
		if (id >= 0) {
			return true;
		}
		else {
			return false;
		}
	}

	public boolean addExtraPart(ExtraPart extra_part)
	{
		ContentValues values = new ContentValues();
		values.put("attachPoint", extra_part.attachPoint);
		values.put("image", extra_part.image);
		values.put("imageWidth", extra_part.imageWidth);
		values.put("imageHeight", extra_part.imageHeight);

		 long id = db.insert"extra_part", null, values);
		if (id >= 0) {
			return true;
		}
		else {
			return false;
		}
	}

	public boolean addMainBody(MainBody main_body)
	{
		ContentValues values = new ContentValues();
		values.put("cannonAttach", main_body.cannonAttach);
		values.put("engineAttach", main_body.engineAttach);
		values.put("extraAttach", main_body.extraAttach);
		values.put("image", main_body.image);
		values.put("imageWidth", main_body.imageWidth);
		values.put("imageHeight", main_body.imageHeight);

		 long id = db.insert"main_body", null, values);
		if (id >= 0) {
			return true;
		}
		else {
			return false;
		}
	}

	public boolean addCannon(Cannon cannon)
	{
		ContentValues values = new ContentValues();
		values.put("attachPoint", cannon.attachPoint);
		values.put("emitPoint", cannon.emitPoint);
		values.put("image", cannon.image);
		values.put("imageWidth", cannon.imageWidth);
		values.put("imageHeight", cannon.imageHeight);
		values.put("attackImage", cannon.attackImage);
		values.put("attackImageWidth", cannon.attackImageWidth);
		values.put("attackImageHeight", cannon.attackImageHeight);
		values.put("attackSound", cannon.attackSound);
		values.put("damage", cannon.damage);

		 long id = db.insert"cannon", null, values);
		if (id >= 0) {
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
		values.put("image", power_core.image);

		long id = db.insert"power_core", null, values);
		if (id >= 0) {
			return true;
		}
		else {
			return false;
		}
	}

	public boolean addEngine(Engine engine)
	{
		ontentValues values = new ContentValues();
		values.put("baseSpeed", engine.baseSpeed);
		values.put("baseTurnRate", engine.baseTurnRate);
		values.put("attachPoint", engine.attachPoint);
		values.put("image", engine.image);
		values.put("imageWidth", engine.imageWidth);
		values.put("imageHeight", engine.imageHeight);

		long id = db.insert"engine", null, values);
		if (id >= 0) {
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
		values.put("image", asteroid_type.image);
		values.put("imageWidth", asteroid_type.imageWidth);
		values.put("imageHeight", asteroid_type.imageHeight);
		values.put("type", asteroid_type.type);

		long id = db.insert"asteroid_type", null, values);
		if (id >= 0) {
			return true;
		}
		else {
			return false;
		}
	}

}
