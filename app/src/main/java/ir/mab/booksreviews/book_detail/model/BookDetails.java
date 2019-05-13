package ir.mab.booksreviews.book_detail.model;

import java.util.List;

public class BookDetails {
    private List<Item> items;

    public BookDetails() {
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
