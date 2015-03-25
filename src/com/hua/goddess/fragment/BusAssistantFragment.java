package com.hua.goddess.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hua.goddess.R;
import com.hua.goddess.animation.ZoomOutPageTransformer;
import com.hua.goddess.widget.PagerSlidingTabStrip;
import com.umeng.analytics.MobclickAgent;

public class BusAssistantFragment extends Fragment {

	private ViewPager contentPager;
	private mPagerAdapter adapter;
	private PagerSlidingTabStrip tabs;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.news_fragment, null);
		setPager(rootView);
		return rootView;
	}

	private void setPager(View rootView) {
		contentPager = (ViewPager) rootView.findViewById(R.id.content_pager);
		contentPager.setBackgroundColor(getResources().getColor(R.color.white));
		adapter = new mPagerAdapter(getActivity().getSupportFragmentManager());
		contentPager.setAdapter(adapter);
		contentPager.setOffscreenPageLimit(2);
		contentPager.setPageTransformer(true, new ZoomOutPageTransformer());
		tabs = (PagerSlidingTabStrip) rootView.findViewById(R.id.tabs);
		tabs.setTextColorResource(R.color.light_gray_text);
		tabs.setDividerColorResource(R.color.common_list_divider);
//		tabs.setUnderlineColorResource(R.color.common_list_divider);
		tabs.setIndicatorColorResource(R.color.red);
		tabs.setSelectedTextColorResource(R.color.red);
		tabs.setViewPager(contentPager);
	}

	private class mPagerAdapter extends FragmentStatePagerAdapter {

		private String Title[] = { "站点查询", "线路查询" };

		public mPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int arg0) {
			Fragment fragment = null;
			if (arg0 == 0) {
				fragment = new SiteSearchFragment();
			} else if (arg0 == 1) {
				fragment = new LineSearchFragment();
			}
			// else if (arg0 == 2) {
			// fragment = new BusTransferFragment(); 公交换乘
			// }
			return fragment;
		}

		@Override
		public int getCount() {
			return 2;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return Title[position];
		}

	}

	public void onResume() {
		super.onResume();
		MobclickAgent.onPageStart("BusAssistantFragment"); // 统计页面
	}

	public void onPause() {
		super.onPause();
		MobclickAgent.onPageEnd("BusAssistantFragment");
	}
}
