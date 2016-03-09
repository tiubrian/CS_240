package edu.byu.cs.superasteroids.model;
import android.util.Log;

public abstract class AttachablePart extends SpaceshipPart
{
 public Coordinate attachPoint;
 public MainBody body;
 public Coordinate bodyAttach;

 @Override
 public boolean equals(Object o) {
  if (this == o) return true;
  if (o == null || getClass() != o.getClass()) return false;
  if (!super.equals(o)) return false;

  AttachablePart that = (AttachablePart) o;

  return attachPoint.equals(that.attachPoint);

 }

 @Override
 public int hashCode() {
  int result = super.hashCode();
  result = 31 * result + attachPoint.hashCode();
  return result;
 }

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
 
 @Override
 public int maxX()
 {
  Coordinate offset = getOffset();
  return offset.getX() + image.getWidth() / 2;
 }

 @Override 
 public int maxY()
 {
  Coordinate offset = getOffset();
  return offset.getY() + image.getHeight() / 2;
 }
 
 @Override
 public int minX()
 {
  Coordinate offset = getOffset();
  return offset.getX() - image.getWidth() / 2;
 }
 
 @Override
 public int minY()
 {
  Coordinate offset = getOffset();
  return offset.getY() - image.getHeight() / 2;
 }
 

 
 public abstract void setAttach(MainBody body);
}