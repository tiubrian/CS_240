package edu.byu.cs.superasteroids.database;

import edu.byu.cs.superasteroids.model.*;
import edu.byu.cs.superasteroids.database.*;
import android.content.Context;
import android.database.sqlite.*;
import android.test.AndroidTestCase;
import org.junit.*;
import java.util.ArrayList;
import android.util.Log;

/**
 * This class was written so I could learn how to write a Junit test case, and debug some of the equals methods of my model classes.
 */
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
		Log.e("superasteroidsfoo", c.toString());
		Log.e("superasteroidsfoo", result.get(0).toString());
		DAO.addCannon(result.get(0));
		Cannon o = result.get(0);
		assertTrue(1 == 1);
		assertTrue(c.toString()+"damage", c.damage == o.damage);
		assertTrue(c.toString()+"aimage", c.attackImage.equals(o.attackImage));
		assertTrue(c.toString()+"sound", c.attackSound.equals(o.attackSound));
		assertTrue(c.toString()+"image", c.image.equals(o.image));
		assertTrue(c.toString()+"emitpoint", c.emitPoint.equals(o.emitPoint));
		assertTrue(c.toString()+"attach", c.attachPoint.equals(o.attachPoint));
		assertTrue(result.get(0).toString(),c.equals(result.get(0)));
		DAO.clearCannons();
	}

}