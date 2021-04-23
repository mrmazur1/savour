package com.vb4.savour.domain.cases.discover;

import androidx.lifecycle.MutableLiveData;

import com.vb4.savour.data.client.AsyncData;
import com.vb4.savour.data.model.Recipe;
import com.vb4.savour.data.repository.SavourRepository;
import com.vb4.savour.domain.cases.SavourUseCase;

/**
 * Performs a fetch of trending recipes
 */
public class GetDiscoverUseCase extends SavourUseCase<Void, Recipe[]> {
    /**
     * Performs a fetch of trending recipes
     * @param liveData callback to notify
     */
    public GetDiscoverUseCase(MutableLiveData<AsyncData<Recipe[]>> liveData) {
        super(liveData);
    }

    @Override
    public void run(Void request) {
        super.run(request);
        SavourRepository.getInstance().getDiscover(liveData);
    }
}
