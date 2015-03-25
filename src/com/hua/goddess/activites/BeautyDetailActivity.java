package com.hua.goddess.activites;

import me.imid.swipebacklayout.lib.app.SwipeBackActivity;
import android.app.ActionBar;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.hua.goddess.R;
import com.hua.goddess.fragment.BeautyDetailFragment;
import com.umeng.analytics.MobclickAgent;

public class BeautyDetailActivity extends SwipeBackActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shots_detail);
		String title = getIntent().getStringExtra("GRILTAG");

		setActionBarStyle(title);

		getSupportFragmentManager().beginTransaction()
				.replace(R.id.container, new BeautyDetailFragment(title))
				.commit();
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	private void setActionBarStyle(String title) {
		this.getActionBar().setTitle(title);
		getActionBar().setBackgroundDrawable(
				this.getBaseContext().getResources()
						.getDrawable(R.drawable.actionbar_back));
		getActionBar().setIcon(R.drawable.ic_action);
		getActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		int titleId = Resources.getSystem().getIdentifier("action_bar_title",
				"id", "android");
		TextView textView = (TextView) findViewById(titleId);
//		textView.setTypeface(Typeface.createFromAsset(getAssets(),
//				"font/Wendy.ttf"));
		textView.setTextColor(0xFFdfdfdf);
		textView.setTextSize(18);
		textView.setPadding(15, 0, 0, 0);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
	}

	public void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
	}

	public void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}
}
