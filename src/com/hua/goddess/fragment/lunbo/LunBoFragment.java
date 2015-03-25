package com.hua.goddess.fragment.lunbo;

import java.util.ArrayList;
import java.util.Random;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

import com.hua.goddess.R;
import com.hua.goddess.base.communicate.GetBeautyInterface;
import com.hua.goddess.vo.BeautyMainVo;
import com.hua.goddess.vo.BeautyMainVo.Imgs;

/**
 * 轮播界面
 */
public class LunBoFragment extends Fragment {

	private Handler handler = new Handler();
	private LinearLayout lunboContainer;
	private MyViewPager viewpager;
	private BeautyMainVo lunboVo;
	private ArrayList<Imgs> imgs;
	private boolean ifStartLunbo = false;
	private View lunboView;
	private ViewPagerFocusView focusView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = LayoutInflater.from(getActivity()).inflate(
				R.layout.fragment_progress_container, null);
		lunboContainer = (LinearLayout) view.findViewById(R.id.container);

		DisplayMetrics metrics = new DisplayMetrics();
		getActivity().getWindowManager().getDefaultDisplay()
				.getMetrics(metrics);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				metrics.widthPixels, metrics.widthPixels * 7 / 16); // 轮播图片的尺寸宽高比例16:5
		lunboContainer.setLayoutParams(params);

		getLunBoData();
		return view;
	}

	public void pauseLunbo() {
		if (viewpager != null && ifStartLunbo) {
			viewpager.stopTimer();
			ifStartLunbo = false;
		}
	}

	public void startLunbo() {
		if (viewpager != null && !ifStartLunbo) {
			viewpager.startTimer();
			ifStartLunbo = true;
		}
	}

	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		pauseLunbo();
		super.onDestroyView();

	}

	// 获取轮播数据
	private void getLunBoData() {

		if (lunboVo == null) {

			new Thread() {

				@Override
				public void run() {
					try {
						lunboVo = GetBeautyInterface.getNetData("美女", "全部",
								getRandom(), 4);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					handler.post(new Runnable() {

						@Override
						public void run() {
							if (lunboVo != null) {
								imgs = lunboVo.getImgs();
								if (imgs != null && imgs.size() > 0) {
									imgs.remove(imgs.size() - 1);
									initLunBoView();
								} else {
									initLunBoNetErro();
								}
							} else {
								initLunBoNetErro();
							}
						}
					});
				}

			}.start();

		} else {
			initLunBoView();
		}
	}

	private int getRandom() {
		Random random = new Random();
		return random.nextInt(60);
	}

	private void initLunBoView() {
		// TODO Auto-generated method stub
		if (lunboView == null) {
			lunboView = LayoutInflater.from(getActivity()).inflate(
					R.layout.page_store_lunbo, null);
			viewpager = (MyViewPager) lunboView.findViewById(R.id.lunbo);
			focusView = (ViewPagerFocusView) lunboView
					.findViewById(R.id.viewpger_focusview);
			focusView.getBackground().setAlpha(100);
			LunBoAdapter lunboAdapter = new LunBoAdapter(getActivity(), imgs);
			viewpager.setAdapter(lunboAdapter);
			viewpager.setOnPageChangeListener(new GuidePageChangeListener());
			// 总的点数
			focusView.setCount(imgs.size());
			int gbs = 1000 / imgs.size();
			int totalMax = gbs * imgs.size();
			viewpager.setCurrentItem(totalMax); // 初始位置在靠近1000的整个整除的数字

			// 当前位置
			int currentIndex = viewpager.getCurrentItem() % imgs.size();
			focusView.setCurrentIndex(currentIndex);
			// 描述文字
			focusView.setTitle(imgs.get(currentIndex).getDesc().trim());
			startLunbo();
			lunboContainer.removeAllViews();
			lunboContainer.addView(lunboView);
		}
	}

	// 显示轮播网络错误
	private void initLunBoNetErro() {
		// TODO
		final View loadView = LayoutInflater.from(getActivity()).inflate(
				R.layout.loading_view, null);
		loadView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT));
		View netErroView = LayoutInflater.from(getActivity()).inflate(
				R.layout.page_store_net_erro, null);
		Button reloadBtn = (Button) netErroView.findViewById(R.id.reload_btn);
		reloadBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (lunboContainer != null) {
					lunboContainer.removeAllViews();
					lunboContainer.addView(loadView);
					getLunBoData();
				}
			}
		});
		if (lunboContainer != null) {
			lunboContainer.removeAllViews();
			lunboContainer.addView(netErroView);
		}

	}

	// 滑动监听
	private class GuidePageChangeListener implements OnPageChangeListener {

		@Override
		public void onPageScrollStateChanged(int arg0) {

		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		@Override
		public void onPageSelected(final int arg0) {
			int currentIndex = arg0 % imgs.size();
			focusView.setTitle(imgs.get(currentIndex).getDesc().trim());
			focusView.setCurrentIndex(currentIndex);
		}
	}

}
