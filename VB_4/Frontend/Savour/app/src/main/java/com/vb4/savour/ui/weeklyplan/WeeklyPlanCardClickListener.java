package com.vb4.savour.ui.weeklyplan;

/**
 * A listener for when a {@link WeeklyPlanCardViewHolder} is clicked.
 */
public interface WeeklyPlanCardClickListener {
    /**
     * Callback fired when the {@link WeeklyPlanCardViewHolder} is clicked.
     * @param r the recipe that was clicked
     */
    void onClick(int r);
}
