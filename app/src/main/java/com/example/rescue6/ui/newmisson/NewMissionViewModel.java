package com.example.rescue6.ui.newmisson;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.rescue6.R;

public class NewMissionViewModel extends AndroidViewModel {

    private int mObjects = 2;
    private int mDuration = 15;

    private MutableLiveData<String> mLabel1;
    private MutableLiveData<String> mLabel2;

    public NewMissionViewModel(Application application) {
        super(application);
        mLabel1 = new MutableLiveData<>();
        mLabel2 = new MutableLiveData<>();

        mLabel2.setValue(getApplication().getString(R.string.mission_duration, mDuration));
        mLabel1.setValue(getApplication().getString(R.string.findNObjects, mObjects));
    }

    LiveData<String> getObjectString() {
        return mLabel1;
    }


    LiveData<String> getDurationString() {
        return mLabel2;
    }

    int getmObjects() {
        return mObjects;
    }

    int getmDuration() {
        return mDuration;
    }

    void onDurationChanged(int duration) {
        mDuration = duration;
        mLabel2.setValue(getApplication().getString(R.string.mission_duration, mDuration));
    }

    void onObjectsChanged(int objects) {
        mObjects = objects;
        mLabel1.setValue(getApplication().getString(R.string.findNObjects, mObjects));
    }
}