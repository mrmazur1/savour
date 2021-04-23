package com.vb4.savour.domain.cases.profile;

import androidx.lifecycle.MutableLiveData;

import com.vb4.savour.data.client.AsyncData;
import com.vb4.savour.data.repository.SavourRepository;
import com.vb4.savour.domain.cases.SavourUseCase;

/**
 * Performs operation of adding a recipe to a user's favorites list
 */
public class AddRecipeToFavoritesUseCase extends SavourUseCase<Integer, Void> {
    public AddRecipeToFavoritesUseCase(MutableLiveData<AsyncData<Void>> liveData) {
        super(liveData);
    }

    @Override
    public void run(Integer recipeId) {
        super.run(recipeId);
        SavourRepository.getInstance().addRecipeToFavorites(recipeId, liveData);
    }
}
