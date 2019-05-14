package ir.mab.booksreviews.book_detail.model;

import java.util.List;

public class BookDetails {

    private int totalItems;
    private List<Item> items;

    public BookDetails() {
    }

    public int getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
