package edu.byu.cs.superasteroids.model;
import android.util.Log;

public abstract class AttachablePart extends SpaceshipPart
{
 public Coordinate attachPoint;
 public MainBody body;
 public Coordinate bodyAttach;

 /**
 *Compute the offset from the center of the main body to the center of the part.
 */
 public Coordinate getOffset()
 {
   Coordinate temp1 = Coordinate.subtract(bodyAttach,body.image.Center());
   Coordinate temp2 = Coordinate.subtract(image.Center(), attachPoint);
   return Coordinate.add(temp1, temp2);
 }
 
 public void setBody(MainBody body)
 {
   this.body = body;
   if (body != null) this.setAttach(body);
 }
 
 public abstract void setAttach(MainBody body);
}