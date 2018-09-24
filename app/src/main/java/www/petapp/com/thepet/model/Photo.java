package www.petapp.com.thepet.model;

import com.google.firebase.database.IgnoreExtraProperties;


// the data model for photo
@IgnoreExtraProperties
public class Photo {

    private String userID;
    private String breeder;
    private String name;
    private int age;
    private double size;
    private double weight;
    private String description;
    private String date_created;
    private String tag;

    public Photo() {

    }

    public Photo(String userID, String breeder, String name, int age, double size, double weight
                 , String description, String date_created, String tag) {
        this.userID = userID;
        this.breeder = breeder;
        this.name = name;
        this.age = age;
        this.size = size;
        this.weight = weight;
        this.description = description;
        this.date_created = date_created;
        this.tag = tag;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getBreeder() {
        return breeder;
    }

    public void setBreeder(String breeder) {
        this.breeder = breeder;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate_created() {
        return date_created;
    }

    public void setDate_created(String date_created) {
        this.date_created = date_created;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
