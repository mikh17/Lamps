
package com.luxoft.lamps.activities;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;

import com.luxoft.lamps.R;
import com.luxoft.lamps.core.BaseActivity;
import com.luxoft.lamps.core.DeveloperLog;
import com.luxoft.lamps.core.SessionService;
import com.luxoft.lamps.storage.ILamp;

public class AcMain extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_main);
        
        ContentValues cv = new ContentValues();
        cv.put(ILamp.ID_COLUMN, 1);
        cv.put(ILamp.NAME_COLUMN, "SUPER LAMP!!");
        getContentResolver().insert(ILamp.CONTENT_URI, cv);
        
        
        Cursor c = getContentResolver().query(ILamp.CONTENT_URI, null, "", null, "");
        
        boolean isExist = c.moveToFirst();  
        if(isExist) {
            String name = c.getString(1);
            DeveloperLog.d("READ CURSOR: " + name);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.ac_main, menu);
        return true;
    }
    
    @Override
    protected void onSessionConnected(SessionService session) {
        DeveloperLog.d("CONNECTED!!!!");
    }

}
