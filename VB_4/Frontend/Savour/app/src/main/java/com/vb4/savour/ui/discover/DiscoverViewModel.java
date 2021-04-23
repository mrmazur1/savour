package com.vb4.savour.ui.discover;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.vb4.savour.data.client.AsyncData;
import com.vb4.savour.data.model.Recipe;
import com.vb4.savour.domain.cases.discover.GetDiscoverUseCase;

public class DiscoverViewModel extends ViewModel {
    private final MutableLiveData<AsyncData<Recipe[]>> mRecipeList;
    private final GetDiscoverUseCase mGetDiscoverUseCase;

    public DiscoverViewModel() {
        mRecipeList = new MutableLiveData<>();
        mGetDiscoverUseCase = new GetDiscoverUseCase(mRecipeList);
        mGetDiscoverUseCase.run(null);
    }

    /**
     * Get async list of recipes
     * @return async list of recipes
     */
    public LiveData<AsyncData<Recipe[]>> getRecipeList() {
        return mRecipeList;
    }
}