package edu.byu.cs.superasteroids.importer;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;
import edu.byu.cs.superasteroids.database.*;
import edu.byu.cs.superasteroids.model.*;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.IOException;
import org.json.*;
/**
 * Imports the data needed to play the game.
 */
public class GameDataImporter implements IGameDataImporter {

	    /**
     * Imports the data from the .json file the given InputStreamReader is connected to. Imported data
     * should be stored in a SQLite database for use in the ship builder and the game.
     * @param dataInputReader The InputStreamReader connected to the .json file needing to be imported.
     * @return TRUE if the data was imported successfully, FALSE if the data was not imported due
     * to any error.
     */
    public boolean importData(InputStreamReader dataInputReader)
		{
			DbOpenHelper helper = new DbOpenHelper(getContext());
			ModelDAO DAO = new ModelDAO(helper.getWritableDatabase());
			try {
				JSONObject root = new JSONObject(makeString(dataInputReader));
				JSONObject data = root.getJSONObject("asteroidsGame");
				JSONArray extraParts = data.getJSONArray("extraParts");
				JSONArray cannons = data.getJSONArray("cannons");
				JSONArray mainBodies = data.getJSONArray("mainBodies");
				JSONArray powerCores = data.getJSONArray("PowerCores");
				JSONArray engines = data.getJSONArray("engines");
				JSONArray asteroidTypes = data.getJSONArray("asteroids");
				JSONArray backgroundObjects = data.getJSONArray("objects");
				JSONArray levels = data.getJSONArray("levels");

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
				DAO.addBackgroundObject(new BackgroundObject(backgroundObjects.getJSONObject(i)));
				bgObjectIDS[i] = DAO.getLastInsertID();
			}


			for (i = 0; i < levels.length(); i++)
			{
				JSONObject level_obj = levels.getJSONObject(i);
				Level level = new Level(level_obj);
				DAO.addLevel(level);
				long level_db_id = level.number;

				JSONArray level_asteroids = levels.getJSONObject(i).getJSONArray("levelAsteroids");
				int j;
				for (j = 0; j < level_asteroids.length(); j++)
				{
					JSONObject level_ast = level_asteroids.getJSONObject(j);
					int number_of_asteroids = level_ast.getInt("number");
					int asteroid_index = level_ast.getInt("asteroidId") - 1;
					long asteroid_db_id = asteroidTypeIDs[asteroid_index];
					DAO.addLevelAsteroid(level_db_id, asteroid_db_id, number_of_asteroids);
				}

				JSONArray level_objects = levels[i].getJSONArray("levelObjects");
				for (j = 0; j < level_objects.length(); j++)
				{
					JSONObject level_object = level_objects.getJSONObject(j);
					String position = level_object.getString("position");
					Float scale = level_object.getFloat("scale");
					int object_index = level_object.getInt("objectId");
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
