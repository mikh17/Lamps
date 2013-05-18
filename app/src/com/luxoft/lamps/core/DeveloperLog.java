
package com.luxoft.lamps.core;

import android.util.Log;

public class DeveloperLog {

    private static final String TAG = DeveloperLog.class.getSimpleName();
    private static final String PREFIX = TAG + ": ";

    private static boolean isAppDebuggable;

    public static void setAppDebuggable(boolean isAppDebuggable) {
        DeveloperLog.isAppDebuggable = isAppDebuggable;
    }

    public static void v(String message) {
        if (isAppDebuggable)
            Log.v(TAG, PREFIX + message);
    }

    public static void v(String message, Throwable throwable) {
        if (isAppDebuggable)
            Log.v(TAG, PREFIX + message, throwable);
    }

    public static void d(String message) {
        if (isAppDebuggable)
            Log.d(TAG, PREFIX + message);
    }

    public static void d(String message, Throwable throwable) {
        if (isAppDebuggable)
            Log.d(TAG, PREFIX + message, throwable);
    }

    public static void i(String message) {
        if (isAppDebuggable)
            Log.i(TAG, PREFIX + message);
    }

    public static void i(String message, Throwable throwable) {
        if (isAppDebuggable)
            Log.i(TAG, PREFIX + message, throwable);
    }

    public static void w(String message) {
        if (isAppDebuggable)
            Log.w(TAG, PREFIX + message);
    }

    public static void w(String message, Throwable throwable) {
        if (isAppDebuggable)
            Log.w(TAG, PREFIX + message, throwable);
    }

    public static void e(String message) {
        if (isAppDebuggable)
            Log.e(TAG, PREFIX + message);
    }

    public static void e(String message, Throwable throwable) {
        if (isAppDebuggable)
            Log.e(TAG, PREFIX + message, throwable);
    }
}
