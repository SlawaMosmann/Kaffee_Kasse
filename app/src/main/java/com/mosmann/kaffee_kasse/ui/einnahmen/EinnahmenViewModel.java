package com.mosmann.kaffee_kasse.ui.einnahmen;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class EinnahmenViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public EinnahmenViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Einnahmen fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}