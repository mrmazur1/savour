package com.vb4.savour.domain.cases.profile;

import androidx.lifecycle.MutableLiveData;

import com.vb4.savour.data.client.AsyncData;
import com.vb4.savour.data.model.GetProfileResponse;
import com.vb4.savour.data.repository.SavourRepository;
import com.vb4.savour.domain.cases.SavourUseCase;

/**
 * Performs fetch of a user's profile
 */
public class GetProfileUseCase extends SavourUseCase<Void, GetProfileResponse> {
    public GetProfileUseCase(MutableLiveData<AsyncData<GetProfileResponse>> liveData) {
        super(liveData);
    }

    @Override
    public void run(Void aVoid) {
        super.run(aVoid);
        SavourRepository.getInstance().getProfile(liveData);
    }
}
