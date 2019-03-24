package vishnu.rai.booknician.model;

public class recyclerview_item {

    String image, name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public recyclerview_item() {
    }


    public recyclerview_item(String image, String name) {
        this.image = image;
        this.name = name;
    }
}
