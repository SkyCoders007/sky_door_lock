package com.propheticapps.halloweenapplocktheme.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.GridView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.propheticapps.halloweenapplocktheme.Adapter.ImageAdapter2;
import com.propheticapps.halloweenapplocktheme.R;

public class ChangeWallpaper extends Activity {

    GridView gridImage;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_theme);

        //Ads view
        mAdView = (AdView)findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .build();
        mAdView.loadAd(adRequest);

        gridImage = (GridView)findViewById(R.id.gridImage);
        gridImage.setAdapter(new ImageAdapter2(this));
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(ChangeWallpaper.this,ActivityStart.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        startActivity(intent);
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
