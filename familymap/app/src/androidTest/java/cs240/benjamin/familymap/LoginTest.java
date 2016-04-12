package cs240.benjamin.familymap;

import cs240.benjamin.familymap.model.MainModel;
import android.app.Application;
import android.test.ApplicationTestCase;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.EditText;

import org.json.*;
import org.junit.Before;


/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class LoginTest extends ActivityInstrumentationTestCase2<MainActivity> {
    private MainActivity mActivity;

    //I wish there was a way to set these, as hardcoding isn't a versatile solution
    private String hostText = "10.14.35.68";
    private String portText = "8080";

    private String uname = "u";
    private String passText = "p";

    public LoginTest()
    {
        super("cs240.benjamin.familymap", MainActivity.class);
    }

    protected void setUp() throws Exception
    {
        super.setUp();

        mActivity = getActivity();

    }

    public void testLoginForm()
    {
        EditText host = (EditText)mActivity.findViewById(R.id.host1);
        EditText port = (EditText)mActivity.findViewById(R.id.port1);
        EditText user = (EditText)mActivity.findViewById(R.id.username1);
        EditText pass = (EditText)mActivity.findViewById(R.id.password1);

        assertNotNull(host);
        assertNotNull(pass);
        assertNotNull(port);
        assertNotNull(user);

        host.setText(hostText);
        port.setText(portText);
        user.setText(uname);
        pass.setText(passText);

        Button loginButton = (Button)mActivity.findViewById(R.id.login_button1);
        assertNotNull(loginButton);
        loginButton.callOnClick();

        
    }

}