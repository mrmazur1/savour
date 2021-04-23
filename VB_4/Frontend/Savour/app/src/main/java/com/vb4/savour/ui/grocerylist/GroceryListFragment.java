package com.vb4.savour.ui.grocerylist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vb4.savour.R;
import com.vb4.savour.data.client.AsyncData;
import com.vb4.savour.data.model.GroceryListModelPiece;

/** Grocery List */
public class GroceryListFragment extends Fragment {
    private GroceryListViewModel groceryListViewModel;
    private RecyclerView mGroceryListRecyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        groceryListViewModel =
                new ViewModelProvider(this).get(GroceryListViewModel.class);
        View root = inflater.inflate(R.layout.fragment_grocery_list, container, false);

        mGroceryListRecyclerView = root.findViewById(R.id.recycler_view_grocery_list);
        mGroceryListRecyclerView.addItemDecoration(new DividerItemDecoration(mGroceryListRecyclerView.getContext(), DividerItemDecoration.VERTICAL));

        fetch();

        return root;
    }

    public void fetch() {
        LiveData<AsyncData<GroceryListModelPiece[]>> groceryList = groceryListViewModel.getGroceryList();
        GroceryListAdapter adapter = new GroceryListAdapter(groceryList, getViewLifecycleOwner());
        mGroceryListRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mGroceryListRecyclerView.setAdapter(adapter);
    }
}