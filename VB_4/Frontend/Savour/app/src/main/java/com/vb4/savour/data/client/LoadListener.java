package com.vb4.savour.data.client;

/**
 * Listener for event from {@link SavourClient} when a request
 * begins loading.
 */
public interface LoadListener {
    /**
     * Fired when a request begins loading
     */
    void onLoadBegin();
}
