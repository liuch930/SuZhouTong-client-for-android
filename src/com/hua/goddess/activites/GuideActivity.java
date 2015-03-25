package com.hua.goddess.activites;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.hua.goddess.R;
import com.hua.goddess.animation.DepthPageTransformer;
import com.umeng.analytics.MobclickAgent;

public class GuideActivity extends Activity implements OnPageChangeListener {

	private TextView pageNum;
	private ViewPager vp;
	private List<View> views;
	private ViewPagerAdapter vpAdapter;
	private LayoutInflater inflater;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_guide);
		inflater = LayoutInflater.from(this);
		initViews();
		initPageNum();
	}

	private void initPageNum() {
		pageNum = (TextView) findViewById(R.id.page_num);
		Typeface typeface = Typeface.createFromAsset(getAssets(),
				"font/Roboto-Thin.ttf");
		pageNum.setTypeface(typeface);
		pageNum.setText("");
	}

	private void initViews() {
		views = new ArrayList<View>();

		views.add(inflater.inflate(R.layout.views_one, null));
		views.add(inflater.inflate(R.layout.views_two, null));
		views.add(inflater.inflate(R.layout.views_three, null));
		views.add(inflater.inflate(R.layout.views_four, null));

		vpAdapter = new ViewPagerAdapter(views, this);

		vp = (ViewPager) findViewById(R.id.viewpager);
		vp.setPageTransformer(true, new DepthPageTransformer());
		vp.setAdapter(vpAdapter);
		vp.setOnPageChangeListener(this);
	}

	public class ViewPagerAdapter extends PagerAdapter {

		private List<View> views;
		private Activity activity;

		public ViewPagerAdapter(List<View> views, Activity activity) {
			this.views = views;
			this.activity = activity;
		}

		@Override
		public void destroyItem(View arg0, int arg1, Object arg2) {
			((ViewPager) arg0).removeView(views.get(arg1));
		}

		@Override
		public int getCount() {

			if (views != null) {
				return views.size();
			}

			return 0;
		}

		@Override
		public Object instantiateItem(View arg0, int arg1) {
			((ViewPager) arg0).addView(views.get(arg1), 0);
			if (arg1 == 0) {
				AnimationSet animationSet = new AnimationSet(true);
				Animation alphaAnimation = AnimationUtils.loadAnimation(
						GuideActivity.this, R.anim.alpha);
				Animation tAnimation = AnimationUtils.loadAnimation(
						GuideActivity.this, R.anim.trans);
				animationSet.addAnimation(alphaAnimation);
				animationSet.addAnimation(tAnimation);
				ImageView imageView = (ImageView) arg0
						.findViewById(R.id.first_image);
				imageView.startAnimation(animationSet);
			}
			if (arg1 == views.size() - 1) {
				Button mStart = (Button) arg0.findViewById(R.id.mstart);

				mStart.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						setGuided();
						goHome();
					}
				});
			}
			return views.get(arg1);
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return (arg0 == arg1);
		}

		public void goHome() {
			// Intent intent = new Intent(activity, ContentActivity.class);
			// activity.startActivity(intent);
			// overridePendingTransition(android.R.anim.fade_in,
			// android.R.anim.fade_out);
			// activity.finish();
		}

		public void setGuided() {
			SharedPreferences preferences = activity.getSharedPreferences(
					"first_pref", Context.MODE_PRIVATE);
			Editor editor = preferences.edit();
			editor.putBoolean("isFirst", false);
			editor.commit();
		}
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageSelected(int arg0) {
		if (arg0 == 0) {
			pageNum.setText("");
		} else {
			pageNum.setTextColor(0xFF767676);
			pageNum.setText(arg0 + 1 + " - 4");
		}
	}

	public void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
	}

	public void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}
}
