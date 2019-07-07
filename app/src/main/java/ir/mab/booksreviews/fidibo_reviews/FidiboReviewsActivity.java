package ir.mab.booksreviews.fidibo_reviews;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import ir.mab.booksreviews.R;
import ir.mab.booksreviews.book_detail.model.FidiboBookId;
import ir.mab.booksreviews.fidibo_reviews.Adapter.FidiboReviewsAdapter;
import ir.mab.booksreviews.fidibo_reviews.model.FidiboReview;
import ir.mab.booksreviews.goodreads_reviews.Adapter.GoodreadsReviewsAdapter;
import ir.mab.booksreviews.goodreads_reviews.GoodreadsReviewsFetchRemoteData;

public class FidiboReviewsActivity extends AppCompatActivity implements FidiboReviewsContract.View{

    private FidiboReviewsContract.Presenter presenter;
    private FidiboReviewsContract.FetchRemoteData fetchRemoteData;
    private List<FidiboReview> fidiboReviews = new ArrayList<>();

    private RecyclerView fidiboReviewsList;
    private FidiboReviewsAdapter adapter;

    private FrameLayout prog_back;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fidibo_reviews);

        findViews();

        Intent intent = getIntent();
        int id = intent.getIntExtra("id",0);

        FidiboBookId fidiboBookId = new FidiboBookId();
        fidiboBookId.setBookId(Integer.valueOf(id));
        fetchRemoteData = new FidiboReviewsFetchRemoteData(fidiboBookId);

        presenter = new FidiboReviewsPresenter(this,fetchRemoteData);

        presenter.requestDataFromServer();

    }

    private void findViews() {
        fidiboReviewsList = findViewById(R.id.amazon_reviews_list);
        prog_back = findViewById(R.id.progbar_back);
        progressBar = findViewById(R.id.progbar);
    }

    @Override
    public void setData(List<FidiboReview> fidiboReviews) {
        this.fidiboReviews = fidiboReviews;

        adapter = new FidiboReviewsAdapter(this.fidiboReviews);

        recyclerViewInit();
    }

    private void recyclerViewInit() {
        fidiboReviewsList.setLayoutManager(new LinearLayoutManager(this));
        fidiboReviewsList.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        progressBar.setVisibility(View.INVISIBLE);
        prog_back.setVisibility(View.INVISIBLE);
    }

    @Override
    public void setPresenter(FidiboReviewsContract.Presenter presenter) {
        this.presenter = presenter;
    }
}
