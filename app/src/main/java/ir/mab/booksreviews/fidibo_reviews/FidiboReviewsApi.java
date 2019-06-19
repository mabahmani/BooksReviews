package ir.mab.booksreviews.fidibo_reviews;

import java.util.List;

import ir.mab.booksreviews.fidibo_reviews.model.FidiboReview;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface FidiboReviewsApi {

    @GET("/getReviews/{id}")
    Call<List<FidiboReview>> getReviews(@Path("id") String id );
}
