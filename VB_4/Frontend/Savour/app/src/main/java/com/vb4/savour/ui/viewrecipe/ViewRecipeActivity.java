package com.vb4.savour.ui.viewrecipe;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.vb4.savour.R;
import com.vb4.savour.data.client.AsyncDataStatus;
import com.vb4.savour.data.model.Recipe;
import com.vb4.savour.data.model.WeeklyPlanRequest;
import com.vb4.savour.ui.makeit.MakeItActivity;

import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * View Recipe. Expects an {@link android.content.Intent} with {@link Integer} extra with key {@link ViewRecipeActivity#RECIPE_ID_EXTRA_KEY}
 */
public class ViewRecipeActivity extends AppCompatActivity {
    /**
     * Key of {@link android.content.Intent} extra containing the ID of the {@link Recipe} to view
     */
    public static final String RECIPE_ID_EXTRA_KEY = "ViewRecipeIdExtra";

    /** View model */
    private ViewRecipeViewModel mViewModel;

    /** Media view */
    private ImageView mMediaView;

    /** Name view */
    private TextView mNameTextView;

    /** Category view */
    private TextView mCategoryTextView;

    /** Cooking Time view */
    private TextView mCookingTimeTextView;

    /** Cost view */
    private TextView mCostTextView;

    private TextView mRecipeStepsTextView;

    private Button addToWeeklyPlan;
    private TextView addToWeeklyPlanInstructions;
    private Button submitRecipeToWeeklyPlan;
    private TextView addToWeeklyPlanMessage;
    private DatePicker datePicker;

    private Button makeIt;

    public Recipe viewRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_recipe);

        mViewModel = new ViewRecipeViewModel();

        mMediaView = findViewById(R.id.image_view_view_recipe_media);
        mNameTextView = findViewById(R.id.text_view_view_recipe_name);
        mCategoryTextView = findViewById(R.id.text_view_view_recipe_category);
        mCookingTimeTextView = findViewById(R.id.text_view_view_recipe_cooking_time);
        mCostTextView = findViewById(R.id.text_view_view_recipe_cost);
        mRecipeStepsTextView = findViewById(R.id.text_view_view_recipe_steps);
        addToWeeklyPlanInstructions = findViewById(R.id.view_recipe_add_to_weekly_plan_instructions);
        addToWeeklyPlan = findViewById(R.id.view_recipe_add_to_weekly_plan);
        datePicker = findViewById(R.id.date_picker);
        submitRecipeToWeeklyPlan = findViewById(R.id.view_recipe_submit_to_weekly_plan_button);
        addToWeeklyPlanMessage = findViewById(R.id.view_recipe_add_plan_message);
        makeIt = findViewById(R.id.view_recipe_make_it);

        addToWeeklyPlan.setOnClickListener(l -> {
            addToWeeklyPlanInstructions.setVisibility(View.VISIBLE);
            datePicker.setVisibility(View.VISIBLE);
            submitRecipeToWeeklyPlan.setVisibility(View.VISIBLE);
            submitRecipeToWeeklyPlan.setOnClickListener(c -> {
                int day = datePicker.getDayOfMonth();
                int month = datePicker.getMonth() + 1;
                int year = datePicker.getYear();
                WeeklyPlanRequest weeklyPlanRequest = new WeeklyPlanRequest();
                weeklyPlanRequest.day = day + "/" + month + "/" + year;
                weeklyPlanRequest.recipeId = getIntent().getIntExtra(RECIPE_ID_EXTRA_KEY, -1);
                if(weeklyPlanRequest.recipeId != -1) {
                    mViewModel.addToWeeklyPlanner(weeklyPlanRequest).observe(this, data -> {
                        if (data.status == AsyncDataStatus.SUCCESS) {
                            addToWeeklyPlanMessage.setTextColor(0x00FFFFFF); //color – A color value in the form 0xAARRGGBB
                            addToWeeklyPlanMessage.setText("Recipe successfully added to your weekly plan");
                            addToWeeklyPlanMessage.setVisibility(View.VISIBLE);

                        } else if (data.status == AsyncDataStatus.ERROR) {
                            addToWeeklyPlanMessage.setText("Invalid entry.\nEither invalid date\nor you already entered a recipe for this day and meal");
                            addToWeeklyPlanMessage.setVisibility(View.VISIBLE);
                        }
                    });
                }
            });
        });



        final Button addToFavoritesButton = findViewById(R.id.button_view_recipe_add_favorite);
        addToFavoritesButton.setOnClickListener(l -> {
            int recipeId = getIntent().getIntExtra(RECIPE_ID_EXTRA_KEY, -1);

            if (recipeId == -1) return;

            mViewModel.addToFavorites(recipeId).observe(this, r -> {
                if (r.status == AsyncDataStatus.SUCCESS) {
                    Log.d("ViewRecipe", "Success");
                    addToFavoritesButton.setVisibility(View.GONE);
                }
            });
        });

        fetch();

        makeIt.setOnClickListener(l -> {
            Intent intent = new Intent(this, MakeItActivity.class);
            intent.putExtra(ViewRecipeActivity.RECIPE_ID_EXTRA_KEY, viewRecipe);
            startActivity(intent);
        });
    }

    public void fetch() {
        int recipeId = getIntent().getIntExtra(RECIPE_ID_EXTRA_KEY, -1);

        if (recipeId != -1) {
            mViewModel.getRecipe(recipeId).observe(this, r -> {
                if (r.status == AsyncDataStatus.SUCCESS) {
                    viewRecipe = r.payload.recipe;

                    mNameTextView.setText(viewRecipe.name);
                    mCategoryTextView.setText(viewRecipe.category);
                    mCookingTimeTextView.setText(getResources().getString(R.string.view_recipe_cooking_time, viewRecipe.timeMinutes, viewRecipe.timeSeconds));
                    mCostTextView.setText(getResources().getString(R.string.view_recipe_cost, viewRecipe.cost));

                    String[] recipeSteps = Optional
                            .ofNullable(viewRecipe.recipeSteps)
                            .orElse("")
                            .split(",");

                    String formattedRecipeSteps = IntStream
                            .range(0, recipeSteps.length)
                            .mapToObj(idx -> String.format(Locale.US, "%d. %s", idx + 1, recipeSteps[idx]))
                            .collect(Collectors.joining("\n"));

                    mRecipeStepsTextView.setText(formattedRecipeSteps);

                    Glide
                        .with(ViewRecipeActivity.this)
                        .load(viewRecipe.mediaUrl)
                        .into(mMediaView);
                }
            });
        }
    }

}
