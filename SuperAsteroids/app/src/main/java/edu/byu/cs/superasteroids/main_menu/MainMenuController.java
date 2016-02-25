package edu.byu.cs.superasteroids.main_menu;

import edu.byu.cs.superasteroids.model.*;
import android.util.Log;
import edu.byu.cs.superasteroids.base.IView;


public class MainMenuController implements IMainMenuController {

    MainActivity activity;
    public final String tag = "superasteroidsmenu";
    
    public MainMenuController(MainActivity activity)
    {
      this.activity = activity;
    }
    
    /**
     * The MainActivity calls this function when the "quick play" button is pressed.
     */
    public void onQuickPlayPressed()
    {
      AsteroidsModel model = AsteroidsModel.getInstance();
Log.e(tag,"created asteroids model.");
     model.populate(activity.getApplicationContext());
    Log.e(tag,"populated model.");
     model.ship.cannon = model.cannons.get(0);
     model.ship.body = model.main_bodies.get(0);
     model.ship.extra_part = model.extra_parts.get(0);
     model.ship.engine = model.engines.get(0);
     model.ship.power_core = model.power_cores.get(0);
     activity.startGame();
    }
    
    public void setView(IView v)
    {
    }
    
    public IView getView() {
	    return null;
    }

}
