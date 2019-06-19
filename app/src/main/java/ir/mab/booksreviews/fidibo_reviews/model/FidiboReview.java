package ir.mab.booksreviews.fidibo_reviews.model;

public class FidiboReview {
    private String username;
    //private String title;
    //private String rate;
    private String date;
    private String text;

    public FidiboReview() {
    }

    public String getName() {
        return username;
    }

    public void setName(String name) {
        this.username = name;
    }

/*    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }*/

/*    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }*/

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
