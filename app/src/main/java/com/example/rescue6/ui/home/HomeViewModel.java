package com.example.rescue6.ui.home;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.BatteryManager;
import android.os.Build;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import static android.content.Context.BATTERY_SERVICE;

public class HomeViewModel extends AndroidViewModel {

    private MutableLiveData<String> mBattery;
    private MutableLiveData<String> mWifi;

    public HomeViewModel(Application context) {
        super(context);
        mBattery = new MutableLiveData<>();
        mWifi = new MutableLiveData<>();

        BatteryManager bm = (BatteryManager)context.getSystemService(BATTERY_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            int percentage = bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);
            mBattery.setValue("Battery Percentage: " + percentage + "%");
        }

        ConnectivityManager connectionManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiCheck = connectionManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        if(wifiCheck.isConnected()){
            mWifi.setValue("WiFi: Connected");
        } else {
            mWifi.setValue("Wifi: Not Connected");
        }
    }

    public LiveData<String> getBattery() {
        return mBattery;
    }

    public LiveData<String> getWifi(){
        return mWifi;
    }
}