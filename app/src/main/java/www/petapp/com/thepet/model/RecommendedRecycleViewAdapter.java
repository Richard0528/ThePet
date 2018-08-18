package www.petapp.com.thepet.model;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import www.petapp.com.thepet.R;

public class RecommendedRecycleViewAdapter extends RecyclerView.Adapter<RecommendedRecycleViewAdapter.ViewHolder> {

    private List<PetCardItem> recommendedCards;

    public class ViewHolder extends RecyclerView.ViewHolder {

        private CardView mCardView;
        private ImageView mImageView;
        private TextView mName;
        private TextView mDescription;

        public ViewHolder(View itemView) {
            super(itemView);
            mCardView = itemView.findViewById(R.id.recommended_recycleview_card);
            mImageView = itemView.findViewById(R.id.recommendedItemImage);
            mName = itemView.findViewById(R.id.recommendedItemName);
            mDescription = itemView.findViewById(R.id.recommendedItemDescription);
        }
    }

    public RecommendedRecycleViewAdapter(List<PetCardItem> recommendedCards) {
        this.recommendedCards = recommendedCards;
    }

    @Override
    public RecommendedRecycleViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recommended_recyclerview_item, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mImageView.setImageResource(recommendedCards.get(position).getImageURL());
        holder.mName.setText(recommendedCards.get(position).getName());
        holder.mDescription.setText(recommendedCards.get(position).getDescription());
    }


    @Override
    public int getItemCount() {
        return recommendedCards.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
