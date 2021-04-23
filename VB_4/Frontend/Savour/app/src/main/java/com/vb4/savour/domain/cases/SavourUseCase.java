package com.vb4.savour.domain.cases;

import androidx.lifecycle.MutableLiveData;

import com.vb4.savour.data.client.AsyncData;
import com.vb4.savour.data.repository.SavourRepository;

/**
 * Base class used to perform action on {@link SavourRepository}.
 * @param <Request> the type of request this action wants
 * @param <Response> the type of response this action returns
 */
public abstract class SavourUseCase<Request, Response> {
    protected MutableLiveData<AsyncData<Response>> liveData;

    public SavourUseCase(MutableLiveData<AsyncData<Response>> liveData) {
        this.liveData = liveData;
    }

    /**
     * Perform the action
     * @param request request for this action
     */
    public void run(Request request) {
        liveData.postValue(AsyncData.loading());
        // Should be overridden
    }
}
