package com.propheticapps.halloweenapplocktheme.Activity;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.propheticapps.halloweenapplocktheme.Adapter.ImageAdapter2;
import com.propheticapps.halloweenapplocktheme.Service.LockScreenService;
import com.propheticapps.halloweenapplocktheme.R;
import com.propheticapps.halloweenapplocktheme.utils.CustomBuilder;
import com.propheticapps.halloweenapplocktheme.utils.Lock9View;
import java.text.SimpleDateFormat;

public class MainActivity extends Activity{

    private Lock9View lock9View;
    TextView datee;
    Button btnForgot;
    private static String MY_PREFS_NAME = "PatternLock";
    private static String PATTERN_KEY;
    SharedPreferences prefs;
    static int failedCount = 0;
    int LIMIT = 4; //limit for wrong pattern
    public static TextView txtMessage;

    CountDownTimer mCountDownTimer = new CountDownTimer(30 * 1000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            //this will be called every second.
            txtMessage.setText("" + (millisUntilFinished / 1000) + "\nPlease wait...");
        }

        @Override
        public void onFinish() {
            //30 seconds passed.
            txtMessage.setText("Forgot your pattern");
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        makeFullScreen();
        startService(new Intent(MainActivity.this, LockScreenService.class));
        setContentView(R.layout.activity_main);
        prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);

        //Set lockscreen wallppaper
        MyPreferenceActivity2 preff = new MyPreferenceActivity2(getApplicationContext());
        int pos = preff.getImage();
        ImageAdapter2 imageAdapter = new ImageAdapter2();
        Integer[] mThumbIds = imageAdapter.mThumbIds;
        LinearLayout llMain = (LinearLayout)findViewById(R.id.llMain);
        llMain.setBackgroundResource(mThumbIds[pos]);

        //Set date
        datee = (TextView)findViewById(R.id.datetime);
        long date = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String dateString = sdf.format(date);
        datee.setText(dateString);

        txtMessage = (TextView)findViewById(R.id.txtMessage);
        btnForgot = (Button)findViewById(R.id.btnForgot);
        Animation zoomOutAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.animation);
        btnForgot.startAnimation(zoomOutAnimation);


        lock9View = (Lock9View) findViewById(R.id.lock_9_view);

        //Change background

        lock9View.setCallBack(new Lock9View.CallBack() {

            public int counter = 0;
            public int buttCounter = 0;

            @Override
            public void onFinish(String password) {
                PATTERN_KEY = prefs.getString("Pattern", "invalid");
                    Log.i("Counter","..."+counter);
                    if (PATTERN_KEY.equals("invalid")) {
                        Dialog dialog = null;
                        ContextThemeWrapper ctw = new ContextThemeWrapper(MainActivity.this, R.style.MyTheme );
                        CustomBuilder builder = new CustomBuilder( ctw );
                        builder.setMessage("You don't have an any pattern\n\nAre you sure you want to create pattern?")
                                .setTitle("Create new pattern")
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent in = new Intent(MainActivity.this,ChangeActivity.class);
                                        startActivity(in);
                                        finish();
                                    }

                                })
                                .setNegativeButton("No",null)
                                .show();
                        Toast.makeText(MainActivity.this, "Options --> Create new Pattern", Toast.LENGTH_LONG).show();
                    } else {
                        if (password.equals(PATTERN_KEY)) {
                            Intent startMain = new Intent(Intent.ACTION_MAIN);
                            startMain.addCategory(Intent.CATEGORY_HOME);
                            startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(startMain);
                            finish();
                        } else {
                            invalidPattern();

                            Context context = getApplicationContext();
                            LayoutInflater inflater = getLayoutInflater();
                            View toastRoot = inflater.inflate(R.layout.layout_toast2, null);
                            Toast toast = new Toast(context);
                            toast.setView(toastRoot);
                            toast.setGravity(Gravity.BOTTOM | Gravity.BOTTOM,
                                    0, 0);
                            toast.show();
                        }
                    }
                }
        });

        btnForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intn = new Intent(MainActivity.this,ChangeActivity2.class);
                startActivity(intn);
                btnForgot.setVisibility(View.GONE);
            }
        });
    }
    private void invalidPattern(){

        failedCount ++;

        if (failedCount > LIMIT) {

            lock9View.setVisibility(View.GONE);
            txtMessage.setVisibility(View.VISIBLE);
            btnForgot.setVisibility(View.VISIBLE);
            mCountDownTimer.start();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    failedCount = 0;
                    lock9View.setVisibility(View.VISIBLE);
                    txtMessage.setVisibility(View.GONE);
                }
            }, 30000); // 30Sec delay
        } else {
            Log.i("Wrong....", "aa..else.....invalidPattern()");
        }
    }
    private void makeFullScreen() {

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        if(Build.VERSION.SDK_INT < 19) { //View.SYSTEM_UI_FLAG_IMMERSIVE is only on API 19+
            this.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        } else {
            this.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE);
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
    public void unlockScreen(View view) {
        android.os.Process.killProcess(android.os.Process.myPid());
    }
    @Override
    protected void onPause() {
        super.onPause();
        ActivityManager activityManager = (ActivityManager) getApplicationContext()
                .getSystemService(Context.ACTIVITY_SERVICE);
        activityManager.moveTaskToFront(getTaskId(), 0);
    }
    }