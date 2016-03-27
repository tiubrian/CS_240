package cs240.benjamin.familymap.model;

import java.util.HashMap;
import java.util.TreeMap;

/**
 * Created by benjamin on 3/26/16.
 */
public class Person {
    private String firstName;
    private String lastName;
    private String spouseId;
    private String motherId;
    private String fatherId;

    /**
     * It's not good if this gets changed by anyone to whatever they want. :)
     * */
    private String gender;
    private HashMap<String, Event> events;
    public static final String tag = "familyperson";

    Person()
    {

    }

    Person(String firstName, String lastName, String spouseId, String gender, String fatherId, String motherId)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.spouseId = spouseId;
        this.motherId = motherId;
        this.fatherId = fatherId;
        this.gender = gender;
    }

    public void addEvent(String id, Event event)
    {
        events.put(id, event);
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder("Person{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", spouseId='" + spouseId + '\'' +
                ", motherId='" + motherId + '\'' +
                ", fatherId='" + fatherId + '\'' +
                ", gender='" + gender + '\'' +
                '}');

        for (Event e: events.values())
        {
            //portability
            res.append(e.toString() + System.getProperty("line.separator"));
        }

        return res.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Person person = (Person) o;

        if (firstName != null ? !firstName.equals(person.firstName) : person.firstName != null)
            return false;
        if (lastName != null ? !lastName.equals(person.lastName) : person.lastName != null)
            return false;
        if (spouseId != null ? !spouseId.equals(person.spouseId) : person.spouseId != null)
            return false;
        if (motherId != null ? !motherId.equals(person.motherId) : person.motherId != null)
            return false;
        if (fatherId != null ? !fatherId.equals(person.fatherId) : person.fatherId != null)
            return false;
        return !(gender != null ? !gender.equals(person.gender) : person.gender != null);

    }

    @Override
    public int hashCode() {
        int result = firstName != null ? firstName.hashCode() : 0;
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (spouseId != null ? spouseId.hashCode() : 0);
        result = 31 * result + (motherId != null ? motherId.hashCode() : 0);
        result = 31 * result + (fatherId != null ? fatherId.hashCode() : 0);
        result = 31 * result + (gender != null ? gender.hashCode() : 0);
        return result;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSpouseId() {
        return spouseId;
    }

    public void setSpouseId(String spouseId) {
        this.spouseId = spouseId;
    }

    public String getMotherId() {
        return motherId;
    }

    public void setMotherId(String motherId) {
        this.motherId = motherId;
    }

    public String getFatherId() {
        return fatherId;
    }

    public void setFatherId(String fatherId) {
        this.fatherId = fatherId;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
