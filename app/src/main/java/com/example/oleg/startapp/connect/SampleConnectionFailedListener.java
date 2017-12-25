package com.example.oleg.startapp.connect;

import android.util.Log;

import com.newtronlabs.easybluetooth.IBluetoothClient;
import com.newtronlabs.easybluetooth.IBluetoothConnectionFailedListener;

/**
 * Created by oleg on 23.12.17.
 */

public class SampleConnectionFailedListener implements IBluetoothConnectionFailedListener {
    public static final String TAG = "easyBt";

    @Override
    public void onConnectionFailed(IBluetoothClient iBluetoothClient, int i) {
        // Connection attempt failed.
        Log.d(TAG, "Connection Failed!");
    }
}
