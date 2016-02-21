
package edu.byu.cs.superasteroids.ship_builder;

import edu.byu.cs.superasteroids.base.IController;
import edu.byu.cs.superasteroids.base.IGameDelegate;
import edu.byu.cs.superasteroids.content.ContentManager;
import edu.byu.cs.superasteroids.ship_builder.IShipBuildingView.PartSelectionView;
import edu.byu.cs.superasteroids.ship_builder.IShipBuildingView.PartSelectionView.*;
import edu.byu.cs.superasteroids.ship_builder.IShipBuildingView.ViewDirection;
import edu.byu.cs.superasteroids.ship_builder.IShipBuildingView.ViewDirection.*;

import android.util.Log;


/**
 * The ShipBuildingView uses this controller to call the functions found below.
 */
public class ShipBuildingController implements IShipBuildingController {

		public PartSelectionView state;
		public ShipBuildingActivity activity;
		public static String tag = "superasteroidscontrollerfoo";
    /**
     * The ship building view calls this function when a part selection view is loaded. This function
     * should be used to configure the part selection view. Example: Set the arrows for the view in
     * this function.
     * @param partView
     */
		
		public ShipBuilderController(ShipBuildingActivity activity)
		{
			this.activity = activity;
			state = MAIN_BODY;
			Log.e("superasteroidsfoo","Called controller");
		}
		
    void onViewLoaded(PartSelectionView partView)
		{
			Log.e("superasteroidsfoo","Called onviewloaded");
			state = partView;
			activity.setArrow(state, UP, false, "Left Wing");
			activity.setArrow(state, DOWN, false, "Left Wing");					
			switch (state)
			{
				case MAIN_BODY:
					activity.setArrow(state, LEFT, true, "Left Wing");
					activity.setArrow(state, RIGHT, false, "");			
					break;
				case EXTRA_PART:
					activity.setArrow(state, LEFT, true, "Cannon");
					activity.setArrow(state, RIGHT, true, "Main Body");			

					break;

				case CANNON:
					activity.setArrow(state, LEFT, true, "Engine");
					activity.setArrow(state, RIGHT, true, "Left Wing");			
					break;

				case ENGINE:
					activity.setArrow(state, LEFT, true, "Power Core");
					activity.setArrow(state, RIGHT, true, "Cannon");			
					break;
				case POWER_CORE:
				default:
					activity.setArrow(state, LEFT, false,"");
					activity.setArrow(state, RIGHT, true, "Engine");			
			}
		}

    /**
     * The ShipBuildingView calls this function as it is created. Load ship building content in this function.
     * @param content An instance of the content manager. This should be used to load images and sound.
     */
	void loadContent(ContentManager content)
	{
		Log.e("superasteroidsfoo","Called loadcontent");
	}

    /**
     * The ShipBuildingView calls this function when the user makes a swipe/fling motion in the
     * screen. Respond to the user's swipe/fling motion in this function.
     * @param direction The direction of the swipe/fling.
     */
	void onSlideView(ViewDirection direction)
	{
		Log.e("superasteroidsfoo","Called slideview");
		
	}

    /**
     * The part selection fragments call this function when a part is selected from the parts list. Respond
     * to the part selection in this function.
     * @param index The list index of the selected part.
     */
	void onPartSelected(int index)
	{
		Log.e("superasteroidsfoo","Called selectpart");
		
	}

    /**
     * The ShipBuildingView calls this function is called when the start game button is pressed.
     */
	void onStartGamePressed()
	{
		Log.e("superasteroidsfoo","Called startgame pressed");
	}

    /**
     * The ShipBuildingView calls this function when ship building has resumed. Reset the Camera and
     * the ship position as needed when this is called.
     */
    void onResume()
		{
		}
}
