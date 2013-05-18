
package com.luxoft.lamps.core;

import android.app.ActivityManager;
import android.app.Application;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.IBinder;
import android.os.PowerManager;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

public class LApp extends Application {

    public static LApp instance;

    public Context context;

    public Resources res;

    private boolean displayedSplashScreen;

    private SharedPreferences preferences;

    public LApp() {
        instance = this;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        DeveloperLog.setAppDebuggable(isDebuggable());

        this.context = getApplicationContext();
        this.res = getResources();

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(WifiManager.SUPPLICANT_CONNECTION_CHANGE_ACTION);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        preferences.registerOnSharedPreferenceChangeListener(preferencesListener);
    }

    private OnSharedPreferenceChangeListener preferencesListener = new OnSharedPreferenceChangeListener() {

        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

        }
    };

    @Override
    public void onTerminate() {
        preferences.unregisterOnSharedPreferenceChangeListener(preferencesListener);
        super.onTerminate();
    }

    public ActivityManager getActivityManager() {
        return (ActivityManager)this.getSystemService(Context.ACTIVITY_SERVICE);
    }

    public ConnectivityManager getConnectivityManager() {
        return (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    public WifiManager getWifiManager() {
        return (WifiManager)this.getSystemService(Context.WIFI_SERVICE);
    }

    public NotificationManager getNotificationManager() {
        return (NotificationManager)this.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    public synchronized boolean isDisplayedSplashScreen() {
        return displayedSplashScreen;
    }

    public PowerManager getPowerManager() {
        return (PowerManager)getSystemService(POWER_SERVICE);
    }

    public WindowManager getWindowManager() {
        return (WindowManager)this.getSystemService(Context.WINDOW_SERVICE);
    }

    public synchronized void setDisplayedSplashScreen(boolean displayedSplashScreen) {
        this.displayedSplashScreen = displayedSplashScreen;
    }

    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();

        if (networkInfo == null)
            return false;

        return networkInfo.isConnected();
    }

    public boolean isWifiMode() {
        ConnectivityManager cm = getConnectivityManager();
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();

        if (networkInfo == null)
            return false;

        return networkInfo.getType() == ConnectivityManager.TYPE_WIFI;
    }

    public void hideKeyboard(IBinder windowToken) {
        ((InputMethodManager)this.getSystemService(Service.INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow(windowToken, 0);
    }

    public void showKeyboard(View view) {
        ((InputMethodManager)this.getSystemService(Service.INPUT_METHOD_SERVICE)).showSoftInput(
                view, 0);
    }

    public boolean isDebuggable() {
        try {
            PackageInfo pinfo = getPackageManager().getPackageInfo(this.getPackageName(), 0);
            int flags = pinfo.applicationInfo.flags;
            if ((flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0) {
                return true;
            } else {
                return false;
            }
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    public PackageInfo getPackageInfo() {
        try {
            return getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (Throwable e) {
            e.printStackTrace();
            throw new RuntimeException("Unable to read package info", e);
        }
    }
}
