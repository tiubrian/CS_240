package edu.byu.cs.superasteroids.model;

public class GameImage
{
 private String name;
 private int h;
 private int w;
 public GameImage(String imagefile, int width, int height)
 {
  name = imagefile;
  h = height;
  w = width;
 }
}