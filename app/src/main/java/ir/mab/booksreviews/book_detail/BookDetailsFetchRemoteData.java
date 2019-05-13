package ir.mab.booksreviews.book_detail;

import android.util.Log;

import ir.mab.booksreviews.book_detail.model.BookDetails;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookDetailsFetchRemoteData implements BookDetailsContract.FetchRemoteData {
    private String isbn;
    BookDetailsFetchRemoteData(String isbn){
        this.isbn = isbn;
    }

    @Override
    public void getBookDetails(final OnFinishedListener onFinishedListener) {

        BookDetailsApi bookDetailsApi = new BookDetailsRetrofitInstance().getInstance().create(BookDetailsApi.class);

        Call<BookDetails> call = bookDetailsApi.getDetails(isbn);

        call.enqueue(new Callback<BookDetails>() {
            @Override
            public void onResponse(Call<BookDetails> call, Response<BookDetails> response) {
                Log.d("Retrofit","onSuccess: "+isbn);
                onFinishedListener.onFinished(response.body());
            }

            @Override
            public void onFailure(Call<BookDetails> call, Throwable throwable) {
                Log.d("Retrofit","onFailure: "+throwable.getCause());
                onFinishedListener.onFailure(throwable);
            }
        });
    }
}
