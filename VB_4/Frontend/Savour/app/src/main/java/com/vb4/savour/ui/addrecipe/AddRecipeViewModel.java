package com.vb4.savour.ui.addrecipe;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.vb4.savour.data.client.AsyncData;
import com.vb4.savour.data.model.AddRecipeRequest;
import com.vb4.savour.data.model.Ingredient;
import com.vb4.savour.data.model.Recipe;
import com.vb4.savour.data.model.RecipeWithoutId;
import com.vb4.savour.domain.cases.recipe.AddRecipeUseCase;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AddRecipeViewModel extends ViewModel {
    private final MutableLiveData<String> ingredientsError;
    private final MutableLiveData<List<Ingredient>> ingredientList;
    private final MediatorLiveData<Double> totalIngredientCost;
    private final MutableLiveData<RecipeWithoutId> pendingRecipe;

    private final MutableLiveData<ArrayList<String>> recipeSteps;

    private final MutableLiveData<AsyncData<Recipe>> addRecipeResponse;
    private final AddRecipeUseCase addRecipeUseCase;

    public AddRecipeViewModel() {
        ingredientsError = new MutableLiveData<>();
        ingredientList = new MutableLiveData<>(new ArrayList<>());
        totalIngredientCost = new MediatorLiveData<>();
        totalIngredientCost.addSource(ingredientList, ingredients -> {
            double totalCost = ingredients.stream().mapToDouble(i -> 1).sum(); // TODO
            totalIngredientCost.setValue(totalCost);
            setPendingRecipeCost(totalCost);
        });
        pendingRecipe = new MutableLiveData<>(new RecipeWithoutId());

        recipeSteps = new MutableLiveData<>(new ArrayList<>());

        addRecipeResponse = new MutableLiveData<>();
        addRecipeUseCase = new AddRecipeUseCase(addRecipeResponse);
    }

    private void setPendingRecipeCost(double totalCost) {
        RecipeWithoutId recipe = pendingRecipe.getValue();
        recipe.cost = totalCost;
        pendingRecipe.setValue(recipe);
    }

    public LiveData<String> getIngredientsError() {
        return ingredientsError;
    }

    public void setIngredientsError(String ingredientsError) {
        this.ingredientsError.postValue(ingredientsError);
    }

    public LiveData<List<Ingredient>> getIngredients() {
        return ingredientList;
    }

    public LiveData<Double> getIngredientCost() {
        return totalIngredientCost;
    }

    public void addIngredient(Ingredient ingredient) {
        List<Ingredient> value = ingredientList.getValue();
        value.add(ingredient);
        ingredientList.setValue(value);
    }

    public LiveData<RecipeWithoutId> getPendingRecipe() {
        return pendingRecipe;
    }

    public void setPendingRecipeTime(int hours, int minutes, int seconds) {
        RecipeWithoutId recipe = pendingRecipe.getValue();
        recipe.timeHours = hours;
        recipe.timeMinutes = minutes;
        recipe.timeSeconds = seconds;
        pendingRecipe.setValue(recipe);
    }

    public void setPendingRecipeName(String name) {
        RecipeWithoutId recipe = pendingRecipe.getValue();
        recipe.name = name;
        pendingRecipe.setValue(recipe);
    }

    public void addPendingRecipeSteps(String step) {
        RecipeWithoutId recipe = pendingRecipe.getValue();
        ArrayList<String> steps = recipeSteps.getValue();
        steps.add(step);
        recipeSteps.setValue(steps);
        recipe.recipeSteps = steps.stream().collect(Collectors.joining(","));
        pendingRecipe.setValue(recipe);
    }

    public LiveData<ArrayList<String>> getRecipeSteps() {
        return recipeSteps;
    }

    public void setPendingRecipeCategory(String cat) {
        RecipeWithoutId recipe = pendingRecipe.getValue();
        recipe.category = cat;
        pendingRecipe.setValue(recipe);
    }

    public LiveData<AsyncData<Recipe>> addRecipe() {
        AddRecipeRequest request = new AddRecipeRequest();
        request.recipe = pendingRecipe.getValue();

        // TODO - insert real photo
        request.recipe.mediaUrl = "https://en.meming.world/images/en/thumb/b/b9/Cursed_Cat.jpg/569px-Cursed_Cat.jpg";

        List<Ingredient> ingredients = getIngredients().getValue();
        request.ingredients = new Ingredient[ingredients.size()];
        for (int i = 0; i < ingredients.size(); i++) {
            request.ingredients[i] = ingredients.get(i);
        }

        addRecipeUseCase.run(request);
        return addRecipeResponse;
    }
}
