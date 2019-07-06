package ir.mab.booksreviews.search;

import android.util.Log;

import java.util.List;

import ir.mab.booksreviews.search.model.Book;
import ir.mab.booksreviews.search.model.SearchResponse;
import ir.mab.booksreviews.search.model.Title;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchFetchRemoteData implements SearchContract.FetchRemoteData {

    private Title title;

    public SearchFetchRemoteData() {
    }

    public SearchFetchRemoteData(Title title) {
        this.title = title;
    }

    public void setTitle(Title title) {
        this.title = title;
    }

    @Override
    public void getBooks(final OnFinishedListener onFinishedListener) {

        SearchApi searchApi = new SearchRetrofitInstance()
                .getInstance().create(SearchApi.class);

        Call<SearchResponse> call = searchApi.getBooks(title);

        call.enqueue(new Callback<SearchResponse>() {
            @Override
            public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {
                if (response.isSuccessful()) {
                    Log.d("Aminretro","success");
                    onFinishedListener.onFinished(response.body());
                }
            }

            @Override
            public void onFailure(Call<SearchResponse> call, Throwable throwable) {
                Log.d("Aminretro",throwable.getMessage());
                onFinishedListener.onFailure(throwable);
            }
        });
    }
}
