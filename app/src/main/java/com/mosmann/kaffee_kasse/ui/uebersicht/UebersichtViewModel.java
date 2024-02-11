package com.mosmann.kaffee_kasse.ui.uebersicht;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class UebersichtViewModel extends ViewModel {

    private final MutableLiveData<String> mText;
    public UebersichtViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Übersicht fragment");
    }

    public LiveData<String> getText() {
            return mText;
        }
}