package ir.mab.booksreviews.amazon_reviews.model;

import java.util.List;

import ir.mab.booksreviews.BasePresenter;
import ir.mab.booksreviews.BaseView;
import ir.mab.booksreviews.amazon_reviews.model.model.AmazonReview;

public interface AmazonReviewsContract {

    interface View extends BaseView<Presenter>{
        void setData(List<AmazonReview> amazonReviews);
    }

    interface Presenter extends BasePresenter{
        void requestDataFromServer();
    }

    interface FetchRemoteData{

        interface OnFinishedListener{
            void onFinished(List<AmazonReview> amazonReview);
            void onFailure(Throwable t);
        }

        void getReviews(OnFinishedListener onFinishedListener);
    }
}
