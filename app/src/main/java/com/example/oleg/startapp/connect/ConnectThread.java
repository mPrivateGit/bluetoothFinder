//package com.example.oleg.startapp.connect;
//
//
//import android.bluetooth.BluetoothDevice;
//import android.bluetooth.BluetoothSocket;
//
//import java.io.IOException;
//
////Это клиент
//public class ConnectThread extends Thread {
//    private final BluetoothSocket mSocket;
//    private final BluetoothDevice mDevice;
//
//    private ConnectThread(BluetoothDevice device){
//        // используем вспомогательную переменную, которую в дальнейшем
//        // свяжем с mSocket,
//        BluetoothSocket tmp = null;
//        mDevice = device;
//
//        // получаем BluetoothSocket чтобы соединиться с BluetoothDevice
//        try{
//        // MY_UUID это UUID, который используется и в сервере
//        tmp= device.createRfcommSocketToServiceRecord(MY_UUID);
//        } catch(IOException e){}
//        mSocket = tmp;
//        }
//
//    @Override
//    public void run(){
//    // Отменяем сканирование, поскольку оно тормозит соединение
//        mBluetoothAdapter.cancelDiscovery();
//
//        try{
//        // Соединяемся с устройством через сокет.
//        // Метод блокирует выполнение программы до
//        // установки соединения или возникновения ошибки
//            mSocket.connect();
//        } catch(IOException connectException){
//        // Невозможно соединиться. Закрываем сокет и выходим.
//
//        try{
//            mSocket.close();
//        } catch(IOException closeException){}
//            return;
//        }
//
//        // управлчем соединением (в отдельном потоке)
//            manageConnectedSocket(mSocket);
//        }
//
//    /** отмена ожидания сокета */
//    public void cancel(){
//        try{
//            mSocket.close();
//        } catch(IOException e){}
//    }
//}
