package com.example.rescue6;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.net.NetworkInfo;
import android.net.ConnectivityManager;

public class MainActivity extends AppCompatActivity {

    TextView battery;
    NetworkInfo wifiCheck;
    TextView textView;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        battery = findViewById(R.id.battery);
        BatteryManager bm = (BatteryManager)getSystemService(BATTERY_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            int percentage = bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);
            battery.setText("Battery Percentage: " + percentage + "%");
        }

        ConnectivityManager connectionManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        wifiCheck = connectionManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        textView = (TextView) findViewById(R.id.wifi);

        if(wifiCheck.isConnected()){
            textView.setText("WiFi: Connected");
        } else {
            textView.setText("Wifi: Not Connected");
        }
    }

    public void checkWifi(View view){
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }


}

