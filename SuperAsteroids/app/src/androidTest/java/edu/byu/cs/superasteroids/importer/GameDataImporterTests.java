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


/**
 * Created by benjamin on 3/8/16.
 */
public class GameDataImporterTests extends AndroidTestCase {
    public GameDataImporter importer;
    public static String dataFile = "easy_win.json";

    @Before
    public void importDataAndLoadToModel() throws IOException
    {
        importer = new GameDataImporter(getContext());
        importer.importData(new InputStreamReader(getContext().getAssets().open(dataFile)));
        AsteroidsModel.getInstance().populate(getContext());
    }

    public void testExtras()
    {
        
    }
}
