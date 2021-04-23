package com.vb4.savour.ui.makeit;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.vb4.savour.R;
import com.vb4.savour.data.model.Recipe;
import com.vb4.savour.ui.viewrecipe.ViewRecipeActivity;

public class MakeItActivity extends AppCompatActivity {
    private TextView recipeName;
    private ImageView recipeImage;
    private TextView previousStep;
    private TextView nextStep;
    private TextView currentStep;
    private ImageButton next;
    private ImageButton previous;
    private int counter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_it);

        recipeName = findViewById(R.id.make_it_recipe_name_header);
        recipeImage = findViewById(R.id.make_it_recipe_media);
        previousStep = findViewById(R.id.make_it_previous_step);
        nextStep = findViewById(R.id.make_it_next_step);
        currentStep = findViewById(R.id.make_it_current_step);
        previous = findViewById(R.id.make_it_previous_button);
        next = findViewById(R.id.make_it_next_button);

        Recipe recipe = (Recipe) getIntent().getSerializableExtra(ViewRecipeActivity.RECIPE_ID_EXTRA_KEY);
        counter = 0;
        String[] steps = recipe.recipeSteps.split(",");

        recipeName.setText(recipe.name);
        Glide
                .with(MakeItActivity.this)
                .load(recipe.mediaUrl)
                .into(recipeImage);
        previousStep.setText("Get ready to cook!");
        currentStep.setText(steps[0]);
        nextStep.setText(steps[1]);

        previous.setOnClickListener(l -> {
            System.out.println("at start of previous:" + counter);
            if(counter == 1){
                previousStep.setText("Get ready to cook!");
                currentStep.setText(steps[counter - 1]);
                nextStep.setText(steps[counter]);
                counter--;
            }
            else if(counter == 0){
                counter = 1;
                previousStep.setText("Get ready to cook!");
                currentStep.setText(steps[counter - 1]);
                nextStep.setText(steps[counter]);
            }
            else{
                previousStep.setText(steps[counter - 2]);
                currentStep.setText(steps[counter - 1]);
                currentStep.setText(steps[counter]);
                counter--;
            }
        });

        next.setOnClickListener(l -> {
            System.out.println("at start of next:" + counter);
            if(counter == steps.length - 1){
                counter = steps.length - 2;
                previousStep.setText(steps[counter]);
                currentStep.setText(steps[counter + 1]);
                nextStep.setText("Enjoy!");
            }
            else if(counter == steps.length - 2){
                previousStep.setText(steps[counter]);
                currentStep.setText(steps[counter + 1]);
                nextStep.setText("Enjoy!");
                counter++;
            }
            else{
                previousStep.setText(steps[counter]);
                currentStep.setText(steps[counter + 1]);
                nextStep.setText(steps[counter + 2]);
                counter++;
            }
        });
    }
}
