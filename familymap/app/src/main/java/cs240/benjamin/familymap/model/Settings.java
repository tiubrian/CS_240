package cs240.benjamin.familymap.model;

import android.graphics.Color;

/**
 * Created by benjamin on 4/1/16.
 */
public class Settings {
    private static int familyStoryColor = Color.GREEN;
    private static int spouseLineColor = Color.RED;
    private static int lifeStoryColor = Color.BLUE;
    public static int START_FAMILY_LINE_WIDTH = 12;
    public static int FAMILY_LINE_DEC = 2;



    public static int getFamilyStoryColor() {
        return familyStoryColor;
    }

    public static void setFamilyStoryColor(int familyStoryColor) {
        Settings.familyStoryColor = familyStoryColor;
    }

    public static int getSpouseLineColor() {
        return spouseLineColor;
    }

    public static void setSpouseLineColor(int spouseLineColor) {
        Settings.spouseLineColor = spouseLineColor;
    }

    public static int getLifeStoryColor() {
        return lifeStoryColor;
    }

    public static void setLifeStoryColor(int lifeStoryColor) {
        Settings.lifeStoryColor = lifeStoryColor;
    }
}
