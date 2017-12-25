package com.example.oleg.startapp.data;

import android.bluetooth.BluetoothDevice;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;


public interface IDataBase {
    void createProduct(BluetoothDevice device);
    //void deleteProduct(DeviceModel device);
    ArrayList<DeviceModel> getDevices();
    //int size();
}
