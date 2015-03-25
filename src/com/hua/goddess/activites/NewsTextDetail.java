package com.hua.goddess.activites;

import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.WindowManager;

import com.hua.goddess.R;
import com.hua.goddess.fragment.CommentsFragment;
import com.hua.goddess.fragment.NewsTextDetailFragment;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.umeng.analytics.MobclickAgent;

public class NewsTextDetail extends SwipeBackActivity {

	private SwipeBackLayout mSwipeBackLayout;
	private SlidingMenu sm;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shots_detail);

		Bundle myBundle = getIntent().getExtras();
		String newsId = myBundle.getString("newsId");
		mSwipeBackLayout = getSwipeBackLayout();
		mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);

		initSlidingMenu();
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.container, new NewsTextDetailFragment(newsId,sm))
				.commit();
		getSupportFragmentManager()
				.beginTransaction()
				.replace(R.id.comments_list_container,
						new CommentsFragment(newsId)).commit();
	}

	private void initSlidingMenu() {
		WindowManager manager = getWindowManager();
		Display display = manager.getDefaultDisplay();

		sm = new SlidingMenu(NewsTextDetail.this);
		sm.setMode(SlidingMenu.RIGHT);
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		sm.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
		sm.setBehindOffset(display.getWidth() * 1 / 4);
		sm.setShadowDrawable(R.drawable.sidebar_shadow_right);
		sm.setShadowWidth(30);
		sm.setMenu(R.layout.menu_layout);

		sm.setOnOpenedListener(new SlidingMenu.OnOpenedListener() {
			@Override
			public void onOpened() {
				mSwipeBackLayout.setEnableGesture(false);
			}
		});
		sm.setOnClosedListener(new SlidingMenu.OnClosedListener() {
			@Override
			public void onClosed() {
				mSwipeBackLayout.setEnableGesture(true);
			}
		});
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
