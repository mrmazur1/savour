package com.vb4.savour.data.repository;

import androidx.lifecycle.MutableLiveData;

import com.vb4.savour.data.client.AsyncData;
import com.vb4.savour.data.datasource.SavourRemoteDataSource;
import com.vb4.savour.data.model.AddRecipeRequest;
import com.vb4.savour.data.model.AddDeleteWeeklyPlanResponse;
import com.vb4.savour.data.model.CreateIngredientRequest;
import com.vb4.savour.data.model.GetProfileResponse;
import com.vb4.savour.data.model.GroceryListResponsePiece;
import com.vb4.savour.data.model.Ingredient;
import com.vb4.savour.data.model.CreateUserRequest;
import com.vb4.savour.data.model.LoginRequest;
import com.vb4.savour.data.model.Recipe;
import com.vb4.savour.data.model.RemoveFromWeeklyPlanRequest;
import com.vb4.savour.data.model.SearchRequest;
import com.vb4.savour.data.model.User;
import com.vb4.savour.data.model.ViewRecipeResponsePiece;
import com.vb4.savour.data.model.WeeklyPlanRequest;
import com.vb4.savour.data.model.WeeklyPlanResponsePiece;

/**
 * Repository of Savour data
 */
public class SavourRepository {
    private SavourRemoteDataSource remote;

    private static SavourRepository instance;

    private SavourRepository() {
        remote = new SavourRemoteDataSource();
    }

    public static SavourRepository getInstance() {
        if (instance == null) {
            instance = new SavourRepository();
        }

        return instance;
    }

    public void getDiscover(MutableLiveData<AsyncData<Recipe[]>> callback) {
        // TODO - check if cache is dirty
        remote.getDiscover(callback);
    }

    public void getWeeklyPlan(MutableLiveData<AsyncData<WeeklyPlanResponsePiece[]>> callback) {
        remote.getWeeklyPlan(callback);
    }

    public void addToWeeklyPlan(WeeklyPlanRequest weeklyPlanRequest, MutableLiveData<AsyncData<AddDeleteWeeklyPlanResponse>> callback) {
        remote.addToWeeklyPlan(weeklyPlanRequest, callback);
    }

    public void removeFromWeeklyPlan(RemoveFromWeeklyPlanRequest removeFromWeeklyPlanRequest, MutableLiveData<AsyncData<AddDeleteWeeklyPlanResponse>> callback) {
        remote.removeFromWeeklyPlan(removeFromWeeklyPlanRequest, callback);
    }

    public void getSearchResults(SearchRequest searchRequest, MutableLiveData<AsyncData<Recipe[]>> callback) {
        // TODO - check if cache is dirty
        remote.getSearchResults(searchRequest, callback);
    }

    public void getRecipe(int id, MutableLiveData<AsyncData<ViewRecipeResponsePiece[]>> callback) {
        // TODO - check if cache is dirty
        remote.getRecipe(id, callback);
    }

    public void getGroceryList(MutableLiveData<AsyncData<GroceryListResponsePiece[]>> callback) {
        // TODO - check if cache is dirty
        remote.getGroceryList(callback);
    }

    public void getProfile(MutableLiveData<AsyncData<GetProfileResponse>> callback) {
        // TODO - check if cache is dirty
        remote.getProfile(callback);
    }

    public void createUser(CreateUserRequest createUserRequest, MutableLiveData<AsyncData<Void>> callback) {
        remote.createUser(createUserRequest, callback);
    }

    public void getSearchIngredients(String query, MutableLiveData<AsyncData<Ingredient[]>> callback) {
        // TODO - check if cache is dirty
        remote.getSearchIngredients(query, callback);
    }

    public void addRecipe(AddRecipeRequest addRecipeRequest, MutableLiveData<AsyncData<Recipe>> liveData) {
        remote.addRecipe(addRecipeRequest, liveData);
    }

    public void createIngredient(CreateIngredientRequest createIngredientRequest, MutableLiveData<AsyncData<Ingredient>> callback) {
        remote.addIngredient(createIngredientRequest, callback);
    }

    public void markRecipeViewed(int recipeId, MutableLiveData<AsyncData<Void>> callback) {
        remote.markRecipeViewed(recipeId, callback);
    }

    public void addRecipeToFavorites(Integer recipeId, MutableLiveData<AsyncData<Void>> callback) {
        remote.addRecipeToFavorites(recipeId, callback);
    }

    public void login(LoginRequest loginRequest, MutableLiveData<AsyncData<User>> callback) {
        remote.login(loginRequest, callback);
    }
}
