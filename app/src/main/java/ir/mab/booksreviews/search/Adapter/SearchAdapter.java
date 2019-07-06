package ir.mab.booksreviews.search.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import ir.mab.booksreviews.R;
import ir.mab.booksreviews.search.model.Book;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {
    List<Book> books;

    public SearchAdapter(List<Book> books) {
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
    public void onBindViewHolder(@NonNull SearchAdapter.ViewHolder viewHolder, int i) {
        Picasso.get().load(books.get(i).getPicture_url()).into(viewHolder.image);
        viewHolder.title.setText(books.get(i).getTitle());
        viewHolder.cat.setText(books.get(i).getCategory());
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView image;
        private TextView title;
        private TextView cat;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.bookImg);
            title = itemView.findViewById(R.id.bookName);
            cat = itemView.findViewById(R.id.bookCat);
        }
    }
}
