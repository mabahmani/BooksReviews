package ir.mab.booksreviews.search.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import ir.mab.booksreviews.R;
import ir.mab.booksreviews.book_detail.BookDetailsActivity;
import ir.mab.booksreviews.search.model.Book;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {
    List<Book> books;
    Context context;

    public SearchAdapter(List<Book> books, Context context) {
        this.books = books;
        this.context = context;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    @NonNull
    @Override
    public SearchAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.search_items_row,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final SearchAdapter.ViewHolder viewHolder, final int i) {
        Log.d("AminAdapt",books.get(i).getTitle());
        Picasso.get().load(books.get(i).getPicture_url()).into(viewHolder.image);
        viewHolder.title.setText(books.get(i).getTitle());
        viewHolder.cat.setText(books.get(i).getCategory());

        viewHolder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, BookDetailsActivity.class);
                intent.putExtra("isbn",books.get(viewHolder.getAdapterPosition()).getISBN());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView image;
        private TextView title;
        private TextView cat;
        private CardView card;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            card = itemView.findViewById(R.id.card);
            image = itemView.findViewById(R.id.bookImg);
            title = itemView.findViewById(R.id.bookName);
            cat = itemView.findViewById(R.id.bookCat);
        }
    }
}
