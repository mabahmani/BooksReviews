package ir.mab.booksreviews.amazon_reviews.model;

import java.util.List;

import ir.mab.booksreviews.amazon_reviews.model.model.AmazonReview;

public class AmazonReviewsPresenter implements AmazonReviewsContract.Presenter , AmazonReviewsContract.FetchRemoteData.OnFinishedListener {

    private AmazonReviewsContract.View view;
    private AmazonReviewsContract.FetchRemoteData fetchRemoteData;

    public AmazonReviewsPresenter(AmazonReviewsContract.View view, AmazonReviewsContract.FetchRemoteData fetchRemoteData) {
        this.view = view;
        this.fetchRemoteData = fetchRemoteData;
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
    public void onFinished(List<AmazonReview> amazonReview) {
        view.setData(amazonReview);

    }

    @Override
    public void onFailure(Throwable t) {

    }
}
