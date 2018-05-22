package com.propheticapps.halloweenapplocktheme.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import com.propheticapps.halloweenapplocktheme.R;
import java.util.Timer;
import java.util.TimerTask;

public class HomeScreen extends Activity {


    ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homescreen);

        img = (ImageView)findViewById(R.id.imgLogo);
        int timeout = 2000; // make the activity visible for 4 seconds

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                finish();
                Intent homepage = new Intent(HomeScreen.this, ActivityStart.class);
                startActivity(homepage);
            }
        }, timeout);

        Animation anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.animation);
        img.setAnimation(anim);
        anim.start();
    }
}
