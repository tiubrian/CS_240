package cs240.benjamin.familymap.model;

import java.util.HashMap;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cs240.benjamin.familymap.client.HTTPClient;

/**
 * Created by benjamin on 3/14/16.
 */
public class MainModel {
    public static final String tag = "familymainmodel";

    public static String authToken = "";
    public static String userID = "";
    public static HashMap<String, Person> people = new HashMap<String, Person>();

    public static Person getPerson(String id)
    {
        return people.get(id);
    }

    public static void error(String msg)
    {
        Log.e(tag, msg);
    }

    /**
     * @return true for success, false on failure
     * */
    public static boolean sync()
    {
        try {
            people.clear();
            loadPeople();
            loadEvents();
            dumpAllToLog();
            return true;
        }
        catch (Exception e)
        {
            error("exception in sync "+ e.toString());
            return false;
        }
    }

    public static String safeJSONGet(JSONObject obj, String property)
    {
        try{
            return (obj.has(property)) ? obj.getString(property) : "";
        }
        catch (JSONException e)
        {
            return "";
        }
    }

    public static void loadPeople() throws JSONException
    {
        dump("called loadpeople");
       JSONObject res = HTTPClient.doAuthAction("person", authToken);
        dump("doing person action with res "+res.toString());
       JSONArray data = res.getJSONArray("data");
        dump("loading people, got data");

       for (int i=0; i < data.length(); i++)
       {
           dump("on person "+Integer.toString(i));
           JSONObject person_obj =  data.getJSONObject(i);
           String id = person_obj.getString("personID");
           String motherId = safeJSONGet(person_obj, "motherID");
           String fatherId = safeJSONGet(person_obj, "fatherID");

           //because I hate remebering the order of arguments to a constructor
           //also because the Person class should not know about how the server formats person JSON objects
           //what if I need to support multiple formats?
           Person person = new Person();
           person.setFirstName(person_obj.getString("firstName"));
           person.setLastName(person_obj.getString("lastName"));
           person.setSpouseId(person_obj.getString("spouseID"));
           person.setGender(person_obj.getString("gender"));
           person.setFatherId(fatherId);
           person.setMotherId(motherId);
           addPerson(id, person);
       }
    }

    public static void loadEvents() throws JSONException
    {
        dump("called load events");
        JSONObject res = HTTPClient.doAuthAction("event", authToken);
        dump("did events action");
        JSONArray data = res.getJSONArray("data");
        dump("loading events, got data");

        for (int i = 0; i < data.length(); i++)
        {
            dump("on event "+Integer.toString(i));
            JSONObject event_obj = data.getJSONObject(i);
            Event event = new Event();
            event.setCity(event_obj.getString("city"));
            event.setCountry(event_obj.getString("country"));
            event.setDescription(event_obj.getString("description"));
            event.setLat(event_obj.getDouble("latitude"));
            event.setLng(event_obj.getDouble("longitude"));
            String eventId = event_obj.getString("eventID");
            String personId = event_obj.getString("personID");
            addEvent(personId, eventId, event);
        }
    }

    public static void addPerson(String id, Person person)
    {
        people.put(id, person);
    }

    public static void addEvent(String personId, String eventId, Event event)
    {
        if (people.containsKey(personId)) {
            people.get(personId).addEvent(eventId, event);
        }
    }

    public static void dump(String msg)
    {
        Log.d(tag, msg);
    }

    public static void dumpAllToLog()
    {
        dump("dumping main model to log");
        for (Person p: people.values())
        {
            dump(p.toString());
        }
    }
}
