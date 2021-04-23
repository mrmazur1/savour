package com.vb4.savour.ui.discover;

import com.vb4.savour.data.model.Recipe;

/**
 * A listener for when a {@link RecipeCardViewHolder} is clicked.
 */
public interface RecipeCardClickListener {
    /**
     * Callback fired when the {@link RecipeCardViewHolder} is clicked.
     * @param r the recipe that was clicked
     */
    void onClick(Recipe r);
}
