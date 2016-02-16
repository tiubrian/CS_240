package edu.byu.cs.superasteroids.database;

import edu.byu.cs.superasteroids.model.*;
import edu.byu.cs.superasteroids.database.*;
import android.content.Context;
import android.database.sqlite.*;
import android.test.AndroidTestCase;
import org.junit.*;
import java.util.ArrayList;
import android.util.Log;

public class ModelDAOTests extends AndroidTestCase {

	public void testCannonInsert()
	{
		DbOpenHelper helper = new DbOpenHelper(getContext());
		SQLiteDatabase db = helper.getWritableDatabase();
		ModelDAO DAO = new ModelDAO(db);
		Cannon c = new Cannon();
		c.image = new GameImage("foo.png",200,200);
		c.attackImage = new GameImage("projectile.png", 30,30);
		c.damage = 10;
		c.attachPoint = new Coordinate(100,100);
		c.emitPoint = new Coordinate(200,200);
		c.attackSound = "laser.ogg";
		DAO.clearCannons();
		DAO.addCannon(c);
		ArrayList<Cannon> result = DAO.getCannons();
		Log.e("superasteroidsfoo",c.toString());
		Log.e("superasteroidsfoo",result.get(0).toString());
		DAO.addCannon(result.get(0));
		assertTrue(c.equals(result.get(0)));
		DAO.clearCannons();
	}
}