package com.vb4.savour.ui.profile;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.vb4.savour.R;
import com.vb4.savour.data.model.Recipe;
import com.vb4.savour.ui.discover.RecipeCardClickListener;

public class RecipeItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private final ImageView mMediaImageView;

    private Recipe mRecipe;

    @Nullable
    private RecipeCardClickListener mOnClickListener;

    public RecipeItemViewHolder(@NonNull View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        mMediaImageView = itemView.findViewById(R.id.image_view_recipe_item_media);
    }

    public void bind(Recipe recipe, RecipeCardClickListener onClickListener) {
        this.mOnClickListener = onClickListener;
        mRecipe = recipe;

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
