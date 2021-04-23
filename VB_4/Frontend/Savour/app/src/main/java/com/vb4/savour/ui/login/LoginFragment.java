package com.vb4.savour.ui.login;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.vb4.savour.R;
import com.vb4.savour.data.client.AsyncData;
import com.vb4.savour.data.client.AsyncDataStatus;
import com.vb4.savour.data.client.SavourClient;
import com.vb4.savour.data.model.User;

/**
 * Login Fragment.
 */
public class LoginFragment extends Fragment {
    private LoginViewModel mViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        View root = inflater.inflate(R.layout.fragment_login, container, false);

        final EditText usernameField = root.findViewById(R.id.edit_text_login_username);
        final EditText passwordField = root.findViewById(R.id.edit_text_login_password);

        final Button loginButton = root.findViewById(R.id.button_login_submit);
        loginButton.setOnClickListener(l -> {
            mViewModel
                .login(usernameField.getText().toString(), passwordField.getText().toString())
                .observe(getViewLifecycleOwner(), this::onLoginResponseChanged);
        });

        return root;
    }

    private void onLoginResponseChanged(AsyncData<User> response) {
        if (response.status == AsyncDataStatus.SUCCESS) {
            SavourClient.getInstance().setUserId(response.payload.userId);

            // Hide keyboard
            InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);

            // Move away from login screen
            NavHostFragment
                    .findNavController(this)
                    .navigate(R.id.action_login_to_discover);
        }
    }
}
