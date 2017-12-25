package com.example.oleg.startapp;

import android.util.Log;


public final class Sop {
    private static final String TAG = "---> ";

    public static void d(String message){
        Log.d(TAG, message);
    }

    public static void d_tag_mes(String tag, String message){
        Log.d(tag, message);
    }

    public static void d_class(Class c, String message){
        Log.d(c.getCanonicalName(), message);
    }


}
