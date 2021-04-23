package com.vb4.savour;

import com.vb4.savour.data.client.AsyncData;
import com.vb4.savour.data.client.AsyncDataStatus;

import org.junit.Test;

import static org.junit.Assert.*;

public class AsyncDataTest {
    @Test
    public void testLoading() {
        AsyncData<Integer> data = AsyncData.loading();

        assertNull(data.payload);
        assertNull(data.error);
        assertEquals(AsyncDataStatus.LOADING, data.status);
    }

    @Test
    public void testSuccess() {
        AsyncData<Integer> data = AsyncData.success(42);

        assertEquals(new Integer(42), data.payload);
        assertNull(data.error);
        assertEquals(AsyncDataStatus.SUCCESS, data.status);
    }

    @Test
    public void testErrorString() {
        AsyncData<Integer> data = AsyncData.error("Internal Server Error");

        assertNull(data.payload);
        assertEquals("Internal Server Error", data.error);
        assertEquals(AsyncDataStatus.ERROR, data.status);
    }

    @Test
    public void testErrorThrowable() {
        AsyncData<Integer> data = AsyncData.error(new ArithmeticException("Cannot divide by 0"));

        assertNull(data.payload);
        assertEquals("Cannot divide by 0", data.error);
        assertEquals(AsyncDataStatus.ERROR, data.status);
    }
}