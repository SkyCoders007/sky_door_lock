package com.propheticapps.halloweenapplocktheme.Activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.propheticapps.halloweenapplocktheme.Adapter.ImageAdapter;
import com.propheticapps.halloweenapplocktheme.Service.LockScreenService;
import com.propheticapps.halloweenapplocktheme.R;
import com.propheticapps.halloweenapplocktheme.utils.CustomBuilder;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


public class ActivityStart extends Activity {

    private AdView mAdView;
    Button btnCreate,btnChange,close,btnShare,btnRate,btnWall;
    MyPreferenceActivity myPref;
    int position;
    ImageAdapter img = new ImageAdapter();
    Integer[] mThumb = img.mThumbIds;
    public LinearLayout ln;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startService(new Intent(ActivityStart.this, LockScreenService.class));
        setContentView(R.layout.activity_start);

        //Ads view
        mAdView = (AdView)findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .build();
        mAdView.loadAd(adRequest);
        btnCreate = (Button)findViewById(R.id.btnCreate);
        btnChange = (Button)findViewById(R.id.btnChange);
        close = (Button)findViewById(R.id.close);
        btnShare = (Button)findViewById(R.id.btnShare);
        btnRate = (Button)findViewById(R.id.btnRate);
        btnWall = (Button)findViewById(R.id.btnChangeWall);

        myPref = new MyPreferenceActivity(getApplicationContext());

        position = myPref.getGifImage();

        ln =(LinearLayout)findViewById(R.id.lan);


        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homepage = new Intent(ActivityStart.this, ChangeActivity.class);
                homepage.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                homepage.putExtra("imageID", mThumb[position]);
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                startActivity(homepage);
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = null;
                ContextThemeWrapper ctw = new ContextThemeWrapper(ActivityStart.this, R.style.MyTheme );

                CustomBuilder builder = new CustomBuilder( ctw );
                builder.setMessage("Are you sure you want to close?")
                        .setTitle("Halloween-Applock theme")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }

                        })
                        .setNegativeButton("No",null)
                        .show();
            }
        });

        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String myText = "Halloween Applock theme\nDownload and Set Halloween Applock.";
                Bitmap icon = BitmapFactory.decodeResource(getResources(),
                        R.mipmap.applock_256);
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("image/jpg");
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                icon.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                File f = new File(Environment.getExternalStorageDirectory()
                        + File.separator + "temporary_file.jpg");
                try {
                    f.createNewFile();
                    FileOutputStream fo = new FileOutputStream(f);
                    fo.write(bytes.toByteArray());
                }catch (IOException e) {
                    e.printStackTrace();
                }
                String extraText = myText + "\n\nhttps://play.google.com/store/apps/details?id=com.propheticapps.halloweenapplocktheme";
                share.putExtra(Intent.EXTRA_TEXT,extraText);
                share.putExtra(Intent.EXTRA_STREAM,Uri.fromFile(f));
                startActivity(Intent.createChooser(share, "Share with friends"));
            }
        });
        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent in = new Intent(ActivityStart.this,ChangeTheme.class);
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                startActivity(in);
            }
        });
        btnRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(android.content.Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.propheticapps.halloweenapplocktheme"));
                startActivity(intent);
            }
        });
        btnWall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intentChange = new Intent(ActivityStart.this,ChangeWallpaper.class);
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                startActivity(intentChange);
            }
        });
    }
    @Override
    public void onBackPressed() {

        Dialog dialog = null;
        ContextThemeWrapper ctw = new ContextThemeWrapper( this, R.style.MyTheme );

        CustomBuilder builder = new CustomBuilder( ctw );
        builder.setMessage("Are you sure you want to exit?")
                .setTitle("Halloween-Applock theme")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }

                })
                .setNegativeButton("No",null)
                .show();
    }
    @Override
    public void onPause() {
        if (mAdView != null) {
            mAdView.pause();
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mAdView != null) {
            mAdView.resume();
        }
    }

    @Override
    public void onDestroy() {
        if (mAdView != null) {
            mAdView.destroy();
        }
        super.onDestroy();
    }
}
