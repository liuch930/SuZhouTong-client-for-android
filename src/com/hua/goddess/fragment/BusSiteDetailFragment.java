package com.hua.goddess.fragment;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.hua.goddess.R;
import com.hua.goddess.activites.BusLineDetailActivity;
import com.hua.goddess.base.communicate.GetBusSiteDetailInterface;
import com.hua.goddess.dao.BusCollectDao;
import com.hua.goddess.dao.DBHelper;
import com.hua.goddess.vo.BusSiteDetailListVo;
import com.hua.goddess.vo.BusSiteDetailVo;
import com.hua.goddess.vo.BusSiteVo;
import com.umeng.analytics.MobclickAgent;

public class BusSiteDetailFragment extends Fragment {
	private View container_view;
	private LayoutInflater inflater;
	private LinearLayout container_lin;
	private Handler handler = new Handler();
	private ArrayList<BusSiteDetailVo> list;
	private BusSiteDetailListVo bsdlv;
	private PullToRefreshListView mPullRefreshListView;
	private Context context;
	private SiteDetailItemHolder holder;
	private BusSiteDetailAdapter bAdapter;
	private BusSiteVo bus_site_vo;
	private ImageView favoutite;
	private DBHelper mDbHelper;
	private BusCollectDao collectDao;
	private boolean isCollect;
	private String noteGuid;

	public BusSiteDetailFragment() {
	}

	public BusSiteDetailFragment(BusSiteVo vo) {
		this.bus_site_vo = vo;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = getActivity();
		mDbHelper = new DBHelper(context);
		collectDao = new BusCollectDao(mDbHelper);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		this.inflater = inflater;
		container_view = inflater.inflate(R.layout.fragment_container, null);
		container_lin = (LinearLayout) container_view
				.findViewById(R.id.container);
		if (bus_site_vo != null) {
			new getBusSiteDetailThread().start();
			noteGuid = bus_site_vo.getNoteGuid();
			isCollect = collectDao.isExistSite(noteGuid);
		}
		return container_view;
	}

