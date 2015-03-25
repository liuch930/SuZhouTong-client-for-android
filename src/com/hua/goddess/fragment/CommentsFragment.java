package com.hua.goddess.fragment;

import java.util.ArrayList;

import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.hua.goddess.R;
import com.hua.goddess.base.communicate.GetCommentsInterface;
import com.hua.goddess.utils.HtmlResolving;
import com.hua.goddess.vo.CommentVO;
import com.umeng.analytics.MobclickAgent;

public class CommentsFragment extends Fragment {
	private String newsId;
	private ArrayList<CommentVO> comment_list;
	private Handler handler = new Handler();
	private ListView listView;
	private CommentsAdapter adapter;
	private LayoutInflater inflater;
	private View rootView;
	public CommentsFragment(String newsId) {
		this.newsId = newsId;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		this.inflater = inflater;
		rootView = inflater.inflate(R.layout.fragment_comments, container,
				false);
		new CommentsDataThread().start();
		return rootView;
	}

	private void initView() {
		listView = (ListView) rootView.findViewById(R.id.comments_listview);
		((TextView)rootView.findViewById(R.id.no_comment)).setVisibility(View.GONE);
		adapter = new CommentsAdapter();
		listView.setAdapter(adapter);
	}

	class CommentsDataThread extends Thread {
		@Override
		public void run() {
			try {
				String comments = GetCommentsInterface.getNetData(newsId);
				HtmlResolving hResolving = new HtmlResolving();
				comment_list = hResolving.getCommentContent(comments);
				if (comment_list != null && comment_list.size() > 0) {
					handler.post(new Runnable() {
						@Override
						public void run() {
							initView();
						}
					});
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	public void onResume() {
	    super.onResume();
	    MobclickAgent.onPageStart("MainScreen"); //统计页面
	}
	public void onPause() {
	    super.onPause();
	    MobclickAgent.onPageEnd("MainScreen"); 
	}
	
	public class CommentsAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return comment_list.size();
		}

		@Override
		public Object getItem(int position) {
			return comment_list.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			Holder holder;
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.item_comments, null);
				holder = new Holder();
				holder.player = (TextView) convertView
						.findViewById(R.id.item_comment_player);
				holder.body = (TextView) convertView
						.findViewById(R.id.item_comment_body);
				holder.createTime = (TextView) convertView
						.findViewById(R.id.item_comment_create_time);
				convertView.setTag(holder);
			} else {
				holder = (Holder) convertView.getTag();
			}
			Typeface typeface = Typeface.createFromAsset(getActivity()
					.getAssets(), "font/Roboto-Light.ttf");
			holder.player.setText(comment_list.get(position).getName());
			holder.player.setTypeface(typeface);
			holder.body.setText(Html.fromHtml(comment_list.get(position)
					.getContent()));
			holder.body.setTypeface(typeface);
			holder.createTime.setText(comment_list.get(position).getTime());

			return convertView;
		}

		public class Holder {
			TextView player;
			TextView body;
			TextView createTime;
		}
	}

}
