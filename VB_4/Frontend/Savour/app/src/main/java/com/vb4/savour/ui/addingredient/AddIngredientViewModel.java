package com.vb4.savour.ui.addingredient;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.vb4.savour.data.client.AsyncData;
import com.vb4.savour.data.model.CreateIngredientRequest;
import com.vb4.savour.data.model.Ingredient;
import com.vb4.savour.domain.cases.ingredient.CreateIngredientUseCase;

public class AddIngredientViewModel extends ViewModel {
    private final Ingredient pendingIngredient;
    private final MutableLiveData<AsyncData<Ingredient>> createdIngredient;
    private final CreateIngredientUseCase mCreateIngredientUseCase;

    public AddIngredientViewModel() {
        pendingIngredient = new Ingredient();
        createdIngredient = new MutableLiveData<>();
        mCreateIngredientUseCase = new CreateIngredientUseCase(createdIngredient);
    }

    public void setName(String newName) {
        pendingIngredient.name = newName;
    }

    public void setAmount(String newAmount) {
        pendingIngredient.amount = Double.parseDouble(newAmount);
    }

    public void setUnit(String newUnit) {
        pendingIngredient.unit = newUnit;
    }

    public void setCategory(String newCategory) {
        pendingIngredient.type = newCategory;
    }

    public MutableLiveData<AsyncData<Ingredient>> createIngredient() {
        // TODO - call remote
//        CreateIngredientRequest request = new CreateIngredientRequest();
//        request.ingredient = pendingIngredient;
//        mCreateIngredientUseCase.run(request);
//        return createdIngredient;
        return new MutableLiveData<>(AsyncData.success(pendingIngredient));
    }
}
