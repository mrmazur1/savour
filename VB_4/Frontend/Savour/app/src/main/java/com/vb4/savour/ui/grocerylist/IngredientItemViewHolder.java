package com.vb4.savour.ui.grocerylist;

import android.content.res.Resources;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.vb4.savour.R;
import com.vb4.savour.data.model.Ingredient;
import com.vb4.savour.ui.searchingredient.IngredientClickListener;

/**
 * {@link androidx.recyclerview.widget.RecyclerView.ViewHolder} to display an {@link Ingredient}
 */
public class IngredientItemViewHolder extends GenericVH implements View.OnClickListener {
    /** The media view */
    private final ImageView mMediaImageView;

    /** View used to display the ingredient's name */
    private final TextView mNameTextView;

    /** View used to display the ingredient's amount */
    private final TextView mAmountTextView;

    /** View used to mark ingredients obtained */
    private final CheckBox mObtainedCheckBox;

    private Ingredient mIngredient;

    private IngredientClickListener mClickListener;

    public IngredientItemViewHolder(@NonNull View itemView) {
        super(itemView);
        mMediaImageView = itemView.findViewById(R.id.image_view_item_ingredient_media);
        mNameTextView = itemView.findViewById(R.id.text_view_item_ingredient_name);
        mAmountTextView = itemView.findViewById(R.id.text_view_item_ingredient_amount);
        mObtainedCheckBox = itemView.findViewById(R.id.check_box_item_ingredient_obtained);
        itemView.setOnClickListener(this);
    }

    public void hideObtained() {
        mObtainedCheckBox.setVisibility(View.GONE);
    }

    /**
     * Bind an {@link Ingredient} to this ViewHolder
     * @param ingredient the ingredient to display
     */
    public void bind(Ingredient ingredient, IngredientClickListener ingredientClickListener) {
        Resources res = itemView.getContext().getResources();

        mIngredient = ingredient;
        mClickListener = ingredientClickListener;
        mNameTextView.setText(ingredient.name);
        mAmountTextView.setText(res.getString(R.string.ingredient_item_amount, ingredient.amount, ingredient.unit));
    }

    @Override
    public void onClick(View view) {
        if (mClickListener != null) {
            mClickListener.onClick(mIngredient);
        }
    }
}
