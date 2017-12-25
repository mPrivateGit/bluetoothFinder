package com.example.oleg.startapp.data;


public class BaseShema {
    /**
     * Таблица №1 (данные найденных устройств)
     */
    public static final class FoundDevices {
        public static final String TABLE_NAME = "found_device";
    }

    public static final class ColsFoundDevice {
        public static final String UUID = "uuid";
        public static final String DEVICE_NAME = "name";
        public static final String DEVICE_MAC_ADRES = "mac_adres";
    }
}
