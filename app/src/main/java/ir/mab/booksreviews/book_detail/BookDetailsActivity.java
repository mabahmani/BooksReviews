package ir.mab.booksreviews.book_detail;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import ir.mab.booksreviews.R;
import ir.mab.booksreviews.book_detail.model.BookDetails;

public class BookDetailsActivity extends AppCompatActivity implements BookDetailsContract.View{

    private BookDetailsContract.Presenter mPresenter;
    private BookDetailsContract.FetchRemoteData fetchRemoteData;
    private BookDetails bookDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);

        Intent intent = getIntent();
        String isbn = intent.getStringExtra("isbn");
        isbn = "isbn:"+ isbn;

        fetchRemoteData = new BookDetailsFetchRemoteData(isbn);

        mPresenter = new BookDetailsPresenter(this,fetchRemoteData);

        mPresenter.requestDataFromServer();

    }

    @Override
    public void setData(BookDetails bookDetails) {
        this.bookDetails = bookDetails;

        Toast.makeText(getApplicationContext(),bookDetails.getItems().get(0).getVolumeInfo().getTitle(),Toast.LENGTH_SHORT).show();

    }

    @Override
    public void setPresenter(BookDetailsContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.start();
    }
}
