package com.hua.goddess.base.net;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpResponse;

/**
 * 普通网络通信工具类
 * 主要用于获取文本类型的相应body的内容
 * @author hanchao
 * created by May 11, 2010 9:32:37 AM
 */

public class DefaultHttpConnect extends HttpConnect {

	private ParcelMap headers;
	private String responseBody = "";
	
	public DefaultHttpConnect(RequestParcelable requestParam) {
		super(requestParam);
	}

	@Override
	protected void onReceiveBodyStream(InputStream is,long contentLength) throws IOException {
		byte[] b = new byte[1024];
		ByteArrayOutputStream baos = new ByteArrayOutputStream();   
		
		int n=0;
		while((n = is.read(b))>0){
			baos.write(b,0,n);
		}
		
		byte[] bytes = baos.toByteArray();
		baos.close();

		responseBody = EncryptionUtil.decompress(bytes);
		
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


