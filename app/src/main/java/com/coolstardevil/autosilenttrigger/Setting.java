package com.coolstardevil.autosilenttrigger;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class Setting extends AppCompatActivity {
    RadioButton college, office;
    SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        this.setFinishOnTouchOutside(false);

        AdView mAdView =findViewById(R.id.adView2);
        AdRequest adRequestB = new AdRequest.Builder().build();
        mAdView.loadAd(adRequestB);

        RadioGroup radioGroup = findViewById(R.id.radioGroupSetting);
        college = findViewById(R.id.collegeRadio);
        office = findViewById(R.id.officeRadio);
        sharedPref = this.getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        Boolean collegeB = sharedPref.getBoolean("college", false);
        if (collegeB) {
            college.setChecked(true);
            office.setChecked(false);
        }
        else{
            office.setChecked(true);
            college.setChecked(false);
        }
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // find which radio button is selected
                SharedPreferences.Editor editor = sharedPref.edit();
                if (checkedId == R.id.collegeRadio) {
                    editor.putBoolean("college", college.isChecked());
                    editor.apply();
                } else {
                    editor.putBoolean("college", false);
                    editor.apply();
                }
            }
        });
    }
}
