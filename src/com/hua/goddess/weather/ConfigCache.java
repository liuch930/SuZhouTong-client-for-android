package com.hua.goddess.weather;

import java.io.File;
import java.io.IOException;

import com.hua.goddess.GoddessApplication;
import com.hua.goddess.utils.FileUtils;
import com.hua.goddess.utils.NetUtil;

import android.text.TextUtils;
import android.util.Log;

public class ConfigCache {

	public static final int CONFIG_CACHE_MOBILE_TIMEOUT = 2 * 60 * 60 * 1000; // 2 hour mobile net
	public static final int CONFIG_CACHE_WIFI_TIMEOUT = 30 * 60 * 1000; // 30 minute wifi 

	public static String getUrlCache(String url) {
		if (TextUtils.isEmpty(url)) {
			return null;
		}

		File file = new File(GoddessApplication.getInstance().getCacheDir()
				+ File.separator + replaceUrlWithPlus(url));
		if (file.exists() && file.isFile()) {
			long expiredTime = System.currentTimeMillis() - file.lastModified();
			Log.i("lwp", url + ": expiredTime="+expiredTime/1000);
			// 1. in case the system time is incorrect (the time is turn back
			// long ago)
			// 2. when the network is invalid, you can only read the cache
			if (GoddessApplication.mNetWorkState != NetUtil.NETWORN_NONE
					&& expiredTime < 0) {
				return null;
			}
			//如果是wifi网络，则30分钟过期
			if (GoddessApplication.mNetWorkState == NetUtil.NETWORN_WIFI
					&& expiredTime > CONFIG_CACHE_WIFI_TIMEOUT) {
				return null;
				//如果是手机网络，则2个小时过期
			} else if (GoddessApplication.mNetWorkState == NetUtil.NETWORN_MOBILE
					&& expiredTime > CONFIG_CACHE_MOBILE_TIMEOUT) {
				return null;
			}
			try {
				String result = FileUtils.readTextFile(file);
				return result;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static void setUrlCache(String data, String url) {
		if (GoddessApplication.getInstance().getCacheDir() == null) {
			return;
		}
		// File dir = new File(BaseApplication.mSdcardDataDir);
		// if (!dir.exists() &&
		// Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED))
		// {
		// dir.mkdirs();
		// }
		File file = new File(GoddessApplication.getInstance().getCacheDir()
				+ File.separator + replaceUrlWithPlus(url));
		try {
			// 创建缓存数据到磁盘，就是创建文件
			FileUtils.writeTextFile(file, data);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	/**
	 * delete cahce file recursively
	 * 
	 * @param cacheFile
	 *            if null means clear cache function, or clear cache file
	 */
	public static void clearCache(File cacheFile) {
		if (cacheFile == null) {
			try {
				File cacheDir = GoddessApplication.getInstance().getCacheDir();
				if (cacheDir.exists()) {
					clearCache(cacheDir);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (cacheFile.isFile()) {
			cacheFile.delete();
		} else if (cacheFile.isDirectory()) {
			File[] childFiles = cacheFile.listFiles();
			for (int i = 0; i < childFiles.length; i++) {
				clearCache(childFiles[i]);
			}
		}
	}

	public static String replaceUrlWithPlus(String url) {
		// 1. 处理特殊字符
		// 2. 去除后缀名带来的文件浏览器的视图凌乱(特别是图片更需要如此类似处理，否则有的手机打开图库，全是我们的缓存图片)
		if (url != null) {
			return url.replaceAll("http://(.)*?/", "")
					.replaceAll("[.:/,%?&=]", "+").replaceAll("[+]+", "+");
		}
		return null;
	}
}
