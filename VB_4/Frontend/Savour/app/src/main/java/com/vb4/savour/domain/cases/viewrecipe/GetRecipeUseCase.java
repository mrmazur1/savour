package com.vb4.savour.domain.cases.viewrecipe;

import androidx.lifecycle.MutableLiveData;

import com.vb4.savour.data.client.AsyncData;
import com.vb4.savour.data.client.AsyncDataStatus;
import com.vb4.savour.data.model.Ingredient;
import com.vb4.savour.data.model.ViewRecipeModel;
import com.vb4.savour.data.model.ViewRecipeResponsePiece;
import com.vb4.savour.data.repository.SavourRepository;
import com.vb4.savour.domain.cases.SavourUseCase;

/**
 * Performs fetch for recipe from the id
 */
public class GetRecipeUseCase extends SavourUseCase<Integer, ViewRecipeModel> {
    /**
     * Performs fetch for recipe from the id
     * @param liveData callback to notify
     */
    public GetRecipeUseCase(MutableLiveData<AsyncData<ViewRecipeModel>> liveData) {
        super(liveData);
    }

    @Override
    public void run(Integer recipeId) {
        super.run(recipeId);
        MutableLiveData<AsyncData<ViewRecipeResponsePiece[]>> out = new MutableLiveData<>();
        SavourRepository.getInstance().getRecipe(recipeId, out);
        out.observeForever(newInfo -> {
            if (newInfo.status == AsyncDataStatus.ERROR) {
                liveData.postValue(AsyncData.error(newInfo.error));
            } else if (newInfo.status == AsyncDataStatus.LOADING) {
                liveData.postValue(AsyncData.loading());
            } else {
                if (newInfo.payload.length == 0) {
                    liveData.postValue(AsyncData.error("Recipe has no ingredients"));
                } else {
                    liveData.postValue(AsyncData.success(viewRecipeFromResponse(newInfo.payload)));
                }
            }
        });
    }

    private ViewRecipeModel viewRecipeFromResponse(ViewRecipeResponsePiece[] response) {
        ViewRecipeModel model = new ViewRecipeModel();
        model.recipe = response[0].recipeId;
        model.ingredients = new Ingredient[response.length];
        for (int i = 0; i < response.length; i++) {
            model.ingredients[i] = response[i].ingredientsId;
            model.ingredients[i].amount = response[i].amount;
        }

        return model;
    }
}
