package com.example.oleg.startapp.data;

import android.bluetooth.BluetoothDevice;

import java.util.UUID;

/**
 * Created by oleg on 08.12.17.
 */

public class DeviceModel {
    private int id;
    private String name;
    private String address;
    private boolean isActive;

    public DeviceModel(){
//        id = Integer.parseInt(UUID.randomUUID().toString());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    @Override
    public String toString() {
        return "DeviceModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
