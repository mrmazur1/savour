package com.vb4.savour.ui.search;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.vb4.savour.data.client.AsyncData;
import com.vb4.savour.data.model.Recipe;
import com.vb4.savour.data.model.SearchRequest;
import com.vb4.savour.domain.cases.search.SearchUseCase;

/** Prepares and manages data for SearchFragment */
public class SearchViewModel extends ViewModel {
    /** Array of recipe object data*/
    private final MutableLiveData<AsyncData<Recipe[]>> mRecipeList;
    /** Case to preform search of data*/
    private final SearchUseCase mGetSearchUseCase;

    /**
     * Construct SearchViewModel & obtain recipe object data using SearchUseCase
     */
    public SearchViewModel() {
        mRecipeList = new MutableLiveData<>();
        mGetSearchUseCase = new SearchUseCase(mRecipeList);
    }

    /**
     * Obtain array of recipes that fit search criteria defined by query
     * @param searchRequest request that contains what we want to search for in recipe database
     * @return array of recipes that relate to query string
     */
    public LiveData<AsyncData<Recipe[]>> getRecipeList(SearchRequest searchRequest) {
        mGetSearchUseCase.run(searchRequest);
        return mRecipeList;
    }
}

