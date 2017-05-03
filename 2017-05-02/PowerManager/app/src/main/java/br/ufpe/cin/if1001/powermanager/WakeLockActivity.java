package br.ufpe.cin.if1001.powermanager;

import android.app.Activity;
import android.os.Bundle;
import android.os.PowerManager;

public class WakeLockActivity extends Activity {
    PowerManager powerManager;
    PowerManager.WakeLock wakeLock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wake_lock);
        powerManager = (PowerManager) getSystemService(POWER_SERVICE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "MyWakelockTag");
        wakeLock.acquire();

        /*
        *
        *
        * Flag Value	            CPU     Screen	Keyboard
          PARTIAL_WAKE_LOCK         On*	    Off     Off
          SCREEN_DIM_WAKE_LOCK      On	    Dim     Off
          SCREEN_BRIGHT_WAKE_LOCK   On	    Bright  Off
          FULL_WAKE_LOCK            On	    Bright  Bright
	    *
        * */


    }

    @Override
    protected void onPause() {
        super.onPause();
        wakeLock.release();
    }
}
