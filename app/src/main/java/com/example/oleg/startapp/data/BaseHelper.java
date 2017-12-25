package com.example.oleg.startapp.data;

import android.bluetooth.BluetoothDevice;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.oleg.startapp.Sop;

import java.util.ArrayList;
import java.util.List;


public class BaseHelper extends SQLiteOpenHelper
        implements IDataBase{
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "found_devices.db";
    private SQLiteDatabase mSQL;
    // private Context mContext;

    public BaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
       // mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String mSQL = "create table " + BaseShema.FoundDevices.TABLE_NAME + "(" +
                " _id integer primary key autoincrement, " +
                BaseShema.ColsFoundDevice.UUID + ", " +
                BaseShema.ColsFoundDevice.DEVICE_NAME + ", " +
                BaseShema.ColsFoundDevice.DEVICE_MAC_ADRES +
                ")";
        sqLiteDatabase.execSQL(mSQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    @Override
    public void createProduct(BluetoothDevice device) {

        mSQL = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(BaseShema.ColsFoundDevice.UUID, String.valueOf(device.getUuids()));
        contentValues.put(BaseShema.ColsFoundDevice.DEVICE_NAME, device.getName());
        contentValues.put(BaseShema.ColsFoundDevice.DEVICE_MAC_ADRES, device.getAddress());

        mSQL.insert(BaseShema.FoundDevices.TABLE_NAME, null, contentValues);
        mSQL.close();
    }

    @Override
    public ArrayList<DeviceModel> getDevices() {
        ArrayList<DeviceModel> list = new ArrayList<>();

        mSQL = getReadableDatabase();
        String projection [] = {
                BaseShema.ColsFoundDevice.UUID,
                BaseShema.ColsFoundDevice.DEVICE_NAME,
                BaseShema.ColsFoundDevice.DEVICE_MAC_ADRES
        };

        Cursor cursor = mSQL.query(BaseShema.FoundDevices.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null);

        try {
            int targetUUID = cursor
                    .getColumnIndex(BaseShema.ColsFoundDevice.UUID);
            int targetName = cursor
                    .getColumnIndex(BaseShema.ColsFoundDevice.DEVICE_NAME);
            int targetMacAdres = cursor
                    .getColumnIndex(BaseShema.ColsFoundDevice.DEVICE_MAC_ADRES);

            while (cursor.moveToNext()) {
                int uuid = cursor.getInt(targetUUID);
                String name = cursor.getString(targetName);
                String macAdres = cursor.getString(targetMacAdres);

                DeviceModel device = new DeviceModel();
                device.setId(uuid);
                device.setName(name);
                device.setAddress(macAdres);

                Sop.d(device.toString());

                list.add(device);
            }
        } finally {
            cursor.close();
            mSQL.close();
        }

        return list;
    }
}
