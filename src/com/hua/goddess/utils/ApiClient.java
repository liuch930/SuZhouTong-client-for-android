package com.hua.goddess.utils;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.hua.goddess.GoddessApplication;



public class ApiClient {
	public static String connServerForResult(String url) {
		if (NetUtil.getNetworkState(GoddessApplication.getInstance()) == NetUtil.NETWORN_NONE)
			return null;
		HttpGet httpRequest = new HttpGet(url);
		String strResult = "";
		try {
			// HttpClient对象
			HttpClient httpClient = new DefaultHttpClient();
			// 获得HttpResponse对象
			HttpResponse httpResponse = httpClient.execute(httpRequest);
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
				// 取得返回的数据
				strResult = EntityUtils.toString(httpResponse.getEntity());

		} catch (Exception e) {
			e.printStackTrace();
			// 出错会返回空数据，注意处理
		}
		return strResult; // 返回结果
	}
}
