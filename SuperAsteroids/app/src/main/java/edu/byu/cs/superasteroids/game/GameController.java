package edu.byu.cs.superasteroids.game;
import edu.byu.cs.superasteroids.model.*;
import edu.byu.cs.superasteroids.drawing.DrawingHelper;
import edu.byu.cs.superasteroids.base.*;
import android.graphics.Canvas;
import edu.byu.cs.superasteroids.content.ContentManager;

import android.util.Log;



public class GameController implements IController, IGameDelegate {


    public GameActivity activity;
    public ContentManager manager;
    public static String tag = "superasteroidsgame";
    AsteroidsModel model;
    private boolean is_init;
    public GameController(GameActivity activity)
    {
	    this.activity = activity;
	    Log.e(tag,"Called game controller");
	    manager = ContentManager.getInstance();
	    model = null;
	    is_init = false;
    }

    public void loadContent(ContentManager content)
    {
	    Log.e(tag,"Called loadcontent");
	    model = AsteroidsModel.getInstance();
	    model.setActivity(activity);
	    Log.e(tag,"created asteroids model.");
    }

		
		
    public void setView(IView v)
    {
    }
    
    public IView getView() {
	    return null;
    }
		
    public void init()
    {
      model.initLevel(0);
      is_init = true;
    }
    
    public void draw() {
//	    Log.e(tag, "drawing");
	    if (!is_init) init();
	    if (model != null) model.draw();
    }
	    
    public void update(double elapsedTime){
    	    if (model != null) model.update(elapsedTime);
    }
	    
    public void unloadContent(ContentManager content){}



}