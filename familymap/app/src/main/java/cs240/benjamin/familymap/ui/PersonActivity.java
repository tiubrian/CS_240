package cs240.benjamin.familymap.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import cs240.benjamin.familymap.R;
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
    private EventExpandableListAdapter eventAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e(tag, "started person activity");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);
        Intent intent = getIntent();
        if (intent.hasExtra("personId"))
        {
            this.personId = intent.getStringExtra("personId");
            this.person = MainModel.getPerson(personId);
            Log.e(tag, "initialized person activity with id "+personId+" data "+ person.toString());
        }
        else return;

        fname = (TextView)findViewById(R.id.person_fname);
        lname = (TextView)findViewById(R.id.person_lname);
        gender = (TextView)findViewById(R.id.person_gender);

        eventList = (ExpandableListView)findViewById(R.id.person_events);

        populateView();
    }


    private void populateView()
    {
        fname.setText(person.getFirstName());
        lname.setText(person.getLastName());
        gender.setText(person.getFullGenderName());

        this.eventAdapter = new EventExpandableListAdapter(getApplicationContext(), person.getEventIds());
        eventList.setAdapter(eventAdapter);
    }
}
