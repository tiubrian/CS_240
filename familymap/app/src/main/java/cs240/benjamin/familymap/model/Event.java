package cs240.benjamin.familymap.model;

/**
 * Created by benjamin on 3/26/16.
 */
public class Event {
    private String description;
    private double lat;
    private double lng;
    private String country;
    private String city;
    private String year;
    private String personId;

    public Event()
    {

    }

    public Event(String description, double lat, double lng, String country, String city, String year, String personId) {
        //using the setter because the description must always be lower case
        this.setDescription(description);
        this.lat = lat;
        this.lng = lng;
        this.country = country;
        this.city = city;
        this.year = year;
        this.personId = personId;
    }

    public int compareTo(Event other)
    {
        if (other.getDescription().equals("birth") && !description.equals("birth")) return 1;
        if (!other.getDescription().equals("birth") && description.equals("birth")) return -1;
        if (other.getDescription().equals("death") && !description.equals("death")) return -1;
        if (!other.getDescription().equals("death") && description.equals("death")) return 1;

        int year_cmp = year.compareTo(other.getYear());
        if (year_cmp !=0 ) return year_cmp;

        return description.compareTo(other.getDescription());
    }

    public String fullDescription()
    {
        return description + ": " + city + ", " + country + ", ("+year+")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Event event = (Event) o;

        if (Double.compare(event.lat, lat) != 0) return false;
        if (Double.compare(event.lng, lng) != 0) return false;
        if (description != null ? !description.equals(event.description) : event.description != null)
            return false;
        if (country != null ? !country.equals(event.country) : event.country != null) return false;
        if (city != null ? !city.equals(event.city) : event.city != null) return false;
        return !(year != null ? !year.equals(event.year) : event.year != null);

    }

    @Override
    public int hashCode() {
        int result = 0;
        long temp;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        temp = Double.doubleToLongBits(lat);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(lng);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (country != null ? country.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (year != null ? year.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Event{" +
                ", description='" + description + '\'' +
                ", lat=" + lat +
                ", lng=" + lng +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", year='" + year + '\'' +
                '}';
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description.toLowerCase();
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}
