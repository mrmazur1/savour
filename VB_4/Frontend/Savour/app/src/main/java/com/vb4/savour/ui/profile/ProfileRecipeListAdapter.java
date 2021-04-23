package com.vb4.savour.ui.profile;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vb4.savour.R;
import com.vb4.savour.data.model.Recipe;
import com.vb4.savour.ui.discover.RecipeCardClickListener;

public class ProfileRecipeListAdapter extends RecyclerView.Adapter<RecipeItemViewHolder> {
    private Recipe[] mData;
    private RecipeCardClickListener mListener;

    public ProfileRecipeListAdapter(RecipeCardClickListener listener) {
        update(new Recipe[]{});
        mListener = listener;
    }

    public void update(Recipe[] newData) {
        mData = newData;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecipeItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_item, parent, false);
        return new RecipeItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeItemViewHolder holder, int position) {
        holder.bind(mData[position], r -> mListener.onClick(r));
    }

    @Override
    public int getItemCount() {
        return mData.length;
    }
}
