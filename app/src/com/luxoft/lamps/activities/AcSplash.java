
package com.luxoft.lamps.activities;

import android.os.Bundle;
import android.widget.TextView;

import com.luxoft.lamps.R;
import com.luxoft.lamps.core.AppRunner;
import com.luxoft.lamps.core.BaseActivity;
import com.luxoft.lamps.core.LApp;

public class AcSplash extends BaseActivity {

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        AppRunner appRunner = new AppRunner(this);

        if (!appRunner.init()) {
            this.finish();
            return;
        }

        setContentView(R.layout.ac_splash);

        TextView version = (TextView)findViewById(R.id.version);
        version.setText(String.format(getResources().getString(R.string.app_version),
                LApp.instance.getPackageInfo().versionName));

        appRunner.run();
    }    
}
