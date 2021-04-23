package com.vb4.savour.ui.viewrecipe;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.vb4.savour.data.client.AsyncData;
import com.vb4.savour.data.model.AddDeleteWeeklyPlanResponse;
import com.vb4.savour.data.model.Recipe;
import com.vb4.savour.data.model.ViewRecipeModel;
import com.vb4.savour.data.model.WeeklyPlanRequest;
import com.vb4.savour.domain.cases.profile.AddRecipeToFavoritesUseCase;
import com.vb4.savour.domain.cases.profile.MarkRecipeViewedUseCase;
import com.vb4.savour.domain.cases.viewrecipe.GetRecipeUseCase;
import com.vb4.savour.domain.cases.weeklyplan.AddToWeeklyPlanUseCase;

public class ViewRecipeViewModel extends ViewModel {
    private final MutableLiveData<AsyncData<ViewRecipeModel>> mRecipeData;
    private final MutableLiveData<AsyncData<AddDeleteWeeklyPlanResponse>> mData;
    private final GetRecipeUseCase mGetRecipeUseCase;

    private final AddToWeeklyPlanUseCase mAddToWeeklyPlanUseCase;

    private final MutableLiveData<AsyncData<Void>> mAddedToFavorites;
    private final AddRecipeToFavoritesUseCase mAddToFavoritesUseCase;
    private final MutableLiveData<AsyncData<Void>> mRecipeMarked;
    private final MarkRecipeViewedUseCase mMarkRecipeViewedUseCase;


    public ViewRecipeViewModel() {
        mRecipeData = new MutableLiveData<>();
        mData = new MutableLiveData<>();
        mGetRecipeUseCase = new GetRecipeUseCase(mRecipeData);

        mAddToWeeklyPlanUseCase = new AddToWeeklyPlanUseCase(mData);

        mRecipeMarked = new MutableLiveData<>();
        mMarkRecipeViewedUseCase = new MarkRecipeViewedUseCase(mRecipeMarked);
        mAddedToFavorites = new MutableLiveData<>();
        mAddToFavoritesUseCase = new AddRecipeToFavoritesUseCase(mAddedToFavorites);

    }

    /**
     * Get async data of a single {@link Recipe}
     * Side effect: Marks a {@link Recipe} as viewed
     */
    public LiveData<AsyncData<ViewRecipeModel>> getRecipe(int id) {
        mGetRecipeUseCase.run(id);
        mMarkRecipeViewedUseCase.run(id);

        return mRecipeData;
    }

    public LiveData<AsyncData<AddDeleteWeeklyPlanResponse>> addToWeeklyPlanner(WeeklyPlanRequest weeklyPlanRequest) {
        mAddToWeeklyPlanUseCase.run(weeklyPlanRequest);
        return mData;
    }

    public LiveData<AsyncData<Void>> addToFavorites(int id) {
        mAddToFavoritesUseCase.run(id);
        return mAddedToFavorites;
    }
}
