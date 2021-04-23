package com.vb4.savour.ui.main;

/**
 * A listener of events from the soft keyboard
 */
public interface SoftKeyboardListener {
    /**
     * Called when the soft keyboard opens
     */
    void onSoftKeyboardOpened();

    /**
     * Called when the soft keyboard closes
     */
    void onSoftKeyboardClosed();
}
