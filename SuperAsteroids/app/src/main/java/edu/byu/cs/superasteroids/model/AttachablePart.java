package edu.byu.cs.superasteroids.model;
import android.util.Log;

public abstract class AttachablePart extends SpaceshipPart
{
 public Coordinate attachPoint;

 /**
 *Compute the offset from the center of the main body to the center of the part.
 */
 public Coordinate getOffset(MainBody body, Coordinate attach)
 {
   Coordinate temp1 = Coordinate.subtract(attach,body.image.Center());
   Coordinate temp2 = Coordinate.subtract(attach, image.Center());
   return Coordinate.add(temp1, temp2);
 }

 
}