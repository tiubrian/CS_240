package cs240.benjamin.familymap.ui;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;

import cs240.benjamin.familymap.R;
import cs240.benjamin.familymap.model.Event;
import cs240.benjamin.familymap.model.MainModel;
import cs240.benjamin.familymap.model.Person;

public class PersonActivity extends AppCompatActivity {
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

}
