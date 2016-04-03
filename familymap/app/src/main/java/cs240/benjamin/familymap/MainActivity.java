package cs240.benjamin.familymap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.util.Log;

import com.joanzapata.android.iconify.IconDrawable;
import com.joanzapata.android.iconify.Iconify;

import cs240.benjamin.familymap.R;
import cs240.benjamin.familymap.ui.*;

public class MainActivity extends ActionBarActivity implements MapActivityInterface {

    public final static String tag = "familyMainActivity";
    public static FragmentManager fragmentManager;
    private String eventId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        eventId = null;
        Log.e(tag, "creating main activity");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentManager = getSupportFragmentManager();
        Log.e(tag, "creating main activity");
        if (savedInstanceState == null)
        {
            getSupportFragmentManager().beginTransaction().add(R.id.main_fragment , new LoginFragment()).commit();
            Log.e(tag, "added loginfragment");
        }
        Log.e(tag, "adding map fragment");
    }

    public void showMap()
    {
        Log.e(tag,"called show map");
        getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment, new MapFragment()).commit();
        Log.e(tag, "showed map");
    }

    public String getEventId()
    {
        return this.eventId;
    }



    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        Log.e(tag, "calling oncreateoptionsmenu");
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id)
        {
            case R.id.action_filter:
                break;
            case R.id.action_search:
                break;
            case R.id.action_settings:
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }*/
}
