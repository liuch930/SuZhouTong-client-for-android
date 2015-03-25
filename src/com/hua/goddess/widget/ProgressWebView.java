package com.hua.goddess.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.hua.goddess.R;

@SuppressWarnings("deprecation")
public class ProgressWebView extends WebView {

	private ProgressBar progressbar;

	public ProgressWebView(Context context, AttributeSet attrs) {
		super(context, attrs);
		progressbar = (ProgressBar) LayoutInflater.from(context).inflate(
				R.layout.progress_horizontal, null);
		progressbar.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				10, 0, 0));
		addView(progressbar);
		setWebChromeClient(new WebChromeClient());
	}

	public class WebChromeClient extends android.webkit.WebChromeClient {
		@Override
		public void onProgressChanged(WebView view, int newProgress) {
			progressbar.setProgress(newProgress);
			if (newProgress == 100) {
				progressbar.setVisibility(GONE);
			} else {
				if (progressbar.getVisibility() == GONE)
					progressbar.setVisibility(VISIBLE);
			}
			super.onProgressChanged(view, newProgress);
		}

	}

}