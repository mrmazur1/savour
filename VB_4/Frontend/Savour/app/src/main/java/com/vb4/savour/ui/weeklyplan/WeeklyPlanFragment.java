package com.vb4.savour.ui.weeklyplan;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vb4.savour.R;
import com.vb4.savour.data.client.AsyncDataStatus;
import com.vb4.savour.ui.viewrecipe.ViewRecipeActivity;

/** Discover */
public class WeeklyPlanFragment extends Fragment {
    public static boolean edit;
    private boolean firstEdit;
    private TextView editHeader;
    private TextView editDescription;
    /** The ViewModel for this Fragment */
    private WeeklyPlanViewModel weeklyPlanViewModel;

    /** The list of recipes */
    private RecyclerView weeklyPlanRecyclerView;

    /** The adapter for this list of recipes */
    private WeeklyPlanAdapter weeklyPlanAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        edit = false;
        firstEdit = true;
        weeklyPlanViewModel = new ViewModelProvider(this).get(WeeklyPlanViewModel.class);
        View root = inflater.inflate(R.layout.fragment_weekly_plan, container, false);

        weeklyPlanRecyclerView = root.findViewById(R.id.recycler_view_weekly_plan);
        weeklyPlanAdapter = new WeeklyPlanAdapter(r -> {
            Intent intent = new Intent(getActivity(), ViewRecipeActivity.class);
            intent.putExtra(ViewRecipeActivity.RECIPE_ID_EXTRA_KEY, r);
            startActivity(intent);
        });
        weeklyPlanRecyclerView.setAdapter(weeklyPlanAdapter);
        weeklyPlanRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        fetch();
        editHeader = root.findViewById(R.id.edit_weekly_plan);
        editDescription = root.findViewById(R.id.edit_description_weekly_plan);
        return root;
    }

    public void fetch() {
        weeklyPlanViewModel.getWeeklyPlan().observe(getViewLifecycleOwner(), data -> {
            if (data.status == AsyncDataStatus.SUCCESS) {
                weeklyPlanAdapter.update(data.payload);
            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.weekly_plan_edit_menu, menu);
        
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        System.out.println("edit button clicked");
        if(firstEdit == false){
            if(edit == true){
                edit = false;
                editHeader.setVisibility(View.GONE);
                editDescription.setVisibility(View.GONE);
                weeklyPlanViewModel.removeFromWeeklyPlan(WeeklyPlanCardViewHolder.removeFromWeeklyPlanRequest).observe(getViewLifecycleOwner(), data -> {
                    if (data.status == AsyncDataStatus.SUCCESS) {
                        fetch();
                    }
                });
            }
            else{
                edit = true;
                editHeader.setVisibility(View.VISIBLE);
                editDescription.setVisibility(View.VISIBLE);
            }
        }
        else{
            edit = true;
            editHeader.setVisibility(View.VISIBLE);
            editDescription.setVisibility(View.VISIBLE);
        }
        firstEdit = false;
        return false;
    }

}