package com.hua.goddess.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import com.hua.goddess.R;
import com.hua.goddess.widget.ProgressWebView;
import com.umeng.analytics.MobclickAgent;

public class JSCPFragment extends Fragment implements OnClickListener {
	private View rootView;
	private ProgressWebView webView;
	private ImageButton mBtnBack, mBtnForward, mBtnRefresh;
	private ProgressBar mProgressBar;
	private String url = "http://m.jdd.com";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.footer_webview, null);
		initView();
		return rootView;
	}

	private void initView() {
		webView = (ProgressWebView) rootView.findViewById(R.id.webview);

		mBtnBack = (ImageButton) rootView
				.findViewById(R.id.mxx_common_activity_browser_toolbar_btn_back);
		mBtnForward = (ImageButton) rootView
				.findViewById(R.id.mxx_common_activity_browser_toolbar_btn_forward);
		mBtnRefresh = (ImageButton) rootView
				.findViewById(R.id.mxx_common_activity_browser_toolbar_btn_refresh);
		mProgressBar = (ProgressBar) rootView.findViewById(R.id.progressBar1);
		mBtnBack.setEnabled(false);
		mBtnForward.setEnabled(false);
		mBtnRefresh.setOnClickListener(this);
		mBtnForward.setOnClickListener(this);
		mBtnBack.setOnClickListener(this);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
		webView.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return true;
			}

			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				// TODO Auto-generated method stub
				super.onPageStarted(view, url, favicon);
				mProgressBar.setVisibility(View.VISIBLE);
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				// TODO Auto-generated method stub
				super.onPageFinished(view, url);
				mBtnBack.setEnabled(view.canGoBack());
				mBtnForward.setEnabled(view.canGoForward());
				mProgressBar.setVisibility(View.INVISIBLE);
			}

		});
		webView.loadUrl(url);
	}

	public void onResume() {
		super.onResume();
		webView.onResume();
		MobclickAgent.onPageStart("WebViewFragment"); // 统计页面
	}

	public void onPause() {
		super.onPause();
		webView.onPause();
		MobclickAgent.onPageEnd("WebViewFragment");
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		webView.stopLoading();
		webView.destroy();
	}

	public boolean onKeyDown() {
		if (webView.canGoBack()) {
			webView.goBack();
			return true;
		}
		return false;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.mxx_common_activity_browser_toolbar_btn_back:
			if (webView.canGoBack()) {
				webView.goBack();
			}
			break;
		case R.id.mxx_common_activity_browser_toolbar_btn_forward:
			if (webView.canGoForward()) {
				webView.goForward();
			}
			break;
		case R.id.mxx_common_activity_browser_toolbar_btn_refresh:
			webView.reload();
			break;
		default:
			break;
		}

	}
}
