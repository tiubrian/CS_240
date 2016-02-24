package edu.byu.cs.superasteroids.model;
import edu.byu.cs.superasteroids.database.*;
import edu.byu.cs.superasteroids.content.ContentManager;
import android.util.Log;

import java.util.ArrayList;

public abstract class SpaceshipPart
{

 public GameImage image;
 
 public int getImageId()
 {
   return image.getImageId();
 }

}