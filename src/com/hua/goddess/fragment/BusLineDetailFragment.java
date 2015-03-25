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
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.hua.goddess.R;
import com.hua.goddess.activites.BusSiteDetailActivity;
import com.hua.goddess.base.communicate.GetBusLineDetailInterface;
import com.hua.goddess.dao.BusCollectDao;
import com.hua.goddess.dao.DBHelper;
import com.hua.goddess.vo.BusLineDetailListVo;
import com.hua.goddess.vo.BusLineDetailVo;
import com.hua.goddess.vo.BusSiteVo;
import com.hua.goddess.vo.StandInfoVo;
import com.umeng.analytics.MobclickAgent;

public class BusLineDetailFragment extends Fragment {
	private View container_view;
	private LayoutInflater inflater;
	private LinearLayout container_lin;
	private Handler handler = new Handler();
	private BusLineDetailVo line_detail;
	private BusLineDetailListVo bldlv;
	private ArrayList<StandInfoVo> list;
	private PullToRefreshListView mPullRefreshListView;
	private Context context;
	private LineDetailItemHolder holder;
	private BusLineDetailAdapter bAdapter;
	private String guid;
	private boolean isCollect;
	private DBHelper mDbHelper;
	private BusCollectDao collectDao;
	private ImageView favoutite;

	public BusLineDetailFragment() {
	}

	public BusLineDetailFragment(String guid) {
		this.guid = guid;
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
		if (guid != null && !guid.equals("")) {
			new getBusLineDetailThread().start();
			isCollect = collectDao.isExistLine(guid);
		}
		return container_view;
	}

	private void initView() {
		View view = inflater.inflate(R.layout.bus_line_detail, null);
		TextView detail_title = (TextView) view
				.findViewById(R.id.bus_detail_title);
		detail_title.setText(line_detail.getLName() + "路");
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
					collectDao.deleteBusLine(guid);
				} else {
					isCollect = true;
					favoutite
							.setBackgroundResource(R.drawable.bus_ico_fav_full);
					Toast.makeText(context, R.string.add_collect,
							Toast.LENGTH_SHORT).show();
					collectDao.addBusLine(line_detail);
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
						new getBusLineDetailThread().start();
					}
				});

		bAdapter = new BusLineDetailAdapter();
		ListView actualListView = mPullRefreshListView.getRefreshableView();

		// Need to use the Actual ListView when registering for Context Menu
		registerForContextMenu(actualListView);
		actualListView.setAdapter(bAdapter);
		container_lin.removeAllViews();
		container_lin.addView(view);
	}

	class getBusLineDetailThread extends Thread {
		@Override
		public void run() {
			try {
				bldlv = GetBusLineDetailInterface.getNetData(guid);
				if (bldlv == null)
					return;
				line_detail = bldlv.getList();
				if (line_detail == null)
					return;
				list = line_detail.getStandInfo();
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

	class BusLineDetailAdapter extends BaseAdapter {

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
						R.layout.bus_line_detail_item, null);
				holder = new LineDetailItemHolder();
				holder.content_img = (ImageView) convertView
						.findViewById(R.id.bus_line_detail_content_img);
				holder.station_name = (TextView) convertView
						.findViewById(R.id.bus_line_detail_content_station_name);
				holder.intime = (TextView) convertView
						.findViewById(R.id.bus_line_detail_content_intime);
				convertView.setTag(holder);
			} else {
				holder = (LineDetailItemHolder) convertView.getTag();
			}
			final StandInfoVo vo = list.get(position);
			holder.station_name.setText(vo.getSName());
			if ("".equals(vo.getInTime())) {
				holder.content_img.setBackgroundResource(
						R.drawable.bus_ico_buspoint_on_line);
				holder.intime.setVisibility(View.GONE);
			} else {
				holder.content_img.setBackgroundResource(
						R.drawable.bus_ico_buspoint_on_new);
				holder.intime.setVisibility(View.VISIBLE);
				holder.intime.setText("进站时间  " + vo.getInTime());
			}

			convertView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if ("NODATA".equals(vo.getSCode())) {
						Toast.makeText(getActivity(), R.string.bus_no_data,
								Toast.LENGTH_SHORT).show();
					} else {
						Intent intent = new Intent(getActivity(),
								BusSiteDetailActivity.class);
						Bundle bundle = new Bundle();
						BusSiteVo site_vo = new BusSiteVo();
						site_vo.setName(vo.getSName());
						site_vo.setNoteGuid(vo.getSCode());
						bundle.putParcelable("site_detail", site_vo);
						intent.putExtras(bundle);
						getActivity().startActivity(intent);
						getActivity().overridePendingTransition(
								R.anim.abc_fade_in, R.anim.abc_fade_out);
					}
				}
			});
			return convertView;
		}

	}

	class LineDetailItemHolder {
		ImageView content_img;
		TextView station_name;
		TextView intime;
	}

	public void onResume() {
		super.onResume();
		MobclickAgent.onPageStart("BusLineDetailFragment"); // 统计页面
	}

	public void onPause() {
		super.onPause();
		MobclickAgent.onPageEnd("BusLineDetailFragment");
	}
}
