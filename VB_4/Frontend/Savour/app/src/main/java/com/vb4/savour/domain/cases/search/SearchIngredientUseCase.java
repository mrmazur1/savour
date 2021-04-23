package com.vb4.savour.domain.cases.search;

import androidx.lifecycle.MutableLiveData;

import com.vb4.savour.data.client.AsyncData;
import com.vb4.savour.data.model.Ingredient;
import com.vb4.savour.data.repository.SavourRepository;
import com.vb4.savour.domain.cases.SavourUseCase;

/**
 * Performs search for ingredients based on their name
 */
public class SearchIngredientUseCase extends SavourUseCase<String, Ingredient[]> {
    public SearchIngredientUseCase(MutableLiveData<AsyncData<Ingredient[]>> liveData) {
        super(liveData);
    }

    @Override
    public void run(String query) {
        super.run(query);
        SavourRepository.getInstance().getSearchIngredients(query, liveData);
    }
}
