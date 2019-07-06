package ir.mab.booksreviews.search;

import java.util.List;

import ir.mab.booksreviews.search.model.Book;
import ir.mab.booksreviews.search.model.SearchResponse;

public class SearchPresenter implements SearchContract.Presenter, SearchContract.FetchRemoteData.OnFinishedListener {

    private SearchContract.View view;
    private SearchContract.FetchRemoteData fetchRemoteData;

    public SearchPresenter(SearchContract.View view) {
        this.view = view;
    }

    public SearchPresenter(SearchContract.View view, SearchContract.FetchRemoteData fetchRemoteData) {
        this.view = view;
        this.fetchRemoteData = fetchRemoteData;
        view.setPresenter(this);
    }

    public void setFetchRemoteData(SearchContract.FetchRemoteData fetchRemoteData) {
        this.fetchRemoteData = fetchRemoteData;
    }

    public void setView(SearchContract.View view) {
        this.view = view;
    }

    @Override
    public void requestDataFromServer() {
        fetchRemoteData.getBooks(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void onFinished(SearchResponse searchResponse) {
        view.setData(searchResponse);
    }

    @Override
    public void onFailure(Throwable t) {

    }
}
