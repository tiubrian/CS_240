
package edu.byu.cs.superasteroids.ship_builder;

import edu.byu.cs.superasteroids.base.IController;
import edu.byu.cs.superasteroids.base.IGameDelegate;
import edu.byu.cs.superasteroids.content.ContentManager;
import edu.byu.cs.superasteroids.ship_builder.IShipBuildingView.PartSelectionView;
import edu.byu.cs.superasteroids.ship_builder.IShipBuildingView.ViewDirection;
import edu.byu.cs.superasteroids.model.*;

import edu.byu.cs.superasteroids.base.IView;

import android.util.Log;


/**
 * The ShipBuildingView uses this controller to call the functions found below.
 */
public class ShipBuildingController implements IShipBuildingController {

		public PartSelectionView state;
		public ShipBuildingActivity activity;
		public static String tag = "superasteroidscontrollerfoo";
		private ContentManager manager;
    /**
     * The ship building view calls this function when a part selection view is loaded. This function
     * should be used to configure the part selection view. Example: Set the arrows for the view in
     * this function.
     * @param partView
     */
		
		public ShipBuildingController(ShipBuildingActivity activity)
		{
			this.activity = activity;
			state = PartSelectionView.MAIN_BODY;
			Log.e("superasteroidsfoo","Called controller");
			manager = ContentManager.getInstance();
		}
		
    public void onViewLoaded(PartSelectionView partView)
		{
			Log.e("superasteroidsfoo","Called onviewloaded");
			state = partView;
			activity.setArrow(state, ViewDirection.UP, false, "");
			activity.setArrow(state, ViewDirection.DOWN, false, "");					
			switch (state)
			{
				case MAIN_BODY:
					activity.setArrow(state, ViewDirection.LEFT, true, "Left Wing");
					activity.setArrow(state, ViewDirection.RIGHT, false, "");			
					break;
				case EXTRA_PART:
					activity.setArrow(state, ViewDirection.LEFT, true, "Cannon");
					activity.setArrow(state, ViewDirection.RIGHT, true, "Main Body");			

					break;

				case CANNON:
					activity.setArrow(state, ViewDirection.LEFT, true, "Engine");
					activity.setArrow(state, ViewDirection.RIGHT, true, "Left Wing");			
					break;

				case ENGINE:
					activity.setArrow(state, ViewDirection.LEFT, true, "Power Core");
					activity.setArrow(state, ViewDirection.RIGHT, true, "Cannon");			
					break;
				case POWER_CORE:
				default:
					activity.setArrow(state, ViewDirection.LEFT, false,"");
					activity.setArrow(state, ViewDirection.RIGHT, true, "Engine");			
			}
		}

    /**
     * The ShipBuildingView calls this function as it is created. Load ship building content in this function.
     * @param content An instance of the content manager. This should be used to load images and sound.
     */
	public void loadContent(ContentManager content)
	{
		Log.e("superasteroidsfoo","Called loadcontent");
		AsteroidsModel model = AsteroidsModel.getInstance();
		model.populate(activity.getApplicationContext());
		activity.setPartViewImageList(PartSelectionView.CANNON, model.getCannonImages());
		activity.setPartViewImageList(PartSelectionView.ENGINE, model.getEngineImages());
		activity.setPartViewImageList(PartSelectionView.POWER_CORE, model.getPowerCoreImages());
		activity.setPartViewImageList(PartSelectionView.EXTRA_PART, model.getExtraPartImages());
		activity.setPartViewImageList(PartSelectionView.MAIN_BODY, model.getMainBodyImages());
	}

    /**
     * The ShipBuildingView calls this function when the user makes a swipe/fling motion in the
     * screen. Respond to the user's swipe/fling motion in this function.
     * @param direction The direction of the swipe/fling.
     */
	public void onSlideView(ViewDirection direction)
	{
		Log.e("superasteroidsfoo","Called slideview");
			PartSelectionView nstate = state;
			switch (state)
			{
				case MAIN_BODY:
					if (direction == ViewDirection.LEFT) {
		Log.e("superasteroidsfoo","Setting view to extra_part");						
						nstate = PartSelectionView.EXTRA_PART;} 
					else if (direction == ViewDirection.RIGHT) {
		Log.e("superasteroidsfoo","Setting view to right extra_part");						
						nstate = PartSelectionView.EXTRA_PART;} 
					else {
								Log.e("superasteroidsfoo","Setting view to nothing new in main_body");						
					}
					break;
				case EXTRA_PART:
					if (direction == ViewDirection.LEFT) nstate = PartSelectionView.CANNON; 
					else if (direction == ViewDirection.RIGHT) nstate = PartSelectionView.MAIN_BODY; 
					else return;
					break;

				case CANNON:
					if (direction == ViewDirection.LEFT) nstate = PartSelectionView.ENGINE; 
					else if (direction == ViewDirection.RIGHT) nstate = PartSelectionView.EXTRA_PART; 
					else return;
					break;

				case ENGINE:
					if (direction == ViewDirection.RIGHT) nstate = PartSelectionView.POWER_CORE; 
					else if (direction == ViewDirection.LEFT) nstate = PartSelectionView.CANNON; 
					else return;

					break;
				case POWER_CORE:
				default:
					if (direction == ViewDirection.LEFT) nstate = PartSelectionView.ENGINE; 
					else return;
			}
			state = nstate;
		Log.e("superasteroidsfoo","animating");						
			activity.animateToView(nstate, direction);
	}

    /**
     * The part selection fragments call this function when a part is selected from the parts list. Respond
     * to the part selection in this function.
     * @param index The list index of the selected part.
     */
	public void onPartSelected(int index)
	{
		Log.e("superasteroidsfoo","Called selectpart");
		
	}

    /**
     * The ShipBuildingView calls this function is called when the start game button is pressed.
     */
	public void onStartGamePressed()
	{
		Log.e("superasteroidsfoo","Called startgame pressed");
	}

    /**
     * The ShipBuildingView calls this function when ship building has resumed. Reset the Camera and
     * the ship position as needed when this is called.
     */
    public void onResume()
		{
		}
		
		public void setView(IView v)
		{
		}
		
		public IView getView() {
			return null;
		}
		
		public void draw(){}
		
		public void update(double elapsedTime){}
		
		public void unloadContent(ContentManager content){}

}
