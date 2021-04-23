package com.vb4.savour.domain.cases.profile;

import androidx.lifecycle.MutableLiveData;

import com.vb4.savour.data.client.AsyncData;
import com.vb4.savour.data.repository.SavourRepository;
import com.vb4.savour.domain.cases.SavourUseCase;

/**
 * Performs operation of marking a recipe as viewed by a user
 */
public class MarkRecipeViewedUseCase extends SavourUseCase<Integer, Void> {
    public MarkRecipeViewedUseCase(MutableLiveData<AsyncData<Void>> liveData) {
        super(liveData);
    }

    @Override
    public void run(Integer recipeId) {
        super.run(recipeId);
        SavourRepository.getInstance().markRecipeViewed(recipeId, liveData);
    }
}
