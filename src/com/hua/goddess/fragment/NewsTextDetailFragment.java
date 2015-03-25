package com.hua.goddess.fragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.hua.goddess.R;
import com.hua.goddess.base.communicate.GetNewsDetailInterface;
import com.hua.goddess.global.Globe;
import com.hua.goddess.utils.HtmlResolving;
import com.hua.goddess.utils.PreferencesUtils;
import com.hua.goddess.vo.NewsContentVo;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.nostra13.universalimageloader.core.assist.PauseOnScrollListener;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.umeng.analytics.MobclickAgent;

public class NewsTextDetailFragment extends BaseFragment implements
		OnClickListener {

	private String newsId;
	private View container_view;
	private LayoutInflater inflater;
	private LinearLayout container_lin;
	private ArrayList<NewsContentVo> content_list;
	private Handler handler = new Handler();
	private ImageButton back, comment;
	private ImageButton read_mode;
	private boolean readerMode; // 阅读模式（夜间，白天）
	private ImageButton change_text_size;
	public static final int TEXT_TYPE = 0;
	public static final int IMG_TYPE = 1;
	private int fontsize = 17; // 字体大小
	private PreferencesUtils pu;
	private LinearLayout menu_layout;
	private PopupWindow popupWindow;
	private SeekBar fontseek;
	private TextView text1;
	private MAdapter mAdapter;
	private ListView listView;
	private int width;
	private RelativeLayout title_bar;
	private TextView time_smallfont;
	private TextView news_title_name;
	private SlidingMenu sm;
	private FragmentActivity context;
	
	public NewsTextDetailFragment(String newsId,SlidingMenu sm) {
		this.newsId = newsId;
		this.sm = sm;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		context = getActivity();
		this.inflater = inflater;
		container_view = inflater.inflate(R.layout.fragment_container, null);
		container_lin = (LinearLayout) container_view
				.findViewById(R.id.container);
		// 取出屏幕的宽和高
		DisplayMetrics metric = new DisplayMetrics();
		context.getWindowManager().getDefaultDisplay().getMetrics(metric);
		width = metric.widthPixels;
		new getNewsDataThread().start();
		pu = new PreferencesUtils(context);
		readerMode = pu.getBoolean(Globe.READERMODE, false);
		fontsize = pu.getInt(Globe.FONTSIZE, 17); // 初始化文字大小
		return container_view;
	}

	private void initView() {
		View view = inflater.inflate(R.layout.news_text_detail, null);
		view.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT));
		listView = (ListView) view.findViewById(R.id.listview_text);
		listView.setOnScrollListener(new PauseOnScrollListener(imageLoader,
				false, true));
		View head_view = inflater.inflate(R.layout.fragment_container_headview,
				null);
		title_bar = (RelativeLayout) head_view.findViewById(R.id.title_bar);
		time_smallfont = (TextView) head_view.findViewById(R.id.time_smallfont);
		time_smallfont.setText(content_list.get(0).getContentList());
		((TextView) head_view.findViewById(R.id.auther_smallfon))
				.setText(content_list.get(1).getContentList());
		news_title_name = (TextView) head_view
				.findViewById(R.id.news_title_name);
		news_title_name.setText(content_list.get(2).getContentList());
		listView.addHeaderView(head_view);
		content_list.subList(0, 3).clear();
		mAdapter = new MAdapter(content_list);
		listView.setAdapter(mAdapter);
		// 初始化底部菜单
		menu_layout = (LinearLayout) view.findViewById(R.id.menu_layout);
		change_text_size = (ImageButton) view
				.findViewById(R.id.change_text_size);
		change_text_size.setOnClickListener(this);
		read_mode = (ImageButton) view.findViewById(R.id.read_mode);
		read_mode.setOnClickListener(this);
		back = (ImageButton) view.findViewById(R.id.back_img);
		back.setOnClickListener(this);
		comment = (ImageButton) view.findViewById(R.id.comment_img);
		comment.setOnClickListener(this);
		if (readerMode) {
			// 夜间模式
			readerModeNight();
		} else {
			readerMode();
		}
		container_lin.removeAllViews();
		container_lin.addView(view);
	}

	public void onResume() {
		super.onResume();
		MobclickAgent.onPageStart("NewsTextDetail"); // 统计页面
	}

	public void onPause() {
		super.onPause();
		MobclickAgent.onPageEnd("NewsTextDetail");
	}

	class getNewsDataThread extends Thread {
		@Override
		public void run() {
			try {
				String news_detaiol = GetNewsDetailInterface.getNetData(newsId);
				HtmlResolving hResolving = new HtmlResolving();
				content_list = hResolving.getNewsContent(news_detaiol);
				handler.post(new Runnable() {
					@Override
					public void run() {
						if (content_list != null && content_list.size() > 0) {
							initView();
						} else {
							Toast.makeText(context, R.string.no_net_data,
									Toast.LENGTH_SHORT).show();
						}
					}
				});
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.change_text_size: // 修改字体大小
			LinearLayout layout = (LinearLayout) inflater.inflate(
					R.layout.pop_text_size, null);
			popupWindow = new PopupWindow(context);
			popupWindow.setBackgroundDrawable(new BitmapDrawable());
			popupWindow.setWidth(context.getWindowManager()
					.getDefaultDisplay().getWidth());
			popupWindow.setHeight(context.getWindowManager()
					.getDefaultDisplay().getHeight() / 6);
			popupWindow.setAnimationStyle(R.style.AnimationPreview2);
			popupWindow.setOutsideTouchable(true);
			popupWindow.setFocusable(true);// 响应回退按钮事件
			popupWindow.setContentView(layout);

			int[] location = new int[2];
			v.getLocationOnScreen(location);
			popupWindow.showAtLocation(v.findViewById(R.id.change_text_size),
					Gravity.NO_GRAVITY, location[0],
					location[1] - popupWindow.getHeight());

			fontseek = (SeekBar) layout.findViewById(R.id.settings_font);
			fontseek.setMax(20);
			fontseek.setProgress(fontsize - 10);
			fontseek.setSecondaryProgress(0);
			text1 = (TextView) layout.findViewById(R.id.fontSub);
			text1.setText(fontsize + "");
			fontseek.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
				@Override
				public void onProgressChanged(SeekBar seekBar, int progress,
						boolean fromUser) {
					fontsize = progress + 10;
					text1.setText("" + fontsize);
					notifyAdapter();
				}

				@Override
				public void onStartTrackingTouch(SeekBar seekBar) {

				}

				@Override
				public void onStopTrackingTouch(SeekBar seekBar) {
					pu.putInt(Globe.FONTSIZE, fontsize);
				}
			});
			break;
		case R.id.read_mode:

			if (readerMode) {
				readerMode = false;
				notifyAdapter();
				readerMode();
				// 保存数据
				pu.putBoolean(Globe.READERMODE, false);
			} else {
				readerMode = true;
				notifyAdapter();
				readerModeNight();
				// 保存数据
				pu.putBoolean(Globe.READERMODE, true);
			}
			break;
		case R.id.back_img:
			context.finish();
			break;
		case R.id.comment_img:
			if (sm.isMenuShowing()) {
				sm.showMenu(false);
			} else {
				sm.showMenu(true);
			}
			break;
		}

	}

	private void notifyAdapter() {
		if (mAdapter != null)
			mAdapter.notifyDataSetChanged();

	}

	// 白天模式修改界面
	public void readerMode() {
		title_bar.setBackgroundColor(-1);
		listView.setBackgroundColor(-1); // #000000
		time_smallfont.setTextColor(-13421773);
		news_title_name.setTextColor(-13421773);
		read_mode.setImageResource(R.drawable.bottom_menu_mode_light1);
		menu_layout.setBackgroundColor(context.getResources().getColor(
				R.color.menu_bottom_bg));
	}

	// 夜间模式修改界面
	public void readerModeNight() {
		title_bar.setBackgroundColor(-13947856);
		listView.setBackgroundColor(-13947856); // #2b2c30
		time_smallfont.setTextColor(-7895161);
		news_title_name.setTextColor(-7895161);
		read_mode.setImageResource(R.drawable.bottom_menu_mode_light2);
		menu_layout.setBackgroundColor(-13947856);
	}

	public class MAdapter extends BaseAdapter {
		ArrayList<NewsContentVo> voList;

		public MAdapter(ArrayList<NewsContentVo> voList) {
			this.voList = voList;
		}

		@Override
		public int getCount() {
			return voList == null ? 0 : voList.size();
		}

		@Override
		public Object getItem(int position) {
			return voList == null ? null : voList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		public boolean isEnabled(int position) {
			return false;
		}

		public boolean areAllItemsEnabled() {
			return false;
		}

		@Override
		public int getItemViewType(int position) {
			NewsContentVo vo = (NewsContentVo) getItem(position);
			return vo.getIsImg();
		}

		@Override
		public int getViewTypeCount() {
			return 2;
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			final NewsContentVo vo = (NewsContentVo) getItem(position);
			int type = getItemViewType(position);// 获取当前位置对应的类别
			if (convertView == null) {
				switch (type) {
				case TEXT_TYPE:
					convertView = inflater.inflate(
							R.layout.news_content_textview, null);
					break;
				case IMG_TYPE:
					convertView = inflater.inflate(R.layout.news_content_image,
							null);
					break;
				}
			}
			if (vo != null) {
				switch (type) {
				case TEXT_TYPE:
					// 对应设置文字内容F
					TextView tv = (TextView) convertView
							.findViewById(R.id.content_textView1);
					String text = vo.getContentList();
					tv.setTextSize(fontsize);
					if (readerMode) {
						tv.setTextColor(-7895161);// #878787
					} else {
						tv.setTextColor(-13421773);
					}
					tv.setText(Html.fromHtml(text));
					tv.setMovementMethod(LinkMovementMethod.getInstance());
					break;
				case IMG_TYPE:

					// 加载图片
					ImageView iv = (ImageView) convertView
							.findViewById(R.id.content_imageView1);

					String url = vo.getContentList();
					if (vo.getContentList().startsWith("http:")) {
						url = vo.getContentList();
					} else {
						url = Globe.SUZHOU + vo.getContentList();
					}
					imageLoader.displayImage(url, iv, options,
							new ListImgLoadingListener());
					break;
				default:
					break;
				}
			}
			return convertView;
		}

	}

	private class ListImgLoadingListener extends SimpleImageLoadingListener {

		final List<String> displayedImages = Collections
				.synchronizedList(new LinkedList<String>());

		// int list_img_margin = context.getResources()
		// .getDimensionPixelOffset(R.dimen.activity_article_img_margin);

		@Override
		public void onLoadingComplete(String imageUri, View view,
				Bitmap loadedImage) {
			if (loadedImage != null) {
				// ImageView imageView = (ImageView) view;
				boolean firstDisplay = !displayedImages.contains(imageUri);
				if (firstDisplay) {
					// FadeInBitmapDisplayer.animate(imageView, 500);
					displayedImages.add(imageUri);
					int bmHeight = loadedImage.getHeight();
					int bmWidth = loadedImage.getWidth();
					LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
							android.widget.LinearLayout.LayoutParams.MATCH_PARENT,
							(bmHeight * width - 300) / bmWidth);
					lp.leftMargin = 100;
					lp.rightMargin = 100;
					lp.topMargin = 10;
					lp.bottomMargin = 10;
					view.setLayoutParams(lp);
				}
			}
		}
	}
}
