package com.vb4.savour.ui.searchingredient;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SearchView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vb4.savour.R;
import com.vb4.savour.data.client.AsyncDataStatus;
import com.vb4.savour.data.model.Ingredient;
import com.vb4.savour.ui.addingredient.AddIngredientActivity;
import com.vb4.savour.ui.addrecipe.AddRecipeActivity;

public class SearchIngredientActivity extends AppCompatActivity {
    private SearchIngredientViewModel mViewModel;
    private RecyclerView mSearchResultsRecyclerView;
    private ListIngredientAdapter mSearchResultAdapter;

    public static final String EXTRA_CREATED_INGREDIENT = "SearchIngredientCreateIngredient";
    private static final int REQUEST_CREATE_INGREDIENT = 9;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_ingredient);
        mViewModel = new SearchIngredientViewModel();
        mSearchResultsRecyclerView = findViewById(R.id.recycler_view_search_ingredients_results);
        mSearchResultsRecyclerView.setLayoutManager(new LinearLayoutManager(mSearchResultsRecyclerView.getContext()));
        mSearchResultAdapter = new ListIngredientAdapter(null, this::finishWithIngredient);
        mSearchResultsRecyclerView.setAdapter(mSearchResultAdapter);

        final LinearLayout addIngredientLayout = findViewById(R.id.view_add_ingredient_container);

        mViewModel.getShowAddIngredient().observe(this, addIngredientVisible -> {
            addIngredientLayout.setVisibility(addIngredientVisible ? View.VISIBLE : View.GONE);
            mSearchResultsRecyclerView.setVisibility(addIngredientVisible ? View.GONE : View.VISIBLE);
        });

        final Button createIngredientButton = findViewById(R.id.button_create_ingredient);
        createIngredientButton.setOnClickListener(l -> {
            // Create ingredient
            Intent createIngredientIntent = new Intent(SearchIngredientActivity.this, AddIngredientActivity.class);
            startActivityForResult(createIngredientIntent, REQUEST_CREATE_INGREDIENT);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CREATE_INGREDIENT && resultCode == RESULT_OK) {
            if (data == null) {
                // TODO - show error
            } else {
                Ingredient resultIngredient = (Ingredient) data.getSerializableExtra(EXTRA_CREATED_INGREDIENT);
                finishWithIngredient(resultIngredient);
            }
        }
    }

    private void finishWithIngredient(Ingredient i) {
        Intent intent = new Intent();
        intent.putExtra(AddRecipeActivity.EXTRA_INGREDIENT, i);
        setResult(RESULT_OK, intent);
        finish();
    }

    private void searchIngredient(String query) {
        mViewModel.searchIngredients(query).observe(this, data -> {
            if (data.status == AsyncDataStatus.SUCCESS) {
                mSearchResultAdapter.update(data.payload);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.ingredient_search_menu, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search_ingredient).getActionView();
        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            /**
             * Response to when search is submitted
             * @param query string that was entered into search bar
             * @return true if search submitted; false if not
             */
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchIngredient(query);
                return false;
            }

            /**
             * Response in real time as search is entered into toolbar
             * @param newText string that is present in the toolbar at the given moment
             * @return
             */
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search_ingredient).getActionView();
        searchView.requestFocus();
        return super.onPrepareOptionsMenu(menu);
    }
}
