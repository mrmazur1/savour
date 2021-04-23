package com.vb4.savour.domain.cases.ingredient;

import androidx.lifecycle.MutableLiveData;

import com.vb4.savour.data.client.AsyncData;
import com.vb4.savour.data.model.CreateIngredientRequest;
import com.vb4.savour.data.model.Ingredient;
import com.vb4.savour.data.repository.SavourRepository;
import com.vb4.savour.domain.cases.SavourUseCase;

/**
 * Performs an operation of creating an Ingredient
 */
public class CreateIngredientUseCase extends SavourUseCase<CreateIngredientRequest, Ingredient> {
    public CreateIngredientUseCase(MutableLiveData<AsyncData<Ingredient>> liveData) {
        super(liveData);
    }

    @Override
    public void run(CreateIngredientRequest createIngredientRequest) {
        super.run(createIngredientRequest);
        SavourRepository.getInstance().createIngredient(createIngredientRequest, liveData);
    }
}
