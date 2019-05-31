package ir.mab.booksreviews.goodreads_reviews;

import java.util.List;

import ir.mab.booksreviews.amazon_reviews.model.model.AmazonReview;
import ir.mab.booksreviews.goodreads_reviews.model.GoodreadsReview;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GoodreadsReviewsApi {

    @GET("/goodreads")
    Call<List<GoodreadsReview>> getReviews(@Query("isbn") String isbn,
                                           @Query("page") String page);
}
