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
	public GameController(GameActivity activity)
	{
		this.activity = activity;
		Log.e(tag,"Called game controller");
		manager = ContentManager.getInstance();
	}

	public void loadContent(ContentManager content)
	{
		Log.e(tag,"Called loadcontent");
		AsteroidsModel model = AsteroidsModel.getInstance();
		Log.e(tag,"created asteroids model.");
	}

		
		
    public void setView(IView v)
    {
    }
    
    public IView getView() {
	    return null;
    }
		
    public void draw() {
	    ////Log.e(tag, "drawing");
//	    AsteroidsModel.getInstance().ship.builder_draw(shipX, shipY);
    }
	    
    public void update(double elapsedTime){}
	    
    public void unloadContent(ContentManager content){}



}