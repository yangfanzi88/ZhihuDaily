package com.example.mvp_demo.utils;

import android.util.Log;

/**
 * Created by yangfan on 2017/6/4.
 */

public class Logger {

    public static boolean debug = true;

    public static void v(String tag, String message){
        if(debug){
            Log.v(tag, message);
        }
    }

    public static void d(String tag, String message){
        if(debug){
            Log.d(tag, message);
        }
    }
    public static void i(String tag, String message){
        if(debug){
            Log.i(tag, message);
        }
    }
    public static void w(String tag, String message){
        if(debug){
            Log.w(tag, message);
        }
    }
    public static void e(String tag, String message){
        if(debug){
            Log.e(tag, message);
        }
    }

}
