package ir.mab.booksreviews.fidibo_reviews;

import java.util.List;

import ir.mab.booksreviews.fidibo_reviews.model.FidiboReview;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FidiboReviewsFetchRemoteData implements FidiboReviewsContract.FetchRemoteData {

    private String id;

    public FidiboReviewsFetchRemoteData(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public void getReviews(final OnFinishedListener onFinishedListener) {
        FidiboReviewsApi fidiboReviewsApi = new FidiboReviewsRetrofitInstance()
                .getInstance()
                .create(FidiboReviewsApi.class);

        Call<List<FidiboReview>> call = fidiboReviewsApi.getReviews(id);

        call.enqueue(new Callback<List<FidiboReview>>() {
            @Override
            public void onResponse(Call<List<FidiboReview>> call, Response<List<FidiboReview>> response) {
                if (response.isSuccessful()){
                    onFinishedListener.onFinished(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<FidiboReview>> call, Throwable throwable) {
                    onFinishedListener.onFailure(throwable);
            }
        });
    }
}
