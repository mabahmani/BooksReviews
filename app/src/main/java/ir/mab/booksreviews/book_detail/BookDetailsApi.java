package ir.mab.booksreviews.book_detail;

import ir.mab.booksreviews.book_detail.model.BookDetails;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface BookDetailsApi {

    @GET("volumes/")
    Call<BookDetails> getDetails(@Query("q") String isbn);
}
