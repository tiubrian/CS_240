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

    public EventExpandableListAdapter(Context context, ArrayList<String> eventIds)
    {
        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.events = new ArrayList<Event>();
        Log.e(tag, "Creating event list adapter");

        for (int i = 0; i < eventIds.size(); i++)
        {
            events.add(MainModel.getEvent(eventIds.get(i)));
            Log.e(tag, " event "+events.get(i).toString());
        }

        //TODO: sort events properly
    }

    @Override
    public int getGroupCount() {
        return 1;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return events.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return null;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return events.get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null)
        {
            convertView = inflater.inflate(R.layout.event_header,null);
        }

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        if (convertView == null)
        {
            convertView = inflater.inflate(R.layout.event_child,null);
        }

        TextView itemText = (TextView)convertView.findViewById(R.id.event_list_item);
        Event event = events.get(childPosition);
        Person person  = MainModel.getPerson(event.getPersonId());
        itemText.setText(event.fullDescription()+System.getProperty("line.separator")+person.getFullName());

        ImageView markerImage = (ImageView)convertView.findViewById(R.id.event_marker);
        markerImage.setImageDrawable(new IconDrawable(context, Iconify.IconValue.fa_map_marker));
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
