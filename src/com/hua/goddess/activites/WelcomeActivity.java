package com.hua.goddess.activites;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import cn.jpush.android.api.JPushInterface;

import com.umeng.analytics.MobclickAgent;

public class WelcomeActivity extends Activity {

	private static final int GO_HOME = 100;
	private static final int GO_GUIDE = 200;
	boolean isFirst = false;
	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case GO_HOME:
				goHome();
				break;
			case GO_GUIDE:
				goHome();
				break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.activity_welcome);
		// 开场动画
		// AnimationSet animationSet = new AnimationSet(true);
		// Animation alphaAnimation = AnimationUtils.loadAnimation(
		// WelcomeActivity.this, R.anim.alpha);
		// Animation tAnimation = AnimationUtils.loadAnimation(
		// WelcomeActivity.this, R.anim.trans);
		// animationSet.addAnimation(alphaAnimation);
		// animationSet.addAnimation(tAnimation);
		// ImageView imageView = (ImageView) findViewById(R.id.first_image);
		// imageView.startAnimation(animationSet);
		MobclickAgent.updateOnlineConfig(this);
		init();
	}

	private void init() {
		SharedPreferences preferences = getSharedPreferences("first_pref",
				MODE_PRIVATE);
		isFirst = preferences.getBoolean("isFirst", true);
		if (!isFirst) {
			mHandler.sendEmptyMessageDelayed(GO_HOME, 2800);
		} else {
			mHandler.sendEmptyMessageDelayed(GO_GUIDE, 2800);
		}
	}

	private void goHome() {
		Intent intent = new Intent(WelcomeActivity.this, ContentActivity.class);
		startActivity(intent);
		overridePendingTransition(android.R.anim.fade_in,
				android.R.anim.fade_out);
		this.finish();
	}

	private void goGuide() {
		Intent intent = new Intent(WelcomeActivity.this, GuideActivity.class);
		startActivity(intent);
		overridePendingTransition(android.R.anim.fade_in,
				android.R.anim.fade_out);
		this.finish();
	}

	public void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
		JPushInterface.onResume(this);
	}

	public void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
		JPushInterface.onPause(this);
	}
}
