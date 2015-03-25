package com.hua.goddess.fragment;

import java.util.ArrayList;

import uk.co.senab.actionbarpulltorefresh.library.ActionBarPullToRefresh;
import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshLayout;
import uk.co.senab.actionbarpulltorefresh.library.listeners.OnRefreshListener;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.TextView;

import com.hua.goddess.R;
import com.hua.goddess.activites.NewsTextDetail;
import com.hua.goddess.base.communicate.GetNewsInterface;
import com.hua.goddess.utils.LocalDisplay;
import com.hua.goddess.vo.NewsListVo;
import com.hua.goddess.vo.NewsVo;

public class NewsListFragment extends BaseFragment implements OnRefreshListener {

	private NewsListVo newslist;
	private Handler handler = new Handler();
	private ListView news_list;
	private NewsItemHolder newsHolder;
	private Context context;
	private ArrayList<NewsVo> list;
	private NewsAdapter nAdapter;
	private int channelId; // 新闻 = 5，社区 = 27，房产 = 23,娱乐 = 21，汽车 = 24，
	private int requiredPage = 1;
	private LinearLayout viewContainer;
	private View view;
	private PullToRefreshLayout mPullToRefreshLayout;

	public NewsListFragment() {
	}

	public NewsListFragment(int channelId) {
		this.channelId = channelId;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = getActivity();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_progress_container,
				null);
		viewContainer = (LinearLayout) rootView.findViewById(R.id.container);
		LocalDisplay.init(context);
		new GetNewsDataThread().start();
		return rootView;
	}

	private void initListView() {
		if (context == null)
			return;
		view = LayoutInflater.from(context).inflate(
				R.layout.fragment_classic_header_with_list_view, null);
		news_list = (ListView) view.findViewById(R.id.news_list);

		mPullToRefreshLayout = (PullToRefreshLayout) view
				.findViewById(R.id.ptr_layout);

		ActionBarPullToRefresh.from(getActivity()).allChildrenArePullable()
				.listener(this).setup(mPullToRefreshLayout);
		nAdapter = new NewsAdapter();
		news_list.setAdapter(nAdapter);

		viewContainer.removeAllViews();
		viewContainer.addView(view);
	}

	// 获取搜索数据
	class GetNewsDataThread extends Thread {
		@Override
		public void run() {
			try {
				newslist = GetNewsInterface.getNetData(channelId, requiredPage);
				handler.post(new Runnable() {
					@Override
					public void run() {
						if (newslist != null) {
							list = newslist.getList();
							if (list != null && list.size() > 0) {
								initListView(); // 初始化页面
							} else {
								initNetErro();
							}
						} else {
							initNetErro();
						}
					}
				});
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void onRefreshStarted(View view) {
		new AsyncTask<Void, Void, Void>() {

			@Override
			protected Void doInBackground(Void... params) {
				try {
					newslist = GetNewsInterface.getNetData(channelId,
							requiredPage);
				} catch (Exception e) {
					e.printStackTrace();
				}
				return null;
			}

			@Override
			protected void onPostExecute(Void result) {
				super.onPostExecute(result);
				if (newslist != null) {
					list = newslist.getList();
					if (list != null && list.size() > 0) {
						nAdapter.notifyDataSetChanged();
					} 
				}
				mPullToRefreshLayout.setRefreshComplete();
			}
		}.execute();

	}

	private void initNetErro() {
		// TODO
		final View loadView = LayoutInflater.from(context).inflate(
				R.layout.loading_view, null);
		loadView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT));
		View netErroView = LayoutInflater.from(context).inflate(
				R.layout.page_store_net_erro, null);
		Button reloadBtn = (Button) netErroView.findViewById(R.id.reload_btn);
		netErroView.findViewById(R.id.net_erro_img).setVisibility(View.VISIBLE);
		reloadBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (viewContainer != null) {
					viewContainer.removeAllViews();
					viewContainer.addView(loadView);
					new GetNewsDataThread().start();
				}
			}
		});
		if (viewContainer != null) {
			viewContainer.removeAllViews();
			viewContainer.addView(netErroView);
		}

	}

	class NewsAdapter extends BaseAdapter {

		@Override
		public int getCount() {

			return list.size();
		}

		@Override
		public Object getItem(int position) {

			return null;
		}

		@Override
		public long getItemId(int position) {

			return 0;
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			if (convertView == null) {
				convertView = LayoutInflater.from(context).inflate(
						R.layout.news_common_item, null);
				newsHolder = new NewsItemHolder();
				newsHolder.imgNewsPic = (ImageView) convertView
						.findViewById(R.id.imgNewsPic);
				newsHolder.title = (TextView) convertView
						.findViewById(R.id.tvTitle);
				newsHolder.description = (TextView) convertView
						.findViewById(R.id.tvDescription);
				newsHolder.date_text = (TextView) convertView
						.findViewById(R.id.date_text);
				convertView.setTag(newsHolder);
			} else {
				newsHolder = (NewsItemHolder) convertView.getTag();
			}
			imageLoader.displayImage(list.get(position).getImage().getSrc(),
					newsHolder.imgNewsPic, options, animateFirstListener);
			newsHolder.title.setText(list.get(position).getTitle());
			newsHolder.date_text.setText(list.get(position).getDate());
			newsHolder.description.setText(list.get(position).getContent());
			convertView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent intent = new Intent(getActivity(),
							NewsTextDetail.class);
					Bundle bundle = new Bundle();
					bundle.putString("newsId", list.get(position).getNewsId());
					intent.putExtras(bundle);
					getActivity().startActivity(intent);
					getActivity().overridePendingTransition(R.anim.abc_fade_in,
							R.anim.abc_fade_out);

				}
			});
			return convertView;
		}
	}

	class NewsItemHolder {
		ImageView imgNewsPic;
		TextView title;
		TextView date_text;
		TextView description;
	}

}
