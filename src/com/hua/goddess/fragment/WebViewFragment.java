package com.hua.goddess.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.hua.goddess.R;
import com.hua.goddess.widget.ProgressWebView;
import com.umeng.analytics.MobclickAgent;

public class WebViewFragment extends Fragment {
	private View rootView;
	private ProgressWebView webView;
	private String url;

	public WebViewFragment() {
	}

	public WebViewFragment(String url) {
		this.url = url;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.webview, null);
		initView();
		return rootView;
	}

	private void initView() {
		webView = (ProgressWebView) rootView.findViewById(R.id.webview);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
		webView.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return true;
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				// TODO Auto-generated method stub
				super.onPageFinished(view, url);
			}

		});
		webView.loadUrl(url);
	}

	public void onResume() {
		super.onResume();
		MobclickAgent.onPageStart("WebViewFragment"); // 统计页面
	}

	public void onPause() {
		super.onPause();
		MobclickAgent.onPageEnd("WebViewFragment");
	}

	public boolean onKeyDown() {
		if (webView.canGoBack()) {
			webView.goBack();
			return true;
		}
		return false;
	}
}
