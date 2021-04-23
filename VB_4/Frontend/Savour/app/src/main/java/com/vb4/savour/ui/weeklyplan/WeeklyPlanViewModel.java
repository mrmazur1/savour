package com.vb4.savour.ui.weeklyplan;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.vb4.savour.data.client.AsyncData;
import com.vb4.savour.data.model.AddDeleteWeeklyPlanResponse;
import com.vb4.savour.data.model.RemoveFromWeeklyPlanRequest;
import com.vb4.savour.data.model.WeeklyPlanResponsePiece;
import com.vb4.savour.domain.cases.weeklyplan.RemoveFromWeeklyPlanUseCase;
import com.vb4.savour.domain.cases.weeklyplan.WeeklyPlanUseCase;

public class WeeklyPlanViewModel extends ViewModel {
    private final MutableLiveData<AsyncData<WeeklyPlanResponsePiece[]>> mWeeklyPlanList;
    private final WeeklyPlanUseCase mGetWeeklyPlanUseCase;
    private final MutableLiveData<AsyncData<AddDeleteWeeklyPlanResponse>> mRemoveFromList;
    private final RemoveFromWeeklyPlanUseCase mRemoveFromWeeklyPlanUseCase;

    public WeeklyPlanViewModel() {
        mWeeklyPlanList = new MutableLiveData<>();
        mRemoveFromList = new MutableLiveData<>();
        mGetWeeklyPlanUseCase = new WeeklyPlanUseCase(mWeeklyPlanList);
        mRemoveFromWeeklyPlanUseCase = new RemoveFromWeeklyPlanUseCase(mRemoveFromList);
        mGetWeeklyPlanUseCase.run(null);
    }

    /**
     * Get async list of recipes
     * @return async list of recipes
     */
    public LiveData<AsyncData<WeeklyPlanResponsePiece[]>> getWeeklyPlan() {
        return mWeeklyPlanList;
    }

    public LiveData<AsyncData<AddDeleteWeeklyPlanResponse>> removeFromWeeklyPlan(RemoveFromWeeklyPlanRequest removeFromWeeklyPlanRequest) {
        mRemoveFromWeeklyPlanUseCase.run(removeFromWeeklyPlanRequest);
        return mRemoveFromList;
    }
}