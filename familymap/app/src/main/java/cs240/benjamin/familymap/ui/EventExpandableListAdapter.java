package cs240.benjamin.familymap.ui;

import android.content.Context;
import android.util.Log;
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
import cs240.benjamin.familymap.model.Event;
import cs240.benjamin.familymap.model.MainModel;
import cs240.benjamin.familymap.model.Person;

/**
 * Created by benjamin on 3/31/16.
 */
public class EventExpandableListAdapter extends BaseExpandableListAdapter {
    private Context context;
    private ArrayList<Event> events;
    public static final String tag = "familyeventlist";
    public LayoutInflater inflater;

    public EventExpandableListAdapter(Context context, ArrayList<Event> events)
    {
        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.events = events;
        Log.e(tag, "Creating event list adapter");
    }



    @Override
    public int getGroupCount() {
        Log.e(tag, "calling get groupCount");
        return 1;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        Log.e(tag, "calling getChildrenCount with gp "+groupPosition+" returning "+events.size());
        return events.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        Log.e(tag, "calling get Group with "+groupPosition);
        return "Life Events";
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        Log.e(tag, "calling getChild with g "+groupPosition + " ch "+childPosition);
        return events.get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        Log.e(tag, "calling getGroup Id with groupPosition "+groupPosition);
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
            convertView = inflater.inflate(R.layout.event_header,null);
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
        Event event = events.get(childPosition);
        Person person  = MainModel.getPerson(event.getPersonId());

        String item_content = event.fullDescription()+System.getProperty("line.separator")+person.getFullName();
        Log.e(tag, "setting text to "+item_content);
        itemText.setText(item_content);

        ImageView markerImage = (ImageView)convertView.findViewById(R.id.list_item_icon);
        markerImage.setImageDrawable(new IconDrawable(context, Iconify.IconValue.fa_map_marker));
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        Log.e(tag, "calling ischild selectable.");
        return true;
    }
}
