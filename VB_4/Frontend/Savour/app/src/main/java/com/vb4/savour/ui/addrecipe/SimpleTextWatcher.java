package com.vb4.savour.ui.addrecipe;

import android.text.Editable;
import android.text.TextWatcher;

import java.util.function.Consumer;

public class SimpleTextWatcher implements TextWatcher {
    private final Consumer<String> listener;

    public SimpleTextWatcher(Consumer<String> textListener) {
        listener = textListener;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        listener.accept(charSequence.toString());
    }

    @Override
    public void afterTextChanged(Editable editable) {}
}
