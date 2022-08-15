package com.flatcode.simplemultiapps.MultipleDelete;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MultiDelete extends ViewModel {
    //Initialize variable
    MutableLiveData<String> mutableLiveData = new MutableLiveData<>();

    //Create set text method
    public void setText(String s) {
        //Set value
        mutableLiveData.setValue(s);
    }

    //Create get text method
    public MutableLiveData<String> getText(){
        return mutableLiveData;
    }
}
