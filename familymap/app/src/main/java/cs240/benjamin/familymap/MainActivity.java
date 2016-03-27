package cs240.benjamin.familymap;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.util.Log;
import cs240.benjamin.familymap.R;
import cs240.benjamin.familymap.ui.*;

public class MainActivity extends ActionBarActivity{

    public final static String tag = "familyMainActivity";
    public static FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e(tag, "creating main activity");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentManager = getSupportFragmentManager();
        Log.e(tag, "creating main activity");
        if (savedInstanceState == null)
        {
            getSupportFragmentManager().beginTransaction().add(R.id.main_fragment , new LoginFragment(this)).commit();
            Log.e(tag, "added loginfragment");
        }
        Log.e(tag, "adding map fragment");
    }

    public void showMap()
    {
        Log.e(tag,"called show map");
        getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment, new MapFragment(this)).commit();
        Log.e(tag, "showed map");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
