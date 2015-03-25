package com.hua.goddess.fragment.lunbo;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.hua.goddess.R;
import com.hua.goddess.activites.BeautyPhotoDetailsActivity;
import com.hua.goddess.utils.LoadingImgUtil;
import com.hua.goddess.vo.BeautyMainVo.Imgs;

/**
 * 轮播适配
 */
public class LunBoAdapter extends PagerAdapter {

	private Context context;
	private ArrayList<Imgs> lunboList;

	public LunBoAdapter(Context context, ArrayList<Imgs> lunboList) {
		this.lunboList = lunboList;
		this.context = context;
	}

	@Override
	public int getCount() {
		return Integer.MAX_VALUE;
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		((ViewPager) container).removeView((ImageView) object);
	}

	@Override
	public Object instantiateItem(ViewGroup container, final int position) {

		final int park = position % lunboList.size();
		final Imgs vo = lunboList.get(park);
		ImageView img = new ImageView(context);
		img.setTag(position);
		img.setScaleType(ScaleType.CENTER_CROP);
		LoadingImgUtil.loadimgAnimate(vo.getThumbLargeTnUrl(), img);
		((ViewPager) container).addView(img, 0);
		img.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(context, BeautyPhotoDetailsActivity.class);
				Bundle bundle = new Bundle();
				bundle.putInt("position", park);
				bundle.putSerializable("imgs", lunboList);
				intent.putExtras(bundle);
				context.startActivity(intent);
				((Activity) context).overridePendingTransition(
						R.anim.abc_fade_in, R.anim.abc_fade_out);
			}
		});
		return img;
	}

}
