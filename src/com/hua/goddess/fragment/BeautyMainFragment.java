package com.hua.goddess.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.hua.goddess.R;
import com.hua.goddess.activites.BeautyDetailActivity;
import com.umeng.analytics.MobclickAgent;

public class BeautyMainFragment extends Fragment implements OnClickListener {

	private static View currentView = null;

	// 小清新
	private LinearLayout item_xiaoqingxin, item_tiansuchun, item_qingchun,
			item_xiaohua, item_keai, item_luoli, item_weimei, item_suyan;
	// 性感
	private LinearLayout item_xingan, item_youhuo, item_chuangtui, item_bijini,
			item_chemo, item_zuqiubaobei;
	// 高雅
	private LinearLayout item_gaoyaoyoufan, item_xiezhen, item_qizhi,
			item_shishang, item_changfa, item_duanfa;
	// others
	private LinearLayout item_gudianmeinv, item_wangluomeinv, item_feizhuliu,
			item_quanbu;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		if (currentView != null) {
			ViewGroup parent = (ViewGroup) currentView.getParent();
			if (parent != null)
				parent.removeView(currentView);
		}
		try {
			currentView = inflater.inflate(
					R.layout.fragment_beauty_main_layout, container, false);
		} catch (InflateException e) {
			/* map is already there, just return view as it is */
			e.printStackTrace();
		}
		// initData();
		return currentView;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		initView();
		super.onViewCreated(view, savedInstanceState);
	}

	private void initView() {
		// item 1
		item_xiaoqingxin = (LinearLayout) currentView
				.findViewById(R.id.item_xiaoqingxin);
		item_xiaoqingxin.setOnClickListener(this);
		item_tiansuchun = (LinearLayout) currentView
				.findViewById(R.id.item_tiansuchun);
		item_tiansuchun.setOnClickListener(this);
		item_qingchun = (LinearLayout) currentView
				.findViewById(R.id.item_qingchun);
		item_qingchun.setOnClickListener(this);
		item_xiaohua = (LinearLayout) currentView
				.findViewById(R.id.item_xiaohua);
		item_xiaohua.setOnClickListener(this);
		item_keai = (LinearLayout) currentView.findViewById(R.id.item_keai);
		item_keai.setOnClickListener(this);
		item_luoli = (LinearLayout) currentView.findViewById(R.id.item_luoli);
		item_luoli.setOnClickListener(this);
		item_weimei = (LinearLayout) currentView.findViewById(R.id.item_weimei);
		item_weimei.setOnClickListener(this);
		item_suyan = (LinearLayout) currentView.findViewById(R.id.item_suyan);
		item_suyan.setOnClickListener(this);
		// item 2
		item_xingan = (LinearLayout) currentView.findViewById(R.id.item_xingan);
		item_xingan.setOnClickListener(this);
		item_youhuo = (LinearLayout) currentView.findViewById(R.id.item_youhuo);
		item_youhuo.setOnClickListener(this);
		item_chuangtui = (LinearLayout) currentView
				.findViewById(R.id.item_chuangtui);
		item_chuangtui.setOnClickListener(this);
		item_bijini = (LinearLayout) currentView.findViewById(R.id.item_bijini);
		item_bijini.setOnClickListener(this);
		item_chemo = (LinearLayout) currentView.findViewById(R.id.item_chemo);
		item_chemo.setOnClickListener(this);
		item_zuqiubaobei = (LinearLayout) currentView
				.findViewById(R.id.item_zuqiubaobei);
		item_zuqiubaobei.setOnClickListener(this);
		// item 3
		item_gaoyaoyoufan = (LinearLayout) currentView
				.findViewById(R.id.item_gaoyaoyoufan);
		item_gaoyaoyoufan.setOnClickListener(this);
		item_xiezhen = (LinearLayout) currentView
				.findViewById(R.id.item_xiezhen);
		item_xiezhen.setOnClickListener(this);
		item_qizhi = (LinearLayout) currentView.findViewById(R.id.item_qizhi);
		item_qizhi.setOnClickListener(this);
		item_shishang = (LinearLayout) currentView
				.findViewById(R.id.item_shishang);
		item_shishang.setOnClickListener(this);
		item_changfa = (LinearLayout) currentView
				.findViewById(R.id.item_changfa);
		item_duanfa = (LinearLayout) currentView.findViewById(R.id.item_duanfa);
		item_changfa.setOnClickListener(this);
		item_duanfa.setOnClickListener(this);
		// others
		item_gudianmeinv = (LinearLayout) currentView
				.findViewById(R.id.item_gudianmeinv);
		item_gudianmeinv.setOnClickListener(this);
		item_wangluomeinv = (LinearLayout) currentView
				.findViewById(R.id.item_wangluomeinv);
		item_wangluomeinv.setOnClickListener(this);
		item_feizhuliu = (LinearLayout) currentView
				.findViewById(R.id.item_feizhuliu);
		item_feizhuliu.setOnClickListener(this);
		item_quanbu = (LinearLayout) currentView.findViewById(R.id.item_quanbu);
		item_quanbu.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		String[] tags = {"小清新","甜素纯","清纯","校花","可爱","嫩萝莉","唯美","素颜","性感美女","诱惑",
				"长腿","比基尼","车模","足球宝贝","高雅大气很有范","写真","气质", "时尚","长发","短发",
				"古典美女","网络美女","非主流","全部"};
		String grilTag = "";
		switch (v.getId()) {
		case R.id.item_xiaoqingxin:
			grilTag = tags[0];
			break;
		case R.id.item_tiansuchun:
			grilTag = tags[1];
			break;
		case R.id.item_qingchun:
			grilTag = tags[2];
			break;
		case R.id.item_xiaohua:
			grilTag = tags[3];
			break;
		case R.id.item_keai:
			grilTag = tags[4];
			break;
		case R.id.item_luoli:
			grilTag = tags[5];
			break;
		case R.id.item_weimei:
			grilTag = tags[6];
			break;
		case R.id.item_suyan:
			grilTag = tags[7];
			break;
		case R.id.item_gaoyaoyoufan:
			grilTag = tags[8];
			break;
		case R.id.item_xiezhen:
			grilTag = tags[9];
			break;
		case R.id.item_chuangtui:
			grilTag = tags[10];
			break;
		case R.id.item_bijini:
			grilTag = tags[11];
			break;
		case R.id.item_chemo:
			grilTag = tags[12];
			break;
		case R.id.item_zuqiubaobei:
			grilTag = tags[13];
			break;
		case R.id.item_xingan:
			grilTag = tags[14];
			break;
		case R.id.item_youhuo:
			grilTag = tags[15];
			break;
		case R.id.item_qizhi:
			grilTag = tags[16];
			break;
		case R.id.item_shishang:
			grilTag = tags[17];
			break;
		case R.id.item_changfa:
			grilTag = tags[18];
			break;
		case R.id.item_duanfa:
			grilTag = tags[19];
			break;
		case R.id.item_gudianmeinv:
			grilTag = tags[20];
			break;
		case R.id.item_wangluomeinv:
			grilTag = tags[21];
			break;
		case R.id.item_feizhuliu:
			grilTag = tags[22];
			break;
		case R.id.item_quanbu:
			grilTag = tags[23];
			break;	
		default:
			grilTag = tags[23];
			break;
		}
		Intent intent = new Intent(getActivity(), BeautyDetailActivity.class);
		Bundle bundle = new Bundle();
		bundle.putString("GRILTAG", grilTag);
		intent.putExtras(bundle);
		getActivity().startActivity(intent);
		getActivity().overridePendingTransition(R.anim.abc_fade_in,
				R.anim.abc_fade_out);
		MobclickAgent.onEvent(getActivity(), "beauty_detail");
	}

//	@Override
//	public void onDestroyView() {
//		super.onDestroyView();
//		LunBoFragment f = (LunBoFragment) getFragmentManager()
//				.findFragmentById(R.id.lunbo_fragment);
//		if (f != null)
//			getFragmentManager().beginTransaction().remove(f).commitAllowingStateLoss();
//	}
}
