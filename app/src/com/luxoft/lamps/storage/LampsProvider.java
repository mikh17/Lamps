
package com.luxoft.lamps.storage;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.luxoft.lamps.core.SessionService;
import com.luxoft.lamps.core.SessionState;
import com.tjeannin.provigen.InvalidContractException;
import com.tjeannin.provigen.ProviGenProvider;

public class LampsProvider extends ProviGenProvider {

    private SessionService sessionService;

    public LampsProvider() throws InvalidContractException {
        super(ILamp.class);
    }

    @Override
    public boolean onCreate() {
        doBindService();
        return super.onCreate();
    }

    private void doBindService() {
        getContext().bindService(new Intent(this.getContext(), SessionService.class), mConnection,
                Context.BIND_AUTO_CREATE);
    }

    private ServiceConnection mConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
            sessionService = ((SessionService.LocalBinder)service).getService();
            sessionService.setState(SessionState.SESSION_ACTIVE);
        }

        public void onServiceDisconnected(ComponentName className) {
            sessionService = null;
        }
    };
}
