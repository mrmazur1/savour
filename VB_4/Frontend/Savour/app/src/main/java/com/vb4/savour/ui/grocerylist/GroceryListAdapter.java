package com.vb4.savour.ui.grocerylist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.vb4.savour.R;
import com.vb4.savour.data.client.AsyncData;
import com.vb4.savour.data.client.AsyncDataStatus;
import com.vb4.savour.data.model.GroceryListModelPiece;
import com.vb4.savour.data.model.Ingredient;
import com.vb4.savour.data.model.Recipe;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashSet;

/**
 * Adapter to display Grocery List
 */
public class GroceryListAdapter extends RecyclerView.Adapter<GenericVH> {
    /** View Type of Header */
    private static final int VIEW_TYPE_HEADER = 0;

    /** View Type of Item */
    private static final int VIEW_TYPE_ITEM = 1;

    /** Data displayed in {@link RecyclerView} */
    private DataItem[] mData;

    /** A set of indexes that belong to headers */
    private final HashSet<Integer> mHeaderIndexes;

    /**
     * Create a new adapter used to display recipes and ingredients on the Grocery List
     * @param liveData liveData containing the grocery list response
     * @param lifecycleOwner a lifecycle owner this adapter can use to observe the liveData
     */
    public GroceryListAdapter(LiveData<AsyncData<GroceryListModelPiece[]>> liveData,
                              LifecycleOwner lifecycleOwner) {
        mData = new DataItem[]{};
        mHeaderIndexes = new HashSet<>();

        liveData.observe(lifecycleOwner, data -> {
            if (data.status == AsyncDataStatus.SUCCESS && data.payload != null) {
                int itemCount = data.payload.length +
                        Arrays.stream(data.payload).map(r -> r.ingredients).mapToInt(Array::getLength).sum();
                mData = new DataItem[itemCount];
                mHeaderIndexes.clear();

                // Copy recipe and ingredients to data array
                int dataIdx = 0;
                for (int recipeIdx = 0; recipeIdx < data.payload.length; recipeIdx++) {
                    GroceryListModelPiece piece = data.payload[recipeIdx];
                    mHeaderIndexes.add(dataIdx);
                    mData[dataIdx++] = DataItem.recipe(piece.recipe);
                    for (Ingredient ingredient : piece.ingredients) {
                        mData[dataIdx++] = DataItem.ingredient(ingredient);
                    }
                }
            }

            notifyDataSetChanged();
        });
    }

    @Override
    public int getItemViewType(int position) {
        return mHeaderIndexes.contains(position) ? VIEW_TYPE_HEADER : VIEW_TYPE_ITEM;
    }

    @NonNull
    @Override
    public GenericVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;

        if (viewType == VIEW_TYPE_HEADER) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.header_recipe, parent, false);
            return new RecipeHeaderViewHolder(view);
        } else if (viewType == VIEW_TYPE_ITEM) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ingredient, parent, false);
            return new IngredientItemViewHolder(view);
        }

        throw new IllegalArgumentException("viewType is not " + VIEW_TYPE_HEADER + " or " + VIEW_TYPE_ITEM);
    }

    @Override
    public void onBindViewHolder(@NonNull GenericVH holder, int position) {
        if (holder.getItemViewType() == VIEW_TYPE_HEADER) {
            ((RecipeHeaderViewHolder)holder).bind(mData[position].recipe);
        } else if (holder.getItemViewType() == VIEW_TYPE_ITEM) {
            ((IngredientItemViewHolder)holder).bind(mData[position].ingredient, null);
        } else {
            throw new IllegalArgumentException("viewType is not " + VIEW_TYPE_HEADER + " or " + VIEW_TYPE_ITEM);
        }
    }

    @Override
    public int getItemCount() {
        return mData.length;
    }

    /**
     * Wrapper for either a {@link Recipe} header or {@link Ingredient} item to be displayed
     */
    private static class DataItem {
        /** The view type of this item */
        public int viewType;

        /** If this is a header, the {@link Recipe} to display */
        @Nullable
        public Recipe recipe;

        /** If this is an item, the {@link com.vb4.savour.data.model.Ingredient} to display */
        @Nullable
        public Ingredient ingredient;

        /**
         * Create a {@link DataItem}
         * @param viewType the view type of this item
         * @param r if the data item is a header, the recipe, otherwise {@code null}
         * @param i if the data item is an item, the ingredient, otherwise {@code null}
         */
        private DataItem(int viewType, @Nullable Recipe r, @Nullable Ingredient i) {
            this.viewType = viewType;
            this.recipe = r;
            this.ingredient = i;
        }

        /**
         * Create a Recipe item
         * @param recipe the recipe to display
         * @return a data item representing a header
         */
        public static DataItem recipe(@NonNull Recipe recipe) {
            return new DataItem(VIEW_TYPE_HEADER, recipe, null);
        }

        /**
         * Create an Ingredient item
         * @param ingredient the ingredient to display
         * @return a data item representing an item
         */
        public static DataItem ingredient(Ingredient ingredient) {
            return new DataItem(VIEW_TYPE_ITEM, null, ingredient);
        }
    }
}
