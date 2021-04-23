package com.vb4.savour.domain.cases.recipe;

import androidx.lifecycle.MutableLiveData;

import com.vb4.savour.data.client.AsyncData;
import com.vb4.savour.data.model.AddRecipeRequest;
import com.vb4.savour.data.model.Recipe;
import com.vb4.savour.data.repository.SavourRepository;
import com.vb4.savour.domain.cases.SavourUseCase;

/**
 * Performs operation of adding a recipe
 */
public class AddRecipeUseCase extends SavourUseCase<AddRecipeRequest, Recipe> {
    public AddRecipeUseCase(MutableLiveData<AsyncData<Recipe>> liveData) {
        super(liveData);
    }

    @Override
    public void run(AddRecipeRequest addRecipeRequest) {
        super.run(addRecipeRequest);
        SavourRepository.getInstance().addRecipe(addRecipeRequest, liveData);
    }
}
