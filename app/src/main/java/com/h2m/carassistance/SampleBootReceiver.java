package com.h2m.carassistance;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;

/**
 * This BroadcastReceiver automatically (re)starts the alarm when the device is
 * rebooted. This receiver is set to be disabled (android:enabled="false") in the
 * application's manifest file. When the user sets the alarm, the receiver is enabled.
 * When the user cancels the alarm, the receiver is disabled, so that rebooting the
 * device will not trigger this receiver.
 */
// BEGIN_INCLUDE(autostart)
public class SampleBootReceiver extends BroadcastReceiver {
    SampleAlarmReceiver alarm1 = new SampleAlarmReceiver();
    SampleAlarmReceiver alarm2 = new SampleAlarmReceiver();
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED"))
        {
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.HOUR_OF_DAY, 10);
            cal.set(Calendar.MINUTE, 30);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            alarm1.setAlarm(context,cal,1);
            Calendar cal1 = Calendar.getInstance();
            cal1.set(Calendar.HOUR_OF_DAY, 19);
            cal1.set(Calendar.MINUTE, 30);
            cal1.set(Calendar.SECOND, 0);
            cal1.set(Calendar.MILLISECOND, 0);
            alarm2.setAlarm(context,cal1,2);

        }
    }
}