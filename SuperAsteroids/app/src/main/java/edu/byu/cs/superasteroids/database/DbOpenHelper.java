package edu.byu.cs.superasteroids.database;

import android.content.Context;
import android.database.sqlite.*;


public class DbOpenHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "asteroids.sqlite";
    private static final int DB_VERSION = 1;

    public DbOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

//    @Override
    public void onCreate(SQLiteDatabase db) {

        final String SQL =
								"CREATE TABLE IF NOT EXISTS asteroid_type" + 
								"(" + 
								"id int not null primary key autoincrement," + 
								"name String," + 
								"image text," + 
								"imageWidth int," + 
								"imageHeight int," + 
								"type String" + 
								");" + 
								"CREATE TABLE IF NOT EXISTS background_object" + 
								"(" + 
								"image string," + 
								"Id int not null primary key autoincrement" + 
								");" + 
								"CREATE TABLE IF NOT EXISTS level_object" + 
								"(" + 
								"level_number int ," + 
								"scale real," + 
								"objectId int," + 
								"position string" + 
								");" + 
								"CREATE TABLE IF NOT EXISTS level" + 
								"(" + 
								"number int not null primary key autoincrement," + 
								"title String," + 
								"hint String," + 
								"width int," + 
								"height int," + 
								"music String" + 
								");" + 
								"CREATE TABLE IF NOT EXISTS level_asteroid" + 
								"(" + 
								"levelnumber int," + 
								"asteroidId int" + 
								");" + 
								"CREATE TABLE IF NOT EXISTS main_body" + 
								"(" + 
								"cannonAttach String," + 
								"engineAttach String," + 
								"extraAttach String," + 
								"image text," + 
								"imageWidth int," + 
								"imageHeight int" + 
								");" + 
								"CREATE TABLE IF NOT EXISTS cannon" + 
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
								");" + 
								"CREATE TABLE IF NOT EXISTS extra_part" + 
								"(" + 
								"attachPoint String," + 
								"image text," + 
								"imageWidth int" + 
								"imageHeight int" + 
								");" + 
								"CREATE TABLE IF NOT EXISTS engine" + 
								"(" + 
								"baseSpeed int," + 
								"baseTurnRate int," + 
								"attachPoint String," + 
								"image text," + 
								"imageWidth int," + 
								"imageHeight int" + 
								");" + 
								"CREATE TABLE IF NOT EXISTS power_core" + 
								"(" + 
								"cannonBoost int," + 
								"engineBoost int," + 
								"image text" + 
								");";

        db.execSQL(SQL);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        return;
    }
}
