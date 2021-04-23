package com.vb4.savour.domain.cases.weeklyplan;


import androidx.lifecycle.MutableLiveData;

import com.vb4.savour.data.client.AsyncData;
import com.vb4.savour.data.model.AddDeleteWeeklyPlanResponse;
import com.vb4.savour.data.model.WeeklyPlanRequest;
import com.vb4.savour.data.repository.SavourRepository;
import com.vb4.savour.domain.cases.SavourUseCase;

/**
 * Add recipe to weekly planner
 */
public class AddToWeeklyPlanUseCase extends SavourUseCase<WeeklyPlanRequest, AddDeleteWeeklyPlanResponse> {
    /**
     * Add recipe to weekly planner via recipe id and day
     * @param liveData callback to notify
     */
    public AddToWeeklyPlanUseCase(MutableLiveData<AsyncData<AddDeleteWeeklyPlanResponse>> liveData) {
        super(liveData);
    }

    @Override
    public void run(WeeklyPlanRequest weeklyPlanRequest) {
        super.run(weeklyPlanRequest);
        SavourRepository.getInstance().addToWeeklyPlan(weeklyPlanRequest, liveData);
    }
}
