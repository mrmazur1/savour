package com.vb4.savour.ui.weeklyplan;

import android.content.res.Resources;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.vb4.savour.R;
import com.vb4.savour.data.model.Recipe;
import com.vb4.savour.data.model.RemoveFromWeeklyPlanRequest;
import com.vb4.savour.data.model.WeeklyPlanResponsePiece;

/**
 * {@link androidx.recyclerview.widget.RecyclerView.ViewHolder} for displaying a {@link Recipe}
 */
public class WeeklyPlanCardViewHolder extends RecyclerView.ViewHolder {
    /** The recipe being displayed */
    private WeeklyPlanResponsePiece mDayPlan;

    /** The breakfast media view */
    private final ImageButton mBreakfastMediaImageButton;

    /** The lunch media view */
    private final ImageButton mLunchMediaImageButton;

    /** The dinner media view */
    private final ImageButton mDinnerMediaImageButton;

    /** The view displaying the day of week */
    private final TextView mDayTextView;

    public static RemoveFromWeeklyPlanRequest removeFromWeeklyPlanRequest;

    /** Listener fired when this ViewHolder is pressed */
    @Nullable
    private WeeklyPlanCardClickListener mOnClickListener;

    public WeeklyPlanCardViewHolder(@NonNull View itemView) {
        super(itemView);

        mBreakfastMediaImageButton = itemView.findViewById(R.id.weekly_plan_breakfast_card_media);
        mLunchMediaImageButton = itemView.findViewById(R.id.weekly_plan_lunch_card_media);
        mDinnerMediaImageButton = itemView.findViewById(R.id.weekly_plan_dinner_card_media);
        mDayTextView = itemView.findViewById(R.id.weekday_name);
        removeFromWeeklyPlanRequest = new RemoveFromWeeklyPlanRequest();

        mBreakfastMediaImageButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if(WeeklyPlanFragment.edit == false){
                    if(mDayPlan.recipes[0] != null) {
                        mOnClickListener.onClick(mDayPlan.recipes[0].id);
                    }
                }
                else if(WeeklyPlanFragment.edit){
                    Resources res = itemView.getContext().getResources();
                    mBreakfastMediaImageButton.setBackgroundResource(R.drawable.ic_action_navigation_close);
                    Glide.with(itemView)
                            .clear(mBreakfastMediaImageButton);
                    //mDayPlan.recipes[0] = null;
                    removeFromWeeklyPlanRequest.recipeId = mDayPlan.recipes[0].id;
                    removeFromWeeklyPlanRequest.day = mDayPlan.day;
                }
            }
        });

        mLunchMediaImageButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if(WeeklyPlanFragment.edit == false) {
                    if (mDayPlan.recipes[1] != null) {
                        mOnClickListener.onClick(mDayPlan.recipes[1].id);
                    }
                }
                else if(WeeklyPlanFragment.edit){
                    Resources res = itemView.getContext().getResources();
                    mLunchMediaImageButton.setBackgroundResource(R.drawable.ic_action_navigation_close);
                    Glide.with(itemView)
                            .clear(mLunchMediaImageButton);
                    //mDayPlan.recipes[1] = null;
                    removeFromWeeklyPlanRequest.recipeId = mDayPlan.recipes[1].id;
                    removeFromWeeklyPlanRequest.day = mDayPlan.day;
                }
            }
        });

        mDinnerMediaImageButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if(WeeklyPlanFragment.edit == false) {
                    if (mDayPlan.recipes[2] != null) {
                        mOnClickListener.onClick(mDayPlan.recipes[2].id);
                    }
                }
                else if(WeeklyPlanFragment.edit){
                    //Resources res = itemView.getContext().getResources();
                    mDinnerMediaImageButton.setBackgroundResource(R.drawable.ic_action_navigation_close);
                    Glide.with(itemView)
                            .clear(mDinnerMediaImageButton);
                    //mDayPlan.recipes[2] = null;
                    removeFromWeeklyPlanRequest.recipeId = mDayPlan.recipes[2].id;
                    removeFromWeeklyPlanRequest.day = mDayPlan.day;

                }
            }
        });

    }

    /**
     * Bind a {@link Recipe} to this {@link androidx.recyclerview.widget.RecyclerView.ViewHolder}
     * @param piece the weekly plan response piece to bind
     * @param onClickListener listener fired when this {@link androidx.recyclerview.widget.RecyclerView.ViewHolder} is pressed
     */
    public void bind(WeeklyPlanResponsePiece piece, WeeklyPlanCardClickListener onClickListener) {
        Resources res = itemView.getContext().getResources();

        this.mOnClickListener = onClickListener;
        mDayPlan = piece;
        mDayTextView.setText(piece.day);


        if(piece.recipes[0] != null) {
            mBreakfastMediaImageButton.setBackground(null);
            Glide
                    .with(itemView)
                    .load(piece.recipes[0].mediaUrl)
                    .into(mBreakfastMediaImageButton);


        }

        if(piece.recipes[1] != null){
            mLunchMediaImageButton.setBackground(null);
            Glide
                    .with(itemView)
                    .load(piece.recipes[1].mediaUrl)
                    .into(mLunchMediaImageButton);
        }

        if(piece.recipes[2] != null){
            mDinnerMediaImageButton.setBackground(null);
            Glide
                    .with(itemView)
                    .load(piece.recipes[2].mediaUrl)
                    .into(mDinnerMediaImageButton);
        }
    }

}
