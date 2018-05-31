package com.coolstardevil.autosilenttrigger;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class TimeBased extends AppCompatActivity {

    private InterstitialAd interstitialAd;
    private TextView from, to, start, end, fromC, toC;
    private ToggleButton sun, mon, tue, wed, thu, fri, sat;
    private Intent intent;
    private String formTime, toTime, startTime, endTime;
    private SharedPreferences sharedPref;
    Button oneTimeCancel, set;
    RadioButton silent, vibrate;
    NotificationManager notificationManager;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.time_based);

        interstitialAd = new InterstitialAd(this);
        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequestB = new AdRequest.Builder().build();
        mAdView.loadAd(adRequestB);

        fromC = findViewById(R.id.fromC);
        toC = findViewById(R.id.toC);
        oneTimeCancel = findViewById(R.id.cancelOneTime);
        sun = findViewById(R.id.sun);
        mon = findViewById(R.id.mon);
        tue = findViewById(R.id.tue);
        wed = findViewById(R.id.wed);
        thu = findViewById(R.id.thu);
        fri = findViewById(R.id.fri);
        sat = findViewById(R.id.sat);

        interstitialAd.setAdUnitId(getResources().getString(R.string.interstitial_ad_unit_id));
        AdRequest adRequest = new AdRequest.Builder().build();
        interstitialAd.loadAd(adRequest);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
        sharedPref = this.getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        Boolean one_time_set = sharedPref.getBoolean("one_time_set", false);

        //Set College OR Office Text
        setText();

        sun.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    toggleSet(sun,true,getString(R.string.sun));
                    } else {
                    toggleSet(sun,false,getString(R.string.sun));
                }
            }
        });
        mon.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    toggleSet(mon,true,getString(R.string.mon));
                } else {
                    toggleSet(mon,false,getString(R.string.mon));
                }
            }
        });
        tue.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    toggleSet(tue,true,getString(R.string.tue));
                } else {
                    toggleSet(tue,false,getString(R.string.tue));
                }
            }
        });
        wed.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    toggleSet(wed,true,getString(R.string.wed));
                } else {
                    toggleSet(wed,false,getString(R.string.wed));
                }
            }
        });
        thu.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    toggleSet(thu,true,getString(R.string.thu));
                } else {
                    toggleSet(thu,false,getString(R.string.thu));
                }
            }
        });
        fri.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    toggleSet(fri,true,getString(R.string.fri));
                } else {
                    toggleSet(fri,false,getString(R.string.fri));
                }
            }
        });
        sat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    toggleSet(sat,true,getString(R.string.sat));
                } else {
                    toggleSet(sat,false,getString(R.string.sat));
                }
            }
        });
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        dndAccess();

        from = findViewById(R.id.oneTimeFrom);
        to = findViewById(R.id.oneTimeTo);
        start = findViewById(R.id.recessStart);
        end = findViewById(R.id.recessEnd);
        RadioGroup radioGroup = findViewById(R.id.radioGroup);
        silent = findViewById(R.id.silentRadioButton2);
        vibrate = findViewById(R.id.vibrateRadioButton);
        set = findViewById(R.id.set);
        from.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                from.setText(R.string.click_me);
                formTime = null;
                return false;
            }
        });
        to.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                to.setText(R.string.click_me);
                toTime = null;
                return false;
            }
        });
        start.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                start.setText(R.string.click_me);
                startTime = null;
                return false;
            }
        });
        end.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                end.setText(R.string.click_me);
                endTime = null;
                return false;
            }
        });
        if (one_time_set) {
            String from_preset = sharedPref.getString("from_preset", "9:30");
            String to_preset = sharedPref.getString("to_preset", "17:00");
            String start_preset = sharedPref.getString("start_preset", "12:50");
            String end_preset = sharedPref.getString("end_preset", "1:40");
            Boolean vibrate_preset = sharedPref.getBoolean("vibrate_mode", true);
            String[] from_parameters = from_preset.split(":");
            String[] to_parameters = to_preset.split(":");
            String[] start_parameters = start_preset.split(":");
            String[] end_parameters = end_preset.split(":");

            formTime = from_preset;
            toTime = to_preset;
            startTime = start_preset;
            endTime = end_preset;

            from.setText(getTime(Integer.parseInt(from_parameters[0]), Integer.parseInt(from_parameters[1])));
            to.setText(getTime(Integer.parseInt(to_parameters[0]), Integer.parseInt(to_parameters[1])));


            Boolean recessSet = sharedPref.getBoolean("recessSet", false);
            if(recessSet) {
                start.setText(getTime(Integer.parseInt(start_parameters[0]), Integer.parseInt(start_parameters[1])));
                end.setText(getTime(Integer.parseInt(end_parameters[0]), Integer.parseInt(end_parameters[1])));
            }

            set.setText("Update");
            oneTimeCancel.setVisibility(View.VISIBLE);
            if (!vibrate_preset) {
                silent.setChecked(true);
                vibrate.setChecked(false);
            }
        }
        intent = new Intent(getApplicationContext(), VibrateReceiver.class);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // find which radio button is selected
                if (checkedId == R.id.vibrateRadioButton) {
                    intent = new Intent(getApplicationContext(), VibrateReceiver.class);
                } else {
                    intent = new Intent(getApplicationContext(), ModeReceiver.class);
                }
            }
        });
    }

    public void timePicker(final View v) {
        Calendar mcurrentTime = Calendar.getInstance();
        final int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        final int minute = mcurrentTime.get(Calendar.MINUTE);

        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(TimeBased.this, new TimePickerDialog.OnTimeSetListener() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {

                String time = getTime(selectedHour, selectedMinute);
                switch (v.getId()) {

                    case R.id.oneTimeFrom:
                        from.setText(time);
                        formTime = String.format("%02d:%02d", selectedHour, selectedMinute);
                        break;

                    case R.id.oneTimeTo:
                        to.setText(time);
                        toTime = String.format("%02d:%02d", selectedHour, selectedMinute);
                        break;
                    case R.id.recessStart:
                        start.setText(time);
                        startTime = String.format("%02d:%02d", selectedHour, selectedMinute);
                        break;
                    case R.id.recessEnd:
                        end.setText(time);
                        endTime = String.format("%02d:%02d", selectedHour, selectedMinute);
                        break;
                    default:
                        break;
                }
            }
        }, hour, minute, false);
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();
    }

    public void cancelOneTime(View view) {

        AlertDialog.Builder alert = new AlertDialog.Builder(
                TimeBased.this);
        alert.setMessage("Are you sure to cancel ?");
        alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                AlarmManager alarmMgr;
                alarmMgr = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);

                PendingIntent alarmIntent;
                intent = new Intent(getApplicationContext(), VibrateReceiver.class);
                Intent ring_intent = new Intent(getApplicationContext(), RingReceiver.class);

                if (sharedPref.getBoolean("vibrate_mode", true)) {
                    alarmIntent = PendingIntent.getBroadcast(getApplicationContext(), 10, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                    vibrate.setChecked(true);
                    silent.setChecked(false);
                } else {
                    intent = new Intent(getApplicationContext(), ModeReceiver.class);
                    alarmIntent = PendingIntent.getBroadcast(getApplicationContext(), 10, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                    silent.setChecked(true);
                    vibrate.setChecked(false);
                }
                alarmMgr.cancel(alarmIntent);

                alarmIntent = PendingIntent.getBroadcast(getApplicationContext(), 11, ring_intent, PendingIntent.FLAG_UPDATE_CURRENT);
                alarmMgr.cancel(alarmIntent);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putBoolean("one_time_set", false);
                editor.apply();
                oneTimeCancel.setVisibility(View.GONE);

                from.setText(R.string.click_me);
                to.setText(R.string.click_me);
                start.setText(R.string.click_me);
                end.setText(R.string.click_me);
                set.setText(R.string.set);
                interstitialAd.show();
                Toast.makeText(getApplicationContext(), "Auto Silent Trigger cancelled successfully.", Toast.LENGTH_SHORT).show();
            }
        });
        alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                interstitialAd.show();
                dialog.dismiss();
            }
        });

        alert.show();
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)

    public void set(View view) {
        ValidationKotlin validation = new ValidationKotlin(getApplicationContext());
        Boolean validation_status = validation.checkFromTo(formTime, toTime);
        if (startTime != null || endTime != null) {
            validation_status = validation.checkStartEnd(startTime, endTime);
        }
        if (validation_status) {

            sharedPref = this.getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putBoolean("one_time_set", true);
            editor.putString("from_preset", formTime);
            editor.putString("to_preset", toTime);
            editor.putString("start_preset", startTime);
            editor.putString("end_preset", endTime);
            //editor.putString("date_preset", date.getText().toString());
            editor.putBoolean("vibrate_mode", vibrate.isChecked());
            editor.apply();


            String[] start_parameters = null, end_parameters = null, from_parameters = null, to_parameters = null;
            if (startTime != null && endTime != null) {
                start_parameters = startTime.split(":");
                end_parameters = endTime.split(":");
            }
            if (formTime != null && toTime != null) {
                from_parameters = formTime.split(":");
                to_parameters = toTime.split(":");
            }

            alarmSet(from_parameters, to_parameters, start_parameters, end_parameters);
            Toast.makeText(getApplicationContext(), "Auto Silent is Now Active.", Toast.LENGTH_SHORT).show();
            interstitialAd.show();
            // Toast.makeText(getApplicationContext(),"Thanks For Your Support\n               \uD83D\uDE0A", Toast.LENGTH_SHORT).show();
            set.setText("Update");
            oneTimeCancel.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setText();
        dndAccess();
    }

    private void alarmSet(String[] from_parameters, String[] to_parameters, String[] start_parameters, String[] end_parameters) {
        Calendar calendar = Calendar.getInstance();
        Calendar now = Calendar.getInstance();
        long alarm, alarm2, alarm3, alarm4;
        AlarmManager alarmMgr, alarmManagerTwo, alarmManagerThree, alarmManagerFour;
        alarmMgr = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        alarmManagerTwo = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        alarmManagerThree = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        alarmManagerFour = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);

        Intent ring_intent = new Intent(getApplicationContext(), RingReceiver.class);

        if (from_parameters != null && to_parameters != null) {

            PendingIntent alarmIntent = PendingIntent.getBroadcast(getApplicationContext(), 10, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(from_parameters[0]));
            calendar.set(Calendar.MINUTE, Integer.parseInt(from_parameters[1]));
            calendar.set(Calendar.SECOND, 1);
            if (calendar.getTimeInMillis() <= now.getTimeInMillis())
                alarm = calendar.getTimeInMillis() + (AlarmManager.INTERVAL_DAY + 1);
            else
                alarm = calendar.getTimeInMillis();
            alarmMgr.setInexactRepeating(AlarmManager.RTC_WAKEUP,
                    alarm, AlarmManager.INTERVAL_DAY, alarmIntent);

            PendingIntent alarmIntent2 = PendingIntent.getBroadcast(getApplicationContext(), 11, ring_intent, PendingIntent.FLAG_UPDATE_CURRENT);
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(to_parameters[0]));
            calendar.set(Calendar.MINUTE, Integer.parseInt(to_parameters[1]));
            calendar.set(Calendar.SECOND, 1);
            if (calendar.getTimeInMillis() <= now.getTimeInMillis())
                alarm2 = calendar.getTimeInMillis() + (AlarmManager.INTERVAL_DAY + 1);
            else
                alarm2 = calendar.getTimeInMillis();
            alarmManagerTwo.setInexactRepeating(AlarmManager.RTC_WAKEUP,
                    alarm2, AlarmManager.INTERVAL_DAY, alarmIntent2);
        }
        if (start_parameters != null && end_parameters != null) {
            PendingIntent alarmIntent3 = PendingIntent.getBroadcast(getApplicationContext(), 10, ring_intent, PendingIntent.FLAG_UPDATE_CURRENT);
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(start_parameters[0]));
            calendar.set(Calendar.MINUTE, Integer.parseInt(start_parameters[1]));
            calendar.set(Calendar.SECOND, 1);
            if (calendar.getTimeInMillis() <= now.getTimeInMillis())
                alarm3 = calendar.getTimeInMillis() + (AlarmManager.INTERVAL_DAY + 1);
            else
                alarm3 = calendar.getTimeInMillis();
            alarmManagerThree.setInexactRepeating(AlarmManager.RTC_WAKEUP,
                    alarm3, AlarmManager.INTERVAL_DAY, alarmIntent3);

            PendingIntent alarmIntent4 = PendingIntent.getBroadcast(getApplicationContext(), 11, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(end_parameters[0]));
            calendar.set(Calendar.MINUTE, Integer.parseInt(end_parameters[1]));
            calendar.set(Calendar.SECOND, 1);
            if (calendar.getTimeInMillis() <= now.getTimeInMillis())
                alarm4 = calendar.getTimeInMillis() + (AlarmManager.INTERVAL_DAY + 1);
            else
                alarm4 = calendar.getTimeInMillis();
            alarmManagerFour.setInexactRepeating(AlarmManager.RTC_WAKEUP,
                    alarm4, AlarmManager.INTERVAL_DAY, alarmIntent4);

            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putBoolean("recessSet", true);
            editor.apply();

        }
        else {
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putBoolean("recessSet", false);
            editor.apply();
        }
    }

    public String getTime(int hour, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        SimpleDateFormat formatter = new SimpleDateFormat("hh:mm a", Locale.ENGLISH);
        String formattedTime = formatter.format(calendar.getTime());

        return formattedTime;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //inflater to combine two XMl files
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.help) {
            startActivity(new Intent(this, Help.class));
        } else if (item.getItemId() == R.id.feedBack) {
            sendFeedback();
        } else if (item.getItemId() == R.id.setting) {
            startActivity(new Intent(this, Setting.class));
        }
        return super.onOptionsItemSelected(item);
    }


    private void setText() {
        Boolean college = sharedPref.getBoolean("college", true);
        Boolean sunD = sharedPref.getBoolean("Sun", false);
        Boolean monD = sharedPref.getBoolean("Mon", true);
        Boolean tueD = sharedPref.getBoolean("Tue", true);
        Boolean wedD = sharedPref.getBoolean("Wed", true);
        Boolean thuD = sharedPref.getBoolean("Thu", true);
        Boolean friD = sharedPref.getBoolean("Fri", true);
        Boolean satD = sharedPref.getBoolean("Sat", true);
        if (!college) {
            fromC.setText(R.string.office);
            toC.setText(R.string.officeEnd);
        } else {
            fromC.setText(R.string.college);
            toC.setText(R.string.collegeEnd);
        }
        if(sunD){
            sun.setTextColor(getResources().getColor(R.color.colorRed));
            sun.setChecked(true);
        } else {
            sun.setTextColor(getResources().getColor(R.color.colorBlack));
            sun.setChecked(false);
        }
        if(tueD){
            tue.setTextColor(getResources().getColor(R.color.colorRed));
            tue.setChecked(true);
        } else {
            tue.setTextColor(getResources().getColor(R.color.colorBlack));
            tue.setChecked(false);
        }
        if(monD){
            mon.setTextColor(getResources().getColor(R.color.colorRed));
            mon.setChecked(true);
        } else {
            mon.setTextColor(getResources().getColor(R.color.colorBlack));
            mon.setChecked(false);
        }
        if(wedD){
            wed.setTextColor(getResources().getColor(R.color.colorRed));
            wed.setChecked(true);
        } else {
            wed.setTextColor(getResources().getColor(R.color.colorBlack));
            wed.setChecked(false);
        }
        if(thuD){
            thu.setTextColor(getResources().getColor(R.color.colorRed));
            thu.setChecked(true);
        } else {
            thu.setTextColor(getResources().getColor(R.color.colorBlack));
            thu.setChecked(false);
        }
        if(friD){
            fri.setTextColor(getResources().getColor(R.color.colorRed));
            fri.setChecked(true);
        } else {
            fri.setTextColor(getResources().getColor(R.color.colorBlack));
            fri.setChecked(false);
        }
        if(satD){
            sat.setTextColor(getResources().getColor(R.color.colorRed));
            sat.setChecked(true);
        } else {
            sat.setTextColor(getResources().getColor(R.color.colorBlack));
            sat.setChecked(false);
        }
    }

    private void toggleSet(ToggleButton toggleButton, Boolean boo ,String day) {
        sharedPref = this.getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        if (boo) {
            editor.putBoolean(day, true);
            toggleButton.setTextColor(getResources().getColor(R.color.colorRed));
        } else {
            editor.putBoolean(day, false);
            toggleButton.setTextColor(getResources().getColor(R.color.colorBlack));
        }
        editor.apply();
    }

    private void sendFeedback() {
        final Intent _Intent = new Intent(android.content.Intent.ACTION_SEND);
        _Intent.setType("text/html");
        _Intent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{getString(R.string.mail_feedback_email)});
        _Intent.putExtra(android.content.Intent.EXTRA_SUBJECT, getString(R.string.mail_feedback_subject));
        _Intent.putExtra(android.content.Intent.EXTRA_TEXT, getString(R.string.mail_feedback_message));
        startActivity(Intent.createChooser(_Intent, getString(R.string.title_send_feedback)));
    }
    private void dndAccess(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && !notificationManager.isNotificationPolicyAccessGranted()) {
            Toast.makeText(TimeBased.this, R.string.DNDaccess, Toast.LENGTH_LONG).show();
            Intent intent = new Intent(
                    android.provider.Settings
                            .ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);
            startActivity(intent);

        }
    }
}
