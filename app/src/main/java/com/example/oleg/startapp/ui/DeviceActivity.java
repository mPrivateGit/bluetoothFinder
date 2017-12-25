package com.example.oleg.startapp.ui;

import android.bluetooth.BluetoothDevice;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.oleg.startapp.R;
import com.example.oleg.startapp.data.BaseHelper;
import com.example.oleg.startapp.data.DeviceModel;
import com.example.oleg.startapp.recyclers.adapters.DeviceModelAdapter;

import java.util.ArrayList;

//todo // проверь работу кол бека
public class DeviceActivity extends AppCompatActivity
        implements onUpdateRecycler {
    protected RecyclerView mRecyclerView;
    private ArrayList<DeviceModel> devices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device);
        setContentView(R.layout.recyler_view);

        devices =  new ArrayList<>();
        BaseHelper baseHelper = new BaseHelper(this);
        devices = baseHelper.getDevices();

        setUpRecyclerView();
    }

    private void setUpRecyclerView(){
        //контайнер для ресайкл
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));

        mRecyclerView.swapAdapter(new DeviceModelAdapter(this, devices),
                true);
    }

    @Override
    public void updateRecycler() {
        setUpRecyclerView();
    }
}
