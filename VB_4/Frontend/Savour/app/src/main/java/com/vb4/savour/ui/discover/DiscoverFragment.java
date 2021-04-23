package com.vb4.savour.ui.discover;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vb4.savour.R;
import com.vb4.savour.data.client.AsyncDataStatus;
import com.vb4.savour.ui.viewrecipe.ViewRecipeActivity;

/** Discover */
public class DiscoverFragment extends Fragment {
    /** The ViewModel for this Fragment */
    private DiscoverViewModel discoverViewModel;

    /** The list of recipes */
    private RecyclerView discoverRecyclerView;

    /** The adapter for this list of recipes */
    private DiscoverAdapter discoverAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        discoverViewModel = new ViewModelProvider(this).get(DiscoverViewModel.class);
        View root = inflater.inflate(R.layout.fragment_discover, container, false);

        discoverRecyclerView = root.findViewById(R.id.recycler_view_discover);
        discoverAdapter = new DiscoverAdapter(r -> {
            Intent intent = new Intent(getActivity(), ViewRecipeActivity.class);
            intent.putExtra(ViewRecipeActivity.RECIPE_ID_EXTRA_KEY, r.id);
            startActivity(intent);
        });
        discoverRecyclerView.setAdapter(discoverAdapter);
        discoverRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        fetch();

        return root;
    }

    public void fetch() {
        discoverViewModel.getRecipeList().observe(getViewLifecycleOwner(), data -> {
            if (data.status == AsyncDataStatus.SUCCESS) {
                discoverAdapter.update(data.payload);
            }
        });
    }
}