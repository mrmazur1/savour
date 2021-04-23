package com.vb4.savour.domain.cases.search;

import androidx.lifecycle.MutableLiveData;

import com.vb4.savour.data.client.AsyncData;
import com.vb4.savour.data.model.Recipe;
import com.vb4.savour.data.model.SearchRequest;
import com.vb4.savour.data.repository.SavourRepository;
import com.vb4.savour.domain.cases.SavourUseCase;

/**
 * Performs a search for a recipe by name
 */
public class SearchUseCase extends SavourUseCase<SearchRequest, Recipe[]> {
    /**
     * Performs a search for a recipe by name
     * @param liveData callback to notify
     */
    public SearchUseCase(MutableLiveData<AsyncData<Recipe[]>> liveData) {
        super(liveData);
    }

    @Override
    public void run(SearchRequest searchRequest) {
        super.run(searchRequest);
        SavourRepository.getInstance().getSearchResults(searchRequest, liveData);
    }
}

