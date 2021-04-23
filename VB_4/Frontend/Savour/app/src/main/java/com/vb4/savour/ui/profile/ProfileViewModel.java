package com.vb4.savour.ui.profile;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.navigation.fragment.NavHostFragment;

import com.vb4.savour.R;
import com.vb4.savour.data.client.AsyncData;
import com.vb4.savour.data.model.GetProfileResponse;
import com.vb4.savour.domain.cases.auth.LogoutUseCase;
import com.vb4.savour.domain.cases.profile.GetProfileUseCase;

public class ProfileViewModel extends ViewModel {
    private MutableLiveData<AsyncData<GetProfileResponse>> mProfileResponse;
    private GetProfileUseCase mGetProfileUseCase;

    private MutableLiveData<AsyncData<Boolean>> mLogoutResponse;
    private LogoutUseCase mLogoutUseCase;

    public ProfileViewModel() {
        mProfileResponse = new MutableLiveData<>();
        mGetProfileUseCase = new GetProfileUseCase(mProfileResponse);
        mGetProfileUseCase.run(null);

        mLogoutResponse = new MutableLiveData<>();
        mLogoutUseCase = new LogoutUseCase(mLogoutResponse);
    }

    public LiveData<AsyncData<GetProfileResponse>> getProfile() {
        return mProfileResponse;
    }

    public LiveData<AsyncData<Boolean>> logout(Context context) {
        mLogoutUseCase.run(context);
        return mLogoutResponse;
    }
}