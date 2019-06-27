package ir.mab.booksreviews.book_detail;

import android.util.Log;

import ir.mab.booksreviews.book_detail.model.BookDetails;
import ir.mab.booksreviews.book_detail.model.FidiboBookDetails;
import ir.mab.booksreviews.book_detail.model.FidiboBookId;

public class BookDetailsPresenter implements BookDetailsContract.Presenter,
        BookDetailsContract.FetchRemoteData.OnFinishedListener,
        BookDetailsContract.FetchRemoteData.FidiboBookIdOnFinishedListener,
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
        fetchRemoteData.getFidiboBookId(this);
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
    public void onFinished(FidiboBookId fidiboBookId) {
        if (fidiboBookId.getBookId() != null){
            mView.setFidiboBookId(fidiboBookId);
            fetchRemoteData.getFidiboBookDetails(this,fidiboBookId.getBookId());
        }

        else {
            Log.d("GOOGLE","ARE GOOGLE!");
            mView.getGoogle();
        }
    }

    @Override
    public void onFinished(FidiboBookDetails fidiboBookDetails) {
        Log.d("START","start shod!"+ fidiboBookDetails.getBookName());
        mView.setFidiboData(fidiboBookDetails);
    }

    @Override
    public void onFailure(Throwable t) {

    }

}