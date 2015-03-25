package com.hua.goddess.activites;

import com.hua.goddess.R;
import com.hua.goddess.fragment.BusSiteDetailFragment;
import com.hua.goddess.vo.BusSiteVo;
import com.umeng.analytics.MobclickAgent;

import android.os.Bundle;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

public class BusSiteDetailActivity extends SwipeBackActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shots_detail);

		getSupportFragmentManager()
				.beginTransaction()
				.replace(
						R.id.container,
						new BusSiteDetailFragment((BusSiteVo) getIntent()
								.getParcelableExtra("site_detail"))).commit();
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
