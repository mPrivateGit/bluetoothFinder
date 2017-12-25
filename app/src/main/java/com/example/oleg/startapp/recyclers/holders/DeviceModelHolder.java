package com.example.oleg.startapp.recyclers.holders;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.os.ParcelUuid;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.oleg.startapp.R;
import com.example.oleg.startapp.connect.SampleConnectionCallback;
import com.example.oleg.startapp.connect.SampleConnectionFailedListener;
import com.example.oleg.startapp.connect.SampleDataCallback;
import com.example.oleg.startapp.connect.SampleDataSentCallback;
import com.example.oleg.startapp.data.DeviceModel;
import com.newtronlabs.easybluetooth.BluetoothClient;
import com.newtronlabs.easybluetooth.BluetoothServer;
import com.newtronlabs.easybluetooth.IBluetoothClient;
import com.newtronlabs.easybluetooth.IBluetoothServer;


public class DeviceModelHolder extends RecyclerView.ViewHolder{
    private final String SERVER_SERVICE_NAME = "oleg.service";
    private final String SERVER_CONNECT_UUID = "550e8400-e29b-41d4-a716-446655440000" ;

    private BluetoothServerSocket serverSocket;
    BluetoothSocket mBluetoothSocket = null;
    private BluetoothAdapter mBtnAdapter;
    private TextView mDeviceName;
    private Context mContext;
    private DeviceModel mDevice;
    private Button mBtnConnect;
    private BluetoothDevice mBluetoothDevice;

    IBluetoothServer btServer;

    public DeviceModelHolder(View itemView, Context context) {
        super(itemView);
        mContext = context;

        mBtnAdapter = BluetoothAdapter.getDefaultAdapter();
        mDeviceName = (TextView) itemView.findViewById(R.id.txt_found_device);
        mBtnConnect = (Button) itemView.findViewById(R.id.btn_connect);
        mBtnConnect.setEnabled(false);

//        btServer = new BluetoothServer.Builder(mContext,
//                        "EasyBtService",
//                        ParcelUuid.fromString("00001101-0000-1000-8000-00805F9B34FB"))
//                        .build();
    }

    public void bind(final DeviceModel device) {
        if (TextUtils.isEmpty(device.getName())) {
            mDeviceName.setText(R.string.str_non_devices);
        } else {
            mDevice = device;
            mDeviceName.setText(device.getName());
            mBtnConnect.setEnabled(true);

            mBluetoothDevice = mBtnAdapter.getRemoteDevice(mDevice.getAddress());
            mBtnConnect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    /** попытка создать собственный коннект **/
                    //connect.start();

                    /** Используя библиотеку**/
                    createServer();
                }
            });
        }

/** попытка создать собственный коннект **/
//    protected Thread connect = new Thread(new Runnable() {
//        @Override
//        public void run() {
//            mBtnAdapter.cancelDiscovery();
//
//            //server
//            try {
//                serverSocket = mBtnAdapter
//                        .listenUsingRfcommWithServiceRecord(SERVER_SERVICE_NAME,
//                                UUID.fromString(SERVER_CONNECT_UUID));
//                serverSocket.accept();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//            //client
//            try {
//                mBluetoothSocket = mBluetoothDevice
//                        .createRfcommSocketToServiceRecord(UUID
//                                .fromString(SERVER_CONNECT_UUID));
//                Sop.d("connecting...");
//                mBluetoothSocket.connect();
//                Sop.d("OK");
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    });
    }

    private void createServer() {
        btServer = new BluetoothServer.Builder(mContext,
                "EasyBtService", ParcelUuid.fromString("00001101-0000-1000-8000-00805F9B34FB"))
                .build();

        if (btServer == null) {
            // Server could not be created.
        } else {
            // Block until a client connects.
            IBluetoothClient btClient = btServer.accept();
            // Set a data callback to receive data from the remote device.
            btClient.setDataCallback(new SampleDataCallback());
            // Set a connection callback to be notified of connection changes.
            btClient.setConnectionCallback(new SampleConnectionCallback());
            // Set a data send callback to be notified when data is sent of fails to send.
            btClient.setDataSentCallback(new SampleDataSentCallback());
            btClient.sendData("ServerGreeting", "Hello Client".getBytes());
            //We don't want to accept any other clients.
            btServer.disconnect();
        }
    }
}
