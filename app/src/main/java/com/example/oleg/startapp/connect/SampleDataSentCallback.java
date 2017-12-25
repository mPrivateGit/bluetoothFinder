package com.example.oleg.startapp.connect;

import android.util.Log;

import com.newtronlabs.easybluetooth.IBluetoothClient;
import com.newtronlabs.easybluetooth.IBluetoothDataSentCallback;
import com.newtronlabs.easybluetooth.IBluetoothMessageEvent;

/**
 * Created by oleg on 23.12.17.
 */

public class SampleDataSentCallback implements IBluetoothDataSentCallback {
    public static final String TAG = "easyBt";

    @Override
    public void onDataSent(IBluetoothClient bluetoothClient, IBluetoothMessageEvent messageEvent)
    {
        Log.d(TAG, "Data Sent: " + messageEvent.getTag() + " Data: " + new String(messageEvent.getData()));
    }

    @Override
    public void onDataSendFailed(IBluetoothClient bluetoothClient, @SendFailureReason int failureReason)
    {
        if(failureReason == REASON_DATA_FORMAT_INVALID)
        {
            Log.d(TAG, "Failed to send data. Invalid Format");
        }
        else if(failureReason == REASON_REMOTE_CONNECTION_CLOSED)
        {
            Log.d(TAG, "Failed to send data. Connection Lost.");
        }
    }
}
