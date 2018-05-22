package com.propheticapps.halloweenapplocktheme.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.propheticapps.halloweenapplocktheme.SquareImageView;
import com.squareup.picasso.Picasso;
import com.propheticapps.halloweenapplocktheme.Activity.FullScreenImage;
import com.propheticapps.halloweenapplocktheme.Activity.MyPreferenceActivity2;
import com.propheticapps.halloweenapplocktheme.R;


public class ImageAdapter2 extends BaseAdapter {

    private Context mContext;

    public ImageAdapter2() {

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
    public ImageAdapter2(Context c) {
        mContext = c;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        SquareImageView imageView;
        if (convertView == null){
            imageView = new SquareImageView(mContext);
            Picasso.with(mContext).load(mThumbIds[position])
                    .resize(200, 200).into(imageView);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    MyPreferenceActivity2 myPref = new MyPreferenceActivity2(mContext);
                    myPref.setImage(position);

                    Intent intent = new Intent(mContext,FullScreenImage.class);
                    intent.putExtra("imageID", mThumbIds[position]);
                    mContext.startActivity(intent);

                }
            });

        }
        else{
            imageView = (SquareImageView) convertView;
        }
        return imageView;
    }
    public Integer[] mThumbIds = {
            R.drawable.one,R.drawable.two,R.drawable.three,R.drawable.four,R.drawable.five,
            R.drawable.six,R.drawable.eight,R.drawable.nine,R.drawable.ten,
            R.drawable.elevan,R.drawable.thirty,R.drawable.fourty,R.drawable.fifty,R.drawable.sixty,
            R.drawable.seventy,R.drawable.eighty,R.drawable.ninty,R.drawable.twenty,R.drawable.twentyone,
            R.drawable.twentytwo,R.drawable.twentythree
    };
}
