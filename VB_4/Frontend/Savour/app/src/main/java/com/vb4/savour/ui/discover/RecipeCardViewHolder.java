package com.vb4.savour.ui.discover;

import android.content.res.Resources;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.vb4.savour.R;
import com.vb4.savour.data.model.Recipe;

/**
 * {@link androidx.recyclerview.widget.RecyclerView.ViewHolder} for displaying a {@link Recipe}
 */
public class RecipeCardViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    /** The recipe being displayed */
    private Recipe mRecipe;

    /** The media view */
    private final ImageView mMediaImageView;

    /** The view displaying the name */
    private final TextView mNameTextView;

    /** The view displaying the category */
    private final TextView mCategoryTextView;

    /** The view displaying the cooking time */
    private final TextView mCookingTimeTextView;

    /** The view displaying the cost */
    private final TextView mCostTextView;

    /** Listener fired when this ViewHolder is pressed */
    @Nullable
    private RecipeCardClickListener mOnClickListener;

    public RecipeCardViewHolder(@NonNull View itemView) {
        super(itemView);

        itemView.setOnClickListener(this);

        mMediaImageView = itemView.findViewById(R.id.recipe_card_media);
        mNameTextView = itemView.findViewById(R.id.recipe_card_name);
        mCategoryTextView = itemView.findViewById(R.id.recipe_card_category);
        mCookingTimeTextView = itemView.findViewById(R.id.recipe_card_cooking_time);
        mCostTextView = itemView.findViewById(R.id.recipe_card_cost);
    }

    /**
     * Bind a {@link Recipe} to this {@link androidx.recyclerview.widget.RecyclerView.ViewHolder}
     * @param recipe the recipe to bind
     * @param onClickListener listener fired when this {@link androidx.recyclerview.widget.RecyclerView.ViewHolder} is pressed
     */
    public void bind(Recipe recipe, RecipeCardClickListener onClickListener) {
        Resources res = itemView.getContext().getResources();

        this.mOnClickListener = onClickListener;
        mRecipe = recipe;
        mNameTextView.setText(recipe.name);
        mCategoryTextView.setText(res.getString(R.string.recipe_card_category, recipe.category));
        mCookingTimeTextView.setText(res.getString(R.string.recipe_card_cooking_time, recipe.timeMinutes, recipe.timeSeconds));
        mCostTextView.setText(res.getString(R.string.recipe_card_cost, recipe.cost));

        Glide
            .with(itemView)
            .load(recipe.mediaUrl)
            .into(mMediaImageView);
    }

    @Override
    public void onClick(View view) {
        if (this.mOnClickListener != null) {
            mOnClickListener.onClick(mRecipe);
        }
    }
}
