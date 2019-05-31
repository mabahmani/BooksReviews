package ir.mab.booksreviews.amazon_reviews.model;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import ir.mab.booksreviews.R;
import ir.mab.booksreviews.amazon_reviews.model.Adapter.AmazonReviewsAdapter;
import ir.mab.booksreviews.amazon_reviews.model.model.AmazonReview;

public class AmazonReviewsActivity extends AppCompatActivity implements AmazonReviewsContract.View {

    private AmazonReviewsContract.Presenter presenter;
    private AmazonReviewsContract.FetchRemoteData fetchRemoteData;
    private List<AmazonReview> amazonReviews = new ArrayList<>();

    private RecyclerView amazonReviewsList;
    private AmazonReviewsAdapter adapter;

    private FrameLayout prog_back;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amazon_reviews);

        findViews();

        Intent intent = getIntent();
        String isbn10 = intent.getStringExtra("isbn10");

        fetchRemoteData = new AmazonReviewsFetchRemoteData(isbn10,"1");

        presenter = new AmazonReviewsPresenter(this,fetchRemoteData);

        presenter.requestDataFromServer();

    }

    private void findViews() {
        amazonReviewsList = findViewById(R.id.amazon_reviews_list);
        prog_back = findViewById(R.id.progbar_back);
        progressBar = findViewById(R.id.progbar);
    }

    @Override
    public void setData(List<AmazonReview> amazonReviews) {

        this.amazonReviews = amazonReviews;

        adapter = new AmazonReviewsAdapter(this.amazonReviews);

        recyclerViewInit();

    }

    private void recyclerViewInit() {
        amazonReviewsList.setLayoutManager(new LinearLayoutManager(this));
        amazonReviewsList.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        progressBar.setVisibility(View.INVISIBLE);
        prog_back.setVisibility(View.INVISIBLE);
    }

    @Override
    public void setPresenter(AmazonReviewsContract.Presenter presenter) {
        this.presenter = presenter;

    }
}
