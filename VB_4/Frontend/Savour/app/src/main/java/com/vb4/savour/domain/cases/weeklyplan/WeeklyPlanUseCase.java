package com.vb4.savour.domain.cases.weeklyplan;

import androidx.lifecycle.MutableLiveData;

import com.vb4.savour.data.client.AsyncData;
import com.vb4.savour.data.model.WeeklyPlanResponsePiece;
import com.vb4.savour.data.repository.SavourRepository;
import com.vb4.savour.domain.cases.SavourUseCase;

/**
 * Performs a fetch of trending recipes
 */
public class WeeklyPlanUseCase extends SavourUseCase<Void, WeeklyPlanResponsePiece[]> {
    /**
     * Performs a fetch of trending recipes
     * @param liveData callback to notify
     */
    public WeeklyPlanUseCase(MutableLiveData<AsyncData<WeeklyPlanResponsePiece[]>> liveData) {
        super(liveData);
    }

    @Override
    public void run(Void request) {
        super.run(request);
        SavourRepository.getInstance().getWeeklyPlan(liveData);
    }
}
