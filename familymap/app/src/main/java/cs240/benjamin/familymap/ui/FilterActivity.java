package cs240.benjamin.familymap.ui;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.ListView;

import java.util.ArrayList;

import cs240.benjamin.familymap.R;
import cs240.benjamin.familymap.model.MainModel;

public class FilterActivity extends ActionBarActivity {
    private ListView filterList;
    private ArrayList<FilterView> filters;
    public static final String tag = "familyfilter";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        filterList = (ListView)findViewById(R.id.filter_list);


        filters = new ArrayList<FilterView>();
        for (String description : MainModel.descriptions)
        {
            FilterView filter = new FilterView();
            //TODO:: abstract this into some kind of utils function, but I'm not sure where to put it
            String capDescription = description.substring(0,1).toUpperCase() + description.substring(1);
            filter.setSuperText(capDescription + " Events");
            filter.setSubText("FILTER BY " + description.toUpperCase() + " EVENTS");
            filter.setListener(new eventFilterListener(description));
            filter.setEnabled(MainModel.filters.eventTypeVisible(description));
            filters.add(filter);
        }


        FilterView fatherSide = new FilterView();
        fatherSide.setSuperText("Father's Side");
        fatherSide.setSubText("FILTER BY FATHER'S SIDE OF FAMILY");
        fatherSide.setEnabled(MainModel.filters.isFatherSide());
        fatherSide.setListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                MainModel.filters.setFatherSide(isChecked);
            }
        });

        FilterView motherSide = new FilterView();
        motherSide.setSuperText("Mother's Side");
        motherSide.setSubText("FILTER BY MOTHER'S SIDE OF FAMILY");
        motherSide.setEnabled(MainModel.filters.isMotherSide());
        motherSide.setListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                MainModel.filters.setMotherSide(isChecked);
            }
        });

        String gender_sub_text = "FILTER EVENTS BASED ON GENDER";


        FilterView maleFilter = new FilterView();
        maleFilter.setSuperText("Male Events");
        maleFilter.setSubText(gender_sub_text);
        maleFilter.setEnabled(MainModel.filters.isMaleEnabled());
        maleFilter.setListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                MainModel.filters.setMaleEnabled(isChecked);
            }
        });

        FilterView femaleFilter = new FilterView();
        femaleFilter.setSuperText("Female Events");
        femaleFilter.setSubText(gender_sub_text);
        femaleFilter.setEnabled(MainModel.filters.isFemaleEnabled());
        femaleFilter.setListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                MainModel.filters.setFemaleEnabled(isChecked);
            }
        });


        filters.add(fatherSide);
        filters.add(motherSide);
        filters.add(maleFilter);
        filters.add(femaleFilter);

        Log.e(tag, "num filters "+Integer.toString(filters.size()));
        FilterAdapter filterAdapter = new FilterAdapter(this, R.layout.filter_row, filters);

        filterList.setAdapter(filterAdapter);
    }
}

class eventFilterListener implements CompoundButton.OnCheckedChangeListener
{
    public final static String tag = "familyeventfilter";
    private String description;
    public eventFilterListener(String description)
    {
        this.description = description;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        Log.e(tag, " clicking on event switch "+description);
        MainModel.filters.setEvent(this.description, isChecked);
    }
}
