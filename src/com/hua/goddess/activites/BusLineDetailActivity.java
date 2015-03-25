package com.hua.goddess.activites;

import me.imid.swipebacklayout.lib.app.SwipeBackActivity;
import android.os.Bundle;

import com.hua.goddess.R;
import com.hua.goddess.fragment.BusLineDetailFragment;
import com.umeng.analytics.MobclickAgent;

public class BusLineDetailActivity extends SwipeBackActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shots_detail);
		getSupportFragmentManager()
				.beginTransaction()
				.replace(
						R.id.container,
						new BusLineDetailFragment(getIntent().getStringExtra(
								"Guid"))).commit();
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
