package com.vb4.savour.ui.addrecipe;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vb4.savour.R;
import com.vb4.savour.data.model.Ingredient;
import com.vb4.savour.ui.grocerylist.IngredientItemViewHolder;

import java.util.List;

public class IngredientListAdapter extends RecyclerView.Adapter<IngredientItemViewHolder> {
    private List<Ingredient> mData;

    public IngredientListAdapter(List<Ingredient> ingredients) {
        mData = ingredients;
    }

    public void update(List<Ingredient> ingredients) {
        this.mData = ingredients;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public IngredientItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_ingredient, parent, false);
        IngredientItemViewHolder vh = new IngredientItemViewHolder(view);
        vh.hideObtained();
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientItemViewHolder holder, int position) {
        holder.bind(mData.get(position), null);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}
