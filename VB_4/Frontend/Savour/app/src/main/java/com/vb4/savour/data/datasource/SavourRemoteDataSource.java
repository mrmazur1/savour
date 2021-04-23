package com.vb4.savour.data.datasource;

import android.net.Uri;

import androidx.lifecycle.MutableLiveData;

import com.vb4.savour.data.client.AsyncData;
import com.vb4.savour.data.client.SavourClient;
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
 * A data source that fetches from a remote server
 */
public class SavourRemoteDataSource {
    public void getDiscover(MutableLiveData<AsyncData<Recipe[]>> callback) {
        SavourClient.getInstance().get("discover", Recipe[].class, callback);
    }

    public void getWeeklyPlan(MutableLiveData<AsyncData<WeeklyPlanResponsePiece[]>> callback) {
        SavourClient.getInstance().get("planner/init?UID=14", WeeklyPlanResponsePiece[].class, callback);
    }

    public void addToWeeklyPlan(WeeklyPlanRequest weeklyPlanRequest, MutableLiveData<AsyncData<AddDeleteWeeklyPlanResponse>> callback) {
        String encodedQuery = Uri.encode(weeklyPlanRequest.day);
        SavourClient.getInstance().post("planner/addtoPlannerRID?RID=" + weeklyPlanRequest.recipeId + "&date=" + encodedQuery, weeklyPlanRequest, AddDeleteWeeklyPlanResponse.class, callback);
    }

    public void removeFromWeeklyPlan(RemoveFromWeeklyPlanRequest removeFromWeeklyPlanRequest, MutableLiveData<AsyncData<AddDeleteWeeklyPlanResponse>> callback) {
        String encodedQuery = Uri.encode(removeFromWeeklyPlanRequest.day);
        SavourClient.getInstance().post("planner/removeCalendar?RID=" + removeFromWeeklyPlanRequest.recipeId + "&date=" + encodedQuery, removeFromWeeklyPlanRequest, AddDeleteWeeklyPlanResponse.class, callback);
    }

    public void getSearchResults(SearchRequest searchRequest, MutableLiveData<AsyncData<Recipe[]>> callback) {
        String encodedQuery = Uri.encode(searchRequest.query);
        SavourClient.getInstance().get("search?q=" + encodedQuery + "&category=" + searchRequest.category, Recipe[].class, callback);
    }

    public void getRecipe(int id, MutableLiveData<AsyncData<ViewRecipeResponsePiece[]>> callback) {
        SavourClient.getInstance().get("recipe/" + id, ViewRecipeResponsePiece[].class, callback);
    }

    public void getGroceryList(MutableLiveData<AsyncData<GroceryListResponsePiece[]>> callback) {
        SavourClient.getInstance().get("grocery/init", GroceryListResponsePiece[].class, callback);
    }

    public void getProfile(MutableLiveData<AsyncData<GetProfileResponse>> callback) {
        SavourClient.getInstance().get("profile", GetProfileResponse.class, callback);
    }

    public void createUser(CreateUserRequest createUserRequest, MutableLiveData<AsyncData<Void>> callback) {
        SavourClient.getInstance().post("profile/addUser", createUserRequest, Void.class, callback);
    }

    public void getSearchIngredients(String query, MutableLiveData<AsyncData<Ingredient[]>> callback) {
        String encodedQuery = Uri.encode(query);
        SavourClient.getInstance().get("ingredient/search?q=" + encodedQuery, Ingredient[].class, callback);
    }

    public void addRecipe(AddRecipeRequest addRecipeRequest, MutableLiveData<AsyncData<Recipe>> liveData) {
        SavourClient.getInstance().post("recipe/addRecipe", addRecipeRequest, Recipe.class, liveData);
    }

    public void addIngredient(CreateIngredientRequest createIngredientRequest, MutableLiveData<AsyncData<Ingredient>> callback) {
        SavourClient.getInstance().post("ingredient/add", createIngredientRequest, Ingredient.class, callback);
    }

    public void markRecipeViewed(int recipeId, MutableLiveData<AsyncData<Void>> callback) {
        SavourClient.getInstance().get("profile/views/" + recipeId, Void.class, callback);
    }

    public void addRecipeToFavorites(Integer recipeId, MutableLiveData<AsyncData<Void>> callback) {
        SavourClient.getInstance().get("profile/favorite/" + recipeId, Void.class, callback);
    }

    public void login(LoginRequest loginRequest, MutableLiveData<AsyncData<User>> callback) {
        SavourClient.getInstance().post("login", loginRequest, User.class, callback);
    }
}
