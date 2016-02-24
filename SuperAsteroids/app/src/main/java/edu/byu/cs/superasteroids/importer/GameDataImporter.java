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
/**
 * Imports the data needed to play the game.
 */
public class GameDataImporter implements IGameDataImporter {

			private Context context;
			private final static String importtag = "superasteroidsimportfoo";
	
			public GameDataImporter(Context context)
			{
				this.context = context;
			}
			
	    /**
     * Imports the data from the .json file the given InputStreamReader is connected to. Imported data
     * should be stored in a SQLite database for use in the ship builder and the game.
     * @param dataInputReader The InputStreamReader connected to the .json file needing to be imported.
     * @return TRUE if the data was imported successfully, FALSE if the data was not imported due
     * to any error.
     */
    public boolean importData(InputStreamReader dataInputReader)
		{
			Log.e(importtag,"called the importer");
		DbOpenHelper helper = new DbOpenHelper(this.context);
		SQLiteDatabase db = helper.getWritableDatabase();
		helper.onCreate(db);
		ModelDAO DAO = new ModelDAO(db);
			try {
				Log.e(importtag,"initialized the database");
				JSONObject root = new JSONObject(makeString(dataInputReader));

				Log.e(importtag,"Parsed ze root");
				JSONObject data = root.getJSONObject("asteroidsGame");

				Log.e(importtag,"Parsed up to the game");

				JSONArray extraParts = data.getJSONArray("extraParts");
				Log.e(importtag,"Parsed up to left wings");

				JSONArray cannons = data.getJSONArray("cannons");
				Log.e(importtag,"Parsed up to cannons");
				JSONArray mainBodies = data.getJSONArray("mainBodies");
				Log.e(importtag,"Parsed up to mainBodies");

				JSONArray powerCores = data.getJSONArray("powerCores");
				Log.e(importtag,"Parsed up to powerCores");

				JSONArray engines = data.getJSONArray("engines");
				Log.e(importtag,"Parsed up to engines");
				JSONArray asteroidTypes = data.getJSONArray("asteroids");
				Log.e(importtag,"Parsed up to asts");

				JSONArray backgroundObjects = data.getJSONArray("objects");
				Log.e(importtag,"Parsed up to bgos");

				JSONArray levels = data.getJSONArray("levels");
				Log.e(importtag,"Parse Successful");
			int i;
			long[] asteroidTypeIDs = new long[asteroidTypes.length()];
			long[] bgObjectIDS = new long[backgroundObjects.length()];

			
			for (i = 0; i < extraParts.length(); i++)
			{
				DAO.addExtraPart(new ExtraPart(extraParts.getJSONObject(i)));
			}

			for (i = 0; i < cannons.length(); i++)
			{
				DAO.addCannon(new Cannon(cannons.getJSONObject(i)));
				
			}
			
			for (i = 0; i < mainBodies.length(); i++)
			{
				DAO.addMainBody(new MainBody(mainBodies.getJSONObject(i)));
				
			}

			for (i = 0; i < powerCores.length(); i++)
			{
				DAO.addPowerCore(new PowerCore(powerCores.getJSONObject(i)));
				
			}

			for (i = 0; i < engines.length(); i++)
			{
				DAO.addEngine(new Engine(engines.getJSONObject(i)));
				
			}
			
			for (i = 0; i < asteroidTypes.length(); i++)
			{
				AsteroidType asteroid_type = new AsteroidType(asteroidTypes.getJSONObject(i));
				DAO.addAsteroidType(asteroid_type);
				long id = DAO.getLastInsertID();
				asteroidTypeIDs[i] = id; // save the inserted id
			}

			for (i = 0; i < backgroundObjects.length(); i++)
			{
				String bg_image = backgroundObjects.getString(i);
				DAO.addBackgroundObject(bg_image);
				bgObjectIDS[i] = DAO.getLastInsertID();
			}


			for (i = 0; i < levels.length(); i++)
			{
				JSONObject level_obj = levels.getJSONObject(i);
				Level level = new Level(level_obj);
				Log.e("superasteroidsparse",level.toString());
				DAO.addLevel(level);
				long level_db_id = level.number;

				JSONArray level_asteroids = level_obj.getJSONArray("levelAsteroids");
				int j;
				for (j = 0; j < level_asteroids.length(); j++)
				{
					JSONObject level_ast = level_asteroids.getJSONObject(j);
					int number_of_asteroids = level_ast.getInt("number");
					int asteroid_index = level_ast.getInt("asteroidId") - 1;
					long asteroid_db_id = asteroidTypeIDs[asteroid_index];
					DAO.addLevelAsteroid(level_db_id, asteroid_db_id, number_of_asteroids);
				}

				JSONArray level_objects = level_obj.getJSONArray("levelObjects");
				for (j = 0; j < level_objects.length(); j++)
				{
					JSONObject level_object = level_objects.getJSONObject(j);
					String position = level_object.getString("position");
					Double scale = level_object.getDouble("scale");
					int object_index = level_object.getInt("objectId") - 1;
					long object_db_id = bgObjectIDS[object_index];
					DAO.addLevelObject(level_db_id, object_db_id, position, scale);
				}
			}

			}
			catch (IOException e)
			{
				return false;
			}
			catch (JSONException e)
			{
			  Log.e(importtag,e.toString()+" jsonexception " + e.getMessage());
			}
			catch (Exception e)
			{
			  Log.e(importtag,e.toString()+" runtimeexception " + e.getMessage());			 
			 throw e;
			}
			return true;
		}
    
    /**
		 * Shamelessly copied from Dr. Rodham's Example
		 */
    private static String makeString(Reader reader) throws IOException {

        StringBuilder sb = new StringBuilder();
        char[] buf = new char[512];

        int n = 0;
        while ((n = reader.read(buf)) > 0) {
            sb.append(buf, 0, n);
        }

        return sb.toString();
    }
	
}
