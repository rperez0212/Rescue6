package com.example.rescue6.model;

import androidx.annotation.NonNull;

/**
 * POJO class for DataSensor
 */
public class SensorDataObject {

    // match with service now column names
    float u_light;
    float u_acc_x;
    float u_acc_y;
    float u_acc_z;

    public float getLight() {
        return u_light;
    }

    public void setLight(float light) {
        this.u_light= light;
    }

    public float getAccx() {
        return u_acc_x;
    }

    public void setAccx(float accx) {
        this.u_acc_x = accx;
    }

    public float getAccy() {
        return u_acc_y;
    }

    public void setAccy(float accy) {
        this.u_acc_y = accy;
    }

    public float getAccz() {
        return u_acc_z;
    }

    public void setAccz(float accz) {
        this.u_acc_z = accz;
    }

    @NonNull
    @Override
    public String toString() {
        return "Light:"+u_light+", accX:"+u_acc_x+", accY:"+u_acc_y+", accZ:"+u_acc_z;
    }
}
