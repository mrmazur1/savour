package com.vb4.savour.domain.cases.weeklyplan;


import androidx.lifecycle.MutableLiveData;

import com.vb4.savour.data.client.AsyncData;
import com.vb4.savour.data.model.AddDeleteWeeklyPlanResponse;
import com.vb4.savour.data.model.RemoveFromWeeklyPlanRequest;
import com.vb4.savour.data.repository.SavourRepository;
import com.vb4.savour.domain.cases.SavourUseCase;

/**
 * Add recipe to weekly planner
 */
public class RemoveFromWeeklyPlanUseCase extends SavourUseCase<RemoveFromWeeklyPlanRequest, AddDeleteWeeklyPlanResponse> {
    /**
     * Add recipe to weekly planner via recipe id and day
     * @param liveData callback to notify
     */
    public RemoveFromWeeklyPlanUseCase(MutableLiveData<AsyncData<AddDeleteWeeklyPlanResponse>> liveData) {
        super(liveData);
    }

    @Override
    public void run(RemoveFromWeeklyPlanRequest removeFromWeeklyPlanRequest) {
        super.run(removeFromWeeklyPlanRequest);
        SavourRepository.getInstance().removeFromWeeklyPlan(removeFromWeeklyPlanRequest, liveData);
    }
}
