package www.petapp.com.thepet.model;

public class PetCardItem {

    private int imageURL;
    private String name;
    private String description;

    public PetCardItem(int imageURL, String name, String description) {
        this.imageURL = imageURL;
        this.name = name;
        this.description = description;
    }

    public int getImageURL() {

        return imageURL;
    }

    public void setImageURL(int imageURL) {

        this.imageURL = imageURL;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public String getDescription() {

        return description;
    }

    public void setDescription(String description) {

        this.description = description;
    }
}
