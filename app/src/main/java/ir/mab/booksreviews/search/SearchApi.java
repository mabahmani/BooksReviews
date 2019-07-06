package ir.mab.booksreviews.search;

import java.util.List;

import ir.mab.booksreviews.search.model.Book;
import ir.mab.booksreviews.search.model.SearchResponse;
import ir.mab.booksreviews.search.model.Title;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;

public interface SearchApi {

    @POST("/books")
    Call<SearchResponse> getBooks(@Body Title title);
}
