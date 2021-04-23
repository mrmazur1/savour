package com.vb4.savour.ui.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.vb4.savour.data.client.AsyncData;
import com.vb4.savour.data.model.LoginRequest;
import com.vb4.savour.data.model.User;
import com.vb4.savour.domain.cases.auth.LoginUseCase;

public class LoginViewModel extends ViewModel {
    private MutableLiveData<AsyncData<User>> loginResponse;
    private LoginUseCase loginUseCase;

    public LoginViewModel() {
        loginResponse = new MutableLiveData<>();
        loginUseCase = new LoginUseCase(loginResponse);
    }

    public LiveData<AsyncData<User>> login(String username, String password) {
        LoginRequest request = new LoginRequest();
        request.username = username;
        request.password = password;
        loginUseCase.run(request);
        return loginResponse;
    }

//    public LiveData<AsyncData<CreateUserResponse>> createUser(String username, String password) {
//        CreateUserRequest request = new CreateUserRequest();
//        request.username = username;
//        request.password = password;
//        request.canAddRecipe = "true";
//        loginUseCase.run(request);
//        return loginResponse;
//    }
}
