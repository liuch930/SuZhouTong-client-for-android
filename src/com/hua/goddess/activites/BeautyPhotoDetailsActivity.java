package com.hua.goddess.activites;

import java.util.ArrayList;

import uk.co.senab.photoview.PhotoView;
import android.app.ActionBar;
import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;

import com.hua.goddess.R;
import com.hua.goddess.animation.DepthPageTransformer;
import com.hua.goddess.utils.LoadingImgUtil;
import com.hua.goddess.vo.BeautyMainVo.Imgs;
import com.umeng.analytics.MobclickAgent;

public class BeautyPhotoDetailsActivity extends Activity implements
		OnPageChangeListener {

	private ArrayList<Imgs> imgs;
	private ViewPager mViewPager;
	public int position;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_pager);
		initPageView();
		if (getIntent() != null) {
			imgs = (ArrayList<Imgs>) getIntent().getSerializableExtra("imgs");
			position = getIntent().getIntExtra("position", 0);
		}
		if (imgs != null && imgs.size() > 0) {
			setActionBarStyle();
			mViewPager.setAdapter(new MyAdapter());
			mViewPager.setCurrentItem(position);
			mViewPager.setOnPageChangeListener(this);
			
			mViewPager.setPageTransformer(true, new DepthPageTransformer());
		}

	}

	protected void initPageView() {

		mViewPager = (ViewPager) findViewById(R.id.view_pager);
	}

	private void setActionBarStyle() {
		getActionBar().setTitle(imgs.get(position).getDesc());
		getActionBar().setBackgroundDrawable(
				this.getBaseContext().getResources()
						.getDrawable(R.drawable.actionbar_back));
		getActionBar().setIcon(R.drawable.ic_action);
		getActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		int titleId = Resources.getSystem().getIdentifier("action_bar_title",
				"id", "android");
		TextView textView = (TextView) findViewById(titleId);
		textView.setTextColor(0xFFdfdfdf);
		textView.setTextSize(18);
		textView.setPadding(15, 0, 0, 0);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	class MyAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return imgs.size();
		}

		@Override
		public void destroyItem(View container, int position, Object object) {
			// TODO Auto-generated method stub
			((ViewGroup) container).removeView((View) object);
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			// TODO Auto-generated method stub
			return arg0 == arg1;
		}

		@Override
		public Object instantiateItem(View container, int arg1) {
			// TODO Auto-generated method stub

			View view = getLayoutInflater().inflate(
					R.layout.beauty_item_detail, null);
			LoadingImgUtil.loadimgAnimate(imgs.get(arg1).getImageUrl(),
					(PhotoView) view.findViewById(R.id.photoview));
			((ViewPager) container).addView(view, LayoutParams.MATCH_PARENT,
					LayoutParams.MATCH_PARENT);

			return view;

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
		if (TextUtils.isEmpty(imgs.get(arg0).getDesc())) {
			getActionBar().setTitle("美女");
		} else {
			getActionBar().setTitle(imgs.get(arg0).getDesc());
		}

	}
}
