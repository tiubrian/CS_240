package cs240.benjamin.familymap.ui;
import cs240.benjamin.familymap.MainActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.vision.face.Landmark;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Iterator;
import com.joanzapata.android.iconify.*;

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
    private HashMap<String, Float> description_hues;
    private TextView event_text;
    private ImageView gender_image_view;
    private String selPersonId;
    private String selEventId;

    public static final float DEFAULT_MARKER_COLOR = BitmapDescriptorFactory.HUE_AZURE;

    public MapFragment()
    {
        Log.e(tag, "initializing the mapfragment without an activity");
        this.selPersonId = null;
        this.selEventId = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (container == null) {
            return null;
        }
//        view = (ScrollView) inflater.inflate(R.layout.map_layout, container, false);
        view = (RelativeLayout) inflater.inflate(R.layout.map_layout, container, false);
        event_text = (TextView)view.findViewById(R.id.map_event_text);
        Drawable andIcon = new IconDrawable(getActivity(), Iconify.IconValue.fa_android);
        gender_image_view = (ImageView)view.findViewById(R.id.map_gender_image);
        gender_image_view.setImageDrawable(andIcon);
        setWidgetClickListeners();

        setUpMapIfNeeded(); // For setting up the MapFragment

        return view;
    }

    private void setWidgetClickListeners()
    {
        gender_image_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(tag, "clicked on gender image");
                //TODO: transition to person activity
                startPersonActivity();
            }
        });

        event_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(tag, "clicked on event description");
                //TODO: transition to person activity
                startPersonActivity();
            }
        });

    }

    /***** Sets up the map if it is possible to do so *****/
    public void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
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


    private void computeMarkerColors()
    {
        description_hues = new HashMap<String, Float>();
        int num_colors = MainModel.descriptions.size();
        Iterator<String> it = MainModel.descriptions.iterator();
        int i = 0;
        float color_increment = ((float)360) / (num_colors+1);
        while (it.hasNext()) {
            i++;
            description_hues.put(it.next(), i * color_increment);
        }
    }

    private void showGenderImage(String gender)
    {
        gender = gender.toUpperCase();
        switch (gender) {
            case "M":
                Drawable maleIcon = new IconDrawable(getActivity(), Iconify.IconValue.fa_male).colorRes(R.color.wallet_holo_blue_light).sizeDp(40);
                gender_image_view.setImageDrawable(maleIcon);
                break;
            case "F":
                Drawable femaleIcon = new IconDrawable(getActivity(), Iconify.IconValue.fa_female).color(Color.RED).sizeDp(40);
                gender_image_view.setImageDrawable(femaleIcon);
                break;
            default:
                break;

        }
    }

    private void startPersonActivity()
    {
        Log.e(tag, "trying to start person activity with id "+selPersonId);
        if (selPersonId == null) return;
        Intent intent = new Intent(getActivity(), PersonActivity.class);
        intent.putExtra("personId",selPersonId);
        startActivity(intent);
    }

    private void setUpMap() {
        // For showing a move to my loction button
//        mMap.setMyLocationEnabled(true);
        // For dropping a marker at a point on the Map

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Log.e(tag, "clicked on marker");
                String eventId = marker.getSnippet();
                Event e = MainModel.getEvent(eventId);
                Person p = MainModel.getPerson(e.getPersonId());
                String eventText = p.getFirstName() + " " + p.getLastName() + System.getProperty("line.separator")
                        + e.fullDescription();
                Log.e(tag, "setting event text to " + eventText);
                event_text.setText(eventText);
                selPersonId = e.getPersonId();
                selEventId = eventId;

                showGenderImage(p.getGender());
                return false;
            }
        });

        computeMarkerColors();

        for (String eventId: MainModel.events.keySet()) {
            if (MainModel.isEventVisible(eventId)) {
                showEvent(eventId);
            }
        }
//        mMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).title("My Home").snippet("Home Address"));
        // For zooming automatically to the Dropped PIN Location
//        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude,
  //              longitude), 12.0f));
    }

    private void showEvent(String eventId)
    {
        Event e = MainModel.getEvent(eventId);
        LatLng pos = new LatLng(e.getLat(), e.getLng());
        String description = e.getDescription();
        float event_color = (description_hues.containsKey(description)) ? description_hues.get(description) : DEFAULT_MARKER_COLOR;
        mMap.addMarker(new MarkerOptions()
                .position(pos)
                .snippet(eventId)
                .icon(BitmapDescriptorFactory.defaultMarker(event_color)));
        //TODO: determine color here
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

