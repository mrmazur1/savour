package com.vb4.savour.ui.addingredient;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.vb4.savour.R;
import com.vb4.savour.data.client.AsyncDataStatus;
import com.vb4.savour.ui.addrecipe.AddRecipeActivity;
import com.vb4.savour.ui.addrecipe.SimpleTextWatcher;
import com.vb4.savour.ui.searchingredient.SearchIngredientActivity;

public class AddIngredientActivity extends AppCompatActivity {
    private AddIngredientViewModel mViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ingredient);

        mViewModel = new AddIngredientViewModel();

        final EditText ingredientNameEditText = findViewById(R.id.edit_text_add_ingredient_name);
        ingredientNameEditText.addTextChangedListener(new SimpleTextWatcher(mViewModel::setName));

        final EditText ingredientAmountEditText = findViewById(R.id.edit_text_add_ingredient_amount);
        ingredientAmountEditText.addTextChangedListener(new SimpleTextWatcher(mViewModel::setAmount));

        final EditText ingredientUnitEditText = findViewById(R.id.edit_text_add_ingredient_unit);
        ingredientUnitEditText.addTextChangedListener(new SimpleTextWatcher(mViewModel::setUnit));

        final EditText ingredientCategoryEditText = findViewById(R.id.edit_text_add_ingredient_category);
        ingredientCategoryEditText.addTextChangedListener(new SimpleTextWatcher(mViewModel::setCategory));

        final Button submitButton = findViewById(R.id.button_add_ingredient_submit);
        submitButton.setOnClickListener(l -> {
            mViewModel.createIngredient().observe(this, newIngredient -> {
                if (newIngredient.status == AsyncDataStatus.SUCCESS) {
                    // Send ingredient back
                    Intent ingredientIndent = new Intent();
                    ingredientIndent.putExtra(SearchIngredientActivity.EXTRA_CREATED_INGREDIENT, newIngredient.payload);
                    setResult(RESULT_OK, ingredientIndent);
                    finish();
                }
            });
        });
    }
}
