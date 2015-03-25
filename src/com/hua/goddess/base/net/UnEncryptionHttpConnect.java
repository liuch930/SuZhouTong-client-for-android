package com.hua.goddess.base.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpResponse;

/**
 * 普通网络通信工具类---非加密方式
 * 主要用于获取文本类型的相应body的内容
 * @author hanchao
 * created by May 11, 2010 9:32:37 AM
 */

public class UnEncryptionHttpConnect extends HttpConnect {

	private ParcelMap headers;
	private String responseBody;
	
	public UnEncryptionHttpConnect(UnEncryptionRequestParcelable requestParam) {
		super(requestParam);
	}

	@Override
	protected void onReceiveBodyStream(InputStream is,long contentLength) throws IOException {
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader br = new BufferedReader(isr);
		String line;
		StringBuffer sb = new StringBuffer("");
		while((line = br.readLine())!=null){
			sb.append(line);
		}
		
		responseBody = sb.toString();
	}

	@Override
	protected void onReceiverHeaders(HttpResponse httpResponse) throws UnsupportedEncodingException {
		headers = getResponseHeaders(httpResponse);
	}

	public ParcelMap getHeaders() {
		return headers;
	}

	public String getResponseBody() {
		return responseBody;
	}
	
}


