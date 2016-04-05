package cs240.benjamin.familymap.ui;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import cs240.benjamin.familymap.MainActivity;
import cs240.benjamin.familymap.R;
import cs240.benjamin.familymap.model.*;

import java.util.HashMap;
import com.google.android.gms.maps.GoogleMap;
import java.util.List;

public class SettingsActivity extends ActionBarActivity {
    public static HashMap<Integer, String> value_to_color;
    public static HashMap<String, Integer> color_values = color_init();
    public static HashMap<Integer, String> value_to_map_type;
    public static HashMap<String, Integer> map_type_values = map_init();
    public static final String tag = "familysettings";
    public static String[] colors = new String[]{"Red", "Blue", "Green"};
    public static String[] map_types = new String[]{"Normal","Hybrid","Terrain","Satellite"};

    private Spinner family_color;
    private Spinner spouse_color;
    private Spinner life_color;
    private Spinner map_type;
    private TextView logout;
    private TextView resync;
    private Switch life_lines;
    private Switch family_lines;
    private Switch spouse_lines;



    public static HashMap<String, Integer> map_init()
    {
        HashMap<String, Integer> res = new HashMap<String, Integer>();
        res.put("Normal", GoogleMap.MAP_TYPE_NORMAL);
        res.put("Hybrid", GoogleMap.MAP_TYPE_HYBRID);
        res.put("Terrain", GoogleMap.MAP_TYPE_TERRAIN);
        res.put("Satellite",  GoogleMap.MAP_TYPE_SATELLITE);

        value_to_map_type = new HashMap<Integer, String>();
        for (String name : res.keySet())
        {
            value_to_map_type.put(res.get(name), name);
        }
        return res;
    }

    public static HashMap<String, Integer> color_init()
    {
        HashMap<String, Integer> res = new HashMap<String, Integer>();
        res.put("Green", Color.GREEN);
        res.put("Blue", Color.BLUE);
        res.put("Red", Color.RED);

        value_to_color = new HashMap<Integer, String>();
        for (String name : res.keySet())
        {
            value_to_color.put(res.get(name), name);
        }

        return res;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        initViews();
    }

    public void initSpinners()
    {
        final ArrayAdapter<String> spinAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, colors);
        spinAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        ArrayAdapter<String> mapAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, map_types);
        mapAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        life_color = (Spinner)findViewById(R.id.life_story_color);
        spouse_color = (Spinner)findViewById(R.id.spouse_color);
        family_color = (Spinner)findViewById(R.id.family_tree_color);
        map_type = (Spinner)findViewById(R.id.map_type);
        map_type.setAdapter(mapAdapter);
        map_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String val = parent.getItemAtPosition(position).toString();
                Log.e(tag, "changing map type to " + val);
                Settings.setMapType(map_type_values.get(val));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        String sel_type = value_to_map_type.get(Settings.getMapType());
        map_type.setSelection(mapAdapter.getPosition(sel_type));


        life_color.setAdapter(spinAdapter);
        spouse_color.setAdapter(spinAdapter);
        family_color.setAdapter(spinAdapter);

        life_color.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String val = parent.getItemAtPosition(position).toString();
                Settings.setLifeStoryColor(color_values.get(val));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                String color_name = value_to_color.get(Settings.getLifeStoryColor());
                life_color.setSelection(spinAdapter.getPosition(color_name));
            }
        });

        String color_name = value_to_color.get(Settings.getLifeStoryColor());
        life_color.setSelection(spinAdapter.getPosition(color_name));


        family_color.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String val = parent.getItemAtPosition(position).toString();
                Settings.setFamilyStoryColor(color_values.get(val));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                String color_name = value_to_color.get(Settings.getFamilyStoryColor());
                family_color.setSelection(spinAdapter.getPosition(color_name));
            }
        });
        color_name = value_to_color.get(Settings.getFamilyStoryColor());
        family_color.setSelection(spinAdapter.getPosition(color_name));


        spouse_color.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String val = parent.getItemAtPosition(position).toString();
                Settings.setSpouseLineColor(color_values.get(val));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                String color_name = value_to_color.get(Settings.getSpouseLineColor());
                spouse_color.setSelection(spinAdapter.getPosition(color_name));
            }
        });

        color_name = value_to_color.get(Settings.getSpouseLineColor());
        spouse_color.setSelection(spinAdapter.getPosition(color_name));
    }

    public void initSwitches()
    {
        life_lines = (Switch)findViewById(R.id.life_story_switch);
        family_lines = (Switch)findViewById(R.id.family_tree_switch);
        spouse_lines = (Switch)findViewById(R.id.spouse_switch);


        life_lines.setChecked(Settings.isLifeLinesEnabled());
        life_lines.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Settings.setLifeLinesEnabled(isChecked);
            }
        });

        family_lines.setChecked(Settings.isFamilyLinesEnabled());
        family_lines.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Settings.setFamilyLinesEnabled(isChecked);
            }
        });

        spouse_lines.setChecked(Settings.isSpouseLinesEnabled());
        spouse_lines.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Settings.setSpouseLinesEnabled(isChecked);
            }
        });


    }

    public void initViews()
    {
        initSpinners();
        initSwitches();

        logout = (TextView)findViewById(R.id.logout);
        resync = (TextView)findViewById(R.id.resync);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(tag, "clicked on logout");
                //TODO: do logout
                logout();
            }
        });

        resync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(tag, "clicked on ");
                //TODO: do resync
                resync();
            }
        });
    }


    public void logout()
    {
        MainModel.clear();
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void resync()
    {
        if (!MainModel.sync())
        {
            Toast.makeText(getApplicationContext(), "Sync Failed", Toast.LENGTH_LONG).show();
            return;
        }

        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("showMap", true);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

}
