package com.vb4.savour.ui.profile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vb4.savour.R;
import com.vb4.savour.data.client.AsyncDataStatus;
import com.vb4.savour.ui.addrecipe.AddRecipeActivity;
import com.vb4.savour.ui.discover.RecipeCardClickListener;
import com.vb4.savour.ui.viewrecipe.ViewRecipeActivity;

public class ProfileFragment extends Fragment {
    private ProfileViewModel profileViewModel;
    private ProfileRecipeListAdapter recentlyViewedAdapter;
    private ProfileRecipeListAdapter favoritesAdapter;
    private Button addRecipeButton;

    public static final String EXTRA_RECIPE_ID = "AddRecipeRecipeId";
    private static final int REQUEST_ADD_RECIPE = 8;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        profileViewModel =
                new ViewModelProvider(this).get(ProfileViewModel.class);
        View root = inflater.inflate(R.layout.fragment_profile, container, false);

        LinearLayoutManager recentlyViewedListLayoutManager =
                new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager favoriteListLayoutManager =
                new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        HorizontalItemDecoration itemDecoration =
                new HorizontalItemDecoration(getContext(), 32);
        RecipeCardClickListener pushToViewRecipe = r -> {
            // Open recipe
            Intent intent = new Intent(getActivity(), ViewRecipeActivity.class);
            intent.putExtra(ViewRecipeActivity.RECIPE_ID_EXTRA_KEY, r.id);
            startActivity(intent);
        };

        addRecipeButton = root.findViewById(R.id.button_profile_add_recipe);
        final RecyclerView recentlyViewedRecyclerView =
                root.findViewById(R.id.recycler_view_profile_recently_viewed);
        final RecyclerView favoritesRecyclerView =
                root.findViewById(R.id.recycler_view_profile_favorites);

        recentlyViewedAdapter = new ProfileRecipeListAdapter(pushToViewRecipe);
        favoritesAdapter = new ProfileRecipeListAdapter(pushToViewRecipe);

        recentlyViewedRecyclerView.setLayoutManager(recentlyViewedListLayoutManager);
        recentlyViewedRecyclerView.addItemDecoration(itemDecoration);
        recentlyViewedRecyclerView.setAdapter(recentlyViewedAdapter);

        favoritesRecyclerView.setLayoutManager(favoriteListLayoutManager);
        favoritesRecyclerView.addItemDecoration(itemDecoration);
        favoritesRecyclerView.setAdapter(favoritesAdapter);

        addRecipeButton.setOnClickListener(l -> {
            // Open recipe
            Intent intent = new Intent(getActivity(), AddRecipeActivity.class);
            startActivityForResult(intent, REQUEST_ADD_RECIPE);
        });

        final Button logoutButton = root.findViewById(R.id.button_profile_logout);
        logoutButton.setOnClickListener(l -> {
            profileViewModel.logout(getContext()).observe(getViewLifecycleOwner(), success -> {
                if (success.payload) {
                    // Move to login screen
                    NavHostFragment
                            .findNavController(this)
                            .navigate(R.id.action_profile_logout);
                }
            });
        });

        getProfile();

        return root;
    }

    public void getProfile() {
        profileViewModel.getProfile().observe(getViewLifecycleOwner(), profileResponse -> {
            if (profileResponse.status == AsyncDataStatus.SUCCESS) {
                // Update UI
                if (profileResponse.payload.recentlyViewed != null) {
                    recentlyViewedAdapter.update(profileResponse.payload.recentlyViewed);
                }

                if (profileResponse.payload.favoriteRecipes != null) {
                    favoritesAdapter.update(profileResponse.payload.favoriteRecipes);
                }

//                addRecipeButton.setVisibility(View.VISIBLE);
                addRecipeButton.setVisibility(profileResponse.payload.canAddRecipe ? View.VISIBLE : View.GONE);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_ADD_RECIPE && resultCode == Activity.RESULT_OK) {
            int recipeId = data.getIntExtra(EXTRA_RECIPE_ID, -1);
            if (recipeId != -1) {
                // Open View Recipe Activity
                Intent intent = new Intent(getActivity(), ViewRecipeActivity.class);
                intent.putExtra(ViewRecipeActivity.RECIPE_ID_EXTRA_KEY, recipeId);
                startActivity(intent);
            }
        }
    }
}