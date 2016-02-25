package edu.byu.cs.superasteroids.game;
import edu.byu.cs.superasteroids.model.*;
import edu.byu.cs.superasteroids.drawing.DrawingHelper;
import edu.byu.cs.superasteroids.base.*;
import edu.byu.cs.superasteroids.content.ContentManager;

import android.util.Log;



public class GameController implements IController, IGameDelegate {


    public GameActivity activity;
    public ContentManager manager;
    public static String tag = "superasteroidsgame";
    AsteroidsModel model;
    public GameController(GameActivity activity)
    {
	    this.activity = activity;
	    Log.e(tag,"Called game controller");
	    manager = ContentManager.getInstance();
	    model = null;
    }

    public void loadContent(ContentManager content)
    {
	    Log.e(tag,"Called loadcontent");
	    model = AsteroidsModel.getInstance();
	    Log.e(tag,"created asteroids model.");
	    model.initLevel(0);
    }

		
		
    public void setView(IView v)
    {
    }
    
    public IView getView() {
	    return null;
    }
		
    public void draw() {
	    Log.e(tag, "drawing");
	    if (model != null) model.draw();
    }
	    
    public void update(double elapsedTime){
    	    if (model != null) model.update(elapsedTime);
    }
	    
    public void unloadContent(ContentManager content){}



}