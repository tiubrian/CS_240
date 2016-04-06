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
        MainModel.setChanged(true);
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

    public boolean isMotherSide() {
        return motherSide;
    }

    public void setMotherSide(boolean motherSide) {
        this.motherSide = motherSide;
        MainModel.setChanged(true);
    }

    public boolean isFatherSide() {
        return fatherSide;
    }

    public void setFatherSide(boolean fatherSide) {
        this.fatherSide = fatherSide;
        MainModel.setChanged(true);
    }

    public boolean isMaleEnabled() {
        return maleEnabled;
    }

    public void setMaleEnabled(boolean maleEnabled) {
        this.maleEnabled = maleEnabled;
        MainModel.setChanged(true);
    }

    public boolean isFemaleEnabled() {
        return femaleEnabled;
    }

    public void setFemaleEnabled(boolean femaleEnabled) {
        this.femaleEnabled = femaleEnabled;
        MainModel.setChanged(true);
    }
}
