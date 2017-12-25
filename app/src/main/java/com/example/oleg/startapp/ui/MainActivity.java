package com.example.oleg.startapp.ui;

import android.app.AlertDialog;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.example.oleg.startapp.Const;
import com.example.oleg.startapp.R;
import com.example.oleg.startapp.Sop;
import com.example.oleg.startapp.service.FinderService;

public class MainActivity extends AppCompatActivity {
    protected static int REQUEST_BLUETOOTH = 1;
    protected BluetoothAdapter mBTAdapter;
    protected Button mBtnScan;
    protected Button mBtnDevices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // создание кнопок
        createButtons();

        mBTAdapter = BluetoothAdapter.getDefaultAdapter();
        // проверка есть ли у устройства блютуз
        have_bluetooth(mBTAdapter);

    }

    private void have_bluetooth(BluetoothAdapter mBTAdapter) {
        if (mBTAdapter == null) {
            // Device does not support Bluetooth
            new AlertDialog.Builder(this)
                    .setTitle("Not compatible")
                    .setMessage("Your phone does not support Bluetooth")
                    .setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            System.exit(0);
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
            Sop.d("Device does not support Bluetooth");

            //включение блютуз
        } else if (!mBTAdapter.isEnabled()) {
            Intent enableBT = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBT, REQUEST_BLUETOOTH);
        }
    }

    private void createButtons(){
        mBtnScan = findViewById(R.id.btn_scan);
        mBtnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,
                        FinderService.class);
                intent.setAction(Const.ACTION.SCAN);
                startService(intent);
            }
        });

        mBtnDevices = (Button) findViewById(R.id.btnDevices);
        mBtnDevices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,
                        DeviceActivity.class);
                startActivity(intent);
            }
        });
    }

}
