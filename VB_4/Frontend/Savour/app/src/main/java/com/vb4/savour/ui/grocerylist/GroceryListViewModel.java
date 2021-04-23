package com.vb4.savour.ui.grocerylist;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.vb4.savour.data.client.AsyncData;
import com.vb4.savour.data.model.GroceryListModelPiece;
import com.vb4.savour.domain.cases.grocerylist.GetGroceryListUseCase;

public class GroceryListViewModel extends ViewModel {
    /** The Grocery List recipes */
    private MutableLiveData<AsyncData<GroceryListModelPiece[]>> mRecipes;

    /** {@link com.vb4.savour.domain.cases.SavourUseCase} to get Grocery List */
    private GetGroceryListUseCase mGetGroceryListUseCase;

    public GroceryListViewModel() {
        mRecipes = new MutableLiveData<>();
        mGetGroceryListUseCase = new GetGroceryListUseCase(mRecipes);
        mGetGroceryListUseCase.run(null);
    }

    /**
     * Get async data of the grocery list
     * @return async data of the grocery list
     */
    public LiveData<AsyncData<GroceryListModelPiece[]>> getGroceryList() {
        return mRecipes;
    }
}