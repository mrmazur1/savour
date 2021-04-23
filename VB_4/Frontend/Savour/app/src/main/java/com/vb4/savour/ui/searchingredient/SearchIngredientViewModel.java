package com.vb4.savour.ui.searchingredient;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.vb4.savour.data.client.AsyncData;
import com.vb4.savour.data.model.Ingredient;
import com.vb4.savour.domain.cases.search.SearchIngredientUseCase;

public class SearchIngredientViewModel extends ViewModel {
    private MutableLiveData<AsyncData<Ingredient[]>> mIngredients;
    private SearchIngredientUseCase searchIngredientUseCase;

    private MediatorLiveData<Boolean> showAddIngredient;

    public SearchIngredientViewModel() {
        mIngredients = new MutableLiveData<>();
        searchIngredientUseCase = new SearchIngredientUseCase(mIngredients);

        showAddIngredient = new MediatorLiveData<>();
        showAddIngredient.addSource(mIngredients, ingredients -> {
            showAddIngredient.setValue(true);
        });
//        showAddIngredient.addSource(mIngredients, ingredients -> {
//            if (ingredients.payload == null) {
//                showAddIngredient.setValue(false);
//            } else {
//                showAddIngredient.setValue(ingredients.payload.length == 0);
//            }
//        });
    }

    public LiveData<Boolean> getShowAddIngredient() {
        return showAddIngredient;
    }

    public LiveData<AsyncData<Ingredient[]>> searchIngredients(String query) {
        searchIngredientUseCase.run(query);
        return mIngredients;
    }
}
