package ir.mab.booksreviews.book_detail;

import android.util.Log;

import ir.mab.booksreviews.book_detail.model.BookDetails;
import ir.mab.booksreviews.book_detail.model.FidiboBookDetails;
import ir.mab.booksreviews.book_detail.model.FidiboBookId;
import ir.mab.booksreviews.fidibo_reviews.FidiboReviewsRetrofitInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookDetailsFetchRemoteData implements BookDetailsContract.FetchRemoteData {
    private String isbn;
    private String fidiboBookId;
    BookDetailsFetchRemoteData(String isbn){
        this.isbn = isbn;
    }

    @Override
    public void getBookDetails(final OnFinishedListener onFinishedListener) {

        BookDetailsApi bookDetailsApi = new BookDetailsRetrofitInstance().getInstance().create(BookDetailsApi.class);

        Call<BookDetails> call = bookDetailsApi.getDetails(isbn);

        Log.d("Retrofit",isbn+" "+call.request().url().toString());

        call.enqueue(new Callback<BookDetails>() {
            @Override
            public void onResponse(Call<BookDetails> call, Response<BookDetails> response) {
                Log.d("Retrofit","onSuccess: "+isbn);
                onFinishedListener.onFinished(response.body());
            }

            @Override
            public void onFailure(Call<BookDetails> call, Throwable throwable) {
                Log.d("Retrofit","onFailure: "+throwable);
                onFinishedListener.onFailure(throwable);
            }
        });
    }


/*    @Override
    public void getFidiboBookId(final FidiboBookIdOnFinishedListener onFinishedListener) {
        BookDetailsApi bookDetailsApi = new FidiboReviewsRetrofitInstance()
                .getInstance().create(BookDetailsApi.class);

        Call<FidiboBookId> call = bookDetailsApi.getFidiboBookId(isbn.replace("isbn:",""));
        call.enqueue(new Callback<FidiboBookId>() {
            @Override
            public void onResponse(Call<FidiboBookId> call, Response<FidiboBookId> response) {
                if (response.isSuccessful()){
                    onFinishedListener.onFinished(response.body());
                }
            }

            @Override
            public void onFailure(Call<FidiboBookId> call, Throwable throwable) {
                    onFinishedListener.onFailure(throwable);
            }
        });

    }*/

    @Override
    public void getFidiboBookDetails(final FidiboBookDetailsOnFinishedListener onFinishedListener) {
        BookDetailsApi bookDetailsApi = new FidiboReviewsRetrofitInstance()
                .getInstance().create(BookDetailsApi.class);

        Call<FidiboBookDetails> call = bookDetailsApi.getFidiboBookDetails(isbn.replace("isbn:",""));
        call.enqueue(new Callback<FidiboBookDetails>() {
            @Override
            public void onResponse(Call<FidiboBookDetails> call, Response<FidiboBookDetails> response) {
                    onFinishedListener.onFinished(response.body());
            }

            @Override
            public void onFailure(Call<FidiboBookDetails> call, Throwable throwable) {
                    onFinishedListener.onFailure(throwable);
            }
        });
    }
}
