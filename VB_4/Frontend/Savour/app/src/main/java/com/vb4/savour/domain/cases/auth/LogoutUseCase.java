package com.vb4.savour.domain.cases.auth;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;

import androidx.lifecycle.MutableLiveData;

import com.vb4.savour.R;
import com.vb4.savour.data.client.AsyncData;
import com.vb4.savour.data.client.SavourClient;
import com.vb4.savour.domain.cases.SavourUseCase;

/**
 * Performs operation of removing saved data from login
 */
public class LogoutUseCase extends SavourUseCase<Context, Boolean> {
    public LogoutUseCase(MutableLiveData<AsyncData<Boolean>> liveData) {
        super(liveData);
    }

    @Override
    public void run(Context ctx) {
        super.run(ctx);

        // Remove from client
        SavourClient.getInstance().clearUserId();

        // Remove from shared preferences
        Resources res = ctx.getResources();
        String prefKey = res.getString(R.string.preference_file_key);
        String prefToken = res.getString(R.string.preference_file_token_key);
        SharedPreferences sharedPrefs = ctx.getSharedPreferences(
                prefKey,
                Context.MODE_PRIVATE
        );

        sharedPrefs.edit().remove(prefToken).apply();

        liveData.postValue(AsyncData.success(true));
    }
}
