package com.mosmann.kaffee_kasse.ui;

import android.text.InputFilter;
import android.text.Spanned;

public class DecimalDigitsInputFilter implements InputFilter {

    private final int decimalDigits;

    public DecimalDigitsInputFilter(int decimalDigits) {
        this.decimalDigits = decimalDigits;
    }

    @Override
    public CharSequence filter(CharSequence source, int start, int end,
                               Spanned dest, int dstart, int dend) {
        String currentText = dest.toString();
        String newText = currentText.substring(0, dstart) + source.toString() + currentText.substring(dend);

        String[] parts = newText.split("[.,]");
        if (parts.length > 1) {
            String decimalPart = parts[1];
            if (decimalPart.length() > decimalDigits) {
                return "";
            }
        }

        return null;
    }
}