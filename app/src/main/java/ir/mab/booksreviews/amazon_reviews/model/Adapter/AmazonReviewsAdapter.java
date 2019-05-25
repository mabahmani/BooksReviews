package ir.mab.booksreviews.amazon_reviews.model.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ir.mab.booksreviews.R;
import ir.mab.booksreviews.amazon_reviews.model.model.AmazonReview;

public class AmazonReviewsAdapter extends RecyclerView.Adapter<AmazonReviewsAdapter.ViewHolder> {
    private List<AmazonReview> amazonReviews;

    public AmazonReviewsAdapter(List<AmazonReview> amazonReviews) {
        this.amazonReviews = amazonReviews;
    }

    @NonNull
    @Override
    public AmazonReviewsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.amazon_reviews_items_row,viewGroup,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AmazonReviewsAdapter.ViewHolder viewHolder, int i) {
        viewHolder.userName.setText(amazonReviews.get(i).getName());
        viewHolder.title.setText(amazonReviews.get(i).getTitle());
        viewHolder.date.setText(amazonReviews.get(i).getDate());
        viewHolder.text.setText(amazonReviews.get(i).getText());

    }

    @Override
    public int getItemCount() {
        return amazonReviews.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView userName,title,date,text;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.user_name);
            title = itemView.findViewById(R.id.title);
            date = itemView.findViewById(R.id.date);
            text = itemView.findViewById(R.id.text);
        }
    }
}
