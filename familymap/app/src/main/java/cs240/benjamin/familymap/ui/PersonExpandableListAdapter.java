package cs240.benjamin.familymap.ui;

import android.content.Context;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.joanzapata.android.iconify.IconDrawable;
import com.joanzapata.android.iconify.Iconify;

import java.util.ArrayList;

import cs240.benjamin.familymap.R;
import cs240.benjamin.familymap.model.MainModel;
import cs240.benjamin.familymap.model.Person;

/**
 * Created by benjamin on 3/31/16.
 */
public class PersonExpandableListAdapter  extends BaseExpandableListAdapter {
    private Context context;
    private ArrayList<Person> people;
    private ArrayList<String> relationships;
    public static final String tag = "familypersonlist";
    public LayoutInflater inflater;

    public PersonExpandableListAdapter (Context context, ArrayList<Pair<String, String>> people)
    {
        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.people = new ArrayList<Person>();
        this.relationships = new ArrayList<String>();
        Log.e(tag, "Creating person list adapter");
        for (int i = 0; i < people.size(); i++)
        {
            Pair<String, String> data = people.get(i);
            String id = data.first;
            String relationship = data.second;
            this.people.add(MainModel.getPerson(id));
            this.relationships.add(relationship);
        }

        //TODO: sort people properly
    }



    @Override
    public int getGroupCount() {
        Log.e(tag, "calling get groupCount");
        return 1;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        Log.e(tag, "calling getChildrenCount with gp "+groupPosition+" returning "+people.size());
        return people.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return null;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return people.get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        Log.e(tag, "calling getChildId with g "+groupPosition + " ch "+childPosition);
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        Log.e(tag, "getting group view");
        if (convertView == null)
        {
            convertView = inflater.inflate(R.layout.person_list_header,null);
        }
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        Log.e(tag, "calling getChildView with args gp "+groupPosition+" cp "+childPosition);
        if (convertView == null)
        {
            convertView = inflater.inflate(R.layout.list_child,null);
        }

        TextView itemText = (TextView)convertView.findViewById(R.id.list_item_text);
        Person person = people.get(childPosition);

        //TODO: get relation to source node
        String item_content = person.getFullName() + System.getProperty("line.separator") + relationships.get(childPosition);
        Log.e(tag, "setting text to "+item_content);
        itemText.setText(item_content);

        ImageView markerImage = (ImageView)convertView.findViewById(R.id.list_item_icon);

        //TODO:Abstract this
        Iconify.IconValue icon = Iconify.IconValue.fa_android;
        switch (person.getGender().toUpperCase())
        {
            case "M":
                icon = Iconify.IconValue.fa_male;
                break;
            case "F":
                icon = Iconify.IconValue.fa_female;
                break;
            default:
                break;
        }
        markerImage.setImageDrawable(new IconDrawable(context, icon));
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        Log.e(tag, "calling ischild selectable.");
        return true;
    }
}
