package com.hua.goddess;

import android.app.Application;
import android.content.Context;
import cn.jpush.android.api.JPushInterface;

import com.hua.goddess.utils.NetUtil;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.umeng.analytics.MobclickAgent;

public class GoddessApplication extends Application {
	private static Application mApplication;
	public static int mNetWorkState;
	public static synchronized Application getInstance() {
		return mApplication;
	}
	
	public void onCreate() {
		super.onCreate();
		mApplication = this;
		mNetWorkState = NetUtil.getNetworkState(this);
		JPushInterface.setDebugMode(false); 	// 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);     		// 初始化 JPush
        MobclickAgent.openActivityDurationTrack(false);
		initImageLoader(getApplicationContext());
		
		MobclickAgent.setDebugMode(false); //打开测试模式
	}

	public static void initImageLoader(Context context) {
		// This configuration tuning is custom. You can tune every option, you
		// may tune some of them,
		// or you can create default configuration by
		// ImageLoaderConfiguration.createDefault(this);
		// method.
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				context).threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.discCacheFileNameGenerator(new Md5FileNameGenerator())
				.tasksProcessingOrder(QueueProcessingType.LIFO)
//				.writeDebugLogs() // Remove for release app
				.build();
		// Initialize ImageLoader with configuration.
		ImageLoader.getInstance().init(config);

	}
}