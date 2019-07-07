package ir.mab.booksreviews.history;

import java.util.Date;

public class Barcode {
    private String isbn;
    private Date date;

    public Barcode() {

    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
