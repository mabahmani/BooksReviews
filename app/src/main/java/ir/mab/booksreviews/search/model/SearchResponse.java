package ir.mab.booksreviews.search.model;

import java.util.List;

public class SearchResponse {
    private int count;
    private List<Book> books;

    public SearchResponse() {
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}
