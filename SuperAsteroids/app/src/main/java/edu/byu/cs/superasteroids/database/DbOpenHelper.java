package edu.byu.cs.superasteroids.database;

import android.content.Context;
import android.database.sqlite.*;
import android.util.Log;


public class DbOpenHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "asteroids.sqlite";
    private static final int DB_VERSION = 1;

    public DbOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

//    @Override
    public void onCreate(SQLiteDatabase db) {
				final String create_cannon = "CREATE TABLE IF NOT EXISTS cannon" + 
				"(" + 
				"attachPoint String," + 
				"emitPoint String," + 
				"image text," + 
				"imageWidth int," + 
				"imageHeight int," + 
				"attackImage text," + 
				"attackImageWidth int," + 
				"attackImageHeight int," + 
				"attackSound String," + 
				"damage int" + 
				");";
				
				Log.e("superasteroidsfoo:","Executing Create statement: "+create_cannon);
				
				db.execSQL(create_cannon);

			
				final String create_asteroid_type = "CREATE TABLE IF NOT EXISTS asteroid_type" + 
					"(" + 
					"id integer not null primary key autoincrement," + 
					"name String," + 
					"image text," + 
					"imageWidth int," + 
					"imageHeight int," + 
					"type String" + 
					");";
				
				db.execSQL(create_asteroid_type);

				final String create_background_object = "CREATE TABLE IF NOT EXISTS background_object" + 
				"(" + 
				"image string," + 
				"Id integer not null primary key autoincrement" + 
				");";
				db.execSQL(create_background_object);
				
				final String create_level_object = "CREATE TABLE IF NOT EXISTS level_object" + 
				"(" + 
				"level_number int ," + 
				"scale real," + 
				"objectId int," + 
				"position string" + 
				");";
				db.execSQL(create_level_object);
				
				final String create_level = "CREATE TABLE IF NOT EXISTS level" + 
				"(" + 
				"number integer not null primary key autoincrement," + 
				"title String," + 
				"hint String," + 
				"width int," + 
				"height int," + 
				"music String" + 
				");";
				db.execSQL(create_level);
				
				final String create_level_asteroid = "CREATE TABLE IF NOT EXISTS level_asteroid" + 
				"(" + 
				"levelnumber int," + 
				"asteroidId int" +
				"number_of_asteroids int" +
				");";
				db.execSQL(create_level_asteroid);
				
				final String create_main_body = "CREATE TABLE IF NOT EXISTS main_body" + 
				"(" + 
				"cannonAttach String," + 
				"engineAttach String," + 
				"extraAttach String," + 
				"image text," + 
				"imageWidth int," + 
				"imageHeight int" + 
				");";
				
				db.execSQL(create_main_body);
				
				final String create_extra_part = "CREATE TABLE IF NOT EXISTS extra_part" + 
				"(" + 
				"attachPoint String," + 
				"image text," + 
				"imageWidth int" + 
				"imageHeight int" + 
				");";

				db.execSQL(create_extra_part);
				final String create_engine = "CREATE TABLE IF NOT EXISTS engine" + 
				"(" + 
				"baseSpeed int," + 
				"baseTurnRate int," + 
				"attachPoint String," + 
				"image text," + 
				"imageWidth int," + 
				"imageHeight int" + 
				");";
				
				db.execSQL(create_engine);
				
				final String create_power_core = "CREATE TABLE IF NOT EXISTS power_core" + 
				"(" + 
				"cannonBoost int," + 
				"engineBoost int," + 
				"image text" + 
				");";
				db.execSQL(create_power_core);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        return;
    }
}
