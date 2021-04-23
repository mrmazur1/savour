package com.vb4.savour.data.model;

import java.io.Serializable;

public class AddRecipeRequest implements Serializable {
    public RecipeWithoutId recipe;
    public Ingredient[] ingredients;
}
