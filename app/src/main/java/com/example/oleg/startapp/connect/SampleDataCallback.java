package com.example.oleg.startapp.connect;

import android.util.Log;

import com.newtronlabs.easybluetooth.IBluetoothDataReceivedCallback;
import com.newtronlabs.easybluetooth.IBluetoothMessageEvent;

/**
 * Created by oleg on 23.12.17.
 */

public class SampleDataCallback implements IBluetoothDataReceivedCallback {
    public static final String TAG = "easyBt";

    @Override
    public void onDataReceived(IBluetoothMessageEvent messageEvent)
    {
        // Data was received.
        Log.d(TAG, "Received: "
                + messageEvent.getTag()
                + " Data: "
                + new String(messageEvent.getData()));
    }
}
