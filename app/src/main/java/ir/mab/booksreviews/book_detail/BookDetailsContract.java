package ir.mab.booksreviews.book_detail;

import ir.mab.booksreviews.BasePresenter;
import ir.mab.booksreviews.BaseView;
import ir.mab.booksreviews.book_detail.model.BookDetails;

public interface BookDetailsContract {

    interface View extends BaseView<Presenter> {

        void setData (BookDetails bookDetails);
    }

    interface Presenter extends BasePresenter {

        void requestDataFromServer();

    }

    interface FetchRemoteData {

        interface OnFinishedListener{
            void onFinished(BookDetails bookDetails);
            void onFailure(Throwable t);
        }

        void getBookDetails (OnFinishedListener onFinishedListener);

    }
}
