package ir.mab.booksreviews.goodreads_reviews;

import java.util.List;

import ir.mab.booksreviews.amazon_reviews.model.model.AmazonReview;
import ir.mab.booksreviews.goodreads_reviews.model.GoodreadsReview;

public class GoodreadsReviewsPresenter implements GoodreadsReviewsContract.Presenter , GoodreadsReviewsContract.FetchRemoteData.OnFinishedListener {

    private GoodreadsReviewsContract.View view;
    private GoodreadsReviewsContract.FetchRemoteData fetchRemoteData;

    public GoodreadsReviewsPresenter(GoodreadsReviewsContract.View view, GoodreadsReviewsContract.FetchRemoteData fetchRemoteData) {
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
    public void onFinished(List<GoodreadsReview> goodreadsReviews) {
        view.setData(goodreadsReviews);

    }

    @Override
    public void onFailure(Throwable t) {

    }
}
