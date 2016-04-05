package cs240.benjamin.familymap.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;

import android.app.Application;
import android.graphics.Color;
import android.util.Log;
import android.util.Pair;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cs240.benjamin.familymap.client.HTTPClient;

/**
 * Created by benjamin on 3/14/16.
 */
public class MainModel extends Application {
    public static final String tag = "familymainmodel";

    public static String authToken = "";
    public static String userID = "";
    public static HashMap<String, Person> people = new HashMap<String, Person>();
    public static HashMap<String, Event> events = new HashMap<String, Event>();
    public static TreeSet<String> descriptions = new TreeSet<String>();
    public static Filters filters = null;

    public static Person getPerson(String id)
    {
        if (!people.containsKey(id)) return null;
        return people.get(id);
    }

    public static void error(String msg)
    {
        Log.e(tag, msg);
    }

    public static void clear()
    {
        authToken = "";
        userID = "";
        people.clear();
        events.clear();
    }


    /**
     * @return true for success, false on failure
     * */
    public static boolean sync()
    {
        HashMap<String, Person> temp_people = (HashMap<String, Person>)people.clone();
        HashMap<String, Event> temp_events = (HashMap<String, Event>)events.clone();
        try {
            people.clear();
            events.clear();
            loadPeople();
            loadEvents();
            computeDescriptionSet();
            filters = new Filters(descriptions);
            computeFamilySides();
//            dumpAllToLog();
            return true;
        }
        catch (Exception e)
        {
            //It may be that the data was cleared, but sync failed
            //If so, we need to restore the old data
            events = (HashMap<String, Event>)temp_events.clone();
            people = (HashMap<String, Person>)temp_people.clone();
            computeDescriptionSet();
            error("exception in sync "+ e.toString());
            return false;
        }
    }

    private static void recSetFatherSide(Person person)
    {
        if (person == null) return;
        person.setOnFatherSide(true);
        recSetFatherSide(person.getMother());
        recSetFatherSide(person.getFather());
        return;
    }

    private static void recSetMotherSide(Person person)
    {
        if (person == null) return;
        person.setOnMotherSide(true);
        recSetMotherSide(person.getMother());
        recSetMotherSide(person.getFather());
        return;

    }

    private static void computeFamilySides()
    {
        Person person = getPerson(userID);
        if (person == null)
        {
            Log.e(tag, "Uh-Oh, the current user is null!");
            return;
        }

        recSetFatherSide(person.getFather());
        recSetMotherSide(person.getMother());
    }

    private static void computeDescriptionSet() {
        descriptions.clear();
        for (Event e: events.values()) {
            descriptions.add(e.getDescription());
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
      //     dump("on person "+Integer.toString(i));
           JSONObject person_obj =  data.getJSONObject(i);
           String id = person_obj.getString("personID");
           String motherId = safeJSONGet(person_obj, "mother");
           String fatherId = safeJSONGet(person_obj, "father");

           //because I hate remembering the order of arguments to a constructor
           //also because the Person class should not know about how the server formats person JSON objects
           //what if I need to support multiple formats?
           Person person = new Person();
           person.setFirstName(person_obj.getString("firstName"));
           person.setLastName(person_obj.getString("lastName"));
           person.setSpouseId(safeJSONGet(person_obj, "spouse"));
           person.setGender(person_obj.getString("gender"));
           person.setFatherId(fatherId);
           person.setMotherId(motherId);
           person.setId(id);
           addPerson(id, person);
       }
    }

    public static void loadEvents() throws JSONException
    {
        dump("called load events");
        JSONObject res = HTTPClient.doAuthAction("event", authToken);
        dump("did events action with res "+res.toString());
        JSONArray data = res.getJSONArray("data");
        dump("loading events, got data");

        for (int i = 0; i < data.length(); i++)
        {
  //          dump("on event " + Integer.toString(i));
            JSONObject event_obj = data.getJSONObject(i);
//            dump("got event obj " + event_obj.toString());
            Event event = new Event();
            event.setCity(event_obj.getString("city"));
            event.setCountry(event_obj.getString("country"));
            event.setDescription(event_obj.getString("description"));
            event.setLat(event_obj.getDouble("latitude"));
            event.setLng(event_obj.getDouble("longitude"));
            event.setYear(event_obj.getString("year"));
            String eventId = event_obj.getString("eventID");
            String personId = event_obj.getString("personID");
            event.setPersonId(personId);
            event.setId(eventId);
    //        dump("filled event obj");
            addEvent(personId, eventId, event);
        }
    }

    public static ArrayList<Pair<String, String>> getImmediateRelatives(String personId)
    {
        ArrayList<Pair<String, String>> relatives = new ArrayList<Pair<String, String>>();

        Person p = getPerson(personId);
        String motherId = p.getMotherId();
        if (personExists(motherId)) relatives.add(new Pair<String, String>(motherId, "Mother"));

        String fatherId = p.getFatherId();
        if (personExists(fatherId)) relatives.add(new Pair<String, String>(fatherId, "Father"));

        String spouseId = p.getSpouseId();
        if (personExists(spouseId)) relatives.add(new Pair<String, String>(spouseId, "Spouse"));

        for (String childId : people.keySet())
        {
            Person child = getPerson(childId);
            if (child.getMotherId().equals(personId) || child.getFatherId().equals(personId) ) {
                relatives.add(new Pair<String, String>(childId, "Child"));
            }

        }
        return relatives;
    }

    public static boolean personExists(String personId)
    {
        if (personId == null) return false;
        return people.containsKey(personId);
    }

    public static void addPerson(String id, Person person)
    {
        people.put(id, person);
    }

    public static void addEvent(String personId, String eventId, Event event)
    {
        if (people.containsKey(personId)) {
            people.get(personId).addEvent(eventId);
            events.put(eventId, event);
        }
    }

    public static Event getEvent(String eventId)
    {
        if (!events.containsKey(eventId)) return null;
        return events.get(eventId);
    }

    //should not be called if filters is null
    public static boolean isEventVisible(String eventId)
    {
        if (filters == null)
        {
            Log.e(tag, "Error! Trying to check event visibility when filters are null.");
            return true;
        }
        return filters.eventVisible(getEvent(eventId));
    }

    public static int getLifeStoryColor()
    {
        //TODO: use settings to determine this
        return Color.BLUE;
    }

    public static int getSpouseLineColor()
    {
        return Color.RED;
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
