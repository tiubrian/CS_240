package edu.byu.cs.superasteroids.importer;

import edu.byu.cs.superasteroids.model.*;
import edu.byu.cs.superasteroids.database.*;
import android.database.sqlite.*;
import android.content.Context;
import android.util.Log;

import java.io.InputStreamReader;
import java.io.Reader;
import java.io.IOException;
import org.json.*;
import org.junit.Before;

import android.test.AndroidTestCase;
import java.util.ArrayList;

/**
 * Created by benjamin on 3/8/16.
 */
public class GameDataImporterTests extends AndroidTestCase {
    public GameDataImporter importer;
    public static String dataFile = "asteroid_types.json";
    public AsteroidsModel model;
    public static final String tag = "superasteroidstest";
    public boolean is_init = false;

    public void ensure_init()
    {
        if (is_init) return;
        else {
            try {importDataAndLoadToModel();}
            catch (IOException e)
            {
                assertTrue("IOException",false);
            }
            is_init = true;
        }

    }

    @Before
    public void importDataAndLoadToModel() throws IOException
    {
        importer = new GameDataImporter(getContext());
        importer.importData(new InputStreamReader(getContext().getAssets().open(dataFile)));
        model = AsteroidsModel.getInstance();
        model.populate(getContext());
        Log.e(tag, "loaded model");
    }


    public void testCounts()
    {
        ensure_init();
        Log.e(tag, "running testCounts");
        assertTrue("incorrect number of extras", model.extra_parts.size() == 2);
        assertTrue("incorrect number of cannons", model.cannons.size() == 2);
        assertTrue("incorrect number of bodies", model.main_bodies.size() == 2);
        assertTrue("incorrect number of engines", model.engines.size() == 2);
        assertTrue("incorrect number of power_cores", model.power_cores.size() == 2);
        assertTrue("incorrect number of levels", model.levels.size() == 3);
    }

    public void testLevels()
    {
        ensure_init();
        Level l0 = model.levels.get(0);
        Level l1 = model.levels.get(1);
        Level l2 = model.levels.get(2);

        AsteroidType expected;
        expected = new AsteroidType();
        expected.name = "regular";
        expected.type = "regular";
        expected.image = new GameImage("images/asteroids/asteroid.png", 169, 153);
        assertTrue("type 0 matches", expected.equals(l0.asteroids.get(0).type));
        assertTrue("");

        expected = new AsteroidType();
        expected.name = "growing";
        expected.type = "growing";
        expected.image = new GameImage("images/asteroids/blueasteroid.png", 161, 178);
        assertTrue(expected.toString()+" type 1 matches "+l1.asteroids.get(0).type.toString(), expected.equals(l1.asteroids.get(0).type));

        expected = new AsteroidType();
        expected.name = "octeroid";
        expected.type = "octeroid";
        expected.image = new GameImage("images/asteroids/asteroid.png", 169, 153);
        assertTrue("type 2 matches", expected.equals(l2.asteroids.get(0).type));


    }

    public void testCannons()
    {
        ensure_init();
        Cannon c1 = new Cannon();
        c1.attachPoint = new Coordinate(14,240);
        c1.emitPoint = new Coordinate(104,36);
        c1.image = new GameImage("images/parts/cannon1.png", 160, 360);
        c1.attackImage = new GameImage("images/parts/laser.png", 50, 250);
        c1.attackSound =  "sounds/laser.mp3";
        c1.damage = 1;
        assertTrue("cannon 1 mismatch", c1.equals(model.cannons.get(0)));


        Cannon c2 = new Cannon();
        c2.attachPoint = new Coordinate(19,137);
        c2.emitPoint = new Coordinate(184, 21);
        c2.image = new GameImage("images/parts/cannon2.png", 325, 386);
        c2.attackImage = new GameImage("images/parts/laser2.png", 105, 344);
        c2.attackSound =  "sounds/laser.mp3";
        c2.damage = 2;
        assertTrue("cannon 2 mismatch", c2.equals(model.cannons.get(1)));

    }

    public void testBodies()
    {
        ensure_init();
        MainBody b1 = new MainBody();
        b1.cannonAttach = new Coordinate("190,227");
        b1.engineAttach = new Coordinate("102,392");
        b1.extraAttach = new Coordinate("6,253");
        b1.image = new GameImage("images/parts/mainbody1.png", 200, 400);
        assertTrue(model.main_bodies.get(0).toString()+" body 1 mismatch  "+b1.toString(), b1.equals(model.main_bodies.get(0)));

        MainBody b2 = new MainBody();
        b2.cannonAttach = new Coordinate("143,323");
        b2.engineAttach = new Coordinate("85,459");
        b2.extraAttach = new Coordinate("26,323");
        b2.image = new GameImage("images/parts/mainbody2.png", 156, 459);
        assertTrue("body 2 mismatch", b2.equals(model.main_bodies.get(1)));

    }

    public void testExtras()
    {
        ensure_init();
        ExtraPart e1 = new ExtraPart();
        e1.attachPoint = new Coordinate("312,94");
        e1.image = new GameImage("images/parts/extrapart1.png", 320, 240);
        assertTrue("extra 1 mismatch", e1.equals(model.extra_parts.get(0)));


        ExtraPart e2 = new ExtraPart();
        e2.attachPoint = new Coordinate("310,124");
        e2.image =  new GameImage("images/parts/extrapart2.png", 331, 309);
        assertTrue("extra 2 mismatch", e2.equals(model.extra_parts.get(1)));
    }

    public void testPowerCores()
    {
        ensure_init();
        PowerCore p1 = new PowerCore();
        p1.cannonBoost = 10;
        p1.engineBoost = 10;
        p1.image =  new GameImage("images/Ellipse.png");
        assertTrue(p1.toString()+" power 1 mismatch ", p1.equals(model.power_cores.get(0)));


        PowerCore p2 = new PowerCore();
        p2.cannonBoost = 10;
        p2.engineBoost = 10;
        p2.image = new GameImage("images/Triangle.png");
        assertTrue("power 2 mismatch", p2.equals(model.power_cores.get(1)));
    }

    public void testEngines()
    {
        ensure_init();
        Engine e1 = new Engine();
        e1.baseSpeed =  350;
        e1.baseTurnRate = 270;
        e1.attachPoint = new Coordinate("106,6");
        e1.image = new GameImage("images/parts/engine1.png", 220, 160);
        assertTrue(e1.toString() +" engine 1 mismatch " + model.engines.get(0).toString(), e1.equals(model.engines.get(0)));


        Engine e2 = new Engine();
        e2.baseSpeed = 500;
        e2.baseTurnRate = 360;
        e2.attachPoint = new  Coordinate("107,7");
        e2.image = new GameImage( "images/parts/engine2.png", 208, 222);
        assertTrue("engine 2 mismatch", e2.equals(model.engines.get(1)));


    }
}
