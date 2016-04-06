package cs240.benjamin.familymap.model;

import android.app.Application;
import android.graphics.Color;
import com.google.android.gms.maps.GoogleMap;


/**
 * Created by benjamin on 4/1/16.
 */
public class Settings extends Application {
    private static int familyStoryColor = Color.GREEN;
    private static int spouseLineColor = Color.RED;
    private static int lifeStoryColor = Color.BLUE;
    public static int START_FAMILY_LINE_WIDTH = 12;
    public static int FAMILY_LINE_DEC = 2;
    private static int mapType = GoogleMap.MAP_TYPE_NORMAL;
    private static boolean lifeLinesEnabled = true;
    private static boolean familyLinesEnabled = true;
    private static boolean spouseLinesEnabled = true;

    public static boolean isLifeLinesEnabled() {
        return lifeLinesEnabled;
    }

    public static void setLifeLinesEnabled(boolean lifeLinesEnabled) {
        Settings.lifeLinesEnabled = lifeLinesEnabled;
        MainModel.setChanged(true);
    }

    public static boolean isFamilyLinesEnabled() {
        return familyLinesEnabled;
    }

    public static void setFamilyLinesEnabled(boolean familyLinesEnabled) {
        Settings.familyLinesEnabled = familyLinesEnabled;
        MainModel.setChanged(true);
    }

    public static boolean isSpouseLinesEnabled() {
        return spouseLinesEnabled;
    }

    public static void setSpouseLinesEnabled(boolean spouseLinesEnabled) {
        Settings.spouseLinesEnabled = spouseLinesEnabled;
        MainModel.setChanged(true);
    }

    public static int getMapType() {
        return mapType;
    }

    public static void setMapType(int mapType) {
        Settings.mapType = mapType;
    }

    public static int getFamilyStoryColor() {
        return familyStoryColor;
    }

    public static void setFamilyStoryColor(int familyStoryColor) {
        Settings.familyStoryColor = familyStoryColor;
        MainModel.setChanged(true);
    }

    public static int getSpouseLineColor() {
        return spouseLineColor;
    }

    public static void setSpouseLineColor(int spouseLineColor) {
        Settings.spouseLineColor = spouseLineColor;
        MainModel.setChanged(true);
    }

    public static int getLifeStoryColor() {
        return lifeStoryColor;
    }

    public static void setLifeStoryColor(int lifeStoryColor) {
        Settings.lifeStoryColor = lifeStoryColor;
        MainModel.setChanged(true);
    }
}
