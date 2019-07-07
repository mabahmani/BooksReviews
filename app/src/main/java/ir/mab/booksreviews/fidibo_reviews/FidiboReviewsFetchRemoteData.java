package ir.mab.booksreviews.fidibo_reviews;

import android.util.Log;

import java.util.List;

import ir.mab.booksreviews.book_detail.model.FidiboBookId;
import ir.mab.booksreviews.fidibo_reviews.model.FidiboReview;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FidiboReviewsFetchRemoteData implements FidiboReviewsContract.FetchRemoteData {

    private FidiboBookId id;

    public FidiboReviewsFetchRemoteData(FidiboBookId id) {
        this.id = id;
    }

    public FidiboBookId getId() {
        return id;
    }

    public void setId(FidiboBookId id) {
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
                Log.d("FidibRev",response.message());
                if (response.isSuccessful()){
                    Log.d("FidibRev","success");
                    onFinishedListener.onFinished(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<FidiboReview>> call, Throwable throwable) {
                Log.d("FidibRev",throwable.getMessage());
                onFinishedListener.onFailure(throwable);
            }
        });
    }
}
