package com.mosmann.kaffee_kasse.ui.ausgaben;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AusgabenViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public AusgabenViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Ausgaben fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}