package cs240.benjamin.familymap.ui;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.joanzapata.android.iconify.IconDrawable;
import com.joanzapata.android.iconify.Iconify;

import java.util.ArrayList;

import cs240.benjamin.familymap.MainActivity;
import cs240.benjamin.familymap.R;
import cs240.benjamin.familymap.model.Event;
import cs240.benjamin.familymap.model.MainModel;
import cs240.benjamin.familymap.model.Person;

public class PersonActivity extends ActionBarActivity {
    private String personId;
    public static final String tag = "familypersonactivity";

    private TextView fname;
    private TextView lname;
    private TextView gender;
    private Person person;
    private ExpandableListView eventList;
    private ExpandableListView relativeList;
    private EventExpandableListAdapter eventAdapter;
    private PersonExpandableListAdapter relativeAdapter;
    private ArrayList<Pair<String,String>> relatives;
    private ArrayList<Event> events;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e(tag, "started person activity");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(new IconDrawable(this, Iconify.IconValue.fa_arrow_left).color(Color.LTGRAY));
        actionBar.setTitle("Person Activity");


        if (savedInstanceState == null) {
            Intent intent = getIntent();
            if (intent.hasExtra("personId")) {
                setPersonId(intent.getStringExtra("personId"));
                Log.e(tag, "initialized person activity with id " + personId + " data " + person.toString());
            } else return;

        }
        else
        {
            setPersonId(savedInstanceState.getString("personId"));
            Log.e(tag, "initialized from savedstate with id "+this.personId);
        }



        fname = (TextView) findViewById(R.id.person_fname);
        lname = (TextView) findViewById(R.id.person_lname);
        gender = (TextView) findViewById(R.id.person_gender);

        eventList = (ExpandableListView) findViewById(R.id.person_events);
        relativeList = (ExpandableListView) findViewById(R.id.person_relatives);
        populateView();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        Log.e(tag, " calling save instance ");
        outState.putString("personId", this.personId);
    }


    public void setPersonId(String id)
    {
        this.personId = id;
        this.person = MainModel.getPerson(id);
        if (!MainModel.people.containsKey(id))
        {
            Log.e(tag, "Main Model doesn't have key "+id);
            MainModel.dumpAllToLog();
        }
        else {
            Log.e(tag, "Main Model has said key, data "+this.person.toString());
        }
    }

    private void populateView() {
        if (fname == null) Log.e(tag, "fname is null");
        if (lname == null) Log.e(tag, "lname is null");
        if (gender == null) Log.e(tag, "gender is null");
        if (this.person == null) Log.e(tag, "person is null");
        fname.setText(this.person.getFirstName());
        lname.setText(this.person.getLastName());
        gender.setText(this.person.getFullGenderName());
        Log.e(tag, "set textviews ");
        this.events = this.person.getSortedEvents();
        Log.e(tag, "got person events");
        this.eventAdapter = new EventExpandableListAdapter(getApplicationContext(), this.events);
        this.relatives = MainModel.getImmediateRelatives(personId);
        this.relativeAdapter = new PersonExpandableListAdapter(getApplicationContext(), this.relatives);
        Log.e(tag, "created adapters");
        eventList.setAdapter(eventAdapter);
        relativeList.setAdapter(relativeAdapter);
        setupListeners();
    }

    private void setupListeners() {
        relativeList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Intent intent = new Intent(getApplicationContext(), PersonActivity.class);
                intent.putExtra("personId", relatives.get(childPosition).first);
                startActivity(intent);
                return false;
            }
        });

        eventList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Log.e(tag, " clicked on event: "+events.get(childPosition).toString());
                Intent intent = new Intent(getApplicationContext(), MapActivity.class);
                intent.putExtra("eventId", events.get(childPosition).getId());
                startActivity(intent);
                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                Log.e(tag, "clicking on home button");
                finish();
                return true;
            case R.id.go_to_top:
                Log.e(tag, "clicking on go to top");
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
            default:
                return true;

        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.back_menu, menu);
        setMenuItem(menu, R.id.go_to_top, Iconify.IconValue.fa_angle_double_up);
        return true;
    }

    private void setMenuItem(Menu menu, int id, Iconify.IconValue value)
    {

        MenuItem item = menu.findItem(id);
        if (item == null)
        {
            Log.e(tag, "menu item is null");
        }

        else item.setIcon(new IconDrawable(getApplicationContext(), value).color(Color.LTGRAY).sizeDp(40));
    }

}
