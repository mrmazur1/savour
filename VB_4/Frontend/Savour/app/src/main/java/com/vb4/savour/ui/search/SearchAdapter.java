package com.vb4.savour.ui.search;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vb4.savour.R;
import com.vb4.savour.data.model.Recipe;
import com.vb4.savour.ui.discover.RecipeCardClickListener;
import com.vb4.savour.ui.discover.RecipeCardViewHolder;


/**Provides access to data items and makes a view for each item in the data set*/
public class SearchAdapter extends RecyclerView.Adapter<RecipeCardViewHolder> {
    /**Array of recipe objects*/
    private Recipe[] mData;

    private RecipeCardClickListener mListener;

    /**
     * Constructs Adapter and updates data upon construction
     *
     */
    public SearchAdapter(RecipeCardClickListener listener) {
        update(new Recipe[]{});
        mListener = listener;
    }

    /**
     * Update array of recipes and notify data set changed
     * @param newData new array of recipes to update old recipe array with
     */
    public void update(Recipe[] newData) {
        mData = newData;
        notifyDataSetChanged();
    }

    /**
     * View to hold recipe data item in
     * @param parent parent view
     * @param viewType Type of view
     * @return view to hold recipe data item
     */
    @NonNull
    @Override
    public RecipeCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_card, parent, false);
        return new RecipeCardViewHolder(view);
    }

    /**
     * Bind holder and data item together
     * @param holder holder for data item
     * @param position position of data item in recipe data array
     */
    @Override
    public void onBindViewHolder(@NonNull RecipeCardViewHolder holder, int position) {
        holder.bind(mData[position], r -> mListener.onClick(r));
    }

    /**
     * Get the total number of data items in recipe array
     * @return number of data items in recipe array
     */
    @Override
    public int getItemCount() {
        return mData.length;
    }




}
