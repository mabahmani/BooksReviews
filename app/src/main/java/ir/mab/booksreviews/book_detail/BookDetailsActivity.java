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

import java.util.Calendar;
import java.util.Date;

import ir.mab.booksreviews.MainActivity;
import ir.mab.booksreviews.R;
import ir.mab.booksreviews.amazon_reviews.model.AmazonReviewsActivity;
import ir.mab.booksreviews.book_detail.model.BookDetails;
import ir.mab.booksreviews.book_detail.model.FidiboBookDetails;
import ir.mab.booksreviews.book_detail.model.FidiboBookId;
import ir.mab.booksreviews.fidibo_reviews.FidiboReviewsActivity;
import ir.mab.booksreviews.goodreads_reviews.GoodreadsReviewsActivity;
import ir.mab.booksreviews.history.Barcode;
import ir.mab.booksreviews.history.BarcodeList;
import ir.mab.booksreviews.utils.SharedPref;

public class BookDetailsActivity extends AppCompatActivity implements BookDetailsContract.View{

    private BookDetailsContract.Presenter mPresenter;
    private BookDetailsContract.FetchRemoteData fetchRemoteData;
    private BookDetails bookDetails;
    private FidiboBookDetails fidiboBookDetails;
    private FidiboBookId fidiboBookId;

    private ImageView bookCover,expand,collapse;
    private ImageView bookThumb;
    private TextView genre,bookName,author,pages,overallRating,pubDate,description,bookNotFound;
    private FrameLayout prog_back;
    private ProgressBar progressBar;
    private CardView amazon,goodreads,fidibo;

    private String bookIsbn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details_fa);

        Intent intent = getIntent();
        String isbn = intent.getStringExtra("isbn");
        bookIsbn = isbn;
        isbn = "isbn:"+ isbn;

        //Toast.makeText(this,isbn,Toast.LENGTH_LONG).show();
        fetchRemoteData = new BookDetailsFetchRemoteData(isbn);

        mPresenter = new BookDetailsPresenter(this,fetchRemoteData);

        mPresenter.start();


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
        bookNotFound = findViewById(R.id.bookNotFound);
    }

    @Override
    public void setData(BookDetails bookDetails) {

        this.bookDetails = bookDetails;

        if (bookDetails.getTotalItems() > 0)
            setViews();
        else {
            progressBar.setVisibility(View.INVISIBLE);
            bookNotFound.setVisibility(View.VISIBLE);
            //Toast.makeText(this,"Failed!",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void setFidiboData(FidiboBookDetails fidiboData) {
        setContentView(R.layout.activity_book_details_fa);
        findView();
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
        fidibo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(BookDetailsActivity.this, FidiboReviewsActivity.class);
                myIntent.putExtra("id", fidiboBookId.getBookId());
                startActivity(myIntent);

            }
        });
        this.fidiboBookDetails = fidiboData;
        if (fidiboData != null)
            setFidiboViews();
        else {
            //Toast.makeText(this,"Failed!",Toast.LENGTH_LONG).show();
        }
    }

    private void setFidiboViews() {
        if (!fidiboBookDetails.getPicture_url().equals("")) {
            Picasso.get()
                    .load(fidiboBookDetails.getPicture_url())
                    .into(bookCover);

            Picasso.get()
                    .load(fidiboBookDetails.getPicture_url())
                    .into(bookThumb);
        }

        genre.setText(fidiboBookDetails.getCategory());
        bookName.setText(fidiboBookDetails.getTitle());
        //author.setText(fidiboBookDetails.get());
        pages.setText(String.valueOf(fidiboBookDetails.getPages()));
        overallRating.setText(fidiboBookDetails.getPublisher());
        pubDate.setText(fidiboBookDetails.getRelease_date());
        description.setText(fidiboBookDetails.getDescription());

        progressBar.setVisibility(View.INVISIBLE);
        prog_back.setVisibility(View.INVISIBLE);

        Barcode barcode = new Barcode();
        barcode.setIsbn(bookIsbn);
        Date currentTime = Calendar.getInstance().getTime();
        barcode.setDate(currentTime);
        barcode.setBookName(fidiboBookDetails.getTitle());
        BarcodeList barcodeList = SharedPref.getInstance(this).getBarcodeList();
        barcodeList.addBarcode(barcode);
        SharedPref.getInstance(this).putBarcodeList(barcodeList);
    }

    @Override
    public void setFidiboBookId(FidiboBookId fidiboBookId)
    {
        if (fidiboBookId != null) {
            //Toast.makeText(getApplicationContext(), "Fidibo ID:" + fidiboBookId.getBookId(), Toast.LENGTH_LONG).show();
            this.fidiboBookId = fidiboBookId;
        }
        else {
           // Toast.makeText(getApplicationContext(), "Fidibo ID: Not Found!" , Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void getGoogle() {
        setContentView(R.layout.activity_book_details);
        findView();
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

        goodreads.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(BookDetailsActivity.this, GoodreadsReviewsActivity.class);
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

        mPresenter.requestDataFromServer();
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

        Log.d("IMGAE_LINK",bookDetails.getItems().get(0).getVolumeInfo().getImageLinks().getThumbnail());

        Barcode barcode = new Barcode();
        barcode.setIsbn(bookIsbn);
        Date currentTime = Calendar.getInstance().getTime();
        barcode.setDate(currentTime);
        barcode.setBookName(bookDetails.getItems().get(0).getVolumeInfo().getTitle());
        BarcodeList barcodeList = SharedPref.getInstance(this).getBarcodeList();
        barcodeList.addBarcode(barcode);
        SharedPref.getInstance(this).putBarcodeList(barcodeList);
    }

    @Override
    public void setPresenter(BookDetailsContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
