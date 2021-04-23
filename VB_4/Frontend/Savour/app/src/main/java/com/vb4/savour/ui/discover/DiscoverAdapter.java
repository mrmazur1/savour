package com.vb4.savour.ui.discover;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vb4.savour.R;
import com.vb4.savour.data.model.Recipe;

/**
 * Adapter to supply list on {@link DiscoverFragment} with recipes.
 */
public class DiscoverAdapter extends RecyclerView.Adapter<RecipeCardViewHolder> {
    /** The array this adapter displays */
    private Recipe[] mData;

    /** A listener subscribed to receive event where {@link RecipeCardViewHolder} is clicked */
    private RecipeCardClickListener mListener;

    /**
     * Create a new empty adapter with a listener to click events on items
     * @param listener listener notified when recipe is clicked
     */
    public DiscoverAdapter(RecipeCardClickListener listener) {
        update(new Recipe[]{});
        mListener = listener;
    }

    /**
     * Update the data displayed by this adapter
     * @param newData the new recipe list
     */
    public void update(Recipe[] newData) {
        mData = newData;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecipeCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_card, parent, false);
        return new RecipeCardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeCardViewHolder holder, int position) {
        holder.bind(mData[position], r -> mListener.onClick(r));
    }

    @Override
    public int getItemCount() {
        return mData.length;
    }
}
