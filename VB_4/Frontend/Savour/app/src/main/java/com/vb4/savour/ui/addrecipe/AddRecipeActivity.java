package com.vb4.savour.ui.addrecipe;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vb4.savour.R;
import com.vb4.savour.data.client.AsyncDataStatus;
import com.vb4.savour.data.model.Ingredient;
import com.vb4.savour.data.model.RecipeCategory;
import com.vb4.savour.ui.profile.ProfileFragment;
import com.vb4.savour.ui.searchingredient.SearchIngredientActivity;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class AddRecipeActivity extends AppCompatActivity {
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_IMAGE_SELECT = 2;

    public static final int REQUEST_INGREDIENT_ADD = 3;
    public static final String EXTRA_INGREDIENT = "AddRecipeIngredientExtra";
    
    private ImageView mMediaPreviewView;
    private Uri currentPhotoUri;
    private IngredientListAdapter ingredientListAdapter;
    private AddRecipeViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);

        mViewModel = new AddRecipeViewModel();

        final EditText nameEditText = findViewById(R.id.edit_text_add_recipe_name);
        nameEditText.addTextChangedListener(new SimpleTextWatcher(mViewModel::setPendingRecipeName));

        final RecyclerView ingredientListView = findViewById(R.id.recycler_view_add_recipe_ingredients);
        ingredientListAdapter = new IngredientListAdapter(new ArrayList<>());
        ingredientListView.setAdapter(ingredientListAdapter);
        ingredientListView.setLayoutManager(new LinearLayoutManager(ingredientListView.getContext()));
        mViewModel.getIngredients().observe(this, ingredients -> ingredientListAdapter.update(ingredients));

        final Spinner categorySpinner = findViewById(R.id.spinner_add_recipe_category);
        ArrayAdapter<CharSequence> categoryAdapter = ArrayAdapter.createFromResource(this,
                R.array.recipe_categories_array, android.R.layout.simple_spinner_item);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(categoryAdapter);
        mViewModel.setPendingRecipeCategory(RecipeCategory.BREAKFAST);
        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        mViewModel.setPendingRecipeCategory(RecipeCategory.BREAKFAST);
                        break;
                    case 1:
                        mViewModel.setPendingRecipeCategory(RecipeCategory.LUNCH);
                        break;
                    case 2:
                        mViewModel.setPendingRecipeCategory(RecipeCategory.DINNER);
                        break;
                    case 3:
                        mViewModel.setPendingRecipeCategory(RecipeCategory.DESSERT);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        mMediaPreviewView = findViewById(R.id.image_view_add_recipe_media_preview);

        final Button addIngredientButton = findViewById(R.id.button_add_ingredient);
        addIngredientButton.setOnClickListener(l -> {
            Intent addIngredientIntent = new Intent(this, SearchIngredientActivity.class);
            startActivityForResult(addIngredientIntent, REQUEST_INGREDIENT_ADD);
        });

        final Button selectImageButton = findViewById(R.id.button_add_recipe_photos);
        selectImageButton.setOnClickListener(l -> selectImage());

        final Button takeImageButton = findViewById(R.id.button_add_recipe_camera);
        takeImageButton.setOnClickListener(l -> takeImage());

        final TextView ingredientErrorTextView = findViewById(R.id.text_view_ingredient_error_add_recipe);
        mViewModel.getIngredientsError().observe(this, ingredientError -> {
            ingredientErrorTextView.setVisibility(ingredientError == null ? View.GONE : View.VISIBLE);
            ingredientErrorTextView.setText(ingredientError);
        });

        final TextView recipeCostTextView = findViewById(R.id.text_view_ingredient_cost_add_recipe);
        mViewModel.getIngredientCost().observe(this, cost -> recipeCostTextView.setText(
            getResources().getString(R.string.recipe_card_cost, cost))
        );

        final TextView timeTextView = findViewById(R.id.text_view_add_recipe_time);
        mViewModel.getPendingRecipe().observe(this, recipe -> {
            timeTextView.setText(getResources().getString(
                R.string.recipe_cooking_time_hms,
                recipe.timeHours, recipe.timeMinutes, recipe.timeSeconds)
            );
        });

        final Button changeTimeButton = findViewById(R.id.button_add_recipe_change_time);
        changeTimeButton.setOnClickListener(l -> displayTimePickerDialog());

        final TextView recipeStepsTextView = findViewById(R.id.text_view_add_recipe_steps);
        mViewModel.getRecipeSteps().observe(this, steps -> {
            String visibleRecipeSteps = IntStream
                    .range(0, steps.size())
                    .mapToObj(index -> String.format(Locale.US, "%d. %s", index + 1, steps.get(index)))
                    .collect(Collectors.joining("\n"));
            recipeStepsTextView.setText(visibleRecipeSteps);
        });

        final Button addRecipeStepButton = findViewById(R.id.button_add_recipe_step);
        addRecipeStepButton.setOnClickListener(l -> {
            final EditText input = new EditText(AddRecipeActivity.this);
            input.setHint("Recipe Step");
            input.setMaxLines(1);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
            input.setLayoutParams(lp);

            new AlertDialog.Builder(this)
                .setTitle("Add Recipe Step")
                .setView(input)
                .setNegativeButton("Dismiss", null)
                .setPositiveButton("Confirm", (dialogInterface, i) -> {
                    // Save selected time
                    mViewModel.addPendingRecipeSteps(input.getText().toString());
                })
                .create()
                .show();
        });

        final Button submitRecipeButton = findViewById(R.id.button_add_recipe_submit);
        submitRecipeButton.setOnClickListener(l -> {
            if (validate()) {
                addRecipe();
            }
        });
    }

    public void addRecipe(){
        mViewModel.addRecipe().observe(this, addRecipeStatus -> {
            if (addRecipeStatus.status == AsyncDataStatus.SUCCESS) {
                Toast
                        .makeText(AddRecipeActivity.this, "Recipe successfully added", Toast.LENGTH_LONG)
                        .show();

                // Send recipe id to previous activity
                Intent finishedIntent = new Intent();
                finishedIntent.putExtra(ProfileFragment.EXTRA_RECIPE_ID, addRecipeStatus.payload.id);
                setResult(RESULT_OK, finishedIntent);
                finish();
            }
        });
    }

    private void displayTimePickerDialog() {
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_recipe_time_picker, null);

        final NumberPicker hourNumberPicker = dialogView.findViewById(R.id.number_picker_recipe_hour);
        hourNumberPicker.setMinValue(0);
        hourNumberPicker.setMaxValue(23);

        final NumberPicker minuteNumberPicker = dialogView.findViewById(R.id.number_picker_recipe_minute);
        minuteNumberPicker.setMinValue(0);
        minuteNumberPicker.setMaxValue(59);

        final NumberPicker secondNumberPicker = dialogView.findViewById(R.id.number_picker_recipe_second);
        secondNumberPicker.setMinValue(0);
        secondNumberPicker.setMaxValue(59);

        new AlertDialog.Builder(this)
            .setTitle("Recipe Time")
            .setView(dialogView)
            .setNegativeButton("Dismiss", null)
            .setPositiveButton("Confirm", (dialogInterface, i) -> {
                // Save selected time
                mViewModel.setPendingRecipeTime(
                    hourNumberPicker.getValue(),
                    minuteNumberPicker.getValue(),
                    secondNumberPicker.getValue()
                );
            })
            .create()
            .show();
    }

    private boolean validate() {
        if (mViewModel.getIngredients().getValue().isEmpty()) {
            mViewModel.setIngredientsError("Ingredient list cannot be empty");
            return false;
        }

        mViewModel.setIngredientsError(null);

        return true;
    }

    private void selectImage() {
        Intent selectImageIntent = new Intent();
        selectImageIntent.setType("image/*");
        selectImageIntent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(selectImageIntent, "Select Image"), REQUEST_IMAGE_SELECT);
    }

    private void takeImage() {
        Intent takeImageIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takeImageIntent.resolveActivity(getPackageManager()) != null) {
            try {
                File imageFile = createImageFile();
                Uri imageURI = FileProvider.getUriForFile(
                        this, "com.vb4.savour.fileprovider", imageFile);
                takeImageIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageURI);
                currentPhotoUri = imageURI;
                startActivityForResult(takeImageIntent, REQUEST_IMAGE_CAPTURE);
            } catch (IOException e) {
                e.printStackTrace();
                // TODO - display error
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            mMediaPreviewView.setImageURI(currentPhotoUri);
        } else if (requestCode == REQUEST_IMAGE_SELECT && resultCode == RESULT_OK) {
            currentPhotoUri = data.getData();
            mMediaPreviewView.setImageURI(currentPhotoUri);
        } else if (requestCode == REQUEST_INGREDIENT_ADD && resultCode == RESULT_OK) {
            Ingredient i = (Ingredient)data.getSerializableExtra(EXTRA_INGREDIENT);
            mViewModel.addIngredient(i);
            mViewModel.setIngredientsError(null);
            Log.d("AddRecipeActivity", "Add ingredient: " + i.name);
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private File createImageFile() throws IOException {
        String imageFileName = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US)
                .format(new Date());
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        return File.createTempFile(imageFileName, ".jpg", storageDir);
    }
}
