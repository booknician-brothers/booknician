package vishnu.rai.booknician.model;

public class orderpage_recyclerview_item {


    String book_name;
    String book_image;
    String order_date;
    String time_date;


    public orderpage_recyclerview_item(String book_name, String book_image, String order_date, String time_date) {
        this.book_name = book_name;
        this.book_image = book_image;
        this.order_date = order_date;
        this.time_date = time_date;
    }

    public String getBook_name() {
        return book_name;
    }

    public void setBook_name(String book_name) {
        this.book_name = book_name;
    }

    public String getBook_image() {
        return book_image;
    }

    public void setBook_image(String book_image) {
        this.book_image = book_image;
    }

    public String getOrder_date() {
        return order_date;
    }

    public void setOrder_date(String order_date) {
        this.order_date = order_date;
    }

    public String getTime_date() {
        return time_date;
    }

    public void setTime_date(String time_date) {
        this.time_date = time_date;
    }

    public orderpage_recyclerview_item() {
    }
}
