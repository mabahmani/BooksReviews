package ir.mab.booksreviews.goodreads_reviews;

import java.util.List;

import ir.mab.booksreviews.BasePresenter;
import ir.mab.booksreviews.BaseView;
import ir.mab.booksreviews.amazon_reviews.model.model.AmazonReview;
import ir.mab.booksreviews.goodreads_reviews.model.GoodreadsReview;

public interface GoodreadsReviewsContract {

    interface View extends BaseView<Presenter>{
        void setData(List<GoodreadsReview> goodreadsReviews);
    }

    interface Presenter extends BasePresenter{
        void requestDataFromServer();
    }

    interface FetchRemoteData{

        interface OnFinishedListener{
            void onFinished(List<GoodreadsReview> goodreadsReviews);
            void onFailure(Throwable t);
        }

        void getReviews(OnFinishedListener onFinishedListener);
    }
}
