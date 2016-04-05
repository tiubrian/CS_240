package cs240.benjamin.familymap.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;

import cs240.benjamin.familymap.R;

/**
 * Created by benjamin on 4/5/16.
 */
public class FilterAdapter extends ArrayAdapter<FilterView> {
    private ArrayList<FilterView> filters;
    private Context mContext;
    private LayoutInflater mInflater;

    public FilterAdapter(Context context, int resourceId, ArrayList<FilterView> filters)
    {
        super(context, resourceId);
        this.mContext = context;
        this.filters = filters;
        this.mInflater = (LayoutInflater)(context.getSystemService(Context.LAYOUT_INFLATER_SERVICE));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View row = convertView;
        FilterViewHolder holder = new FilterViewHolder();
        if (row == null)
        {
            row = mInflater.inflate(R.layout.filter_row, parent, false);
            holder.subText = (TextView)row.findViewById(R.id.filter_sub_text);
            holder.superText = (TextView)row.findViewById(R.id.filter_super_text);
            holder.enabled = (Switch)row.findViewById(R.id.filter_switch);
            row.setTag(holder);
        }
        else
        {
            holder = (FilterViewHolder)row.getTag();
        }

        FilterView filter = filters.get(position);

        if (filter != null)
        {
            holder.superText.setText(filter.getSuperText());
            holder.subText.setText(filter.getSubText());
            holder.enabled.setChecked(filter.isEnabled());
        }

        return row;
    }

}

class FilterViewHolder
{
    TextView superText;
    TextView subText;
    Switch enabled;
}