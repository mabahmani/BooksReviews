package ir.mab.booksreviews.book_detail;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import ir.mab.booksreviews.R;
import ir.mab.booksreviews.amazon_reviews.model.AmazonReviewsActivity;
import ir.mab.booksreviews.book_detail.model.BookDetails;

public class BookDetailsActivity extends AppCompatActivity implements BookDetailsContract.View{

    private BookDetailsContract.Presenter mPresenter;
    private BookDetailsContract.FetchRemoteData fetchRemoteData;
    private BookDetails bookDetails;

    private ImageView bookCover,expand,collapse;
    private ImageView bookThumb;
    private TextView genre,bookName,author,pages,overallRating,pubDate,description;
    private FrameLayout prog_back;
    private ProgressBar progressBar;
    private CardView amazon,goodreads,fidibo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);

        findView();

        Intent intent = getIntent();
        String isbn = intent.getStringExtra("isbn");
        isbn = "isbn:"+ isbn;

        Toast.makeText(this,isbn,Toast.LENGTH_LONG).show();
        fetchRemoteData = new BookDetailsFetchRemoteData(isbn);

        mPresenter = new BookDetailsPresenter(this,fetchRemoteData);

        mPresenter.requestDataFromServer();

        amazon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(BookDetailsActivity.this, AmazonReviewsActivity.class);
                myIntent.putExtra("isbn10",
                        bookDetails.getItems().get(0)
                        .getVolumeInfo().getIndustryIdentifiers()
                        .get(1).getIdentifier());
                startActivity(myIntent);
            }
        });


        expand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                description.setMaxLines(Integer.MAX_VALUE);
                expand.setVisibility(View.GONE);
                collapse.setVisibility(View.VISIBLE);
            }
        });

        collapse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                description.setMaxLines(4);
                expand.setVisibility(View.VISIBLE);
                collapse.setVisibility(View.GONE);
            }
        });

    }

    private void findView() {
        bookCover = findViewById(R.id.toolbarImage);
        bookThumb = findViewById(R.id.book_img);
        genre = findViewById(R.id.genre);
        bookName = findViewById(R.id.book_name);
        author = findViewById(R.id.author);
        pages = findViewById(R.id.pages);
        overallRating = findViewById(R.id.overall_rating);
        pubDate = findViewById(R.id.pub_date);
        description = findViewById(R.id.description_txt);
        prog_back = findViewById(R.id.progbar_back);
        progressBar = findViewById(R.id.progbar);
        amazon = findViewById(R.id.amazon);
        goodreads = findViewById(R.id.goodreads);
        fidibo = findViewById(R.id.fidibo);
        expand = findViewById(R.id.expand_img);
        collapse = findViewById(R.id.collapse_img);
    }

    @Override
    public void setData(BookDetails bookDetails) {

        this.bookDetails = bookDetails;

        if (bookDetails.getTotalItems() > 0)
            setViews();
        else {
            Toast.makeText(this,"Failed!",Toast.LENGTH_LONG).show();
        }
    }

    @SuppressLint("SetTextI18n")
    private void setViews() {

        if (bookDetails.getItems().get(0).getVolumeInfo().getImageLinks() != null) {
            Log.d("IMGAE_LINK",bookDetails.getItems().get(0).getVolumeInfo().getImageLinks().getThumbnail());
            Picasso.get()
                    .load(bookDetails.getItems().get(0).getVolumeInfo().getImageLinks().getThumbnail().replace("http","https"))
                    .into(bookCover);

            Picasso.get()
                    .load(bookDetails.getItems().get(0).getVolumeInfo().getImageLinks().getSmallThumbnail().replace("http","https"))
                    .into(bookThumb);
        }

        genre.setText(bookDetails.getItems().get(0).getVolumeInfo().getCategories().get(0));
        bookName.setText(bookDetails.getItems().get(0).getVolumeInfo().getTitle());
        author.setText(bookDetails.getItems().get(0).getVolumeInfo().getAuthors().get(0));
        pages.setText(String.valueOf(bookDetails.getItems().get(0).getVolumeInfo().getPageCount()));
        overallRating.setText(bookDetails.getItems().get(0).getVolumeInfo().getAverageRating()+"/5");
        pubDate.setText(String.valueOf(bookDetails.getItems().get(0).getVolumeInfo().getPublishedDate()));
        description.setText(bookDetails.getItems().get(0).getVolumeInfo().getDescription());

        progressBar.setVisibility(View.INVISIBLE);
        prog_back.setVisibility(View.INVISIBLE);

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
