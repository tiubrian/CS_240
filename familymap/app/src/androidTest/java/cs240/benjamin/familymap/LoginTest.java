package cs240.benjamin.familymap;

import cs240.benjamin.familymap.model.Event;
import cs240.benjamin.familymap.model.MainModel;
import cs240.benjamin.familymap.model.Person;

import android.app.Application;
import android.test.ApplicationTestCase;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import org.json.*;
import org.junit.Before;

import java.util.ArrayList;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class LoginTest extends ActivityInstrumentationTestCase2<MainActivity> {
    private MainActivity mActivity;

    //I wish there was a way to set these, as hardcoding isn't a versatile solution
    private String hostText = "10.24.66.76";
    private String portText = "8080";

    private String uname = "u";
    private String passText = "p";

    public final static String tag = "familymaplogintest";

    public LoginTest()
    {
        super("cs240.benjamin.familymap", MainActivity.class);
    }

    protected void setUp() throws Exception
    {
        super.setUp();

        mActivity = getActivity();

    }


    public void enterText(final EditText field, final String text)
    {
        assertNotNull(field);
        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                field.setText(text);
            }
        });
//        getInstrumentation().waitForIdleSync();
//        getInstrumentation().sendStringSync(text);
    }

    public void clickButton(final Button button)
    {
      assertNotNull(button);
      getInstrumentation().runOnMainSync(new Runnable() {
          @Override
          public void run() {
              button.performClick();
          }
      });
    }

    public void testLoginForm()
    {
        final EditText host = (EditText)mActivity.findViewById(R.id.host1);
        final EditText port = (EditText)mActivity.findViewById(R.id.port1);
        final EditText user = (EditText)mActivity.findViewById(R.id.username1);
        final EditText pass = (EditText)mActivity.findViewById(R.id.password1);

        enterText(host, hostText);
        enterText(port, portText);
        enterText(user, uname);
        enterText(pass, passText);

        Button loginButton = (Button)mActivity.findViewById(R.id.login_button1);
        clickButton(loginButton);

        int num_tries = 30;
        while (MainModel.people.size() <= 0 || MainModel.events.size() <= 0)
        {
            num_tries--;
            if (num_tries <= 0) assertTrue("timed out waiting for data", false);
            try {
               Thread.sleep(1000, 0);
            }
            catch (InterruptedException e)
            {
                 Log.e(tag, "interrupted ");
            }
        }

        checkRelationships();
        checkEventSorting();
        doFilterTests();
    }


    public void checkRelationships()
    {
        for (Person p: MainModel.people.values())
        {
            String spouseId = p.getSpouseId();
            Person spouse = MainModel.getPerson(spouseId);
            //The spouse relation should be symmetric
            if (spouse != null) assertTrue(spouse.getSpouseId().equals(p.getId()));

            Person mother = p.getMother();
            if (mother != null) assertTrue(mother.getId().equals(p.getMotherId()));

            Person father = p.getFather();
            if (father != null) assertTrue(father.getId().equals(p.getFatherId()));
        }
    }

    public void checkEventSorting()
    {
        for (Person p : MainModel.people.values())
        {
            ArrayList<Event> events = p.getSortedEvents();
            int num_events = events.size();
            boolean seenBirth = false;
            for (int i = 0; i < num_events; i++)
            {
                Event curr = events.get(i);
                switch (curr.getDescription().toLowerCase())
                {
                    case "birth":
                        assertTrue("The first birth event is not the first event for person "+p.toString(), !seenBirth && i > 0);
                        seenBirth = true;
                        break;

                    case "death":
                        //if there are multiple death events, this will cause a problem.
                        assertTrue("The first death event is not the last event for person "+p.toString(), i == num_events-1);
                        break;
                    default:
                        break;
                }
            }
        }
    }

    public void genderFilterTests()
    {
        MainModel.filters.setMaleEnabled(false);
        for (Event e : MainModel.events.values())
        {
            if(e.getPerson().getGender().toLowerCase().equals("m")) {
                assertTrue("Male events should be invisible when filtered."
                        , !MainModel.isEventVisible(e.getId()));
            }
        }

        MainModel.filters.setFemaleEnabled(false);
        for (Event e : MainModel.events.values())
        {
            if(e.getPerson().getGender().toLowerCase().equals("f")) {
                assertTrue("Female events should be invisible when filtered."
                        , !MainModel.isEventVisible(e.getId()));
            }
        }

        MainModel.filters.setFemaleEnabled(true);
        MainModel.filters.setMaleEnabled(true);
        //all events should be visible
        checkEventsVisible();
    }

    public void checkEventsVisible()
    {
        for (String eventId : MainModel.events.keySet())
        {
            assertTrue("all events should be visible", MainModel.isEventVisible(eventId));
        }
    }

    public void recCheckFatherSideDisabled(Person p)
    {
        if (p == null) return;
        for (String id : p.getEventIds())
        {
            assertTrue("all of person "+p.toString()+" events should be invisible. ", !MainModel.isEventVisible(id));
        }
        recCheckFatherSideDisabled(p.getFather());
        recCheckFatherSideDisabled(p.getMother());
    }

    public void recCheckMotherSideDisabled(Person p)
    {
        if (p == null) return;
        for (String id : p.getEventIds())
        {
            assertTrue("all of person "+p.toString()+" events should be invisible. ", !MainModel.isEventVisible(id));
        }
        recCheckMotherSideDisabled(p.getFather());
        recCheckMotherSideDisabled(p.getMother());
    }


    public void familySideFilterTests()
    {
        Person user = MainModel.getPerson(MainModel.userID);

        MainModel.filters.setFatherSide(false);
        recCheckFatherSideDisabled(user.getFather());
        MainModel.filters.setFatherSide(true);

        MainModel.filters.setMotherSide(false);
        recCheckMotherSideDisabled(user.getMother());
        MainModel.filters.setMotherSide(true);
    }

    public void descriptionFilterTests()
    {
        for (String description : MainModel.descriptions)
        {
            MainModel.filters.setEvent(description, false);
            for (Event e: MainModel.events.values())
            {
                if (e.getDescription().toLowerCase().equals(description.toLowerCase()))
                {
                    assertTrue("Event filter of type "+description+" not disabling events.", !MainModel.isEventVisible(e.getId()));
                }
            }
            MainModel.filters.setEvent(description, true);
        }
    }

    public void doFilterTests() {
        genderFilterTests();
        familySideFilterTests();
        descriptionFilterTests();
    }



}