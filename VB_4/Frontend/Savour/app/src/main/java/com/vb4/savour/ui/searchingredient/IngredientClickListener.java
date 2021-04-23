package com.vb4.savour.ui.searchingredient;

import com.vb4.savour.data.model.Ingredient;

/**
 * A listener for when a {@link Ingredient} is clicked.
 */
public interface IngredientClickListener {
    /**
     * Callback fired when the {@link Ingredient} is clicked.
     * @param ingredient the ingredient that was clicked
     */
    void onClick(Ingredient ingredient);
}
