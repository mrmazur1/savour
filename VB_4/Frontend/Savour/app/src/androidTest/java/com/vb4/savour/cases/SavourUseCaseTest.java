package com.vb4.savour.cases;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;

import com.vb4.savour.data.client.AsyncData;
import com.vb4.savour.domain.cases.SavourUseCase;

import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SavourUseCaseTest {
    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Test
    public void testResponse() {
        MutableLiveData<AsyncData<Integer>> out = new MutableLiveData<>();
        SavourUseCase<Void, Integer> responseOnlyUseCase = new SavourUseCase<Void, Integer>(out) {
            @Override
            public void run(Void aVoid) {
                super.run(aVoid);
                liveData.postValue(AsyncData.success(42));
            }
        };
        responseOnlyUseCase.run(null);
        assertEquals(new Integer(42), out.getValue().payload);
    }

    @Test
    public void testRequest() {
        int request = 52;
        MutableLiveData<AsyncData<Integer>> out = new MutableLiveData<>();
        SavourUseCase<Integer, Integer> responseOnlyUseCase = new SavourUseCase<Integer, Integer>(out) {
            @Override
            public void run(Integer request) {
                super.run(request);
                liveData.postValue(AsyncData.success(request * 2));
            }
        };
        responseOnlyUseCase.run(request);
        assertEquals(new Integer(request * 2), out.getValue().payload);
    }
}
