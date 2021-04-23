package com.vb4.savour.ui.grocerylist;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Exists to have a shared parent class of {@link IngredientItemViewHolder} and {@link RecipeHeaderViewHolder}
 */
public class GenericVH extends RecyclerView.ViewHolder {
    public GenericVH(@NonNull View itemView) {
        super(itemView);
    }
}
