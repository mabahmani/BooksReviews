package ir.mab.booksreviews.book_detail;

import ir.mab.booksreviews.BasePresenter;
import ir.mab.booksreviews.BaseView;
import ir.mab.booksreviews.book_detail.model.BookDetails;
import ir.mab.booksreviews.book_detail.model.FidiboBookDetails;
import ir.mab.booksreviews.book_detail.model.FidiboBookId;

public interface BookDetailsContract {

    interface View extends BaseView<Presenter> {

        void setData (BookDetails bookDetails);

        void setFidiboData(FidiboBookDetails fidiboData);

        void setFidiboBookId(FidiboBookId fidiboBookId);

        void getGoogle();
    }

    interface Presenter extends BasePresenter {

        void requestDataFromServer();

    }

    interface FetchRemoteData {

        interface OnFinishedListener{
            void onFinished(BookDetails bookDetails);
            void onFailure(Throwable t);
        }


        interface FidiboBookDetailsOnFinishedListener{
            void onFinished(FidiboBookDetails fidiboBookDetails);
            void onFailure(Throwable t);
        }

        void getBookDetails (OnFinishedListener onFinishedListener);
        void getFidiboBookDetails(FidiboBookDetailsOnFinishedListener onFinishedListener);
    }
}