	private void initView() {
		View view = inflater.inflate(R.layout.bus_detail, null);
		TextView detail_title = (TextView) view
				.findViewById(R.id.bus_detail_title);
		detail_title.setText(bus_site_vo.getName());
		RelativeLayout position = (RelativeLayout) view
				.findViewById(R.id.bus_detail_title_position_layout);
		if (bus_site_vo.getRoad() != null && !"".equals(bus_site_vo.getRoad())) {
			position.setVisibility(View.VISIBLE);
			TextView position_text = (TextView) view
					.findViewById(R.id.bus_detail_position_text);
			position_text.setText("位于" + bus_site_vo.getRoad()
					+ bus_site_vo.getDirect());
		} else {
			position.setVisibility(View.GONE);
		}
		favoutite = (ImageView) view.findViewById(R.id.bus_detail_favourite);
		if (isCollect) {
			favoutite.setBackgroundResource(R.drawable.bus_ico_fav_full);
		} else {
			favoutite.setBackgroundResource(R.drawable.bus_ico_fav);
		}
		favoutite.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				favoutite.setClickable(false);
				if (isCollect) {
					isCollect = false;
					favoutite.setBackgroundResource(R.drawable.bus_ico_fav);
					Toast.makeText(context, R.string.del_collect,
							Toast.LENGTH_SHORT).show();
					collectDao.deleteBusSite(noteGuid);
				} else {
					isCollect = true;
					favoutite
							.setBackgroundResource(R.drawable.bus_ico_fav_full);
					Toast.makeText(context, R.string.add_collect,
							Toast.LENGTH_SHORT).show();
					collectDao.addBusSite(bus_site_vo);
				}
				favoutite.setClickable(true);
			}
		});
		mPullRefreshListView = (PullToRefreshListView) view
				.findViewById(R.id.bus_detail_list_layout);
		// Set a listener to be invoked when the list should be refreshed.
		mPullRefreshListView
				.setOnRefreshListener(new OnRefreshListener<ListView>() {
					@Override
					public void onRefresh(
							PullToRefreshBase<ListView> refreshView) {
						String label = DateUtils.formatDateTime(context,
								System.currentTimeMillis(),
								DateUtils.FORMAT_SHOW_TIME
										| DateUtils.FORMAT_SHOW_DATE
										| DateUtils.FORMAT_ABBREV_ALL);

						// Update the LastUpdatedLabel
						refreshView.getLoadingLayoutProxy()
								.setLastUpdatedLabel(label);

						// Do work to refresh the list here.
						new getBusSiteDetailThread().start();
					}
				});

		// Add an end-of-list listener
		mPullRefreshListView
				.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {

					@Override
					public void onLastItemVisible() {
						Toast.makeText(context, "End of List!",
								Toast.LENGTH_SHORT).show();
					}
				});

		bAdapter = new BusSiteDetailAdapter();
		ListView actualListView = mPullRefreshListView.getRefreshableView();

		// Need to use the Actual ListView when registering for Context Menu
		registerForContextMenu(actualListView);
		actualListView.setAdapter(bAdapter);
		container_lin.removeAllViews();
		container_lin.addView(view);
	}

	class getBusSiteDetailThread extends Thread {
		@Override
		public void run() {
			try {
				bsdlv = GetBusSiteDetailInterface.getNetData(noteGuid);
				if (bsdlv == null)
					return;
				list = bsdlv.getList();
				if (list != null && list.size() > 0) {
					handler.post(new Runnable() {
						@Override
						public void run() {
							if (mPullRefreshListView != null
									&& bAdapter != null) {
								bAdapter.notifyDataSetChanged();
								// Call onRefreshComplete when the list has been
								// refreshed.
								mPullRefreshListView.onRefreshComplete();
							} else {
								initView();
							}
						}
					});
				} else {

				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	class BusSiteDetailAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = LayoutInflater.from(context).inflate(
						R.layout.bus_station_detail_item, null);
				holder = new SiteDetailItemHolder();
				holder.line_name = (TextView) convertView
						.findViewById(R.id.bus_station_line_name);
				holder.text1 = (TextView) convertView
						.findViewById(R.id.bus_station_text1);
				holder.detail_count = (TextView) convertView
						.findViewById(R.id.bus_station_detail_count);
				holder.text2 = (TextView) convertView
						.findViewById(R.id.bus_station_text2);
				convertView.setTag(holder);
			} else {
				holder = (SiteDetailItemHolder) convertView.getTag();
			}

			final BusSiteDetailVo vo = list.get(position);
			holder.line_name.setText(vo.getLName());
			int distince = Integer.valueOf(vo.getDistince()) - 1;
			if (distince == 0) {
				holder.detail_count.setText(R.string.pull_in_site);
				holder.text1.setVisibility(View.INVISIBLE);
				holder.text2.setVisibility(View.GONE);
			} else if (distince < 0) {
				holder.detail_count.setText(R.string.due_out);
				holder.text1.setVisibility(View.VISIBLE);
				holder.text1.setText(R.string.next_bus);
				holder.text2.setVisibility(View.GONE);
			} else {
				holder.detail_count.setText(distince + "");
				holder.text1.setVisibility(View.VISIBLE);
				holder.text1.setText(R.string.next_distance);
				holder.text2.setVisibility(View.VISIBLE);
			}
			convertView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(getActivity(),
							BusLineDetailActivity.class);
					Bundle bundle = new Bundle();
					bundle.putString("Guid", vo.getGuid());
					intent.putExtras(bundle);
					getActivity().startActivity(intent);
					getActivity().overridePendingTransition(R.anim.abc_fade_in,
							R.anim.abc_fade_out);
				}
			});
			return convertView;
		}

	}

	class SiteDetailItemHolder {
		TextView line_name;
		TextView text1;
		TextView detail_count;
		TextView text2;
	}

	public void onResume() {
		super.onResume();
		MobclickAgent.onPageStart("BusSiteDetailFragment"); // 统计页面
	}

	public void onPause() {
		super.onPause();
		MobclickAgent.onPageEnd("BusSiteDetailFragment");
	}
}
