package com.vb4.savour.domain.cases.auth;

import androidx.lifecycle.MutableLiveData;

import com.vb4.savour.data.client.AsyncData;
import com.vb4.savour.data.model.LoginRequest;
import com.vb4.savour.data.model.User;
import com.vb4.savour.data.repository.SavourRepository;
import com.vb4.savour.domain.cases.SavourUseCase;

/**
 * Performs an operation to attempt a login to Savour remote
 */
public class LoginUseCase extends SavourUseCase<LoginRequest, User> {
    public LoginUseCase(MutableLiveData<AsyncData<User>> liveData) {
        super(liveData);
    }

    @Override
    public void run(LoginRequest loginRequest) {
        super.run(loginRequest);
        SavourRepository.getInstance().login(loginRequest, liveData);
    }
}
