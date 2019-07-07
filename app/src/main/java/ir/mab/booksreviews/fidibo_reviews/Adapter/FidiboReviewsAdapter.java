package ir.mab.booksreviews.fidibo_reviews.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ir.mab.booksreviews.R;
import ir.mab.booksreviews.fidibo_reviews.model.FidiboReview;
import ir.mab.booksreviews.goodreads_reviews.model.GoodreadsReview;

public class FidiboReviewsAdapter extends RecyclerView.Adapter<FidiboReviewsAdapter.ViewHolder> {
    private List<FidiboReview> fidiboReviews;

    public FidiboReviewsAdapter(List<FidiboReview> fidiboReviews) {
        this.fidiboReviews = fidiboReviews;
    }

    @NonNull
    @Override
    public FidiboReviewsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.fidibo_reviews_items_row,viewGroup,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final FidiboReviewsAdapter.ViewHolder viewHolder, int i) {
        viewHolder.userName.setText(fidiboReviews.get(i).getSender());
        viewHolder.date.setText(fidiboReviews.get(i).getDate());
        viewHolder.text.setText(fidiboReviews.get(i).getContent());

        viewHolder.expand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewHolder.text.setMaxLines(Integer.MAX_VALUE);
                viewHolder.expand.setVisibility(View.GONE);
                viewHolder.collapse.setVisibility(View.VISIBLE);
            }
        });

        viewHolder.collapse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewHolder.text.setMaxLines(4);
                viewHolder.expand.setVisibility(View.VISIBLE);
                viewHolder.collapse.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public int getItemCount() {
        return fidiboReviews.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView userName,title,date,text;
        ImageView expand , collapse;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.user_name);
            date = itemView.findViewById(R.id.date);
            text = itemView.findViewById(R.id.text);
            expand = itemView.findViewById(R.id.expand_img);
            collapse = itemView.findViewById(R.id.collapse_img);
        }
    }
}
