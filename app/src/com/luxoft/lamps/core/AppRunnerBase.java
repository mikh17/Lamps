
package com.luxoft.lamps.core;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public abstract class AppRunnerBase {

    private static final String PARAM_REDIRECT_ACTIVITY = "REDIRECT_ACTIVITY";
    protected static final int SPLASH_TIME_MAX_MS = 2 * 1000;

    protected final BaseActivity activity;

    protected final boolean displayedSplashScreenOldValue;

    public AppRunnerBase(BaseActivity activity) {
        this.activity = activity;
        this.displayedSplashScreenOldValue = LApp.instance.isDisplayedSplashScreen();
    }

    public boolean init() {
        if (LApp.instance.isDisplayedSplashScreen()) {
            displayRootActivity();
            return false;
        }
        return true;
    }

    public boolean redirectIfNotInitialized() {
        return redirectIfNotInitialized(true);
    }

    protected abstract Class<?> getSplashClass();

    protected abstract Class<?> getRootClass();

    public boolean redirectIfNotInitialized(boolean returnToParentActivity) {

        if (!LApp.instance.isDisplayedSplashScreen()) {

            Intent intent = new Intent(LApp.instance, getSplashClass());

            if (returnToParentActivity) {

                Bundle extras = activity.getIntent().getExtras();

                if (extras == null)
                    extras = new Bundle();

                extras.putString(PARAM_REDIRECT_ACTIVITY, activity.getClass().getName());

                if (extras != null)
                    intent.putExtras(extras);
            }

            activity.startActivity(intent);
            activity.finish();

            return true;
        }

        return false;
    }

    protected void displayRootActivity() {

        Bundle extras = activity.getIntent().getExtras();

        if (extras == null || !extras.containsKey(PARAM_REDIRECT_ACTIVITY)) {
            activity.startActivity(new Intent(activity, getRootClass()));
        } else {
            try {
                Intent intentRedirect = new Intent(activity, Class.forName(extras
                        .getString(PARAM_REDIRECT_ACTIVITY)));
                intentRedirect.putExtras(extras);
                activity.startActivity(intentRedirect);

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        activity.finish();
    }

    public void run() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                LApp.instance.setDisplayedSplashScreen(true);
                displayRootActivity();
                activity.finish();
            }
        }, SPLASH_TIME_MAX_MS);
    }
}
