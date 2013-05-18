
package com.luxoft.lamps.core;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

public class BaseActivity extends Activity {

    private boolean isBound = false;
    private SessionService sessionService;

    public SessionService getSession() {
        return sessionService;
    }

    protected void onSessionConnected(SessionService session) {
    }

    private ServiceConnection mConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
            isBound = true;
            sessionService = ((SessionService.LocalBinder)service).getService();
            onSessionConnected(sessionService);
        }

        public void onServiceDisconnected(ComponentName className) {
            sessionService = null;
        }
    };

    void doBindService() {
        Activity parent = getParent();
        Context context = (parent == null ? this : parent);
        context.bindService(new Intent(BaseActivity.this, SessionService.class), mConnection,
                Context.BIND_AUTO_CREATE);
    }

    void doUnbindService() {
        try {
            if (isBound) {
                isBound = false;
                Activity parent = getParent();
                Context context = (parent == null ? this : parent);
                context.unbindService(mConnection);
            }
        } catch (Exception ex) {
            Log.e("BaseActivity", "Failed to unbind service", ex);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        doBindService();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(null);
    }

    @Override
    protected void onDestroy() {
        doUnbindService();
        super.onDestroy();
    }
}
