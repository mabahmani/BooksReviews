package ir.mab.booksreviews.book_detail;

import android.util.Log;

import ir.mab.booksreviews.book_detail.model.BookDetails;
import ir.mab.booksreviews.book_detail.model.FidiboBookDetails;
import ir.mab.booksreviews.book_detail.model.FidiboBookId;

public class BookDetailsPresenter implements BookDetailsContract.Presenter,
        BookDetailsContract.FetchRemoteData.OnFinishedListener,
        BookDetailsContract.FetchRemoteData.FidiboBookDetailsOnFinishedListener{

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
    public void start() {
        fetchRemoteData.getFidiboBookDetails(this);
    }

    @Override
    public void stop() {

    }

    @Override
    public void onFinished(BookDetails bookDetails) {
        //Log.d("BOOKDetails",bookDetails.getItems().size()+"");
        mView.setData(bookDetails);
    }


    @Override
    public void onFinished(FidiboBookDetails fidiboBookDetails) {
        if (fidiboBookDetails != null) {
            Log.d("START", "start shod!" + fidiboBookDetails.getTitle());
            mView.setFidiboData(fidiboBookDetails);
            FidiboBookId fidiboBookId = new FidiboBookId();
            fidiboBookId.setBookId(fidiboBookDetails.getId());
            mView.setFidiboBookId(fidiboBookId);
        }

        else {
            Log.d("GOOGLE","ARE GOOGLE!");
            mView.getGoogle();
        }
    }

    @Override
    public void onFailure(Throwable t) {

    }

}