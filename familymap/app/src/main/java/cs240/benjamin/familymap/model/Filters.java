package cs240.benjamin.familymap.model;

import java.util.HashMap;
import java.util.Set;

import cs240.benjamin.familymap.model.Event;
import cs240.benjamin.familymap.model.Person;

/**
 * Created by benjamin on 4/5/16.
 */
public class Filters {
    public HashMap<String, Boolean> eventsEnabled;
    private boolean motherSide;
    private boolean fatherSide;
    private boolean maleEnabled;
    private boolean femaleEnabled;

    public Filters(Set<String> eventDescriptions)
    {
        motherSide = true;
        fatherSide = true;
        maleEnabled = true;
        femaleEnabled = true;
        eventsEnabled = new HashMap<String, Boolean>();
        for (String description : eventDescriptions)
        {
            description = description.toLowerCase();
            eventsEnabled.put(description, true);
        }
    }

    public void setEvent(String description, boolean value)
    {
        description = description.toLowerCase();
        eventsEnabled.put(description, value);
    }

    public boolean eventTypeVisible(String description)
    {
        Boolean result = eventsEnabled.get(description.toLowerCase());
        return (result==null) ? false : result;
    }

    public boolean eventVisible(Event e)
    {
        if (e == null) return false;
        if (!eventTypeVisible(e.getDescription())) return false;
        Person p = e.getPerson();
        String gender = p.getGender().toUpperCase();

        switch(gender)
        {
            case "M":
                if (!maleEnabled) return false;
                break;
            case "F":
                if (!femaleEnabled) return false;
                break;
            default:
                break;
        }

        if (!fatherSide && p.isOnFatherSide()) return false;
        if (!motherSide && p.isOnMotherSide()) return false;
        return true;
    }
}
