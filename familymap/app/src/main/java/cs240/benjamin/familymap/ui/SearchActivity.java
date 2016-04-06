package cs240.benjamin.familymap.ui;

import android.content.Context;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import com.joanzapata.android.iconify.IconDrawable;
import com.joanzapata.android.iconify.Iconify;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import cs240.benjamin.familymap.R;
import cs240.benjamin.familymap.model.Event;
import cs240.benjamin.familymap.model.MainModel;
import cs240.benjamin.familymap.model.Person;

public class SearchActivity extends ActionBarActivity {
    private ListView resultList;
    private EditText queryText;
    private String query;
    public static final String tag = "familysearchactivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Search");
        query = "";


        if (savedInstanceState != null)
        {
            query = savedInstanceState.getString("query");
        }

        queryText = (EditText)findViewById(R.id.search_query);

        queryText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                doSearch();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        this.resultList = (ListView)findViewById(R.id.search_list);
        if (resultList == null) Log.e(tag, "result listview is null after init");

        ResultAdapter adapter = new ResultAdapter(this, R.id.search_list, new ArrayList<Result>());
        resultList.setAdapter(adapter);
        if (query.length() > 0) doSearch();
    }


    public void doSearch()
    {
        query = queryText.getText().toString();
        Log.e(tag, "doing search with query "+query);

        clearResults();
        if (query.length() <= 0) return;

        ArrayList<Result> newResults = new ArrayList<Result>();

        for (Person p : MainModel.people.values())
        {
            if (p.containsString(query))
            {
                newResults.add(new Result(p.getFullName(), p.getGenderIcon()));
            }
        }


        Log.e(tag, "total matched people: "+Integer.toString(newResults.size()));

        for (Event e : MainModel.events.values())
        {
            if (e.containsString(query))
            {
                Person p = e.getPerson();
                String text = e.fullDescription()+System.getProperty("line.separator")+p.getFullName();
                newResults.add(new Result(text, Iconify.IconValue.fa_map_marker));
            }
        }

        ((ResultAdapter)resultList.getAdapter()).setResults(newResults);
    }

    public void clearResults()
    {
        if (resultList == null) Log.e(tag, " resultList View is null");
        ResultAdapter adapter = (ResultAdapter)resultList.getAdapter();
        if (adapter == null) Log.e(tag, " adapter is null");
        adapter.clear();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        outState.putString("query", query);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                Log.e(tag, "clicking on home button");
                finish();
                return true;
            default:
                return true;

        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}

class Result
{
    String text;
    Iconify.IconValue imageId;
    public Result(String text, Iconify.IconValue imageId)
    {
        this.text = text;
        this.imageId = imageId;
    }


}

class ResultAdapter extends ArrayAdapter<Result> {
    private ArrayList<Result> results;
    private Context mContext;
    private LayoutInflater mInflater;
    public static final String tag = "familyresultadapter";

    public ResultAdapter(Context context, int resourceId, ArrayList<Result> results)
    {
        super(context, resourceId);
        this.mContext = context;
        this.results = results;
        this.mInflater = (LayoutInflater)(context.getSystemService(Context.LAYOUT_INFLATER_SERVICE));
    }


    public void setResults(ArrayList<Result> newResults)
    {
        this.results = newResults;
    }

    @Override
    public void clear()
    {
        super.clear();
        results.clear();
    }


    @Override
    public int getCount()
    {
        Log.e(tag, "calling getCOunt");
        return results.size();
    }

    @Override
    public Context getContext()
    {
        Log.e(tag, "getting context");
        return mContext;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View row = convertView;
        ResultViewHolder holder = new ResultViewHolder();
        if (row == null)
        {
            row = mInflater.inflate(R.layout.list_child, parent, false);
            holder.text = (TextView)row.findViewById(R.id.list_item_text);
            holder.icon = (ImageView)row.findViewById(R.id.list_item_icon);
            row.setTag(holder);
        }
        else
        {
            holder = (ResultViewHolder)row.getTag();
        }

        Result result = results.get(position);

        if (result != null)
        {
            holder.text.setText(result.text);
            holder.icon.setImageDrawable(new IconDrawable(mContext, result.imageId));
        }

        return row;
    }

}

class ResultViewHolder
{
    TextView text;
    ImageView icon;
}