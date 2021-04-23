package com.vb4.savour.ui.weeklyplan;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vb4.savour.R;
import com.vb4.savour.data.model.WeeklyPlanResponsePiece;
import com.vb4.savour.ui.discover.RecipeCardViewHolder;

/**
 * Adapter to supply list on {@link WeeklyPlanFragment} with recipes.
 */
public class WeeklyPlanAdapter extends RecyclerView.Adapter<WeeklyPlanCardViewHolder> {
    /** The array this adapter displays */
    private WeeklyPlanResponsePiece[] mData;

    /** A listener subscribed to receive event where {@link RecipeCardViewHolder} is clicked */
    private WeeklyPlanCardClickListener mListener;

    /**
     * Create a new empty adapter with a listener to click events on items
     * @param listener listener notified when recipe is clicked
     */
    public WeeklyPlanAdapter(WeeklyPlanCardClickListener listener) {
        update(new WeeklyPlanResponsePiece[]{});
        mListener = listener;
    }

    /**
     * Update the data displayed by this adapter
     * @param newData the new recipe list
     */
    public void update(WeeklyPlanResponsePiece[] newData) {
        mData = newData;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public WeeklyPlanCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.weekly_plan_card, parent, false);
        return new WeeklyPlanCardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WeeklyPlanCardViewHolder holder, int position) {
        holder.bind(mData[position], r -> mListener.onClick(r));
    }

    @Override
    public int getItemCount() {
        return mData.length;
    }
}