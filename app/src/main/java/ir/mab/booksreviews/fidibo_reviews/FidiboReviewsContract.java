package ir.mab.booksreviews.fidibo_reviews;

import java.util.List;

import ir.mab.booksreviews.BasePresenter;
import ir.mab.booksreviews.BaseView;
import ir.mab.booksreviews.fidibo_reviews.model.FidiboReview;
import ir.mab.booksreviews.goodreads_reviews.GoodreadsReviewsContract;
import ir.mab.booksreviews.goodreads_reviews.model.GoodreadsReview;

public interface FidiboReviewsContract {

    interface View extends BaseView<Presenter> {
        void setData(List<FidiboReview> fidiboReviews);
    }

    interface Presenter extends BasePresenter {
        void requestDataFromServer();
    }

    interface FetchRemoteData{

        interface OnFinishedListener{
            void onFinished(List<FidiboReview> fidiboReviews);
            void onFailure(Throwable t);
        }

        void getReviews(OnFinishedListener onFinishedListener);
    }
}
