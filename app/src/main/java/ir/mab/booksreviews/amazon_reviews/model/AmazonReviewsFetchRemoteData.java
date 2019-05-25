package ir.mab.booksreviews.amazon_reviews.model;

import java.util.List;

import ir.mab.booksreviews.amazon_reviews.model.model.AmazonReview;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AmazonReviewsFetchRemoteData implements AmazonReviewsContract.FetchRemoteData {

    private String isbn10;
    private String page;

    public AmazonReviewsFetchRemoteData(String isbn10, String page) {
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

        AmazonReviewsApi amazonReviewsApi = new AmazonReviewsRetrofitInstance()
                .getInstance()
                .create(AmazonReviewsApi.class);

        Call<List<AmazonReview>> call = amazonReviewsApi.getReviews(isbn10,page);

        call.enqueue(new Callback<List<AmazonReview>>() {
            @Override
            public void onResponse(Call<List<AmazonReview>> call, Response<List<AmazonReview>> response) {
                if (response.isSuccessful()) {
                    onFinishedListener.onFinished(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<AmazonReview>> call, Throwable throwable) {
                onFinishedListener.onFailure(throwable);
            }
        });

    }
}
