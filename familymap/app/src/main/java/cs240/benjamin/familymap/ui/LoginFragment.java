package cs240.benjamin.familymap.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Button;
import android.util.Log;
import android.os.AsyncTask;
import java.net.URL;
import cs240.benjamin.familymap.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import cs240.benjamin.familymap.client.HTTPClient;
import cs240.benjamin.familymap.R;
import cs240.benjamin.familymap.model.MainModel;

import android.widget.Toast;

/**
 * Created by benjamin on 3/7/16.
 */
public class LoginFragment extends Fragment {

    private EditText password;
    private EditText username;
    private EditText host;
    private EditText port;
    private Button login;
    private String passText;
    private String userText;
    private String hostText;
    private String portText;

    public static final String tag = "familyLoginFragment";
//    public MainActivity parent;

    public LoginFragment(MainActivity a)
    {
        //parent = a;
        Log.e(tag, "creating login fragment");
    }

    public String toString()
    {
        return username.getText()+" pass "+password.getText()+" host "+host.getText()+" port "+port.getText();
    }

    public String getUserText()
    {
        return username.getText().toString();
    }

    public String getPassText()
    {
        return password.getText().toString();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.login_fragment, container, false);

        password = (EditText)v.findViewById(R.id.password1);
        username = (EditText)v.findViewById(R.id.username1);
        host = (EditText)v.findViewById(R.id.host1);
        port = (EditText)v.findViewById(R.id.port1);
        login = (Button)v.findViewById(R.id.login_button1);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e(tag, LoginFragment.this.toString());
                LoginFragment.this.doLogin();
            }
        });
        return v;
    }

    public void doLogin()
    {
        Log.e(tag, "doing login");
        userText = username.getText().toString();
        passText = password.getText().toString();
        portText = port.getText().toString();
        hostText = host.getText().toString();
        Authenticate authTask = new Authenticate();
        authTask.execute();
    }

    public void failLogin()
    {
        Log.e(tag, "failing login");
        Toast toast= Toast.makeText(getActivity().getApplicationContext(), "Login Failed", Toast.LENGTH_LONG);
        toast.show();
    }

    public void onLoginSucceded()
    {
        Log.e(tag, "successful login");
        UserDataTask udataTask = new UserDataTask();
        udataTask.execute();
    }

    public void showUserData(String fname, String lname)
    {
        Log.e(tag, "calling show user data with fname: "+fname+" lname "+lname);
        Toast toast = Toast.makeText(getActivity().getApplicationContext(), "First Name "+fname+ " Last Name "+lname, Toast.LENGTH_LONG);
        toast.show();
        //parent.showMap();
    }

    public class UserDataTask extends AsyncTask<URL, Integer, JSONObject>
    {
        public static final String tag = "familyudatatask";

        @Override
        public JSONObject doInBackground(URL... urls)
        {
            Log.e(tag, "called doinbackground");
            JSONObject res = HTTPClient.doAuthAction("person/"+MainModel.userID, MainModel.authToken);
            Log.e(tag, "did get action, res: "+res.toString());
            return res;
        }

        public void onProgressUpdate(Integer... progress)
        {

        }

        public void onPostExecute(JSONObject result)
        {
            if (result.has("message")) {
                failLogin();
            }
            else {
                String fname = "";
                String lname = "";
                try {
                    fname = result.getString("firstName");
                    lname = result.getString("lastName");
                }
                catch (JSONException e) {
                    Log.e(tag, e.getMessage() + " str " + e.toString());
//                    failLogin();
                    return;
                }
                showUserData(fname, lname);
            }
        }
    }


    public class Authenticate extends AsyncTask<URL, Integer, JSONObject>
    {
        public static final String tag = "familyauthtask";

        @Override
        public JSONObject doInBackground(URL... urls)
        {
            Log.e(tag, "called doinbackground");
            JSONObject body = new JSONObject();
            try {
                body.put("username", userText);
                body.put("password", passText);
            }
            catch (JSONException e)
            {
                Log.e(tag, "json error "+e.getMessage());
            }
            HTTPClient.setIP(hostText);
            HTTPClient.setPort(portText);
            Log.e(tag, "made request body "+body.toString());
            JSONObject res = HTTPClient.doPostAction("user/login", body);
            Log.e(tag, "did post action, res: "+res.toString());
            return res;
        }

        public void onProgressUpdate(Integer... progress)
        {

        }

        public void onPostExecute(JSONObject result)
        {
            if (result.has("message")) {
                failLogin();
            }
            else {
                try {
                    MainModel.userID = result.getString("personId   ");
                    MainModel.authToken = result.getString("Authorization");}
                catch (JSONException e) {
                    Log.e(tag, e.getMessage()+" str "+e.toString());
                    failLogin();
                    return;
                }
                onLoginSucceded();
            }
        }
    }

}
