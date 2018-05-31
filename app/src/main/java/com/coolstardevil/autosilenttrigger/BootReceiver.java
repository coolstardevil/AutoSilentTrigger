package com.coolstardevil.autosilenttrigger;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.Calendar;


public class BootReceiver extends BroadcastReceiver {
    Intent intentMod;

    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    @Override
    public void onReceive(final Context context, Intent intent) {
        SharedPreferences sharedPref = context.getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        Boolean one_time_set = sharedPref.getBoolean("one_time_set", false);
        intentMod = new Intent(context, VibrateReceiver.class);
        if (one_time_set) {
            String from_preset = sharedPref.getString("from_preset", "10:20");
            String to_preset = sharedPref.getString("to_preset", "10:30");
            String start_preset = sharedPref.getString("start_preset", "10:20");
            String end_preset = sharedPref.getString("end_preset", "10:30");

            String[] from_parameters = from_preset.split(":");
            String[] to_parameters = to_preset.split(":");
            String[] start_parameters = start_preset.split(":");
            String[] end_parameters = end_preset.split(":");
            Boolean vibrate_preset = sharedPref.getBoolean("vibrate_mode", true);
            if (!vibrate_preset) {
               intentMod = new Intent(context,ModeReceiver.class);
            }

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
                        alarmSet(context,from_parameters,to_parameters,start_parameters,end_parameters);
                    }
                    break;

                case Calendar.MONDAY:
                    if(monD){
                        alarmSet(context,from_parameters,to_parameters,start_parameters,end_parameters);
                    }
                    break;
                case Calendar.TUESDAY:
                    if(tueD){
                        alarmSet(context,from_parameters,to_parameters,start_parameters,end_parameters);
                    }
                    break;
                case Calendar.WEDNESDAY:
                    if(wedD){
                        alarmSet(context,from_parameters,to_parameters,start_parameters,end_parameters);
                    }
                    break;
                case Calendar.THURSDAY:
                    if(thuD){
                        alarmSet(context,from_parameters,to_parameters,start_parameters,end_parameters);
                    }
                    break;
                case Calendar.FRIDAY:
                    if(friD){
                        alarmSet(context,from_parameters,to_parameters,start_parameters,end_parameters);
                    }
                    break;
                case Calendar.SATURDAY:
                    if(satD){
                        alarmSet(context,from_parameters,to_parameters,start_parameters,end_parameters);
                    }
                    break;

            }
        }
    }
    private void alarmSet(Context context ,String[] from_parameters,String[] to_parameters,String[]start_parameters,String[]end_parameters){
        Calendar calendar = Calendar.getInstance();
        Calendar now = Calendar.getInstance();
        long alarm,alarm2,alarm3,alarm4;
        AlarmManager alarmMgr, alarmManagerTwo, alarmManagerThree, alarmManagerFour;
        alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManagerTwo = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManagerThree = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManagerFour = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Intent ring_intent = new Intent(context, RingReceiver.class);

        PendingIntent alarmIntent = PendingIntent.getBroadcast(context, 10, intentMod, PendingIntent.FLAG_UPDATE_CURRENT);
        calendar.setTimeInMillis(System.currentTimeMillis());
        //calendar.set(year, month, day);
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(from_parameters[0]));
        calendar.set(Calendar.MINUTE, Integer.parseInt(from_parameters[1]));
        calendar.set(Calendar.SECOND, 1);
        if(calendar.getTimeInMillis()<=now.getTimeInMillis())
            alarm=calendar.getTimeInMillis()+(AlarmManager.INTERVAL_DAY+1);
        else
            alarm = calendar.getTimeInMillis();
        alarmMgr.setInexactRepeating(AlarmManager.RTC_WAKEUP,
                alarm, AlarmManager.INTERVAL_DAY, alarmIntent);

        PendingIntent alarmIntent2 = PendingIntent.getBroadcast(context, 11, ring_intent, PendingIntent.FLAG_UPDATE_CURRENT);
        calendar.setTimeInMillis(System.currentTimeMillis());
        //calendar.set(year, month, day);
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(to_parameters[0]));
        calendar.set(Calendar.MINUTE, Integer.parseInt(to_parameters[1]));
        calendar.set(Calendar.SECOND, 1);
        if(calendar.getTimeInMillis()<=now.getTimeInMillis())
            alarm2=calendar.getTimeInMillis()+(AlarmManager.INTERVAL_DAY+1);
        else
            alarm2 = calendar.getTimeInMillis();
        alarmManagerTwo.setInexactRepeating(AlarmManager.RTC_WAKEUP,
                alarm2, AlarmManager.INTERVAL_DAY, alarmIntent2);

        PendingIntent alarmIntent3 = PendingIntent.getBroadcast(context, 10, ring_intent, PendingIntent.FLAG_UPDATE_CURRENT);
        calendar.setTimeInMillis(System.currentTimeMillis());
        //calendar.set(year, month, day);
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(start_parameters[0]));
        calendar.set(Calendar.MINUTE, Integer.parseInt(start_parameters[1]));
        calendar.set(Calendar.SECOND, 1);
        if(calendar.getTimeInMillis()<=now.getTimeInMillis())
            alarm3=calendar.getTimeInMillis()+(AlarmManager.INTERVAL_DAY+1);
        else
            alarm3 = calendar.getTimeInMillis();
        alarmManagerThree.setInexactRepeating(AlarmManager.RTC_WAKEUP,
                alarm3, AlarmManager.INTERVAL_DAY, alarmIntent3);

        PendingIntent alarmIntent4 = PendingIntent.getBroadcast(context, 11, intentMod, PendingIntent.FLAG_UPDATE_CURRENT);
        calendar.setTimeInMillis(System.currentTimeMillis());
        //calendar.set(year, month, day);
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(end_parameters[0]));
        calendar.set(Calendar.MINUTE, Integer.parseInt(end_parameters[1]));
        calendar.set(Calendar.SECOND, 1);
        if(calendar.getTimeInMillis()<=now.getTimeInMillis())
            alarm4=calendar.getTimeInMillis()+(AlarmManager.INTERVAL_DAY+1);
        else
            alarm4 = calendar.getTimeInMillis();
        alarmManagerFour.setInexactRepeating(AlarmManager.RTC_WAKEUP,
                alarm4, AlarmManager.INTERVAL_DAY, alarmIntent4);
    }
}