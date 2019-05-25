package ir.mab.booksreviews.amazon_reviews.model;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.LinearLayout;

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
    }

    @Override
    public void setPresenter(AmazonReviewsContract.Presenter presenter) {
        this.presenter = presenter;

    }
}
