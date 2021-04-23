package com.vb4.savour.ui.searchingredient;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.vb4.savour.R;
import com.vb4.savour.data.model.Ingredient;
import com.vb4.savour.ui.grocerylist.IngredientItemViewHolder;

public class ListIngredientAdapter extends RecyclerView.Adapter<IngredientItemViewHolder> {
    @Nullable private Ingredient[] mData;
    private IngredientClickListener mOnClickListener;

    public ListIngredientAdapter(@Nullable Ingredient[] initialData, IngredientClickListener ingredientClickListener) {
        this.mData = initialData;
        mOnClickListener = ingredientClickListener;
    }

    public void update(Ingredient[] ingredients) {
        this.mData = ingredients;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public IngredientItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ingredient, parent, false);
        IngredientItemViewHolder vh = new IngredientItemViewHolder(view);
        vh.hideObtained();

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientItemViewHolder holder, int position) {
        holder.bind(mData[position], mOnClickListener);
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.length;
    }
}
