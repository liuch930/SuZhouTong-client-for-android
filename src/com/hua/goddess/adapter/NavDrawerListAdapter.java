package com.hua.goddess.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hua.goddess.R;
import com.hua.goddess.vo.NavDrawerItem;

public class NavDrawerListAdapter extends BaseAdapter {

	private Context context;
	private ArrayList<NavDrawerItem> navDrawerItems;
	private String switcher;
	
	public NavDrawerListAdapter(Context context,
			ArrayList<NavDrawerItem> navDrawerItems, String switcher) {
		this.context = context;
		this.navDrawerItems = navDrawerItems;
		this.switcher = switcher;
	}

	@Override
	public int getCount() {
		return navDrawerItems.size();
	}

	@Override
	public Object getItem(int position) {
		return navDrawerItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			LayoutInflater mInflater = (LayoutInflater) context
					.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
			convertView = mInflater.inflate(R.layout.behind_list_show, null);
		}

		if((position == 0 || position == 3) && "no".equals(switcher) ) {
			convertView.setVisibility(View.GONE);
		} else {
			convertView.setVisibility(View.VISIBLE);
		}
		
		ImageView imgIcon = (ImageView) convertView
				.findViewById(R.id.imageview_behind_icon);
		TextView txtTitle = (TextView) convertView
				.findViewById(R.id.textview_behind_title);

		imgIcon.setImageResource(navDrawerItems.get(position).getIcon());
		txtTitle.setText(navDrawerItems.get(position).getTitle());

		return convertView;
	}

}
