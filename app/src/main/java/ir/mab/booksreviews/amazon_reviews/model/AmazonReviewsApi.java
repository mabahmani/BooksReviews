package ir.mab.booksreviews.amazon_reviews.model;

import java.util.List;

import ir.mab.booksreviews.amazon_reviews.model.model.AmazonReview;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface AmazonReviewsApi {

    @GET("/")
    Call<List<AmazonReview>> getReviews(@Query("isbn") String isbn,
                                        @Query("page") String page);
}
