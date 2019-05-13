package ir.mab.booksreviews.book_detail;

import android.util.Log;

import ir.mab.booksreviews.book_detail.model.BookDetails;

public class BookDetailsPresenter implements BookDetailsContract.Presenter , BookDetailsContract.FetchRemoteData.OnFinishedListener {

    private BookDetailsContract.View mView;
    private BookDetailsContract.FetchRemoteData fetchRemoteData;

    BookDetailsPresenter(BookDetailsContract.View mView, BookDetailsContract.FetchRemoteData fetchRemoteData) {
        this.mView = mView;
        this.fetchRemoteData = fetchRemoteData;
        mView.setPresenter(this);
    }

    @Override
    public void requestDataFromServer() {
        fetchRemoteData.getBookDetails(this);
    }

    @Override
    public void onFinished(BookDetails bookDetails) {
        Log.d("onFinished: ", bookDetails.getItems().get(0).getVolumeInfo().getTitle());
        mView.setData(bookDetails);
    }

    @Override
    public void onFailure(Throwable t) {

    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }
}