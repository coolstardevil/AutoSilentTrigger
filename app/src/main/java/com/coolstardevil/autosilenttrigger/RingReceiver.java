package com.coolstardevil.autosilenttrigger;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.widget.Toast;

import java.util.Calendar;


public class RingReceiver extends BroadcastReceiver {
    Boolean time_set;
    @Override
    public void onReceive(Context context, Intent intent) {
        SharedPreferences sharedPref = context.getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        time_set = sharedPref.getBoolean("one_time_set", false);
        Boolean sunD = sharedPref.getBoolean("Sun", false);
        Boolean monD = sharedPref.getBoolean("Mon", true);
        Boolean tueD = sharedPref.getBoolean("Tue", true);
        Boolean wedD = sharedPref.getBoolean("Wed", true);
        Boolean thuD = sharedPref.getBoolean("Thu", true);
        Boolean friD = sharedPref.getBoolean("Fri", true);
        Boolean satD = sharedPref.getBoolean("Sat", true);
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);

        switch (day) {
            case Calendar.SUNDAY:
                if(sunD){
                    mode(context);
                }
                break;

            case Calendar.MONDAY:
                if(monD){
                    mode(context);
                }
                break;
            case Calendar.TUESDAY:
                if(tueD){
                    mode(context);
                }
                break;
            case Calendar.WEDNESDAY:
                if(wedD){
                    mode(context);
                }
                break;
            case Calendar.THURSDAY:
                if(thuD){
                    mode(context);
                }
                break;
            case Calendar.FRIDAY:
                if(friD){
                    mode(context);
                }
                break;
            case Calendar.SATURDAY:
                if(satD){
                    mode(context);
                }
                break;

        }

    }
    private void mode(Context context){
        if (time_set) {
            Toast.makeText(context, "Auto Silent Trigger Ringing Mode!", Toast.LENGTH_SHORT).show();
            AudioManager am = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
            am.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
        }
    }
}
