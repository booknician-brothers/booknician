package vishnu.rai.booknician.model;

public class recyclerview_item {

    String image;
    String name;
    String authorname;


    public recyclerview_item(String image, String name, String authorname) {
        this.image = image;
        this.name = name;
        this.authorname = authorname;
    }



    public String getAuthorname() {
        return authorname;
    }

    public void setAuthorname(String authorname) {
        this.authorname = authorname;
    }

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



}
