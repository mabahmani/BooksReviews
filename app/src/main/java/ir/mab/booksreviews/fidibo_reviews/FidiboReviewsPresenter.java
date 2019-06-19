package ir.mab.booksreviews.fidibo_reviews;

import java.util.List;

import ir.mab.booksreviews.fidibo_reviews.model.FidiboReview;

public class FidiboReviewsPresenter implements FidiboReviewsContract.Presenter , FidiboReviewsContract.FetchRemoteData.OnFinishedListener {

    private FidiboReviewsContract.View view;
    private FidiboReviewsContract.FetchRemoteData fetchRemoteData;

    public FidiboReviewsPresenter(FidiboReviewsContract.View view, FidiboReviewsContract.FetchRemoteData fetchRemoteData) {
        this.view = view;
        this.fetchRemoteData = fetchRemoteData;
        view.setPresenter(this);
    }

    @Override
    public void requestDataFromServer() {
        fetchRemoteData.getReviews(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void onFinished(List<FidiboReview> fidiboReviews) {
        view.setData(fidiboReviews);
    }

    @Override
    public void onFailure(Throwable t) {

    }
}
