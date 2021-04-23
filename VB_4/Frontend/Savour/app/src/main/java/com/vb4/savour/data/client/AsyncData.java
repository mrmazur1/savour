package com.vb4.savour.data.client;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * A response from performing a call with the {@link SavourClient}
 * @param <T> the type of the JSON-decoded response payload
 */
public class AsyncData<T> {
    /** The JSON-decoded payload from the response */
    @Nullable public T payload;

    /** The status of this call */
    @NonNull public AsyncDataStatus status;

    /** The error returned by running this call */
    @Nullable public String error;

    /**
     * Construct an {@link AsyncData}
     * @param status the status of the data
     * @param payload the payload of the data
     * @param errorMessage the error message from retrieving data
     */
    private AsyncData(@NonNull AsyncDataStatus status, @Nullable T payload, @Nullable String errorMessage) {
        this.status = status;
        this.payload = payload;
        this.error = errorMessage;
    }

    /**
     * Factory method to indicate successful data
     * @param data the payload
     * @param <E> the type of the payload
     * @return new {@link AsyncData} with payload and success
     */
    public static <E> AsyncData<E> success(E data) {
        return new AsyncData<>(AsyncDataStatus.SUCCESS, data, null);
    }

    /**
     * Factory method to indicate loading data
     * @param <E> the type of the potential payload
     * @return new loading {@link AsyncData}
     */
    public static <E> AsyncData<E> loading() {
        return new AsyncData<>(AsyncDataStatus.LOADING, null, null);
    }

    /**
     * Factory method to indicate unsuccessful data
     * @param e the error
     * @param <E> the type of the payload
     * @return new {@link AsyncData} with error
     */
    public static <E> AsyncData<E> error(String e) {
        return new AsyncData<>(AsyncDataStatus.ERROR, null, e);
    }

    /**
     * Factory method to indicate unsuccessful data
     * @param t the error
     * @param <E> the type of the payload
     * @return new {@link AsyncData} with error
     */
    public static <E> AsyncData<E> error(Throwable t) {
        return error(t.getMessage());
    }
}
