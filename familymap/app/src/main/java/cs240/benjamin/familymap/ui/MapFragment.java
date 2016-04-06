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
import android.view.MenuInflater;
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
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.vision.face.Landmark;

import org.w3c.dom.Text;

import java.util.ArrayList;
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
    private ArrayList<Polygon> lifeStory;
    private Polygon spouseLine;
    private ArrayList<Polygon> familyLines;

    public static final int LIFE_STORY_WIDTH = 10;
    public static final int SPOUSE_LINE_WIDTH = 10;
    /**
     * To avoid markers landing on top of one another, a random fudge factor is added to the coordinates of each marker.
     * The fudge factor is of the form random() / MARKER_FUDGE_DENOM. Higher values of the denominator mean less fudging.
     * */
    public static final int MARKER_FUDGE_DENOM = 100000;

    public static final float DEFAULT_MARKER_COLOR = BitmapDescriptorFactory.HUE_AZURE;

    public MapFragment()
    {
        Log.e(tag, "initializing the mapfragment without an activity");
        this.selPersonId = null;
        this.selEventId = null;
        Log.e(tag, "Map fragment has event id " + this.selEventId);
        lifeStory = new ArrayList<Polygon>();
        spouseLine = null;
        familyLines = new ArrayList<Polygon>();
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
        setHasOptionsMenu(true);

        setUpMapIfNeeded(); // For setting up the MapFragment

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        Log.e(tag, "calling create options menu in fragment");
        inflater.inflate(R.menu.action_menu, menu);
        setMenuItem(menu, R.id.action_settings, Iconify.IconValue.fa_gear);
        setMenuItem(menu, R.id.action_filter, Iconify.IconValue.fa_filter);
        setMenuItem(menu, R.id.action_search, Iconify.IconValue.fa_search);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        Log.e(tag, "called frag item selected");
        int id = item.getItemId();
        Intent intent;
        switch (id)
        {
            case R.id.action_filter:
                Log.e(tag, "clicked on filter action");
                intent = new Intent(getActivity().getApplicationContext(), FilterActivity.class);
                startActivity(intent);
                break;
            case R.id.action_search:
                Log.e(tag, "clicked on search action");
                intent = new Intent(getActivity().getApplicationContext(), SearchActivity.class);
                startActivity(intent);
                break;
            case R.id.action_settings:
                Log.e(tag, "clicked on settings action");
                intent = new Intent(getActivity().getApplicationContext(), SettingsActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    private void setMenuItem(Menu menu, int id, Iconify.IconValue value)
    {

        MenuItem item = menu.findItem(id);
        if (item == null)
        {
            Log.e(tag, "menu item is null");
        }

        else item.setIcon(new IconDrawable(getActivity().getApplicationContext(), value).color(Color.LTGRAY).sizeDp(40));
    }


    private void setWidgetClickListeners()
    {
        gender_image_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(tag, "clicked on gender image");
                startPersonActivity();
            }
        });

        event_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(tag, "clicked on event description");
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
        Log.e(tag, "trying to start person activity with id " + selPersonId);
        if (selPersonId == null) return;
        Intent intent = new Intent(getActivity(), PersonActivity.class);
        intent.putExtra("personId", selPersonId);
        startActivity(intent);
    }

    private void setEvent(String eventId)
    {
        Event e = MainModel.getEvent(eventId);
        Person p = MainModel.getPerson(e.getPersonId());
        String eventText = p.getFirstName() + " " + p.getLastName() + System.getProperty("line.separator")
                + e.fullDescription();
//        Log.e(tag, "setting event text to " + eventText);
        event_text.setText(eventText);
        selPersonId = e.getPersonId();
        selEventId = eventId;
        updateLines();

        showGenderImage(p.getGender());
    }

    private void refreshIfNeeded()
    {
        Log.e(tag, "refreshing");
        if (MainModel.isChanged()) {
            clearFamilyStoryLines();
            clearLifeStory();
            clearSpouseLine();
            mMap.clear();
            mMap.setMapType(Settings.getMapType());
            showEvents();
        }
        MainModel.setChanged(false);
    }

    private void setUpMap() {
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
  //              Log.e(tag, "clicked on marker");
                String eventId = marker.getSnippet();
                setEvent(eventId);
                refreshIfNeeded();
                return false;
            }
        });

        mMap.setMapType(Settings.getMapType());

        computeMarkerColors();

        showEvents();

        selEventId = ((MapActivityInterface)getActivity()).getEventId();
        if (selEventId != null)
        {
            Log.e(tag, "Setting up initial event");
            setEvent(selEventId);
            Event e = MainModel.getEvent(selEventId);
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(e.getLat(), e.getLng()), 12.0f));
        }
