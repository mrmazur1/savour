package com.vb4.savour.ui.main;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.vb4.savour.R;
import com.vb4.savour.data.client.SavourClient;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView navView;
    private ConstraintLayout rootLayout;
    private int normalHeight = 0;

    private final List<SoftKeyboardListener> softKeyboardListeners;

    private static final String LOG_TAG = MainActivity.class.getName();

    public MainActivity() {
        softKeyboardListeners = new ArrayList<>();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_discover, R.id.navigation_search, R.id.navigation_weekly_plan,
                R.id.navigation_grocery_list, R.id.navigation_profile)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
        SavourClient.initialize(getApplicationContext());

        // Hide bottom navigation view when login fragment
        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            if (destination.getId() == R.id.navigation_login) {
                navView.setVisibility(View.GONE);
            } else {
                navView.setVisibility(View.VISIBLE);
            }
        });

        rootLayout = findViewById(R.id.container);
        rootLayout.getViewTreeObserver().addOnGlobalLayoutListener(this::onGlobalLayoutListener);
    }

    public void addSoftKeyboardListener(SoftKeyboardListener listener) {
        softKeyboardListeners.add(listener);
    }

    public void removeSoftKeyboardListener(SoftKeyboardListener listener) {
        softKeyboardListeners.remove(listener);
    }

    /**
     * Change visibility of bottom navigation
     * @param visible the visibility of the bottom navigation
     */
    public void setBottomNavigationVisible(boolean visible) {
        navView.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    private void onGlobalLayoutListener() {
        int newHeight = rootLayout.getHeight();

        if (normalHeight > newHeight + 100) {
            Log.d(LOG_TAG, "Keyboard is open");
            normalHeight = newHeight;
            softKeyboardListeners.forEach(SoftKeyboardListener::onSoftKeyboardOpened);
        } else if (newHeight > normalHeight + 100) {
            Log.d(LOG_TAG, "Keyboard is closed");
            normalHeight = newHeight;
            softKeyboardListeners.forEach(SoftKeyboardListener::onSoftKeyboardClosed);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ConstraintLayout rootLayout = findViewById(R.id.container);
        rootLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this::onGlobalLayoutListener);
        softKeyboardListeners.clear();
    }
}