package com.coolstardevil.autosilenttrigger;

import android.annotation.SuppressLint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;

public class Help extends AppCompatActivity {
    ShimmerTextView copyR;
    Shimmer shimmer;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        this.setFinishOnTouchOutside(false);
        TextView helpText = findViewById(R.id.helpText);
        copyR = findViewById(R.id.copyR);
        Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/font.ttf");
        helpText.setTypeface(custom_font);
        helpText.setText("Touch on ClickMe to Set Timings " +"LongPress to clear and Click on Sun , Mon .. to enable and disable the weeks day \n"+
                "\nAuto Silent Trigger include handsome of utility which makes the user to overcome from embarrassment is such important places like Meeting ,College lectures & Office.\n" +

                "This App helps you to have a mode which makes the phone full silent in the time you are busy and comes to the normal mode once you're free. \n" +

                "It has some glamorous features likes.\n" +

                "+ Auto Silent Mode\n" +
                "+ Auto Vibration Mode\n" +
                "+ Auto Ringing Mode\n" +
                "+ Auto DND mode\n" +
                "So, from now Don't get embarrass , disturbed or angry in front of public with your annoying ringtone that may rang anytime anywhere.");

        shimmer = new Shimmer();
        shimmer.start(copyR);
    }
}
