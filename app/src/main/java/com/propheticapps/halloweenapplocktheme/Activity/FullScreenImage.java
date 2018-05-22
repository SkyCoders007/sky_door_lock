package com.propheticapps.halloweenapplocktheme.Activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.propheticapps.halloweenapplocktheme.R;
import com.propheticapps.halloweenapplocktheme.utils.TouchImageView;


public class FullScreenImage extends Activity{


    InterstitialAd mInterstitialAd;
    AdRequest adRequest;
    Button setWallpaper;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_fullscreen_image);

        //Ad setting
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getString(R.string.interstial_ads));
        adRequest = new AdRequest.Builder()
                .build();

        setWallpaper = (Button)findViewById(R.id.setWall);

        mContext = FullScreenImage.this;

        //Get image fro Image adapter
        final Bundle bdl = getIntent().getExtras();
        final int imageRes = bdl.getInt("imageID");
        final TouchImageView image = (TouchImageView) findViewById(R.id.imgDisplay);
        image.setImageResource(imageRes);

        setWallpaper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                mInterstitialAd.loadAd(adRequest);
                mInterstitialAd.setAdListener(new AdListener() {
                    public void onAdLoaded() {
                        showInterstitial();
                    }
                });

                Context context = getApplicationContext();
                LayoutInflater inflater = getLayoutInflater();
                View toastRoot = inflater.inflate(R.layout.layout_toast4, null);
                Toast toast = new Toast(context);
                toast.setView(toastRoot);
                toast.setGravity(Gravity.CENTER | Gravity.CENTER, 0, 0);
                toast.setDuration(500);
                toast.show();
                finish();
            }
        });
    }

    private void showInterstitial() {
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }
    }
}
