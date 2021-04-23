package com.vb4.savour.domain.cases.auth;

import androidx.lifecycle.MutableLiveData;

import com.vb4.savour.data.client.AsyncData;
import com.vb4.savour.data.client.AsyncDataStatus;
import com.vb4.savour.data.model.CreateUserRequest;
import com.vb4.savour.data.model.CreateUserResponse;
import com.vb4.savour.data.repository.SavourRepository;
import com.vb4.savour.domain.cases.SavourUseCase;

/**
 * Performs an operation to create a user
 */
public class CreateUserUseCase extends SavourUseCase<CreateUserRequest, CreateUserResponse> {
    public CreateUserUseCase(MutableLiveData<AsyncData<CreateUserResponse>> liveData) {
        super(liveData);
    }

    @Override
    public void run(CreateUserRequest createUserRequest) {
        super.run(createUserRequest);

        MutableLiveData<AsyncData<Void>> out = new MutableLiveData<>();
        out.observeForever(r -> {
            if (r.status == AsyncDataStatus.ERROR) {
                liveData.postValue(AsyncData.error(r.error));
            } else if (r.status == AsyncDataStatus.LOADING) {
                liveData.postValue(AsyncData.loading());
            } else {
                CreateUserResponse response = new CreateUserResponse();
                response.access_token = "kahsdgasdhdas.asodhasliudguashdda.aosudhiasgdausdgh";

                liveData.postValue(AsyncData.success(response));
            }
        });

        SavourRepository.getInstance().createUser(createUserRequest, out);
    }
}
