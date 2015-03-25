package com.hua.goddess.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.hua.goddess.R;

public class Drawer extends Fragment {

	private final String LIST_TEXT = "text";
	private final String LIST_IMAGEVIEW = "img";
	private SimpleAdapter lvAdapter;
	private ListView lvTitle;
	private int mTag = 0;

	public Drawer() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_drawer, container,
				false);
		lvTitle = (ListView) rootView.findViewById(R.id.drawer_list);
		return rootView;
	}

	
}
