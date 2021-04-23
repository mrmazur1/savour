package com.vb4.savour.domain.cases.grocerylist;

import androidx.lifecycle.MutableLiveData;

import com.vb4.savour.data.client.AsyncData;
import com.vb4.savour.data.model.GroceryListModelPiece;
import com.vb4.savour.data.model.GroceryListResponsePiece;
import com.vb4.savour.data.model.Ingredient;
import com.vb4.savour.data.model.ViewRecipeResponsePiece;
import com.vb4.savour.data.repository.SavourRepository;
import com.vb4.savour.domain.cases.SavourUseCase;

import java.util.ArrayList;

/**
 * Performs a fetch of the user's grocery list
 */
public class GetGroceryListUseCase extends SavourUseCase<Void, GroceryListModelPiece[]> {
    /**
     * Performs a fetch of the user's grocery list
     * @param liveData callback to notify
     */
    public GetGroceryListUseCase(MutableLiveData<AsyncData<GroceryListModelPiece[]>> liveData) {
        super(liveData);
    }

    @Override
    public void run(Void aVoid) {
        super.run(aVoid);

        MutableLiveData<AsyncData<GroceryListResponsePiece[]>> out = new MutableLiveData<>();
        out.observeForever(data -> {
            switch (data.status) {
                case ERROR:
                    liveData.postValue(AsyncData.error(data.error));
                    break;
                case LOADING:
                    liveData.postValue(AsyncData.loading());
                    break;
                case SUCCESS:
                    liveData.postValue(AsyncData.success(modelFromResponse(data.payload)));
                    break;
            }
        });

        SavourRepository.getInstance().getGroceryList(out);
    }

    /**
     * Convert a Grocery List response to a Grocery List model
     * @param response response from the server
     * @return Grocery List model
     */
    private GroceryListModelPiece[] modelFromResponse(GroceryListResponsePiece[] response) {
        ArrayList<GroceryListModelPiece> pieces = new ArrayList<>();

        for (GroceryListResponsePiece responsePiece : response) {
            GroceryListModelPiece modelPiece = new GroceryListModelPiece();
            // responsePiece = one day
            for (ViewRecipeResponsePiece[] responsePieces : responsePiece.recipes) {
                // responsePieces[0] = breakfast, responsePieces[1] = lunch, [2] = dinner
                if (responsePieces.length == 0) continue;
                modelPiece.recipe = responsePieces[0].recipeId;
                modelPiece.ingredients = new Ingredient[responsePieces.length];
                for (int i = 0; i < modelPiece.ingredients.length; i++) {
                    modelPiece.ingredients[i] = responsePieces[i].ingredientsId;
                    modelPiece.ingredients[i].amount = responsePieces[i].amount;
                }
                pieces.add(modelPiece);
            }
        }

        GroceryListModelPiece[] models = new GroceryListModelPiece[pieces.size()];
        for (int i = 0; i < models.length; i++) {
            models[i] = pieces.get(i);
        }

        return models;
    }
}
