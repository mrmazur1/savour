package com.vb4.savour.ui.grocerylist;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.vb4.savour.R;
import com.vb4.savour.data.model.Recipe;

/**
 * {@link androidx.recyclerview.widget.RecyclerView.ViewHolder} to display a {@link Recipe} as a header
 */
public class RecipeHeaderViewHolder extends GenericVH {
    /** The media view */
    private final ImageView mMediaView;

    /** The view to display the recipe's name */
    private final TextView mRecipeNameTextView;

    public RecipeHeaderViewHolder(@NonNull View itemView) {
        super(itemView);
        mMediaView = itemView.findViewById(R.id.image_view_header_recipe_media);
        mRecipeNameTextView = itemView.findViewById(R.id.text_view_header_recipe_name);
    }

    /**
     * Bind a {@link Recipe} to this ViewHolder
     * @param recipe the recipe to display
     */
    public void bind(Recipe recipe) {
        mRecipeNameTextView.setText(recipe.name);

        // Load media
        Glide
            .with(itemView)
            .load(recipe.mediaUrl)
            .into(mMediaView);
    }
}
