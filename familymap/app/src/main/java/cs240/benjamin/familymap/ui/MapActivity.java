package cs240.benjamin.familymap.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import cs240.benjamin.familymap.R;

public class MapActivity extends AppCompatActivity  implements MapActivityInterface {

    public final static String tag = "familyMapActivity";
    private String eventId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e(tag, "called oncreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        if (savedInstanceState == null)
        {
            Intent intent = getIntent();

            this.eventId = (intent.hasExtra("eventId")) ?  intent.getStringExtra("eventId") : null;
            Log.e(tag, "created map activity with id " + eventId);
        }
        else this.eventId = savedInstanceState.getString("eventId");

        getSupportFragmentManager().beginTransaction().add(R.id.main_map_fragment, new MapFragment()).commit();
        Log.e(tag, "created map fragment for map activity");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        Log.e(tag, " calling save instance ");
        outState.putString("eventId", this.eventId);
    }

    public String getEventId()
    {
        return this.eventId;
    }
}