//        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude,
  //              longitude), 12.0f));
    }

    private void showEvents()
    {
        for (String eventId: MainModel.events.keySet()) {
            if (MainModel.isEventVisible(eventId)) {
                showEvent(eventId);
            }
        }
    }

    private void updateLines()
    {
        updateLifeStory();
        updateSpouseLines();
        updateFamilyStoryLines();
    }

    private void updateSpouseLines()
    {
        clearSpouseLine();

        if (!Settings.isSpouseLinesEnabled()) return;

        Event selEvent = MainModel.getEvent(selEventId);
        Person p = selEvent.getPerson();
        Person spouse = MainModel.getPerson(p.getSpouseId());

        if (spouse == null || selEvent == null) return;

        Event firstSpouseEvent = spouse.getEarliestEvent();

        if (firstSpouseEvent == null) return;

        if (!MainModel.isEventVisible(selEvent.getId()) || !MainModel.isEventVisible(firstSpouseEvent.getId())) return;
        spouseLine = drawLine(selEvent, firstSpouseEvent, SPOUSE_LINE_WIDTH, Settings.getSpouseLineColor());
    }

    private void clearSpouseLine()
    {
        if (spouseLine != null) spouseLine.remove();
    }

    private void recDrawFamilyStoryLines(Event event, int width)
    {
    //    Log.e(tag, "Called recDraw with event "+event.toString()+" width: "+width);
        Person p = event.getPerson();
        Person mother = MainModel.getPerson(p.getMotherId());
        Person father = MainModel.getPerson(p.getFatherId());
        if (mother != null)
        {
            Event firstMotherEvent = mother.getEarliestEvent();
            drawFamilyStoryLine(event, firstMotherEvent, width);;
            recDrawFamilyStoryLines(firstMotherEvent, width - Settings.FAMILY_LINE_DEC);
        }

        if (father != null)
        {
            Event firstFatherEvent = father.getEarliestEvent();
            drawFamilyStoryLine(event, firstFatherEvent, width);
            recDrawFamilyStoryLines(firstFatherEvent, width - Settings.FAMILY_LINE_DEC);
        }
    }

    private void updateFamilyStoryLines()
    {
        clearFamilyStoryLines();
        if (!Settings.isFamilyLinesEnabled()) return;
        recDrawFamilyStoryLines(MainModel.getEvent(selEventId), Settings.START_FAMILY_LINE_WIDTH);
    }

    /**
     * If the source and target events are visible, draw a line between the source and target, and save it in the map of lines.
     * This is so that the lines can be removed when changing the selected event.
     * */
    private void drawFamilyStoryLine(Event source, Event target, int width)
    {
        if (source == null || target == null) return;
        if (MainModel.isEventVisible(source.getId()) && MainModel.isEventVisible(target.getId()))
        {
            familyLines.add(drawLine(source, target, width, Settings.getFamilyStoryColor()));
        }
    }

    private void clearFamilyStoryLines()
    {
        for (int i = 0; i < familyLines.size(); i++)
        {
            familyLines.get(i).remove();
        }
        familyLines.clear();
    }

    private void updateLifeStory()
    {
  //      Log.e(tag, "updating life story");
        ArrayList<Event> events = MainModel.getPerson(selPersonId).getSortedEvents();
        ArrayList<Event> visibleEvents = new ArrayList<Event>();
        for (int i=0; i < events.size(); i++)
        {
            Event curr_event = events.get(i);
            if (MainModel.isEventVisible(curr_event.getId()))
            {
                visibleEvents.add(curr_event);
//                Log.e(tag, "visible event: "+curr_event.toString());
            }
        }

        clearLifeStory();
        if (!Settings.isLifeLinesEnabled()) return;

        for (int j = 1; j < visibleEvents.size(); j++)
        {
            Event event1 = visibleEvents.get(j-1);
            Event event2 = visibleEvents.get(j);
            lifeStory.add(drawLine(event1, event2, LIFE_STORY_WIDTH, Settings.getLifeStoryColor()));
        }

    }

    public void clearLifeStory()
    {
        for (int i =0; i < lifeStory.size(); i++)
        {
            lifeStory.get(i).remove();
        }
        lifeStory.clear();
    }

    private void showEvent(String eventId)
    {
        Event e = MainModel.getEvent(eventId);
        LatLng pos = new LatLng(e.getLat() + Math.random()/MARKER_FUDGE_DENOM, e.getLng() + Math.random()/MARKER_FUDGE_DENOM);
        String description = e.getDescription();
        float event_color = (description_hues.containsKey(description)) ? description_hues.get(description) : DEFAULT_MARKER_COLOR;
        mMap.addMarker(new MarkerOptions()
                .position(pos)
                .snippet(eventId)
                .icon(BitmapDescriptorFactory.defaultMarker(event_color)));
    }

    /**
     * */
    private Polygon drawLine(Event event1, Event event2, int width, int color)
    {
    //    Log.e(tag, "calling drawline with events "+event1.toString()+" and "+event2.toString());
        return mMap.addPolygon(new PolygonOptions()
                .add(new LatLng(event1.getLat(), event1.getLng()) ,
                new LatLng(event2.getLat(), event2.getLng()))
                .strokeWidth(width)
                .strokeColor(color)
        );
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

//    /**** The mapfragment's id must be removed from the FragmentManager
//     **** or else if the same it is passed on the next time then
//     **** app will crash ****/
//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        if (mMap != null) {
//            getChildFragmentManager().beginTransaction()
//                    .remove(getChildFragmentManager().findFragmentById(R.id.map_fragment)).commit();
//            mMap = null;
//        }
//    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        setUpMap();
    }
}

