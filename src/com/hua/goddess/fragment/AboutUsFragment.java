package com.hua.goddess.fragment;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hua.goddess.R;

public class AboutUsFragment extends Fragment {
	private View rootView;
	private TextView not_version;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.about_fragment_layout, null);
		initView();
		return rootView;
	}

	private void initView() {
		not_version = (TextView) rootView.findViewById(R.id.not_version);
		not_version.setText("V " + getAppVersion(getActivity()).versionName);

		TextView developers_qq = (TextView) rootView
				.findViewById(R.id.developers_qq);
		developers_qq.setText("开发者QQ: 616104227");
		TextView not_sinaweibo = (TextView) rootView
				.findViewById(R.id.not_sinaweibo);
		not_sinaweibo.setMovementMethod(LinkMovementMethod.getInstance());
		not_sinaweibo.setText(Html.fromHtml("UI设计: "
				+ "<a href=\"http://weibo.com/u/5079211410\">小邪末世_</a>"));
	}

	/**
	 * 获取软件版本号
	 * 
	 * @param context
	 * @return
	 * @throws NameNotFoundException
	 */
	public PackageInfo getAppVersion(Context context) {
		PackageManager packageManager = context.getPackageManager();
		PackageInfo packageInfo = null;
		try {
			packageInfo = packageManager.getPackageInfo(
					context.getPackageName(), 0);
		} catch (NameNotFoundException e) {
			return null;
		}
		return packageInfo;
	}
}
