
package com.luxoft.lamps.core;

import java.util.Timer;
import java.util.TimerTask;

import android.os.Handler;

import com.luxoft.lamps.activities.AcMain;
import com.luxoft.lamps.activities.AcSplash;

public class AppRunner extends AppRunnerBase {

    private long startTimeMillis;

    
    public AppRunner(BaseActivity activity) {
        super(activity);
    }

    @Override
    protected Class<AcSplash> getSplashClass() {
        return AcSplash.class;
    }

    @SuppressWarnings({
            "unchecked", "rawtypes"
    })
    @Override
    protected Class getRootClass() {
        return AcMain.class;
    }
    
    @Override
    public void run() {
        startTimeMillis = System.currentTimeMillis();
        initService();
    }
    
    private void initService() {
        if (activity.getSession() == null || activity.getSession().getState() != SessionState.SESSION_ACTIVE) {
            final Handler handler = new Handler();
            Timer t = new Timer();
            t.schedule(new TimerTask() {
                public void run() {
                    handler.post(new Runnable() {
                        public void run() {
                            initService();
                        }
                    });
                }
            }, 200);
            return;
        } else {
            long endTimeMillis = System.currentTimeMillis();
            long diff = endTimeMillis - startTimeMillis;
            
            if (diff > SPLASH_TIME_MAX_MS || diff <=0) {
                checkAndDisplayActivity();
            } else {
                
                diff = Math.min(LApp.instance.isDebuggable() ? 1000 : SPLASH_TIME_MAX_MS, Math.abs(diff - SPLASH_TIME_MAX_MS));
                
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        checkAndDisplayActivity();
                    }
                }, diff);
            }

        }
    }
    
    private void checkAndDisplayActivity() {
        if (activity == null || activity.isFinishing())
            return;

        displayRootActivity();
    }
}
