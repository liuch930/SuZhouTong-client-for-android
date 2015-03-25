package com.hua.goddess.weather;

import java.net.URLEncoder;

import android.os.AsyncTask;
import android.os.Handler;
import android.text.TextUtils;

import com.hua.goddess.fragment.WeatherFragment;
import com.hua.goddess.global.Globe;
import com.hua.goddess.utils.ApiClient;
import com.hua.goddess.vo.WeatherInfo;

public class GetWeatherTask extends AsyncTask<Void, Void, Integer> {
	private static final String BASE_URL = "http://sixweather.3gpk.net/SixWeather.aspx?city=%s";
	private static final int SCUESS = 0;
	private static final int SCUESS_YUJING = 1;
	private static final int FAIL = -1;
	private Handler mHandler;
	private WeatherFragment wFragment;

	public GetWeatherTask(Handler handler,WeatherFragment wFragment) {
		this.mHandler = handler;
		this.wFragment = wFragment;
	}

	@Override
	protected Integer doInBackground(Void... params) {
		try {
			
			Thread.sleep(400);
			
			String url = String.format(BASE_URL,
					URLEncoder.encode("苏州", "utf-8"));

			// 读取文件中的缓存信息
			String fileResult = ConfigCache.getUrlCache("suzhou");// 读取文件中的缓存
			if (!TextUtils.isEmpty(fileResult)) {
				WeatherInfo allWeather = XmlPullParseUtil
						.parseWeatherInfo(fileResult);
				if (allWeather != null) {
					wFragment.SetAllWeather(allWeather);
					return SCUESS;
				}
			}
			// 最后才执行网络请求
			String netResult = ApiClient.connServerForResult(url);
			if (!TextUtils.isEmpty(netResult)) {
				WeatherInfo allWeather = XmlPullParseUtil
						.parseWeatherInfo(netResult);
				if (allWeather != null) {
					wFragment.SetAllWeather(allWeather);
					ConfigCache.setUrlCache(netResult, "suzhou");
					String yujin = allWeather.getYujing();
					if (!TextUtils.isEmpty(yujin) && !yujin.contains("暂无预警"))
						return SCUESS_YUJING;
					return SCUESS;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return FAIL;
	}

	@Override
	protected void onPostExecute(Integer result) {
		super.onPostExecute(result);
		if(result < 0 ){
			mHandler.sendEmptyMessage(Globe.GET_WEATHER_FAIL);// 获取天气信息失败
		}else{
			mHandler.sendEmptyMessage(Globe.GET_WEATHER_SCUESS);// 获取天气信息成功，通知主线程更新
		}
	}
}
