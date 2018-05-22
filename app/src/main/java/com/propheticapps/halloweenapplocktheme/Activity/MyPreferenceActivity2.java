package com.propheticapps.halloweenapplocktheme.Activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceActivity;

public class MyPreferenceActivity2 extends PreferenceActivity{

    Context context;
    public SharedPreferences sp;
    SharedPreferences.Editor editor;

    private final static String KEY_IMAGEE = "wallimage";

    public MyPreferenceActivity2(Context context)
    {
        this.context = context;
        sp = context.getApplicationContext().getSharedPreferences("ImageWall", 0);
        editor = sp.edit();
    }
    public void setImage(int imgUrl)
    {
        editor.putInt(KEY_IMAGEE, imgUrl);
        editor.commit();
    }
    public Integer getImage()
    {
        return  sp.getInt(KEY_IMAGEE, 0);
    }
}

