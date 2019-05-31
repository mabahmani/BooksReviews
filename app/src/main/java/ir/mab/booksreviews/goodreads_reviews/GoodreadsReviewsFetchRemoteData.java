package ir.mab.booksreviews.goodreads_reviews;

import java.util.List;

import ir.mab.booksreviews.amazon_reviews.model.model.AmazonReview;
import ir.mab.booksreviews.goodreads_reviews.model.GoodreadsReview;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GoodreadsReviewsFetchRemoteData implements GoodreadsReviewsContract.FetchRemoteData {

    private String isbn10;
    private String page;

    public GoodreadsReviewsFetchRemoteData(String isbn10, String page) {
        this.isbn10 = isbn10;
        this.page = page;
    }

    public String getIsbn10() {
        return isbn10;
    }

    public void setIsbn10(String isbn10) {
        this.isbn10 = isbn10;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    @Override
    public void getReviews(final OnFinishedListener onFinishedListener) {

        GoodreadsReviewsApi goodreadsReviewsApi = new GoodreadsReviewsRetrofitInstance()
                .getInstance()
                .create(GoodreadsReviewsApi.class);

        Call<List<GoodreadsReview>> call = goodreadsReviewsApi.getReviews(isbn10,page);

        call.enqueue(new Callback<List<GoodreadsReview>>() {
            @Override
            public void onResponse(Call<List<GoodreadsReview>> call, Response<List<GoodreadsReview>> response) {
                if (response.isSuccessful()) {
                    onFinishedListener.onFinished(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<GoodreadsReview>> call, Throwable throwable) {
                onFinishedListener.onFailure(throwable);
            }
        });

    }
}
