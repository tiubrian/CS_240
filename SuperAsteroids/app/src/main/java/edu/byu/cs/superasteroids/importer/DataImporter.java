package edu.byu.cs.superasteroids.importer;
import android.database.sqlite.SQLiteDatabase;
import edu.byu.cs.superasteroids.database.*;
import java.io.InputStreamReader;
import org.json.JSONArray;
import org.json.JSONObject;
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
    boolean importData(InputStreamReader dataInputReader)
		{
			ModelDAO DAO = new ModelDAO(new SQLiteDatabase());
			
			JSONObject root = new JSONObject(makeString(dataInputReader));
			JSONObject data = root.getJSONObject("asteroidsGame");
			JSONObject extraParts = data.getJSONArray("extraParts");
			JSONObject cannons = data.getJSONArray("cannons");
			JSONObject mainBodies = data.getJSONArray("mainBodies");
			JSONObject powerCores = data.getJSONArray("PowerCores");
			JSONObject engines = data.getJSONArray("engines");
			JSONObject asteroidTypes = data.getJSONArray("asteroids");
			JSONObject backgroundObjects = data.getJSONArray("objects");

			int i;
			long[] asteroidTypeIDs = new long[asteroidTypes.length()];
			
			
			for (i = 0; i < extraParts.length(); i++)
			{
				DAO.addExtraPart(new ExtraPart(extraParts[i]));
			}

			for (i = 0; i < cannons.length(); i++)
			{
				DAO.addCannon(new Cannon(cannons[i]));
				
			}
			
			for (i = 0; i < mainBodies.length(); i++)
			{
				DAO.addMainBody(new MainBody(mainBodies[i]));
				
			}

			for (i = 0; i < powerCores.length(); i++)
			{
				DAO.addPowerCore(new PowerCore(powerCores[i]));
				
			}

			for (i = 0; i < engines.length(); i++)
			{
				DAO.addEngine(new Engine(engines[i]));
				
			}
			
			for (i = 0; i < asteroidTypes.length(); i++)
			{
				AsteroidType asteroid_type = new AsteroidType(asteroidTypes[i]);
				DAO.addAsteroidType(asteroid_type);
				long id = DAO.getLastInsertID();
				asteroidTypeIDs[i] = id; // save the inserted id
			}

			for (i = 0; i < backgroundObjects.length(); i++)
			{
				DAO.addBackgroundObject(new BackgroundObject(extraParts[i]));
			}

			
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
