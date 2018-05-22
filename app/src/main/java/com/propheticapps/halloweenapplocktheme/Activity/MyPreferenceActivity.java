package com.propheticapps.halloweenapplocktheme.Activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceActivity;

public class MyPreferenceActivity extends PreferenceActivity{

    Context context;
    public SharedPreferences sp;
    SharedPreferences.Editor editor;
    private final static String KEY_IMAGE = "image";

    public MyPreferenceActivity(Context context)
    {
        this.context = context;
        sp = context.getApplicationContext().getSharedPreferences("Image", 0);
        editor = sp.edit();
    }
    public void setGifImage(int imgUrl)
    {
        editor.putInt(KEY_IMAGE, imgUrl);
        editor.commit();
    }
    public Integer getGifImage()
    {
        return  sp.getInt(KEY_IMAGE, 0);
    }
}

