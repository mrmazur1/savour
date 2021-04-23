package com.vb4.savour.ui.search;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vb4.savour.R;
import com.vb4.savour.data.client.AsyncDataStatus;
import com.vb4.savour.data.model.RecipeCategory;
import com.vb4.savour.data.model.SearchRequest;
import com.vb4.savour.ui.main.MainActivity;
import com.vb4.savour.ui.main.SoftKeyboardListener;
import com.vb4.savour.ui.viewrecipe.ViewRecipeActivity;

/** Defines and manages layout for search page */
public class SearchFragment extends Fragment implements SoftKeyboardListener {
    /** ViewModel to be used in this fragment */
    private SearchViewModel searchViewModel;

    /** RecyclerView to hold the RecipeCardView's */
    private RecyclerView searchRecyclerView;

    /** Adapter to update data and create view holder for data in this fragment */
    private SearchAdapter searchAdapter;

    private View buttonView;

    /** Reference to this fragment's host activity */
    private MainActivity mainActivity;

    /**
     * Set the layout for SearchFragment when Fragment begins
     * @param inflater instantiate layout XML file into its corresponding view objects
     * @param container defines layout in which views will be listed in
     * @param savedInstanceState save state of fragment
     * @return view for search fragment
     */
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        searchViewModel = new ViewModelProvider(this).get(SearchViewModel.class);
        View root = inflater.inflate(R.layout.fragment_search, container, false);

        searchRecyclerView = root.findViewById(R.id.recycler_view_search);
        searchAdapter = new SearchAdapter(r -> {
            Intent intent = new Intent(getActivity(), ViewRecipeActivity.class);
            intent.putExtra(ViewRecipeActivity.RECIPE_ID_EXTRA_KEY, r.id);
            startActivity(intent);
        });

        searchRecyclerView.setAdapter(searchAdapter);
        searchRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        buttonView = root.findViewById(R.id.searchRelativeLayout);
        buttonView.setVisibility(View.VISIBLE);

        final Button breakfastButton = root.findViewById(R.id.breakfast_button);
        breakfastButton.setOnClickListener(v -> searchForCategory(RecipeCategory.BREAKFAST));

        final Button lunchButton = root.findViewById(R.id.lunch_button);
        lunchButton.setOnClickListener(v -> searchForCategory(RecipeCategory.LUNCH));

        final Button dinnerButton = root.findViewById(R.id.dinner_button);
        dinnerButton.setOnClickListener(v -> searchForCategory(RecipeCategory.DINNER));

        final Button drinkButton = root.findViewById(R.id.drink_button);
        drinkButton.setOnClickListener(v -> searchForCategory(RecipeCategory.DRINKS));

        final Button snackButton = root.findViewById(R.id.snacks_button);
        snackButton.setOnClickListener(v -> searchForCategory(RecipeCategory.SNACKS));

        final Button dessertButton = root.findViewById(R.id.dessert_button);
        dessertButton.setOnClickListener(v -> searchForCategory(RecipeCategory.DESSERT));

        return root;
    }

    /**
     * Initial creation of fragment
     * @param savedInstanceState save state of fragment
     */
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    private void searchForCategory(String category) {
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.category = category;
        buttonView.setVisibility(View.GONE);
        fetch(searchRequest);
    }

    /**
     * Create menu's
     * @param menu menu to create
     * @param inflater instantiate menu XML files into Menu objects
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        inflater.inflate(R.menu.recipe_search_menu, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setOnSearchClickListener(v -> {
            buttonView.setVisibility(View.GONE);
            ((MainActivity)getActivity()).setBottomNavigationVisible(false);
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            /**
             * Response to when search is submitted
             * @param query string that was entered into search bar
             * @return true if search submitted; false if not
             */
            @Override
            public boolean onQueryTextSubmit(String query) {
                SearchRequest searchRequest = new SearchRequest();
                searchRequest.query = query;
                fetch(searchRequest);
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
    }

    public void fetch(SearchRequest searchRequest) {
        searchViewModel.getRecipeList(searchRequest).observe(getViewLifecycleOwner(), data -> {
            if (data.status == AsyncDataStatus.SUCCESS) {
                searchAdapter.update(data.payload);
            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof MainActivity) {
            mainActivity = (MainActivity)context;

            // Subscribe to soft keyboard events
            mainActivity.addSoftKeyboardListener(this);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

        // Unsubscribe from soft keyboard events
        if (mainActivity != null) {
            mainActivity.removeSoftKeyboardListener(this);
            mainActivity = null;
        }
    }

    @Override
    public void onSoftKeyboardOpened() {

    }

    @Override
    public void onSoftKeyboardClosed() {
        mainActivity.setBottomNavigationVisible(true);
    }
}