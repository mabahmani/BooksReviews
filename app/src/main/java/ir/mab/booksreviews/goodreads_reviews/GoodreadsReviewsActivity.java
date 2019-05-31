package ir.mab.booksreviews.goodreads_reviews;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import ir.mab.booksreviews.R;
import ir.mab.booksreviews.amazon_reviews.model.Adapter.AmazonReviewsAdapter;
import ir.mab.booksreviews.amazon_reviews.model.model.AmazonReview;
import ir.mab.booksreviews.goodreads_reviews.Adapter.GoodreadsReviewsAdapter;
import ir.mab.booksreviews.goodreads_reviews.model.GoodreadsReview;

public class GoodreadsReviewsActivity extends AppCompatActivity implements GoodreadsReviewsContract.View {

    private GoodreadsReviewsContract.Presenter presenter;
    private GoodreadsReviewsContract.FetchRemoteData fetchRemoteData;
    private List<GoodreadsReview> goodreadsReviews = new ArrayList<>();

    private RecyclerView goodReadsReviewsList;
    private GoodreadsReviewsAdapter adapter;

    private FrameLayout prog_back;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goodreads_reviews);

        Log.d("CLIKED","goodreads CLicked!");

        findViews();

        Intent intent = getIntent();
        String isbn10 = intent.getStringExtra("isbn10");

        fetchRemoteData = new GoodreadsReviewsFetchRemoteData(isbn10,"1");

        presenter = new GoodreadsReviewsPresenter(this,fetchRemoteData);

        presenter.requestDataFromServer();

    }

    private void findViews() {
        goodReadsReviewsList = findViewById(R.id.amazon_reviews_list);
        prog_back = findViewById(R.id.progbar_back);
        progressBar = findViewById(R.id.progbar);
    }

    @Override
    public void setData(List<GoodreadsReview> goodreadsReviews) {

        this.goodreadsReviews = goodreadsReviews;

        adapter = new GoodreadsReviewsAdapter(this.goodreadsReviews);

        recyclerViewInit();

    }

    private void recyclerViewInit() {
        goodReadsReviewsList.setLayoutManager(new LinearLayoutManager(this));
        goodReadsReviewsList.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        progressBar.setVisibility(View.INVISIBLE);
        prog_back.setVisibility(View.INVISIBLE);
    }

    @Override
    public void setPresenter(GoodreadsReviewsContract.Presenter presenter) {
        this.presenter = presenter;

    }
}
