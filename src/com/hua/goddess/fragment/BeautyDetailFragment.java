package com.hua.goddess.fragment;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Toast;

import com.hua.goddess.R;
import com.hua.goddess.activites.BeautyPhotoDetailsActivity;
import com.hua.goddess.adapter.BeautyItemAdapter;
import com.hua.goddess.base.communicate.GetBeautyInterface;
import com.hua.goddess.vo.BeautyMainVo;
import com.hua.goddess.vo.BeautyMainVo.Imgs;
import com.huewu.pla.lib.WaterFallListView;
import com.huewu.pla.lib.WaterFallListView.IXListViewListener;
import com.huewu.pla.lib.internal.PLA_AdapterView;
import com.huewu.pla.lib.internal.PLA_AdapterView.OnItemClickListener;
import com.umeng.analytics.MobclickAgent;

public class BeautyDetailFragment extends Fragment implements
		IXListViewListener, OnItemClickListener {

	private View currentView;
	private String girlTag;
	private String BEAUTY_COL = "美女";
	private BeautyMainVo beautyVo;
	private ArrayList<Imgs> imgs = new ArrayList<BeautyMainVo.Imgs>();
	private Handler handler = new Handler();
	private LinearLayout viewContainer;
	private int pn = 0; // 从那一条数据开始拿
	private View listView;
	private WaterFallListView myListview;
	private BeautyItemAdapter mAdapter;
	private int rn = 30;// 拿多少条
	private boolean isFresh = false;
	private boolean isLoadMore = false;
	public int position = 0;
	private FragmentActivity activity;

	public BeautyDetailFragment() {
	}

	public BeautyDetailFragment(String girlTag) {
		this.girlTag = girlTag;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		activity = getActivity();
		if (currentView == null) {
			currentView = LayoutInflater.from(activity).inflate(
					R.layout.fragment_progress_container, null);
			viewContainer = (LinearLayout) currentView
					.findViewById(R.id.container);

		} else {
			ViewGroup parent = (ViewGroup) currentView.getParent();
			if (parent != null) {
				parent.removeView(currentView);
			}
		}
		getData();
		return currentView;
	}

	private void initView() {
		if (listView == null || mAdapter == null) {
			listView = LayoutInflater.from(activity).inflate(
					R.layout.waterfall_listview, null);
			myListview = (WaterFallListView) listView
					.findViewById(R.id.myListview);
			myListview.setPullLoadEnable(true);
			myListview.setPullRefreshEnable(true);

			mAdapter = new BeautyItemAdapter(activity, imgs);
			myListview.setAdapter(mAdapter);
			myListview.setXListViewListener(this);
			myListview.setOnItemClickListener(this);

			viewContainer.removeAllViews();
			viewContainer.addView(listView);
		} else {
			mAdapter.updateAdapter(imgs);
			position = imgs.size();
		}

	}

	private void initNetErro() {
		// TODO
		final View loadView = LayoutInflater.from(activity).inflate(
				R.layout.loading_view, null);
		loadView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT));
		View netErroView = LayoutInflater.from(activity).inflate(
				R.layout.page_store_net_erro, null);
		Button reloadBtn = (Button) netErroView.findViewById(R.id.reload_btn);
		netErroView.findViewById(R.id.net_erro_img).setVisibility(View.VISIBLE);
		reloadBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (viewContainer != null) {
					viewContainer.removeAllViews();
					viewContainer.addView(loadView);
					getData();
				}
			}
		});
		if (viewContainer != null) {
			viewContainer.removeAllViews();
			viewContainer.addView(netErroView);
		}

	}

	private void getData() {

		new Thread() {
			@Override
			public void run() {
				try {
					beautyVo = GetBeautyInterface.getNetData(BEAUTY_COL,
							girlTag, pn, rn);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				handler.post(new Runnable() {
					@Override
					public void run() {
						if (beautyVo != null) {
							ArrayList<Imgs> img = beautyVo.getImgs();

							if (img != null && img.size() > 0) {
								img.remove(img.size() - 1);
								if (isFresh) {
									imgs.clear();
									isFresh = false;
									myListview.stopRefresh();
								}
								if (isLoadMore) {
									isLoadMore = false;
									myListview.stopLoadMore();
								}
								imgs.addAll(img);
								initView();
							} else {
								initNetErro();
							}
						} else {
							initNetErro();
						}
					}
				});
			}

		}.start();
	}

	@Override
	public void onItemClick(PLA_AdapterView<?> parent, View view, int position,
			long id) {
		if(position == 0) return;
		Intent intent = new Intent();
		intent.setClass(activity, BeautyPhotoDetailsActivity.class);
		Bundle bundle = new Bundle();
		bundle.putInt("position", position - 1);
		bundle.putSerializable("imgs", imgs);
		intent.putExtras(bundle);
		startActivity(intent);
		activity.overridePendingTransition(R.anim.abc_fade_in,
				R.anim.abc_fade_out);

	}

	@Override
	public void onRefresh() {
		isFresh = true;
		pn = 0;
		getData();
	}

	@Override
	public void onLoadMore() {
		if (beautyVo != null && beautyVo.getTotalNum() > imgs.size()) {
			isLoadMore = true;
			pn = pn + rn;
			getData();
		} else {
			Toast.makeText(activity, "没有更多妹子啦", Toast.LENGTH_SHORT).show();
		}
	}
	
	public void onResume() {
		super.onResume();
		MobclickAgent.onPageStart("BeautyDetailFragment"); // 统计页面
	}

	public void onPause() {
		super.onPause();
		MobclickAgent.onPageEnd("BeautyDetailFragment");
	}
}
