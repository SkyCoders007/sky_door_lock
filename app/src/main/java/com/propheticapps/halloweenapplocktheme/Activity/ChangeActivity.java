package com.propheticapps.halloweenapplocktheme.Activity;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.propheticapps.halloweenapplocktheme.Service.LockScreenService;
import com.propheticapps.halloweenapplocktheme.R;
import com.propheticapps.halloweenapplocktheme.utils.Lock9View;

public class ChangeActivity extends ActionBarActivity {

	private FrameLayout enterPatternContainer, confirmPatternContainer;

	private Lock9View lockViewFirstTry, lockViewConfirm;

	private static String MY_PREFS_NAME = "PatternLock";
	private static String PATTERN_KEY;

	SharedPreferences prefs;
	Editor editor;
	TextView tvMsg;
	private AdView mAdView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		startService(new Intent(ChangeActivity.this, LockScreenService.class));

		setContentView(R.layout.activity_change);


		//Ads view
		mAdView = (AdView)findViewById(R.id.adView);
		AdRequest adRequest = new AdRequest.Builder()
				.build();
		mAdView.loadAd(adRequest);

		prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
		editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();

		tvMsg = (TextView) findViewById(R.id.tvMsg);

		tvMsg.setText(getResources().getString(R.string.draw_pattern_msg));

		enterPatternContainer = (FrameLayout) findViewById(R.id.enterPattern);
		confirmPatternContainer = (FrameLayout) findViewById(R.id.confirmPattern);

		lockViewFirstTry = (Lock9View) findViewById(R.id.lock_viewFirstTry);
		lockViewConfirm = (Lock9View) findViewById(R.id.lock_viewConfirm);

//		we can get a call back string when ever user interacts with the pattern lock view
		lockViewFirstTry.setCallBack(new Lock9View.CallBack() {

			@Override
			public void onFinish(String password) {
				PATTERN_KEY = password;
				enterPatternContainer.setVisibility(View.GONE);
				tvMsg.setText(getResources().getString(R.string.redraw_confirm_pattern_msg));
				confirmPatternContainer.setVisibility(View.VISIBLE);
			}
		});

		lockViewConfirm.setCallBack(new Lock9View.CallBack() {

			@Override
			public void onFinish(String password) {
				if (password.equals(PATTERN_KEY)) {

					Context context = getApplicationContext();
					LayoutInflater inflater = getLayoutInflater();
					View toastRoot = inflater.inflate(R.layout.layout_toast, null);
					Toast toast = new Toast(context);
					toast.setView(toastRoot);
					toast.setGravity(Gravity.BOTTOM,0,50);
					toast.setDuration(Toast.LENGTH_LONG);
					toast.show();
					tvMsg.setText(getResources().getString(R.string.thanks));
					editor.putString("Pattern", password);
					editor.commit();
            		Intent intent = new Intent(ChangeActivity.this,ActivityStart.class);
            		startActivity(intent);
					finish();
				} else {
					Context context = getApplicationContext();
					LayoutInflater inflater = getLayoutInflater();
					View toastRoot = inflater.inflate(R.layout.layout_toast2, null);
					Toast toast = new Toast(context);
					toast.setView(toastRoot);
					toast.setGravity(Gravity.BOTTOM,0,50);
					toast.setDuration(Toast.LENGTH_LONG);
					toast.show();
				}
			}
		});
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();

		Intent intent = new Intent(ChangeActivity.this,ActivityStart.class);
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
