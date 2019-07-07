package ir.mab.booksreviews.fidibo_reviews;

import java.util.List;

import ir.mab.booksreviews.book_detail.model.FidiboBookId;
import ir.mab.booksreviews.fidibo_reviews.model.FidiboReview;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface FidiboReviewsApi {

    @POST("/comments")
    Call<List<FidiboReview>> getReviews(@Body FidiboBookId fidiboBookId);
}
