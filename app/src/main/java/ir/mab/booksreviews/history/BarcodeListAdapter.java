package ir.mab.booksreviews.history;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ir.mab.booksreviews.R;
import ir.mab.booksreviews.book_detail.BookDetailsActivity;
import ir.mab.booksreviews.book_detail.model.BookDetails;

public class BarcodeListAdapter extends RecyclerView.Adapter<BarcodeListAdapter.ViewHolder> {

    List<Barcode> barcodeList;
    Context context;

    public BarcodeListAdapter(List<Barcode> barcodeList, Context context) {
        this.barcodeList = barcodeList;
        this.context = context;
    }

    @NonNull
    @Override
    public BarcodeListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.history_items_row,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final BarcodeListAdapter.ViewHolder viewHolder, final int i) {
        viewHolder.isbn.setText(barcodeList.get(i).getIsbn());
        viewHolder.date.setText(barcodeList.get(i).getDate().toString());
        viewHolder.bookName.setText(barcodeList.get(i).getBookName());

        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, BookDetailsActivity.class);
                intent.putExtra("isbn",barcodeList.get(viewHolder.getAdapterPosition()).getIsbn());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return barcodeList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView isbn;
        TextView date;
        TextView bookName;
        CardView cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.card);
            isbn = itemView.findViewById(R.id.ISBN);
            date = itemView.findViewById(R.id.Date);
            bookName = itemView.findViewById(R.id.book_name);
        }
    }
}
