package com.example.oleg.startapp.recyclers.adapters;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.oleg.startapp.R;
import com.example.oleg.startapp.data.BaseHelper;
import com.example.oleg.startapp.data.DeviceModel;
import com.example.oleg.startapp.recyclers.holders.DeviceModelHolder;

import java.util.ArrayList;


public class DeviceModelAdapter extends RecyclerView.Adapter<DeviceModelHolder>{
    protected Context mContext;
    private ArrayList<DeviceModel> list;

    public DeviceModelAdapter(Context context,
                              ArrayList<DeviceModel> deviceModels){
        list = deviceModels;
        mContext = context;
    }

    @Override
    public DeviceModelHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_view, parent, false);


        return new DeviceModelHolder(view, mContext);
    }

    @Override
    public void onBindViewHolder(DeviceModelHolder holder, int position) {
        if (list.size() != 0) {
            holder.bind(list.get(position));
        } else {
            Toast t = Toast.makeText(mContext, "Exeption in Recycler.Adapetr",
                    Toast.LENGTH_SHORT);
            t.show();
            //holder.bind(new BluetoothDevice());
        }
    }

    @Override
    public int getItemCount() {
        if (list.size()>0) {
            return list.size();
        } else {
            return 1;
        }
    }
}
