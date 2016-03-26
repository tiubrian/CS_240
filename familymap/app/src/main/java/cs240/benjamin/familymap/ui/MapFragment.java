package cs240.benjamin.familymap.ui;
import cs240.benjamin.familymap.MainActivity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import cs240.benjamin.familymap.model.*;
import cs240.benjamin.familymap.R;
import cs240.benjamin.familymap.client.*;
import android.util.Log;
import android.widget.RelativeLayout;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


/**
 * Created by benjamin on 3/23/16.
 */
public class MapFragment extends Fragment implements OnMapReadyCallback {
    public final static String tag = "familymapfragment";

    private View view;
    /**
     * Note that this may be null if the Google Play services APK is not
     * available.
     */

    private GoogleMap mMap;
    private Double latitude, longitude;
    private MainActivity parent;

    public MapFragment()
    {
        Log.e(tag, "initializing the mapfragment without an activity");
    }

    public MapFragment(MainActivity act)
    {
        parent = act;
        if (parent == null) Log.e(tag, "map's parent is null");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (container == null) {
            return null;
        }
        view = (RelativeLayout) inflater.inflate(R.layout.map_layout, container, false);
        // Passing harcoded values for latitude & longitude. Please change as per your need. This is just used to drop a Marker on the Map
        latitude = 26.78;
        longitude = 72.56;

        setUpMapIfNeeded(); // For setting up the MapFragment

        return view;
    }

    /***** Sets up the map if it is possible to do so *****/
    public void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            if (parent == null) Log.e(tag, "map's parent is null in setup map");
            Log.e(tag, "on view created");

           FragmentManager manager = getChildFragmentManager();

            Log.e(tag, "got fragment manager");
            if (manager == null) Log.e(tag, "frag manager is null");
            SupportMapFragment frag = (SupportMapFragment) manager.findFragmentById(R.id.map_fragment);
            Log.e(tag, "Got Fragment");
            if (frag == null) Log.e(tag, "frag is null");
                    frag.getMapAsync(this); // getMap is deprecated
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the
     * camera.
     * <p>
     * This should only be called once and when we are sure that {@link #mMap}
     * is not null.
     */
    private void setUpMap() {
        // For showing a move to my loction button
        mMap.setMyLocationEnabled(true);
        // For dropping a marker at a point on the Map
        mMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).title("My Home").snippet("Home Address"));
        // For zooming automatically to the Dropped PIN Location
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude,
                longitude), 12.0f));
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        if (mMap != null)
            setUpMap();

        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            Log.e(tag, "on view created");
            ((SupportMapFragment) getChildFragmentManager()
                    .findFragmentById(R.id.map_fragment)).getMapAsync(this); // getMap is deprecated
        }
    }

    /**** The mapfragment's id must be removed from the FragmentManager
     **** or else if the same it is passed on the next time then
     **** app will crash ****/
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mMap != null) {
            getChildFragmentManager().beginTransaction()
                    .remove(getChildFragmentManager().findFragmentById(R.id.map_fragment)).commit();
            mMap = null;
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        setUpMap();
    }
}

