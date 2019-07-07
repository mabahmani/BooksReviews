package ir.mab.booksreviews.book_detail;

import ir.mab.booksreviews.book_detail.model.BookDetails;
import ir.mab.booksreviews.book_detail.model.FidiboBookDetails;
import ir.mab.booksreviews.book_detail.model.FidiboBookId;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface BookDetailsApi {

    @GET("volumes/")
    Call<BookDetails> getDetails(@Query("q") String isbn);


    @GET("getBookId/{isbn}")
    Call<FidiboBookId> getFidiboBookId(@Path("isbn") String isbn);

    @GET("books/{isbn}")
    Call<FidiboBookDetails> getFidiboBookDetails(@Path("isbn") String isbn);
}
