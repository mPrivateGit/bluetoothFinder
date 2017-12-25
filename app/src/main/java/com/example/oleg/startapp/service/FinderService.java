package com.example.oleg.startapp.service;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.example.oleg.startapp.Const;
import com.example.oleg.startapp.Sop;
import com.example.oleg.startapp.data.BaseHelper;
import com.example.oleg.startapp.ui.IUpdaterRecycler;

import java.util.ArrayList;
import java.util.Set;
import java.util.concurrent.TimeUnit;


public class FinderService extends Service {
    protected BluetoothAdapter mBTAdapter;
    protected Handler handler = new Handler();
    protected ArrayList<BluetoothDevice> deviceItemList;
    public static boolean isStarted;
    private IUpdaterRecycler iUpdater;
    private BaseHelper iBase;
    protected CheckRunnable checkRunnable = new CheckRunnable();

    class CheckRunnable implements Runnable {

        @Override
        public void run() {
            scan();
            handler.postDelayed(checkRunnable, TimeUnit.SECONDS.toMillis(400));
        }
    }

    private void scan() {
        Sop.d("scan() is working");
        Set<BluetoothDevice> pairedDevices = mBTAdapter.getBondedDevices();
        BluetoothManager manager = null;// with the argument
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            manager = getBaseContext().getSystemService(BluetoothManager.class);
        }

        if (pairedDevices.size() > 0) {
            // There are paired devices. Get the name and address of each paired device.
            for (BluetoothDevice device : pairedDevices) {
                showInformationDevice("Paired:", device);

                deviceItemList.add(device);
            }
        }
        mBTAdapter.startDiscovery();
    }

    private final BroadcastReceiver mReceiver = new BroadcastReceiver(){
        public void onReceive(Context context, Intent intent){
            String action= intent.getAction();
            // Когда найдено новое устройство
            if(BluetoothDevice.ACTION_FOUND.equals(action)){
            // Получаем объект BluetoothDevice из интента
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

                iBase.createProduct(device);
                //deviceItemList.add(device);
                Sop.d("\n" + device.getName()
                        + "\n" + device.getAddress());

           //     iUpdater.onUpdateRecycler();
            }
        }
    };

    protected void showInformationDevice(String worker, BluetoothDevice device) {
        Sop.d(worker+ " found device " + device.getName()
                + " address: " + device.getAddress()
                + " bluetooth class: " + device.getBluetoothClass());

        Parcelable[] uuidExtra = device.getUuids();
        for (int i=0; i<uuidExtra.length; i++) {
            Sop.d("Device: " + device.getName() + ", "
                    + device + ", Service: "
                    + uuidExtra[i].toString());
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        deviceItemList = new ArrayList<>();
        iBase = new BaseHelper(this);
        if (intent!=null && intent.getAction()!=null)
            switch (intent.getAction()) {
                case Const.ACTION.START:
                    Sop.d("Service started...");
                    isStarted = true;
                    handler.post(checkRunnable);
                    break;
                case Const.ACTION.SCAN:
                    scan();
                    break;
                case Const.ACTION.DESTROY:
                    stopSelf();

            }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mBTAdapter = BluetoothAdapter.getDefaultAdapter();

        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        filter.addAction(BluetoothDevice.ACTION_FOUND);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);

        filter.addAction(BluetoothDevice.ACTION_ACL_CONNECTED);
        filter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECT_REQUESTED);
        filter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED);

        registerReceiver(mReceiver, filter);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        mBTAdapter.cancelDiscovery();
        try {
            unregisterReceiver(mReceiver);
        } catch (Exception e) {
            e.printStackTrace();
        }
        isStarted = false;
        super.onDestroy();
    }
}
