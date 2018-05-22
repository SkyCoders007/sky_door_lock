package com.propheticapps.halloweenapplocktheme.Adapter;


import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.propheticapps.halloweenapplocktheme.SquareImageView;
import com.squareup.picasso.Picasso;
import com.propheticapps.halloweenapplocktheme.Activity.ActivityStart;
import com.propheticapps.halloweenapplocktheme.Activity.MyPreferenceActivity;
import com.propheticapps.halloweenapplocktheme.R;


public class ImageAdapter extends BaseAdapter {

    InterstitialAd mInterstitialAd;
    AdRequest adRequest;
    public Context mContext;

    public ImageAdapter() {

    }

    public int getCount() {
        return mThumbIds.length;
    }
    public Object getItem(int position) {
        return mThumbIds[position];
    }
    public long getItemId(int position) {
        return 0;
    }
    public ImageAdapter(Context c) {
        mContext = c;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        SquareImageView imageView;
        //Ad setting
        mInterstitialAd = new InterstitialAd(mContext);
        mInterstitialAd.setAdUnitId(mContext.getString(R.string.interstial_ads));
        adRequest = new AdRequest.Builder()
                .build();
        if (convertView == null){
            imageView = new SquareImageView(mContext);
            Picasso.with(mContext).load(mThumbIds[position])
                    .resize(100,100).into(imageView);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    MyPreferenceActivity myPref = new MyPreferenceActivity(mContext);
                    myPref.setGifImage(position);

                    Intent intent = new Intent(mContext,ActivityStart.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("imageID", mThumbIds[position]);
                    LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View toastRoot = inflater.inflate(R.layout.layout_toast5, null);
                    Toast toast = new Toast(mContext);
                    toast.setView(toastRoot);
                    toast.setGravity(Gravity.BOTTOM,0,50);
                    toast.show();

                    mInterstitialAd.loadAd(adRequest);
                    mInterstitialAd.setAdListener(new AdListener() {
                        public void onAdLoaded() {
                            showInterstitial();
                        }
                    });
                    mContext.startActivity(intent);

                }
            });
        }
        else{
            imageView = (SquareImageView) convertView;
        }
        return imageView;
    }

    private void showInterstitial() {

        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }
    }

    public Integer[] mThumbIds = {
            R.mipmap.one,R.mipmap.two,R.mipmap.three,R.mipmap.four,R.mipmap.five,
            R.mipmap.six,R.mipmap.sevenn,R.mipmap.eight,R.mipmap.nine,R.mipmap.ten,
            R.mipmap.elevan,R.mipmap.twel,R.mipmap.thirty,R.mipmap.ppn,R.mipmap.ppo,
            R.mipmap.fourty,R.mipmap.fifty,R.mipmap.sixty,R.mipmap.seventy,R.mipmap.eighty
    };
}
