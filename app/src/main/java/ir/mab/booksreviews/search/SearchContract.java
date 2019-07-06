package ir.mab.booksreviews.search;

import java.util.List;

import ir.mab.booksreviews.BasePresenter;
import ir.mab.booksreviews.BaseView;
import ir.mab.booksreviews.search.model.Book;
import ir.mab.booksreviews.search.model.SearchResponse;

public interface SearchContract {

    interface View extends BaseView<Presenter> {
        void setData(SearchResponse searchResponse);
    }

    interface Presenter extends BasePresenter {
        void requestDataFromServer();
    }

    interface FetchRemoteData {
        interface OnFinishedListener {
            void onFinished(SearchResponse searchResponse);

            void onFailure(Throwable t);
        }

        void getBooks(SearchContract.FetchRemoteData.OnFinishedListener onFinishedListener);
    }
}
